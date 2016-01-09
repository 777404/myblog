package com.github.zxh0.pbg2.gen;

import com.github.zxh0.pbg2.msg.Enum;
import com.github.zxh0.pbg2.msg.Message;

import java.lang.reflect.Constructor;

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
            MessageGen.genMessage(msg, buf);
        } else if (enumAnnotation != null) {
            // todo
        } else {
            throw new ProtoGenException("@Message or @Enum not annotated for class " + c);
        }

        return buf.toString();
    }

    private static void genEnum(Class<?> c) {

    }

}
