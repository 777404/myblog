package com.github.zxh.akka.minirpg.server;

import akka.actor.UntypedActor;
import akka.io.Tcp.ConnectionClosed;
import akka.io.Tcp.Received;
import akka.util.ByteString;
import com.github.zxh.akka.minirpg.message.JsonMessage;

public class MsgCodec extends UntypedActor {

    private ByteString buf = ByteString.empty();
    
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
            // todo
        }
    }
    
    private void decodeMsg() {
        // todo
    }
    
    private void encodeMsg(JsonMessage msg) {
        
    }
    
}
