package com.mygdx.game;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.math.Rectangle;

public class CollisionManager {
    private final Dinosaur dinosaur;
    private final ArrayList<Obstacle> obstacles; // Renamed for clarity
    private final GameEngine gameEngine;
    private final SoundManager soundManager;

    public CollisionManager(Dinosaur dinosaur, ArrayList<Obstacle> obstacles, GameEngine gameEngine) {
        this.dinosaur = dinosaur;
        this.obstacles = obstacles;
        this.gameEngine = gameEngine;
        this.soundManager = new SoundManager();
    }

    public boolean checkCollision() {
        Rectangle dinosaurBounds = dinosaur.getBounds();

        // Use an iterator to safely remove elements from the list while iterating
        Iterator<Obstacle> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            Obstacle obstacle = iterator.next();
            Rectangle obstacleBounds = obstacle.getBounds();

            if (dinosaurBounds.overlaps(obstacleBounds)) {
                if (obstacle instanceof Star) {
                    gameEngine.incrementScore(100);
                    soundManager.playStarSound();
                    iterator.remove();
                } else {
                    gameEngine.end();
                    return true;
                }
            }
        }
        return false;
    }
}
