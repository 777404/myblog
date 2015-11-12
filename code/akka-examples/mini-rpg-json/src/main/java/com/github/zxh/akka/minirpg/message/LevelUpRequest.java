package com.github.zxh.akka.minirpg.message;

public class LevelUpRequest implements GameRequest {
    
    private final int playerId;

    public LevelUpRequest(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }
    
}
