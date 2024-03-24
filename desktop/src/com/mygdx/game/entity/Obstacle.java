package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Obstacle {
    private static int MOVEMENT = -150;
    private final Texture texture;
    private final Vector2 position;
    private final Rectangle bounds;
    private float speed;

    public Obstacle(float x, float y, String textureFileName) {
        texture = new Texture(Gdx.files.internal(textureFileName));
        position = new Vector2(x, y);
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public void update(float dt) {
        position.add(MOVEMENT * dt, 0);
        bounds.setPosition(position.x, position.y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public static void setMOVEMENT(int MOVEMENT) {
        Obstacle.MOVEMENT = MOVEMENT;
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void dispose() {
        texture.dispose();
    }

}
