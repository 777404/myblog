package com.github.zxh.akka.minirpg.server;

import akka.actor.UntypedActor;
import com.github.zxh.akka.minirpg.domain.Player;
import com.github.zxh.akka.minirpg.message.CreatePlayerRequest;
import com.github.zxh.akka.minirpg.message.CreatePlayerResponse;
import java.util.ArrayList;
import java.util.List;

// game logic
public class MsgHandler extends UntypedActor {
    
    private final List<Player> players = new ArrayList<>();

    @Override
    public void onReceive(Object msg) throws Exception {
        System.out.println("MsgHandler received:" + msg);
        
        // todo
        if (msg instanceof CreatePlayerRequest) {
            int newPlayerId = createPlayer((CreatePlayerRequest) msg);
            getSender().tell(new CreatePlayerResponse(newPlayerId), getSelf());
        }
    }
    
    private int createPlayer(CreatePlayerRequest req) {
        int playerId = players.size() + 1;
        Player newPlayer = new Player();
        newPlayer.setId(playerId);
        newPlayer.setLevel(1);
        newPlayer.setName(req.getPlayerName());
        players.add(newPlayer);
        return playerId;
    }
    
}
