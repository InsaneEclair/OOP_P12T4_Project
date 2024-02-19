package com.mygdx.game;

public class Cloud extends EntityManager {
    public Cloud(float x, float y) {
        super(x, y, "cloud.jpg"); // Specify the texture file for this cloud obstacle
    }

    @Override
    public void update(float dt) {
        position.add(MOVEMENT * dt, 0);
        bounds.setPosition(position.x, position.y);
    }
}
