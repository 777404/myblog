package com.github.zxh0.pbg2.gen;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class EnumGen {

    public static void genEnum(Object enumObj, StringBuilder buf) {
        Class<?> c = enumObj.getClass();
        buf.append("enum ").append(c.getSimpleName()).append(" {\n");
        for (Field field : c.getDeclaredFields()) {
            if (field.getType() == int.class
                    && Modifier.isStatic(field.getModifiers())
                    && Modifier.isFinal(field.getModifiers())) {

                field.setAccessible(true);
                genField(field, buf);
            }
        }
        buf.append("}");
    }

    private static void genField(Field field, StringBuilder buf) {
        try {
            buf.append("    ");
            buf.append(field.getName());
            buf.append(" = ");
            buf.append(field.get(null));
            buf.append(";\n");
        } catch (ReflectiveOperationException e) {
            throw new ProtoGenException(e);
        }
    }

}
