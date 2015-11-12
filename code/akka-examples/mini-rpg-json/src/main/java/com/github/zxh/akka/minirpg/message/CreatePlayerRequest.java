package com.github.zxh.akka.minirpg.message;

public class CreatePlayerRequest implements GameRequest {
    
    private final String playerName;

    public CreatePlayerRequest(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

}
