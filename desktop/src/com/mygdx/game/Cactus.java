package com.mygdx.game;

public class Cactus extends EntityManager {
    //public Rectangle bounds;
    public Cactus(float x, float y, String textureFileName, float speed) {
        super(x, y, textureFileName);
        this.speed = speed; // Assign speed to a class field or directly to the movement logic
    }
}
