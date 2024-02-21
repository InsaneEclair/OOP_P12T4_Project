package com.mygdx.game;

import java.util.Random;

public class AIManager {
    private final GameScreen gameScreen;
    private final Random random;
    private float timeUntilNextObstacle;
    private final float minTimeBetweenObstacles;
    private final float maxTimeBetweenObstacles;
    private float obstacleSpeed = -150; // Initial speed of obstacles
    private int lastScoreIncrement = 0; // Keeps track of the last score increment

    public AIManager(GameScreen gameScreen, float minTimeBetweenObstacles, float maxTimeBetweenObstacles) {
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
        int currentScore = gameScreen.dinosaur.getScore(); // Assumes there's a way to get the current score from GameScreen

        // Check if the score has reached a new 200-point increment
        if (currentScore / 200 > lastScoreIncrement) {
            lastScoreIncrement = currentScore / 200; // Update the last increment tracker

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
        int obstacleType = random.nextInt(gameScreen.getObstacleTextures().length + 1);

        Obstacle newObstacle;
        if (obstacleType < gameScreen.getObstacleTextures().length) {
            String textureName = gameScreen.getObstacleTextures()[obstacleType];
            newObstacle = new Cactus(gameScreen.getViewportWidth(), gameScreen.getGroundYPosition(), textureName, obstacleSpeed);
        } else {
            newObstacle = new Balloon(gameScreen.getViewportWidth(), gameScreen.getAirYPosition(), obstacleSpeed);
        }
        gameScreen.addObstacle(newObstacle);
    }
}
