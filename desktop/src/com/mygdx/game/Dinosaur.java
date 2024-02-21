package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Dinosaur {
    public Texture texture; //Texture being displayed
    private final Texture standingTexture; // When Standing
    public Texture duckingTexture; //  When ducking
    public Vector2 position;
    public Vector2 velocity;
    private static final int GRAVITY = -800;
    private static final int MOVEMENT = 0;
    public Rectangle bounds;
    private boolean isDucking = false;
    private int score;
    private final int groundLevel;

    public Dinosaur(int x, int groundLevel) {
        //position = new Vector2(x, 50);
        this.groundLevel = groundLevel; // Store the ground level
        position = new Vector2(x, groundLevel); // Adjusted position to stand on the land

        velocity = new Vector2(0, 0);
        standingTexture = new Texture("main-character1.png");
        duckingTexture = new Texture("main-character5.png");
        texture = standingTexture; // Default to standing texture
        bounds = new Rectangle(x, groundLevel, standingTexture.getWidth(), standingTexture.getHeight());

        score = 0;
    }
/* Not in use, either use this to increase score by deleting the current 1 or remove this
    public void increaseScore() {
        this.score++;
    }
*/
    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void update(float dt) {
        if (position.y > 0 || velocity.y > 0) {
            velocity.add(0, GRAVITY * dt);
        }

        position.add(MOVEMENT * dt, velocity.y * dt);

        if (position.y < groundLevel) {
            position.y = groundLevel; // Make sure the dinosaur lands on the ground
            velocity.y = 0; // Reset the vertical velocity
        }
        bounds.setPosition(position.x, position.y); // Ensure bounds are updated with position
    }

    public void jump() {
        if (position.y == groundLevel && !isDucking) { // Simple check to prevent double jumping
            velocity.y = 400; // Adjust the jump height as needed
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
    public void dispose() {
        texture.dispose();
    }
}