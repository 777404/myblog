package com.github.zxh.akka.helloworld;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {
    
    public static void main(String[] args) {
        ActorSystem mySystem = ActorSystem.create("mySystem");
        ActorRef myActor = mySystem.actorOf(Props.create(MyActor.class), "myActor");
        myActor.tell("Hello, World!", ActorRef.noSender());
        mySystem.shutdown();
    }
    
}
