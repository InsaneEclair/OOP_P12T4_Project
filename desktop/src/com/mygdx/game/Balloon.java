// Balloon.java
package com.mygdx.game;

public class Balloon extends EntityManager {

    public Balloon(float x, float y, float obstacleSpeed) {
        super(x, y, "balloon.jpg");
        MOVEMENT = (int) obstacleSpeed; // Ensure MOVEMENT is not final and can be set
    }
}

