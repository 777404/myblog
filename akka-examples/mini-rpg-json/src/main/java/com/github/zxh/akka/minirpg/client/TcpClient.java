package com.github.zxh.akka.minirpg.client;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.io.Tcp;
import akka.io.Tcp.CommandFailed;
import akka.io.Tcp.Connected;
import akka.io.TcpMessage;
import com.github.zxh.akka.minirpg.message.GameRequest;
import com.github.zxh.akka.minirpg.message.GameResponse;
import com.github.zxh.akka.minirpg.message.MsgCodec;
import com.google.gson.Gson;
import java.net.InetSocketAddress;

public class TcpClient extends UntypedActor {

    @Override
    public void onReceive(Object msg) throws Exception {
        System.out.println("TcpClient received:" + msg);
        
        if (msg instanceof InetSocketAddress) {
            InetSocketAddress remote = (InetSocketAddress) msg;
            Tcp.get(getContext().system()).manager()
                    .tell(TcpMessage.connect(remote), getSelf());
        } else if (msg instanceof CommandFailed) {
            getContext().stop(getSelf());
        } else if (msg instanceof Connected) {
            final Props codecProps = Props.create(MsgCodec.class, getSender(), getSelf());
            final ActorRef codec = getContext().actorOf(codecProps, "codec");
            getSender().tell(TcpMessage.register(codec), getSelf());
        } else if (msg instanceof GameRequest) {
            getContext().child("codec").get().tell(msg, getSelf());
        } else if (msg instanceof GameResponse) {
            // todo
            System.out.println(new Gson().toJson(msg));
        }
    }
    
}
