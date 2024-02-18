package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.audio.Sound;

public class Dinosaur {
    public Texture texture;
    public Vector2 position;
    public Vector2 velocity;
    private static final int GRAVITY = -50;
    private static final int MOVEMENT = 100;
    public Rectangle bounds;
    public Sound jumpSound;


    public Dinosaur(int x, int y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        texture = new Texture("main-character1.png"); // Make sure you have this texture in your assets
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump1.wav"));
    }

    public void update(float dt) {
        if (position.y > 0 || velocity.y > 0) {
            velocity.add(0, GRAVITY * dt);
        }
        
        
        position.add(MOVEMENT * dt, velocity.y * dt);

        if (position.y < 0) {
            position.y = 0;
            velocity.y = 0; // Reset velocity when on the ground
        }

        bounds.setPosition(position.x, position.y);
    }


    public void jump() {
        if (position.y == 0) { // Simple check to prevent double jumping
            velocity.y = 200; // Adjust the jump height as needed
            jumpSound.play();
        }
    }

    public void dispose() {
        texture.dispose();
    }
}
