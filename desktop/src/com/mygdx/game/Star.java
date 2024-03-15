//Star.java
package com.mygdx.game;

public class Star extends Obstacle {
    public Star(float x, float y, float obstacleSpeed) {
        super(x, y, "star.jpeg");
        setMOVEMENT((int) obstacleSpeed); // Ensure MOVEMENT is not final and can be set
    }
}
