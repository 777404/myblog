package com.github.zxh.akka.minirpg.client;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.io.Tcp;
import akka.io.Tcp.CommandFailed;
import akka.io.Tcp.Connected;
import akka.io.Tcp.ConnectionClosed;
import akka.io.Tcp.Received;
import akka.io.TcpMessage;
import akka.japi.Procedure;
import akka.util.ByteString;
import java.net.InetSocketAddress;

public class TcpClient extends UntypedActor {

    private final ActorRef tcpManager;

    public TcpClient() {
        tcpManager = Tcp.get(getContext().system()).manager();
    }
    
    @Override
    public void onReceive(Object msg) throws Exception {
        System.out.println("TcpClient received " + msg);
        
        if (msg instanceof InetSocketAddress) {
            InetSocketAddress remote = (InetSocketAddress) msg;
            tcpManager.tell(TcpMessage.connect(remote), getSelf());
        } else if (msg instanceof CommandFailed) {
            getContext().stop(getSelf());
        } else if (msg instanceof Connected) {
            getSender().tell(TcpMessage.register(getSelf()), getSelf());
            getContext().become(connected(getSender()));
        }
    }
    
    private Procedure<Object> connected(final ActorRef connection) {
        return (Object msg) -> {
            if (msg instanceof ByteString) {
                connection.tell(TcpMessage.write((ByteString) msg), getSelf());
            } else if (msg instanceof CommandFailed) {
                // OS kernel socket buffer was full
            } else if (msg instanceof Received) {
                //listener.tell(((Received) msg).data(), getSelf());
            } else if (msg.equals("close")) {
                connection.tell(TcpMessage.close(), getSelf());
            } else if (msg instanceof ConnectionClosed) {
                getContext().stop(getSelf());
            }
        };
    }
    
}
