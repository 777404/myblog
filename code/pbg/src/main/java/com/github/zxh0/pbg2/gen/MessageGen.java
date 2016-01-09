package com.github.zxh0.pbg2.gen;

import com.github.zxh0.pbg2.proto.field.rules.Optional;
import com.github.zxh0.pbg2.proto.field.rules.Repeated;
import com.github.zxh0.pbg2.proto.field.rules.Required;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.stream.Stream;

public class MessageGen {

    public static void genMessage(Class<?> c, StringBuilder buf) {
        genMessage(c, buf, "");
    }

    public static void genMessage(Class<?> c, StringBuilder buf, String indentation) {
        try {
            Constructor<?> init = c.getDeclaredConstructor();
            init.setAccessible(true);
            Object msg = init.newInstance();
            MessageGen.genMessage(msg, buf, indentation);
        } catch (ReflectiveOperationException e) {
            throw new ProtoGenException(e);
        }
    }

    private static void genMessage(Object msg, StringBuilder buf, String indentation) {
        Class<?> c = msg.getClass();
        buf.append(indentation)
                .append("message ")
                .append(c.getSimpleName())
                .append(" {\n");
        genNestedTypes(c, buf, indentation);
        genFields(msg, buf, indentation);
        buf.append(indentation)
                .append("}");
    }

    private static void genNestedTypes(Class<?> c, StringBuilder buf, String indentation) {
        for (Class<?> innerClass : c.getDeclaredClasses()) {
            genMessage(innerClass, buf, indentation + "    ");
            buf.append("\n");
        }
    }

    private static void genFields(Object msg, StringBuilder buf, String indentation) {
        for (Field field : msg.getClass().getDeclaredFields()) {
            genField(msg, field, buf, indentation);
        }
    }

    private static void genField(Object msg, Field field, StringBuilder buf, String indentation) {
        Object rule = checkAndGetRule(field);
        Class<?> ruleClass = rule.getClass().getInterfaces()[0];
        buf.append(indentation)
                .append("    ")
                .append(ruleClass.getSimpleName().toLowerCase())
                .append(" ")
                .append(field.getType().getSimpleName().toLowerCase())
                .append(" ")
                .append(StringUtil.toSnakeCase(field.getName()))
                .append(" = ")
                .append(getTag(rule, ruleClass));

        if (rule instanceof Optional) {
            Object defaultValue = getDefaultValue(msg, field);
            if (defaultValue != null) {
                if (defaultValue instanceof String) {
                    defaultValue = StringUtil.quote((String) defaultValue);
                }
                buf.append(" [default = ")
                        .append(defaultValue)
                        .append("]");
            }
        }
        if (rule instanceof Repeated) {
            if (((Repeated) rule).packed()) {
                buf.append(" [packed = true]");
            }
        }

        buf.append(";\n");
    }

    private static Object checkAndGetRule(Field field) {
        Required required = field.getAnnotation(Required.class);
        Repeated repeated = field.getAnnotation(Repeated.class);
        Optional optional = field.getAnnotation(Optional.class);

        Object[] rules = Stream.of(required, repeated, optional)
                .filter(a -> a != null)
                .toArray();
        if (rules.length > 1) {
            throw new ProtoGenException("More than one of @Required, @Repeated or @Optional " +
                    "annotated for field " + field);
        }
        if (rules.length == 0) {
            throw new ProtoGenException("None of @Required, @Repeated or @Optional " +
                    "annotated for field " + field);
        }

        return rules[0];
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
            if (val != null) {
                Field defaultValue = val.getClass().getDeclaredField("defaultValue");
                defaultValue.setAccessible(true);
                return defaultValue.get(val);
            } else {
                return null;
            }
        } catch (ReflectiveOperationException e) {
            throw new ProtoGenException(e);
        }
    }

}
