package com.github.zxh.akka.minirpg.server;

import java.util.HashMap;
import java.util.Map;

public class MsgRegistry {
    
    private static final Map<Integer, Class<?>> msgById = new HashMap<>();
    private static final Map<Class<?>, Integer> idByMsg = new HashMap<>();
    static {
        // todo
    }
    
    public static Class<?> getMsgClass(int msgId) {
        return msgById.get(msgId);
    }
    
    public static int getMsgId(Class<?> msgClass) {
        return idByMsg.get(msgClass);
    }
    
}
