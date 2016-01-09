package com.github.zxh0.pbg2.gen;

import com.github.zxh0.pbg2.proto.Enum;
import com.github.zxh0.pbg2.proto.Message;
import com.github.zxh0.pbg2.proto.OptimizeForOption;
import com.github.zxh0.pbg2.proto.Proto;

import java.lang.reflect.Constructor;
import java.util.stream.Stream;

public class ProtoGen {

    public static String gen(Class<?> c) {
        Object annotation = checkAndGetAnnotation(c);
        StringBuilder buf = new StringBuilder();

        if (annotation instanceof Proto) {
            genProto(c, (Proto) annotation, buf);
        } else if (annotation instanceof Message) {
            genMessage(c, buf);
        } else if (annotation instanceof Enum) {
            genEnum(c, (Enum) annotation, buf);
        }

        return buf.toString();
    }

    private static Object checkAndGetAnnotation(Class<?> c) {
        Proto protoAnnotation = c.getAnnotation(Proto.class);
        Message msgAnnotation = c.getAnnotation(Message.class);
        Enum enumAnnotation = c.getAnnotation(Enum.class);

        Object[] annotations = Stream.of(protoAnnotation, msgAnnotation, enumAnnotation)
                .filter(a -> a != null)
                .toArray();
        if (annotations.length > 1) {
            throw new ProtoGenException("More than one of @Proto, @Message or @Enum " +
                    "annotated for class " + c);
        }
        if (annotations.length == 0) {
            throw new ProtoGenException("None of @Proto, @Message or @Enum " +
                    "annotated for class " + c);
        }

        return annotations[0];
    }

    private static void genProto(Class<?> c, Proto protoAnnotation, StringBuilder buf) {
        genOptions(protoAnnotation, buf);
        for (Class<?> innerClass : c.getDeclaredClasses()) {
            Object annotation = checkAndGetAnnotation(innerClass);
            if (annotation instanceof Message) {
                genMessage(innerClass, buf);
            } else if (annotation instanceof Enum) {
                genEnum(innerClass, (Enum) annotation, buf);
            } else {
                // todo
            }
        }
    }

    private static void genOptions(Proto annotation, StringBuilder buf) {
        boolean hasOption = false;
        if (!annotation.javaPackage().isEmpty()) {
            hasOption = true;
            buf.append("option java_package = \"")
                    .append(annotation.javaPackage())
                    .append("\";\n");
        }
        if (!annotation.javaOuterClassname().isEmpty()) {
            hasOption = true;
            buf.append("option java_outer_classname = \"")
                    .append(annotation.javaOuterClassname())
                    .append("\";\n");
        }
        if (annotation.optimizeFor() != OptimizeForOption.NOT_GIVEN) {
            hasOption = true;
            buf.append("option optimize_for = ")
                    .append(annotation.optimizeFor())
                    .append(";\n");
        }

        if (hasOption) {
            buf.append("\n");
        }
    }

    private static void genMessage(Class<?> c, StringBuilder buf) {
        try {
            Constructor<?> init = c.getDeclaredConstructor();
            init.setAccessible(true);
            Object msg = init.newInstance();
            MessageGen.genMessage(msg, buf);
        } catch (ReflectiveOperationException e) {
            throw new ProtoGenException(e);
        }
    }

    private static void genEnum(Class<?> c, Enum enumAnnotation, StringBuilder buf) {
        EnumGen.genEnum(c, enumAnnotation, buf);
    }

}
