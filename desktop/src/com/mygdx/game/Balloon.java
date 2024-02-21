// Balloon.java
package com.mygdx.game;

public class Balloon extends EntityManager {


    public Balloon(float x, float y) {
        super(x, 95, "oldballoon.jpg");
    }

    // Adjust the position of the balloon based on the dinosaur's position
    public void update(float dt) {
//        if (isDucking) {
//            position.set(dinoPosition.x + BALLOON_OFFSET_X, dinoPosition.y + BALLOON_OFFSET_Y);
//            bounds.setPosition(position.x, position.y);
//        } else {
//            super.update(dt);
//        }
        position.add(MOVEMENT * dt, 0);
        bounds.setPosition(position.x, position.y);
    }
    }

