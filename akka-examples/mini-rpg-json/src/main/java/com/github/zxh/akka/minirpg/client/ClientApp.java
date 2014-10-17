package com.github.zxh.akka.minirpg.client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import java.net.InetSocketAddress;

public class ClientApp {
    
    public static void main(String[] args) {
        ActorSystem mySystem = ActorSystem.create("miniRPG");
        ActorRef msgHandler = mySystem.actorOf(Props.create(MsgHandler.class));
        ActorRef tcpClient = mySystem.actorOf(Props.create(TcpClient.class));
        tcpClient.tell(new InetSocketAddress("localhost", 12345), ActorRef.noSender());
        
        String cmd;
        while (true) {
            cmd = System.console().readLine();
            // todo
        }
    }
    
}
