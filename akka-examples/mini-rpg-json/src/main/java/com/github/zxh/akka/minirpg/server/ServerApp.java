package com.github.zxh.akka.minirpg.server;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ServerApp {
    
    public static void main(String[] args) {
        ActorSystem mySystem = ActorSystem.create("rpgServer");
        ActorRef msgHandler = mySystem.actorOf(Props.create(MsgHandler.class));
        ActorRef tcpServer = mySystem.actorOf(Props.create(TcpServer.class, msgHandler));
        tcpServer.tell(12345, ActorRef.noSender());
    }
    
}
