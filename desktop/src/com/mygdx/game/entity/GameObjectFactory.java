package com.mygdx.game.entity;

import com.mygdx.game.manager.AIManager;

public class GameObjectFactory {
    public static GameObject createGameObject(AIManager.GameObjectType type, float x, float y, float speed) {
        switch (type) {
            case FLYING_SAUCER:
                return new FlyingSaucer(x, y, speed);
            case METEORITE:
                return new Meteorite(x, y, speed);
            case SMALL_STAR:
                return new Star(x, y, "entity/smallstar.png", speed);
            case BIG_STAR:
                return new Star(x, y, "entity/bigstar.png", speed);
            default:
                throw new IllegalArgumentException("Invalid GameObject type: " + type);
        }
    }

}
