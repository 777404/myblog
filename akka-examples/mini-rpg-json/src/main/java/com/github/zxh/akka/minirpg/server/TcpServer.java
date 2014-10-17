package com.github.zxh.akka.minirpg.server;

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
    
    private final ActorRef msgHandler;
    
    public TcpServer(ActorRef msgHandler) {
        this.msgHandler = msgHandler;
    }
    
    @Override
    public void onReceive(Object msg) throws Exception {
        System.out.println("TcpServer received:" + msg);
        
        if (msg instanceof Integer) {
            final int port = (Integer) msg;
            startServer(port);
        } else if (msg instanceof Bound) {
            getSender().tell(msg, getSelf());
        } else if (msg instanceof CommandFailed) {
            getContext().stop(getSelf());
        } else if (msg instanceof Connected) {
            final Connected conn = (Connected) msg;
            getSender().tell(conn, getSelf());
            registerCodec(getSender());
        }
    }
    
    private void startServer(int port) {
        final InetSocketAddress endpoint = new InetSocketAddress("localhost", port);
        final Object bindCmd = TcpMessage.bind(getSelf(), endpoint, 100);
        Tcp.get(getContext().system()).manager()
                .tell(bindCmd, getSelf());
    }
    
    private void registerCodec(ActorRef connection) {
        final Props codecProps = Props.create(MsgCodec.class, connection, msgHandler);
        final ActorRef codec = getContext().actorOf(codecProps);
        connection.tell(TcpMessage.register(codec), getSelf());
    }
    
}
