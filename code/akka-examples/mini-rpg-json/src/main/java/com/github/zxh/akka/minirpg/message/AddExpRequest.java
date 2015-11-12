package com.github.zxh.akka.minirpg.message;

public class AddExpRequest implements GameRequest {
    
    private final int playerId;
    private final int exp;

    public AddExpRequest(int playerId, int exp) {
        this.playerId = playerId;
        this.exp = exp;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getExp() {
        return exp;
    }
    
}
