package com.github.zxh.akka.minirpg.server;

import com.github.zxh.akka.echoserver.*;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.io.Tcp;
import akka.io.Tcp.Bound;
import akka.io.Tcp.CommandFailed;
import akka.io.Tcp.Connected;
import akka.io.TcpMessage;
import java.net.InetSocketAddress;

public class TcpServer extends UntypedActor {

    private final ActorRef tcpManager;
    
    public TcpServer(ActorRef tcpManager) {
        this.tcpManager = tcpManager;
        
    }
    
    @Override
    public void onReceive(Object msg) throws Exception {
        System.out.println("TcpServer received:" + msg);
        
        if (msg instanceof Integer) {
            final int port = (Integer) msg;
            startServer(port);
        } else if (msg instanceof Bound) {
            tcpManager.tell(msg, getSelf());
        } else if (msg instanceof CommandFailed) {
            getContext().stop(getSelf());
        } else if (msg instanceof Connected) {
            final Connected conn = (Connected) msg;
            tcpManager.tell(conn, getSelf());
            final ActorRef handler = getContext().actorOf(Props.create(Handler.class));
            getSender().tell(TcpMessage.register(handler), getSelf());
        }
    }
    
    private void startServer(int port) {
        final InetSocketAddress endpoint = new InetSocketAddress("localhost", port);
        final Object bindCmd = TcpMessage.bind(getSelf(), endpoint, 100);
        Tcp.get(getContext().system())
                .getManager()
                .tell(bindCmd, getSelf());
    }
    
}
