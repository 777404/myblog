package com.github.zxh.akka.echoserver;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.io.Tcp;
import akka.util.ByteString;
import akka.util.ByteStringBuilder;
import java.util.Arrays;

public class Main {
    
    public static void main(String[] args) {
//        ActorSystem mySystem = ActorSystem.create("mySystem");
//        ActorRef tcpManager = Tcp.get(mySystem).getManager();
//        Props accepterProps = null;//Props.create(Accepter.class, tcpManager);
//        ActorRef accepter = mySystem.actorOf(accepterProps, "accepter");
//        accepter.tell(12345, ActorRef.noSender());
        
        ByteString bs = ByteString.empty();
        ByteString bs2 = bs.concat(ByteString.fromString("abc"));
        System.out.println(Arrays.toString(bs.toArray()));
        System.out.println(Arrays.toString(bs2.toArray()));
        
//        bs2.tak
        
        ByteStringBuilder bsb = new ByteStringBuilder();
        bsb.append(bs);
        
//        bsb.
    }
    
}
