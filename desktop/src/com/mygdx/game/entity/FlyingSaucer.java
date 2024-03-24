package com.mygdx.game.entity;

public class FlyingSaucer extends Obstacle {
    public FlyingSaucer(float x, float y, float obstacleSpeed) {
        super(x, y, "entity/flying_saucer.png");
        setMOVEMENT((int) obstacleSpeed); // Ensure MOVEMENT is not final and can be set
    }
}
