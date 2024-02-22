// Balloon.java
package com.mygdx.game;

public class Balloon extends Obstacle {
    public Balloon(float x, float y, float obstacleSpeed) {
        super(x, y, "balloon.jpg");
        setMOVEMENT((int) obstacleSpeed); // Ensure MOVEMENT is not final and can be set
    }
}
