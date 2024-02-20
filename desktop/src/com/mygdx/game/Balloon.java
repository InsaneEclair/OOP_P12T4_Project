// Balloon.java
package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class Balloon extends EntityManager {
    private static final int BALLOON_OFFSET_Y = 1; // Adjust this value as needed
    private static final int BALLOON_OFFSET_X = 10; // Adjust this value as needed

    private boolean isVisible; // Flag to track if the balloon is visible

    public Balloon(float x, float y) {
        super(1, 2F, "balloon.jpg");
        position.set(x, 1); // Set initial position
        bounds.setPosition(x, y); // Update bounds
        isVisible = true; // Initially set the balloon as visible
    }

    // Update method to handle balloon movement and visibility
    public void update(float dt, Vector2 dinoPosition, boolean isDucking) {
        if (isVisible) {
            if (isDucking) {
                position.set(dinoPosition.x + BALLOON_OFFSET_X, dinoPosition.y + BALLOON_OFFSET_Y);
                bounds.setPosition(position.x, position.y);
            } else {
                // Move the balloon horizontally
                position.x += 1 * dt; // Adjust the movement speed as needed
                bounds.setPosition(position.x, position.y);

                // Check if the balloon is out of the screen
                if (position.x > 200 /*width of your game screen*/) {
                    isVisible = false; // Hide the balloon if it's out of the screen
                }
            }
        }
    }

    // Method to check if the balloon is visible
    public boolean isVisible() {
        return isVisible;
    }
}
