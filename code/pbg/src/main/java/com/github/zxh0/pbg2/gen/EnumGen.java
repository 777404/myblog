package com.github.zxh0.pbg2.gen;

import com.github.zxh0.pbg2.msg.Enum;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class EnumGen {

    public static void genEnum(Object enumObj, Enum annotation, StringBuilder buf) {
        Class<?> enumClass = enumObj.getClass();
        buf.append("enum ").append(enumClass.getSimpleName()).append(" {\n");
        genOption(annotation, buf);
        genFields(enumClass, buf);
        buf.append("}");
    }

    private static void genOption(Enum annotation, StringBuilder buf) {
        if (annotation.allowAlias()) {
            buf.append("    option allow_alias = true;\n");
        }
    }

    private static void genFields(Class<?> enumClass, StringBuilder buf) {
        for (Field field : enumClass.getDeclaredFields()) {
            if (field.getType() == int.class
                    && Modifier.isStatic(field.getModifiers())
                    && Modifier.isFinal(field.getModifiers())) {

                field.setAccessible(true);
                genField(field, buf);
            }
        }
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
