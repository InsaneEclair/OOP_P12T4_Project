package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    private int movement = -150;
    private final Texture texture;
    private final Vector2 position;
    private final Rectangle bounds;
    private float speed;

    public Entity(float x, float y, String textureFileName) {
        texture = new Texture(Gdx.files.internal(textureFileName));
        position = new Vector2(x, y);
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public void update(float dt) {
        position.add(getMovement() * dt, 0);
        bounds.setPosition(position.x, position.y);
    }

    public void setMovement(int movement) {this.movement = movement;}

    public Rectangle getBounds() {
        return bounds;
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getSpeed() {return speed;}

    public void setSpeed(float speed) {this.speed = speed;}

    public void dispose() {
        texture.dispose();
    }

    public int getMovement() {
        return movement;
    }
}
