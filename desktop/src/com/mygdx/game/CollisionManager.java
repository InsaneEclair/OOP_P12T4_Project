package com.mygdx.game;
import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;

public class CollisionManager {
    private final Dinosaur dinosaur;
    private final ArrayList<Obstacle> obstacle;
    private final GameEngine gameEngine; // This should be GameEngine, not GameScreen

    public CollisionManager(Dinosaur dinosaur, ArrayList<Obstacle> obstacle, GameEngine gameEngine) {
        this.dinosaur = dinosaur;
        this.obstacle = obstacle;
        this.gameEngine = gameEngine; // Store the reference to GameEngine
    }

    public boolean checkCollision() {
        Rectangle dinosaurBounds = dinosaur.getBounds();

        for (Obstacle obstacles : obstacle) {
            Rectangle obstaclesBounds = obstacles.getBounds();

            if (dinosaurBounds.overlaps(obstaclesBounds)) {
                if (obstacles instanceof Star) {
                    gameEngine.incrementScore(100); // increase total score by 100
                } else {
                    gameEngine.end(); // Transition to GameOverScreen for other obstacles
                    return true; // Collision occurred
                }
            }
        }
        return false;
    }
}