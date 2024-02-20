package com.mygdx.game;
import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Rectangle;

public class CollisionManager {
    private Dinosaur dinosaur;
    private ArrayList<EntityManager> cacti;
    private GameEngine gameEngine; // This should be GameEngine, not GameScreen

    public CollisionManager(Dinosaur dinosaur, ArrayList<EntityManager> cacti, GameEngine gameEngine) {
        this.dinosaur = dinosaur;
        this.cacti = cacti;
        this.gameEngine = gameEngine; // Store the reference to GameEngine
    }

    public boolean checkCollision() {
        Rectangle dinosaurBounds = dinosaur.getBounds();

        for (EntityManager cactus : cacti) {
            Rectangle cactusBounds = cactus.getBounds();

            if (dinosaurBounds.overlaps(cactusBounds)) {
                gameEngine.end(); // Use the game engine to transition to the GameOverScreen
                return true; // Collision occurred
            }
        }
        return false;
    }
}
