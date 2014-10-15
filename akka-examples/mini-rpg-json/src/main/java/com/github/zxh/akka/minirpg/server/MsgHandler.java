package com.github.zxh.akka.minirpg.server;

import akka.actor.UntypedActor;

public class MsgHandler extends UntypedActor {

    @Override
    public void onReceive(Object msg) throws Exception {
        System.out.println("MsgHandler received:" + msg);
        
        // todo
    }
    
}
