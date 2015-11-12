package com.github.zxh.akka.helloworld;

import akka.actor.UntypedActor;

public class MyActor extends UntypedActor {

    @Override
    public void onReceive(Object msg) throws Exception {
        System.out.println(msg);
    }
    
}
