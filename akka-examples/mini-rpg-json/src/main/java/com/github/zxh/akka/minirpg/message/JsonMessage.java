package com.github.zxh.akka.minirpg.message;

public class JsonMessage {
    
    private final int id;
    private final String json;

    public JsonMessage(int id, String json) {
        this.id = id;
        this.json = json;
    }

    public int getId() {
        return id;
    }

    public String getJson() {
        return json;
    }
    
}
