package com.github.zxh.akka.minirpg.message;

import java.util.HashMap;
import java.util.Map;

public class MsgRegistry {
    
    private static final Map<Integer, Class<?>> msgById = new HashMap<>();
    private static final Map<Class<?>, Integer> idByMsg = new HashMap<>();
    static {
        register(1, CreatePlayerRequest.class);
        register(2, CreatePlayerResponse.class);
    }
    
    private static void register(int msgId, Class<?> msgClass) {
        msgById.put(msgId, msgClass);
        idByMsg.put(msgClass, msgId);
    }
    
    public static Class<?> getMsgClass(int msgId) {
        return msgById.get(msgId);
    }
    
    public static int getMsgId(Class<?> msgClass) {
        return idByMsg.get(msgClass);
    }
    
    public static int getMsgId(Object msg) {
        return getMsgId(msg.getClass());
    }
    
}
