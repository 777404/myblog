package com.github.zxh0.pbg2.gen;

import com.github.zxh0.pbg2.proto.field.rules.Optional;
import com.github.zxh0.pbg2.proto.field.rules.Repeated;
import com.github.zxh0.pbg2.proto.field.rules.Required;

import java.lang.reflect.Field;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MessageGen {

    public static void genMessage(Object msg, StringBuilder buf) {
        Class<?> c = msg.getClass();
        buf.append("message ").append(c.getSimpleName()).append(" {\n");
        for (Field field : c.getDeclaredFields()) {
            genField(msg, field, buf);
        }
        buf.append("}");
    }

    private static void genField(Object msg, Field field, StringBuilder buf) {
        Object rule = checkAndGetRule(field);
        Class<?> ruleClass = rule.getClass().getInterfaces()[0];
        buf.append("    ")
                .append(ruleClass.getSimpleName().toLowerCase())
                .append(" ")
                .append(field.getType().getSimpleName().toLowerCase())
                .append(" ")
                .append(StringUtil.toSnakeCase(field.getName()))
                .append(" = ")
                .append(getTag(rule, ruleClass));

        Object defaultValue = getDefaultValue(msg, field);
        if (defaultValue != null) {
            if (defaultValue instanceof String) {
                defaultValue = StringUtil.quote((String) defaultValue);
            }
            buf.append(" [default = ").append(defaultValue).append("]");
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

}
