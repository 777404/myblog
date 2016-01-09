package com.github.zxh0.pbg2.gen;

import com.github.zxh0.pbg2.msg.Enum;
import com.github.zxh0.pbg2.msg.Message;
import com.github.zxh0.pbg2.msg.field.rules.Optional;
import com.github.zxh0.pbg2.msg.field.rules.Repeated;
import com.github.zxh0.pbg2.msg.field.rules.Required;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProtoGen {

    public static String gen(Class<?> msgClass) {
        try {
            Constructor<?> init = msgClass.getDeclaredConstructor();
            init.setAccessible(true);
            Object msgObj = init.newInstance();
            return gen(msgObj);
        } catch (ReflectiveOperationException e) {
            throw new ProtoGenException(e);
        }
    }

    private static String gen(Object msg) {
        StringBuilder buf = new StringBuilder();

        Class<?> c = msg.getClass();
        Message msgAnnotation = c.getAnnotation(Message.class);
        Enum enumAnnotation = c.getAnnotation(Enum.class);

        if (msgAnnotation != null && enumAnnotation != null) {
            throw new ProtoGenException("Both @Message and @Enum annotated for class " + c);
        } else if (msgAnnotation != null) {
            genMessage(msg, buf);
        } else if (enumAnnotation != null) {
            // todo
        } else {
            throw new ProtoGenException("@Message or @Enum not annotated for class " + c);
        }

        return buf.toString();
    }

    private static void genMessage(Object msg, StringBuilder buf) {
        Class<?> c = msg.getClass();
        buf.append("message ").append(c.getSimpleName()).append(" {\n");
        for (Field field : c.getDeclaredFields()) {
            genField(msg, field, buf);
        }
        buf.append("}");
    }

    private static void genField(Object msg, Field field, StringBuilder buf) {
        Required required = field.getAnnotation(Required.class);
        Repeated repeated = field.getAnnotation(Repeated.class);
        Optional optional = field.getAnnotation(Optional.class);

        Object[] rules = Stream.of(required, repeated, optional)
                .filter(a -> a != null)
                .toArray();
        if (rules.length > 1) {
            throw new ProtoGenException("More than one of @Required, @Repeated or @Optional annotated for field " + field);
        }
        if (rules.length == 1) {
            Object rule = rules[0];
            Class<?> ruleClass = rule.getClass().getInterfaces()[0];
            buf.append("    ")
                    .append(ruleClass.getSimpleName().toLowerCase())
                    .append(" ")
                    .append(field.getType().getSimpleName().toLowerCase())
                    .append(" ")
                    .append(toSnakeCase(field.getName()))
                    .append(" = ")
                    .append(getTag(rule, ruleClass));

            Object defaultValue = getDefaultValue(msg, field);
            if (defaultValue instanceof String) {
                // todo escape
                defaultValue = "\"" + defaultValue + "\"";
            }
            if (defaultValue != null) {
                buf.append(" [default = ").append(defaultValue).append("]");
            }

            buf.append(";\n");
        }
    }

    // fooBar => foo_bar
    private static String toSnakeCase(String fieldName) {
        return Pattern.compile("(?=[A-Z])").splitAsStream(fieldName)
                .map(word -> word.toLowerCase())
                .collect(Collectors.joining("_"));
    }

    private static int getTag(Object ruleObj, Class<?> ruleClass) {
        try {
            return (Integer) ruleClass.getMethod("tag").invoke(ruleObj);
        } catch (ReflectiveOperationException e) {
            throw new ProtoGenException(e);
        }
    }

    private static Object getDefaultValue(Object msg, Field field) {
        try {
            field.setAccessible(true);
            Object val = field.get(msg);
            if (val == null) {
                return null;
            } else {
                Field defaultValue = val.getClass().getDeclaredField("defaultValue");
                defaultValue.setAccessible(true);
                return defaultValue.get(val);
            }
        } catch (ReflectiveOperationException e) {
            throw new ProtoGenException(e);
        }
    }

    private static void genEnum(Class<?> c) {

    }

}
