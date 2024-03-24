package com.mygdx.game.manager;

import com.mygdx.game.GameEngine;
import com.mygdx.game.entity.GameObject;
import com.mygdx.game.entity.GameObjectFactory;
import com.mygdx.game.screen.GameScreen;

import java.util.Random;

public class AIManager {
    private final GameEngine gameEngine;
    private final GameScreen gameScreen;
    private final Random random;
    private float timeUntilNextObject;
    private final float minTimeBetweenObjects;
    private final float maxTimeBetweenObjects;
    private float gameObjectSpeed = -150; // Initial speed of GameObjects
    private int lastScoreIncrement = 0; // Keeps track of the last score increment

    public enum GameObjectType {
        METEORITE,
        FLYING_SAUCER,
        SMALL_STAR,
        BIG_STAR
    }

    public AIManager(GameEngine gameEngine, GameScreen gameScreen, float minTimeBetweenObjects, float maxTimeBetweenObjects) {
        this.gameEngine = gameEngine;
        this.gameScreen = gameScreen;
        this.minTimeBetweenObjects = minTimeBetweenObjects;
        this.maxTimeBetweenObjects = maxTimeBetweenObjects;
        this.random = new Random();
        resetObjectTimer();
    }

    private void resetObjectTimer() {
        timeUntilNextObject = getRandomSpawnTime();
    }

    private float getRandomSpawnTime() {
        return random.nextFloat() * (maxTimeBetweenObjects - minTimeBetweenObjects) + minTimeBetweenObjects;
    }

    public void update(float delta) {
        int currentScore = gameEngine.getDinosaur().getScore();

        // Check if the score has reached a new 1000-point increment
        if (currentScore / 1000 > lastScoreIncrement) {
            lastScoreIncrement = currentScore / 1000; // Update the last increment tracker

            // Only increase speed if it does not exceed the maximum
            // Example max speed
            float MAX_GAME_OBJECT_SPEED = -300;
            if (gameObjectSpeed > MAX_GAME_OBJECT_SPEED) {
                // The amount to decrease speed (making it more negative increases speed)
                float SPEED_INCREMENT = -20;
                gameObjectSpeed += SPEED_INCREMENT; // Decrease speed (since speed is negative, adding a negative value increases its magnitude)
                gameScreen.playScoreUpSound();

                // Ensure speed does not exceed the maximum allowed speed
                gameObjectSpeed = Math.max(gameObjectSpeed, MAX_GAME_OBJECT_SPEED);
            }
        }

        timeUntilNextObject -= delta;
        if (timeUntilNextObject <= 0) {
            spawnGameObject();
            resetObjectTimer();
        }
    }

    private void spawnGameObject() {
        GameObjectType type = GameObjectType.values()[random.nextInt(GameObjectType.values().length)];
        float x = gameScreen.getViewportWidth();  // Assuming x position for new object
        float y = determineYPosition(type);
        float speed = gameObjectSpeed;

        GameObject newGameObject = GameObjectFactory.createGameObject(type, x, y, speed);
        gameScreen.addGameObject(newGameObject);
    }

    private float determineYPosition(GameObjectType type) {
        float yPosition;
        switch (type) {
            case METEORITE:
                yPosition = gameScreen.getGroundYPosition();
                break;
            case FLYING_SAUCER:
                yPosition = gameScreen.getAirYPosition();
                break;
            case SMALL_STAR:
            case BIG_STAR:
                // Calculate a random height within the viewport for both small and big stars
                float minHeight = gameScreen.getGroundYPosition();
                float maxHeight = gameScreen.getViewportHeight();
                yPosition = random.nextFloat() * (maxHeight - minHeight) + minHeight;
                break;
            default:
                throw new IllegalStateException("Unexpected GameObjectType: " + type);
        }
        return yPosition;
    }
}