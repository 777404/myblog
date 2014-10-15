package com.github.zxh.akka.minirpg.server;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.io.Tcp.ConnectionClosed;
import akka.io.Tcp.Received;
import akka.io.TcpMessage;
import akka.util.ByteIterator;
import akka.util.ByteString;
import akka.util.ByteStringBuilder;
import com.github.zxh.akka.minirpg.message.GameMessage;
import com.google.gson.Gson;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class MsgCodec extends UntypedActor {
    
    private static final Gson GSON = new Gson();
    
    private final ActorRef connection;
    private ByteString buf = ByteString.empty();

    public MsgCodec(ActorRef connection) {
        this.connection  = connection;
    }
    
    @Override
    public void onReceive(Object msg) throws Exception {
        System.out.println("MsgCodec received:" + msg);
        
        if (msg instanceof Received) {
            final ByteString data = ((Received) msg).data();
            buf = buf.concat(data);
            decodeMsg();
            //getSender().tell(msg, getSelf());
        } else if (msg instanceof ConnectionClosed) {
            getContext().stop(getSelf());
        } else if (msg instanceof GameMessage) {
            ByteString data = encodeMsg(msg);
            connection.tell(TcpMessage.write(data), getSelf());
        }
    }
    
    private void decodeMsg() {
        while (buf.length() > 4) {
            final ByteIterator it = buf.iterator();
            final int msgLength = it.getInt(ByteOrder.BIG_ENDIAN);
            if (buf.length() >= 4 + msgLength) {
                final int msgId = it.getInt(ByteOrder.BIG_ENDIAN);
                
            }
        }
    }
    
    private ByteString encodeMsg(Object msg) {
        final int msgId = MsgRegistry.getMsgId(msg);
        final byte[] jsonBytes = GSON.toJson(msg)
                .getBytes(StandardCharsets.UTF_8);
        
        final ByteStringBuilder bsb = new ByteStringBuilder();
        bsb.putInt(4 + jsonBytes.length, ByteOrder.BIG_ENDIAN);
        bsb.putInt(msgId, ByteOrder.BIG_ENDIAN);
        bsb.putBytes(jsonBytes);
        
        return bsb.result();
    }
    
}
