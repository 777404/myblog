package com.github.zxh.akka.minirpg.domain;

public class Player {
    
    private int id;
    private String name;
    private int exp;
    private int level;

    // Getters & Setters
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getExp() {return exp;}
    public void setExp(int exp) {this.exp = exp;}
    public int getLevel() {return level;}
    public void setLevel(int level) {this.level = level;}
    
    public void addExp(int val) {
        exp += val;
    }
    
    public void levelUp() {
        if (exp > 100) {
            exp -= 100;
            level++;
        }
    }
    
}
