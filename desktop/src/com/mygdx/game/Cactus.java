package com.mygdx.game;

public class Cactus extends Obstacle {
    public Cactus(float x, float y, String textureFileName, float speed) {
        super(x, y, textureFileName);
        this.setSpeed(speed); // Assign speed to a class field or directly to the movement logic
    }
}
