package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Dinosaur {
    private Texture texture; //Texture being displayed
    private final Texture standingTexture; // When Standing
    private final Texture standingTexture2;
    private final Texture duckingTexture; //  When ducking
    private final Texture duckingTexture2;
    private final Vector2 position;
    private final Vector2 velocity;
    private float modify_gravity = -800;
    private float jumpVelocity = 600;
    private static final int MOVEMENT = 0;
    private final Rectangle bounds;
    private boolean isDucking = false;
    private int score;
    private final int groundLevel;
    private float runningTimer = 0; // Timer to track animation progress
    private float duckingTimer = 0; // Timer to track animation progress

    private static final float RUNNING_ANIMATION_SPEED = 0.1f; // Adjust speed as needed (lower for faster animation)
    private static final float DUCKING_ANIMATION_SPEED = 0.1f; // Adjust speed as needed (lower for faster animation)


    public Dinosaur(int x, int groundLevel) {
        this.groundLevel = groundLevel; // Store the ground level
        position = new Vector2(x, groundLevel); // Adjusted position to stand on the land

        velocity = new Vector2(0, 0);
        standingTexture = new Texture("entity/main-character1_dark.png");
        standingTexture2 = new Texture("entity/main-character2_dark.png");
        duckingTexture = new Texture("entity/main-character5_dark.png");
        duckingTexture2 = new Texture("entity/main-character6_dark.png");
        texture = standingTexture; // Default to standing texture
        bounds = new Rectangle(x, groundLevel, standingTexture.getWidth(), standingTexture.getHeight());

        score = 0;
    }

    public void update(float dt) {
        if (position.y > 0 || velocity.y > 0) {
            velocity.add(0, modify_gravity * dt);
        }

        position.add(MOVEMENT * dt, velocity.y * dt);

        if (position.y < groundLevel) {
            position.y = groundLevel; // Make sure the dinosaur lands on the ground
            velocity.y = 0; // Reset the vertical velocity
        }
        bounds.setPosition(position.x, position.y); // Ensure bounds are updated with position

        // Update ducking animation timer if currently ducking
        if (isDucking) {
            runningTimer = 0;
            duckingTimer += dt;

            // Check if timer exceeds animation speed threshold
            if (duckingTimer >= DUCKING_ANIMATION_SPEED) {
                duckingTimer = 0; // Reset timer

                // Alternate between ducking textures
                if (texture == duckingTexture) {
                    texture = duckingTexture2;
                } else {
                    texture = duckingTexture;
                }
            }
        } else {
            duckingTimer = 0; // Reset timer if not ducking
            runningTimer += dt;

            // Check if timer exceeds animation speed threshold
            if (runningTimer >= RUNNING_ANIMATION_SPEED) {
                runningTimer = 0; // Reset timer

                // Alternate between standing textures
                if (texture == standingTexture) {
                    texture = standingTexture2;
                } else {
                    texture = standingTexture;
                }
            }
        }
    }

    public void jump() {
        if (position.y == groundLevel && !isDucking) {
            velocity.y = jumpVelocity;
        }
    }

    public void duck() {
        if (!isDucking) {
            texture = duckingTexture;
            isDucking = true;
            // Update bounds size to match the ducking texture
            bounds.setHeight(duckingTexture.getHeight());
            bounds.setWidth(duckingTexture.getWidth());
        }
    }

    public void standUp() {
        if (isDucking) {
            texture = standingTexture;
            isDucking = false;
            // Reset bounds size to match the standing texture
            bounds.setHeight(standingTexture.getHeight());
            bounds.setWidth(standingTexture.getWidth());
        }
    }

    public void setGravity(float newGravity) {
        this.modify_gravity = newGravity;
    }

    public void setJumpVelocity(float newJumpVelocity) {
        this.jumpVelocity = newJumpVelocity;
    }

    public void setScore(int score) {this.score = score;}

    public int getScore() {return score;}

    public boolean isDucking() { //public getter methods for player controller
        return isDucking;
    }

    public int getGroundLevel() { //public getter methods  for player controller
        return groundLevel;
    }

    public Texture getTexture() {
        return texture;
    }

    public Rectangle getBounds(){return bounds;}

    public Vector2 getPosition() {
        return position;
    }

    public void dispose() {
        texture.dispose();
        standingTexture.dispose();
        standingTexture2.dispose();
        duckingTexture.dispose();
        duckingTexture2.dispose();
    }
}