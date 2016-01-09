package com.github.zxh0.pbg2.gen;

import com.github.zxh0.pbg2.proto.Enum;
import com.github.zxh0.pbg2.proto.Message;
import com.github.zxh0.pbg2.proto.Proto;

import java.lang.reflect.Constructor;
import java.util.stream.Stream;

public class ProtoGen {

    public static String gen(Class<?> c) {
        Object annotation = checkAndGetAnnotation(c);
        StringBuilder buf = new StringBuilder();

        if (annotation instanceof Proto) {
            genProto(c, buf);
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

    private static void genProto(Class<?> c, StringBuilder buf) {
        c.getDeclaredClasses();
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
