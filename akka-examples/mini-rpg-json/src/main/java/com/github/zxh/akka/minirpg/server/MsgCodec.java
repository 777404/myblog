package com.github.zxh.akka.minirpg.server;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.io.Tcp.ConnectionClosed;
import akka.io.Tcp.Received;
import akka.io.TcpMessage;
import akka.util.ByteIterator;
import akka.util.ByteString;
import akka.util.ByteStringBuilder;
import com.github.zxh.akka.minirpg.message.JsonMessage;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MsgCodec extends UntypedActor {


    
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
        } else if (msg instanceof JsonMessage) {
            ByteString data = encodeMsg((JsonMessage) msg);
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
    
    private ByteString encodeMsg(JsonMessage msg) {
        byte[] jsonBytes = msg.getJson().getBytes(StandardCharsets.UTF_8);
        
        ByteStringBuilder bsb = new ByteStringBuilder();
        bsb.putInt(4 + jsonBytes.length, ByteOrder.BIG_ENDIAN);
        bsb.putInt(msg.getId(), ByteOrder.BIG_ENDIAN);
        bsb.putBytes(jsonBytes);
        
        return bsb.result();
    }
    
}
