package com.github.zxh0.pbg2.gen;

import com.github.zxh0.pbg2.msg.Enum;
import com.github.zxh0.pbg2.msg.Message;

import java.lang.reflect.Constructor;

public class ProtoGen {

    public static String gen(Class<?> msgOrEnumClass) {
        try {
            Constructor<?> init = msgOrEnumClass.getDeclaredConstructor();
            init.setAccessible(true);
            Object msgOrEnum = init.newInstance();
            return gen(msgOrEnum);
        } catch (ReflectiveOperationException e) {
            throw new ProtoGenException(e);
        }
    }

    private static String gen(Object msgOrEnum) {
        StringBuilder buf = new StringBuilder();

        Class<?> c = msgOrEnum.getClass();
        Message msgAnnotation = c.getAnnotation(Message.class);
        Enum enumAnnotation = c.getAnnotation(Enum.class);

        if (msgAnnotation != null && enumAnnotation != null) {
            throw new ProtoGenException("Both @Message and @Enum annotated for class " + c);
        } else if (msgAnnotation != null) {
            MessageGen.genMessage(msgOrEnum, buf);
        } else if (enumAnnotation != null) {
            EnumGen.genEnum(msgOrEnum, buf);
        } else {
            throw new ProtoGenException("@Message or @Enum not annotated for class " + c);
        }

        return buf.toString();
    }

}
