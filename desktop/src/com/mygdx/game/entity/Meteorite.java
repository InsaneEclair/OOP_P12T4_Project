package com.mygdx.game.entity;

public class Meteorite extends GameObject {
    public Meteorite(float x, float y, float obstacleSpeed) {
        super(x, y, "entity/meteorite.png");
        setMOVEMENT((int) obstacleSpeed); // Ensure MOVEMENT is not final and can be set
    }
}
