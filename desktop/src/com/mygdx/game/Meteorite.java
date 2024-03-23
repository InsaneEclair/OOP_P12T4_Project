package com.mygdx.game;

public class Meteorite extends Obstacle {
    public Meteorite(float x, float y, float obstacleSpeed) {
        super(x, y,"meteorite.png");
        setMOVEMENT((int) obstacleSpeed); // Ensure MOVEMENT is not final and can be set
    }
}
