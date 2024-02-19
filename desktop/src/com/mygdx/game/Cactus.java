//package com.mygdx.game;
//
//public class Cactus extends EntityManager {
//    public Cactus(float x, float y, String textureFileName) {
//        super(x, y, textureFileName);
//    }
//}
package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

public class Cactus extends EntityManager {
    //public Rectangle bounds;
    public Cactus(float x, float y, String textureFileName) {
        super(x, y, textureFileName);
    }

    @Override
    public void update(float dt) {
        position.add(MOVEMENT * dt, 0);
        bounds.setPosition(position.x, position.y);
    }

    //public Rectangle getBounds(){return bounds;}
}
