package com.github.zxh.akka.minirpg.server;

import akka.actor.UntypedActor;
import com.github.zxh.akka.minirpg.domain.Player;
import com.github.zxh.akka.minirpg.domain.PlayerInfo;
import com.github.zxh.akka.minirpg.message.AddExpRequest;
import com.github.zxh.akka.minirpg.message.AddExpResponse;
import com.github.zxh.akka.minirpg.message.CreatePlayerRequest;
import com.github.zxh.akka.minirpg.message.CreatePlayerResponse;
import com.github.zxh.akka.minirpg.message.GetPlayerInfoRequest;
import com.github.zxh.akka.minirpg.message.GetPlayerInfoResponse;
import com.github.zxh.akka.minirpg.message.LevelUpRequest;
import com.github.zxh.akka.minirpg.message.LevelUpResponse;
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
        } else if (msg instanceof AddExpRequest) {
            int newExp = addExpToPlayer((AddExpRequest) msg);
            getSender().tell(new AddExpResponse(newExp), getSelf());
        } else if (msg instanceof LevelUpRequest) {
            int newLevel = levelUpPlayer((LevelUpRequest) msg);
            getSender().tell(new LevelUpResponse(newLevel), getSelf());
        } else if (msg instanceof GetPlayerInfoRequest) {
            PlayerInfo playerInfo = getPlayerInfo((GetPlayerInfoRequest) msg);
            getSender().tell(new GetPlayerInfoResponse(playerInfo), getSelf());
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
    
    private int addExpToPlayer(AddExpRequest req) {
        Player player = players.get(req.getPlayerId());
        player.addExp(req.getExp());
        return player.getExp();
    }
    
    private int levelUpPlayer(LevelUpRequest req) {
        Player player = players.get(req.getPlayerId());
        player.levelUp();
        return player.getLevel();
    }
    
    private PlayerInfo getPlayerInfo(GetPlayerInfoRequest req) {
        Player player = players.get(req.getPlayerId());
        return new PlayerInfo(player.getId(), player.getName(),
                player.getExp(), player.getLevel());
    }
    
}
