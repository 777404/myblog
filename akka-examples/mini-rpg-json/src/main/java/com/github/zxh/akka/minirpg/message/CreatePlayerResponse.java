package com.github.zxh.akka.minirpg.message;

public class CreatePlayerResponse implements GameResponse {
    
    private final int playerId;

    public CreatePlayerResponse(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }
    
}
