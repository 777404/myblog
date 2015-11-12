package com.github.zxh.akka.minirpg.message;

public class GetPlayerInfoRequest implements GameRequest {
    
    private final int playerId;

    public GetPlayerInfoRequest(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }
    
}
