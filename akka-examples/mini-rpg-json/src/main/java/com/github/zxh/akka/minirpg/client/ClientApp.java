package com.github.zxh.akka.minirpg.client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.github.zxh.akka.minirpg.message.MsgRegistry;
import com.google.gson.Gson;
import java.io.Console;
import java.net.InetSocketAddress;

public class ClientApp {
    
    public static void main(String[] args) {
        ActorSystem mySystem = ActorSystem.create("miniRPG");
        ActorRef tcpClient = mySystem.actorOf(Props.create(TcpClient.class));
        tcpClient.tell(new InetSocketAddress("localhost", 12345), ActorRef.noSender());
        
        while (true) {
            Object msg = parseCmd();
            if (msg != null) {
                tcpClient.tell(msg, ActorRef.noSender());
            }
        }
    }
    
    private static Object parseCmd() {
        Console console = System.console();
        String cmd = console.readLine().trim();
        String[] args = cmd.trim().split("\\s+");
        if (args.length != 3) {
            console.printf("Bad cmd: %s", cmd);
            return null;
        }
        
        try {
            int msgId = Integer.parseInt(args[1]);
            return new Gson().fromJson(args[2], MsgRegistry.getMsgClass(msgId));
        } catch (Exception e) { // todo
            console.writer().println(e.getMessage());
            return null;
        }
    }
    
}
