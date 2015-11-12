package com.github.zxh.akka.minirpg.domain;

import com.github.zxh.akka.minirpg.message.*;

public class PlayerInfo implements GameResponse {
    
    private final int id;
    private final String name;
    private final int exp;
    private final int level;

    public PlayerInfo(int id, String name, int exp, int level) {
        this.id = id;
        this.name = name;
        this.exp = exp;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getExp() {
        return exp;
    }

    public int getLevel() {
        return level;
    }
    
}
