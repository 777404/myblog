package com.github.zxh.akka.echoserver;

import akka.actor.UntypedActor;
import akka.io.Tcp.ConnectionClosed;
import akka.io.Tcp.Received;
import akka.io.TcpMessage;
import akka.util.ByteString;

public class Handler extends UntypedActor {

    @Override
    public void onReceive(Object msg) throws Exception {
        System.out.println("Handler received:" + msg);
        
        if (msg instanceof Received) {
            final ByteString data = ((Received) msg).data();
            getSender().tell(TcpMessage.write(data), getSelf());
        } else if (msg instanceof ConnectionClosed) {
            getContext().stop(getSelf());
        }
    }
    
}
