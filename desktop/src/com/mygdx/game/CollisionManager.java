package com.mygdx.game;
import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;

public class CollisionManager {
    private Dinosaur dinosaur;
    private ArrayList<EntityManager> obstacle;
    private GameEngine gameEngine; // This should be GameEngine, not GameScreen

    public CollisionManager(Dinosaur dinosaur, ArrayList<EntityManager> obstacle, GameEngine gameEngine) {
        this.dinosaur = dinosaur;
        this.obstacle = obstacle;
        this.gameEngine = gameEngine; // Store the reference to GameEngine
    }

    public boolean checkCollision() {
        Rectangle dinosaurBounds = dinosaur.getBounds();

        for (EntityManager obstacles : obstacle) {
            Rectangle obstaclesBounds = obstacles.getBounds();

            if (dinosaurBounds.overlaps(obstaclesBounds)) {
                gameEngine.end(); // Use the game engine to transition to the GameOverScreen
                return true; // Collision occurred
            }
        }
        return false;
    }

}
