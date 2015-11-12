package com.github.zxh.akka.minirpg.message;

public class AddExpResponse implements GameResponse {
    
    private final int exp;

    public AddExpResponse(int exp) {
        this.exp = exp;
    }

    public int getExp() {
        return exp;
    }

}
