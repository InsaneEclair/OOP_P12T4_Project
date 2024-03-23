// Balloon.java
package com.mygdx.game;

public class FlyingSaucer extends Obstacle {
    public FlyingSaucer(float x, float y, float obstacleSpeed) {
        super(x, y, "flying_saucer.png");
        setMOVEMENT((int) obstacleSpeed); // Ensure MOVEMENT is not final and can be set
    }
}
