package com.github.zxh.akka.echoserver;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.io.Tcp;

public class Main {
    
    public static void main(String[] args) {
        ActorSystem mySystem = ActorSystem.create("mySystem");
        ActorRef tcpManager = Tcp.get(mySystem).getManager();
        Props accepterProps = Props.create(Accepter.class, tcpManager);
        ActorRef accepter = mySystem.actorOf(accepterProps, "accepter");
        accepter.tell(12345, ActorRef.noSender());
    }
    
}
