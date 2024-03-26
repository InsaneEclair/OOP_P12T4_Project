package com.mygdx.game.entity;

public class Meteorite extends Entity {
    public Meteorite(float x, float y, float obstacleSpeed) {
        super(x, y, "entity/meteorite.png");
        setMovement((int) obstacleSpeed);
    }
}
