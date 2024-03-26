package com.mygdx.game.entity;

public class FlyingSaucer extends Entity {
    public FlyingSaucer(float x, float y, float obstacleSpeed) {
        super(x, y, "entity/flying_saucer.png");
        setMovement((int) obstacleSpeed);
    }
}
