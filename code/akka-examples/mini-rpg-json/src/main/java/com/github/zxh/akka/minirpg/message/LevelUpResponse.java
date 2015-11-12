package com.github.zxh.akka.minirpg.message;

public class LevelUpResponse implements GameResponse {
    
    private final int level;

    public LevelUpResponse(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
    
}
