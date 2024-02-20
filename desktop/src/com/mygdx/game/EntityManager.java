package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class EntityManager {
    public static final int MOVEMENT = -100;
    protected Texture texture;
    protected Vector2 position;
    protected Rectangle bounds;

    public EntityManager(float x, float y, String textureFileName) {

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
    public void dispose() {
        texture.dispose();
    }


}

//package com.mygdx.game;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector2;
//
//public class EntityManager {
//    public static final int MOVEMENT = -100;
//    public Texture texture;
//    public Vector2 position;
//    public Rectangle bounds;
//
//    public EntityManager(float x, float y, String textureFileName) {
//        texture = new Texture(Gdx.files.internal(textureFileName));
//        position = new Vector2(x, y);
//        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
//    }
//
//    public void update(float dt) {
//        position.add(MOVEMENT * dt, 0);
//        bounds.setPosition(position.x, position.y);
//    }
//
//    public void dispose() {
//        texture.dispose();
//    }
//}
//
//
