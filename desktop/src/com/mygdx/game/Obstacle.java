package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Obstacle {
    public Texture texture;
    public Vector2 position;
    public Rectangle bounds;

    public Obstacle(float x, float y) {
        texture = new Texture("cactus1.png"); // Make sure you have this texture in your assets
        position = new Vector2(x, y);
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public void dispose() {
        texture.dispose();
    }
}
