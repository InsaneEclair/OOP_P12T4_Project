package com.mygdx.game.manager;

import com.mygdx.game.entity.Entity;
import java.util.ArrayList;
import java.util.List;
import com.mygdx.game.entity.GameObjectFactory;
import com.mygdx.game.screen.GameScreen;
import static com.badlogic.gdx.math.MathUtils.random;

public class EntityManager {
    private final List<Entity> entities = new ArrayList<>();

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void update(float delta) {
        for (Entity entity : new ArrayList<>(entities)) {
            entity.update(delta);
        }
    }

    public void spawnGameObject(AIManager.GameObjectType type, GameScreen gameScreen, float gameObjectSpeed) {
        float x = gameScreen.getViewportWidth();
        float y = determineYPosition(type, gameScreen);
        // Use passed gameObjectSpeed instead of aiManager.gameObjectSpeed
        Entity newEntity = GameObjectFactory.createEntity(type, x, y, gameObjectSpeed);
        addEntity(newEntity);
    }

    private float determineYPosition(AIManager.GameObjectType type,  GameScreen gameScreen) {
        float yPosition;
        float maxJumpHeight = gameScreen.calculateMaxJumpHeight();
        float maxHeightLimit = gameScreen.getViewportHeight() * 0.90f;
        switch (type) {
            case METEORITE:
                yPosition = gameScreen.getGroundYPosition();
                break;
            case FLYING_SAUCER:
                yPosition = gameScreen.getAirYPosition();
                break;
            case SMALL_STAR:
            case BIG_STAR:
                float minHeight = gameScreen.getGroundYPosition();
                float effectiveMaxHeight = Math.min(minHeight + maxJumpHeight, maxHeightLimit);
                yPosition = random.nextFloat() * (effectiveMaxHeight - minHeight) + minHeight;
                break;
            default:
                throw new IllegalStateException("Unexpected GameObjectType: " + type);
        }
        return yPosition;
    }
    public List<Entity> getEntities() {
        return entities;
    }
}