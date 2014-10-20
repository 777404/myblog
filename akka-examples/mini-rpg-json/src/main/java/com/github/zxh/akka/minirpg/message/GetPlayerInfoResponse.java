package com.github.zxh.akka.minirpg.message;

import com.github.zxh.akka.minirpg.domain.PlayerInfo;

public class GetPlayerInfoResponse implements GameResponse {
    
    private final PlayerInfo playerInfo;

    public GetPlayerInfoResponse(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

}
