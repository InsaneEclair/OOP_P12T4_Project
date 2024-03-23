    package com.mygdx.game;

import java.util.Random;

public class AIManager {
    private final GameEngine gameEngine;
    private final GameScreen gameScreen;
    private final Random random;
    private float timeUntilNextObstacle;
    private final float minTimeBetweenObstacles;
    private final float maxTimeBetweenObstacles;
    private float obstacleSpeed = -150; // Initial speed of obstacles
    private int lastScoreIncrement = 0; // Keeps track of the last score increment

    public AIManager(GameEngine gameEngine, GameScreen gameScreen, float minTimeBetweenObstacles, float maxTimeBetweenObstacles) {
        this.gameEngine = gameEngine;
        this.gameScreen = gameScreen;
        this.minTimeBetweenObstacles = minTimeBetweenObstacles;
        this.maxTimeBetweenObstacles = maxTimeBetweenObstacles;
        this.random = new Random();
        resetObstacleTimer();
    }

    private void resetObstacleTimer() {
        timeUntilNextObstacle = getRandomSpawnTime();
    }

    private float getRandomSpawnTime() {
        return random.nextFloat() * (maxTimeBetweenObstacles - minTimeBetweenObstacles) + minTimeBetweenObstacles;
    }

    public void update(float delta) {
        int currentScore = gameEngine.getDinosaur().getScore();

        // Check if the score has reached a new 1000-point increment
        if (currentScore / 1000 > lastScoreIncrement) {
            lastScoreIncrement = currentScore / 1000; // Update the last increment tracker

            // Only increase speed if it does not exceed the maximum
            // Example max speed
            float MAX_OBSTACLE_SPEED = -300;
            if (obstacleSpeed > MAX_OBSTACLE_SPEED) {
                // The amount to decrease speed (making it more negative increases speed)
                float SPEED_INCREMENT = -20;
                obstacleSpeed += SPEED_INCREMENT; // Decrease speed (since speed is negative, adding a negative value increases its magnitude)
                gameScreen.playScoreUpSound();

                // Ensure speed does not exceed the maximum allowed speed
                obstacleSpeed = Math.max(obstacleSpeed, MAX_OBSTACLE_SPEED);
            }
        }

        // Your existing obstacle spawning logic...
        timeUntilNextObstacle -= delta;
        if (timeUntilNextObstacle <= 0) {
            spawnObstacle();
            resetObstacleTimer();
        }
    }

    private void spawnObstacle() {
        int obstacleType = random.nextInt(3); // Generate a random number between 0 and 2 (inclusive) for the obstacle type

        Obstacle newObstacle;
        switch (obstacleType) {
            case 0:
                newObstacle = new Meteorite(gameScreen.getViewportWidth(), gameScreen.getGroundYPosition(), obstacleSpeed);
                break;
            case 1:
                newObstacle = new FlyingSaucer(gameScreen.getViewportWidth(), gameScreen.getAirYPosition(), obstacleSpeed);
                break;
            case 2:
                float minHeight = gameScreen.getGroundYPosition();
                float maxHeight = gameScreen.getViewportHeight();
                float randomHeight = random.nextFloat() * (maxHeight - minHeight) + minHeight;
                String starTextureName = gameScreen.getStarTextures()[random.nextInt(gameScreen.getStarTextures().length)];
                newObstacle = new Star(gameScreen.getViewportWidth(), randomHeight, starTextureName, obstacleSpeed);
                break;
            default:
                throw new IllegalStateException("Unexpected obstacle type: " + obstacleType);
        }
        gameScreen.addObstacle(newObstacle);
    }
}