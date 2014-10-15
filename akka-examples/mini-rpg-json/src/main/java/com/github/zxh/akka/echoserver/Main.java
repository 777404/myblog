package com.github.zxh.akka.echoserver;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.io.Tcp;
import akka.util.ByteIterator;
import akka.util.ByteString;
import akka.util.ByteStringBuilder;
import java.nio.ByteOrder;
import java.util.Arrays;

public class Main {
    
    public static void main(String[] args) {
//        ActorSystem mySystem = ActorSystem.create("mySystem");
//        ActorRef tcpManager = Tcp.get(mySystem).getManager();
//        Props accepterProps = null;//Props.create(Accepter.class, tcpManager);
//        ActorRef accepter = mySystem.actorOf(accepterProps, "accepter");
//        accepter.tell(12345, ActorRef.noSender());
        
//        ByteStringBuilder bsb = new ByteStringBuilder();
//        bsb.putInt(2, ByteOrder.BIG_ENDIAN);
//        bsb.putInt(3, ByteOrder.BIG_ENDIAN);
//        bsb.putInt(4, ByteOrder.BIG_ENDIAN);
//        
//        ByteString bs = bsb.result();
//        System.out.println(bs.take(4));
//        bs.drop(4);
//        System.out.println(bs.take(4));
//        System.out.println(bs.length());
//        
//        ByteIterator bi = bs.iterator();
//        System.out.println(bi.getInt(ByteOrder.BIG_ENDIAN));
//        System.out.println(bi.getInt(ByteOrder.BIG_ENDIAN));
//        System.out.println(bi.getInt(ByteOrder.BIG_ENDIAN));
//        
//        System.out.println(bs.take(4));
//        System.out.println(bs.length());
        
        ByteString bs = ByteString.fromString("abcdefg");
        System.out.println(new String(bs.slice(1, 3).toArray()));
    }
    
}
