// Balloon.java
package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class Balloon extends EntityManager {
    private static final int BALLOON_OFFSET_Y = 1; // Adjust this value as needed
    private static final int BALLOON_OFFSET_X = 10; // Adjust this value as needed

    public Balloon(float x, float y) {
        super(x, 3.5F, "balloon.jpg");
    }

    // Adjust the position of the balloon based on the dinosaur's position
    public void update(float dt, Vector2 dinoPosition, boolean isDucking) {
        if (isDucking) {
            position.set(dinoPosition.x + BALLOON_OFFSET_X, dinoPosition.y + BALLOON_OFFSET_Y);
            bounds.setPosition(position.x, position.y);
        } else {
            super.update(dt);
        }
    }
}
