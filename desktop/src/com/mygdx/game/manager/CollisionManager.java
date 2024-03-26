package com.mygdx.game.manager;

import java.util.ArrayList;
import java.util.Iterator;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.GameEngine;
import com.mygdx.game.entity.Dinosaur;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.Star;

public class CollisionManager {
    private final Dinosaur dinosaur;
    private final ArrayList<Entity> entities; // Renamed for clarity
    private final GameEngine gameEngine;
    private final SoundManager soundManager;

    public CollisionManager(Dinosaur dinosaur, ArrayList<Entity> entities, GameEngine gameEngine) {
        this.dinosaur = dinosaur;
        this.entities = entities;
        this.gameEngine = gameEngine;
        this.soundManager = new SoundManager();
    }

    public boolean checkCollision() {
        Rectangle dinosaurBounds = dinosaur.getBounds();

        // Use an iterator to safely remove elements from the list while iterating
        Iterator<Entity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            Rectangle gameObjectBounds = entity.getBounds();

            if (dinosaurBounds.overlaps(gameObjectBounds)) {
                if (entity instanceof Star) {
                        Star star = (Star) entity;
                        String textureFileName = star.getTextureFileName();
                        if(textureFileName.equals("entity/smallstar.png")) {
                            gameEngine.incrementScore(100);
                        } else if (textureFileName.equals("entity/bigstar.png")) {
                            gameEngine.incrementScore(500);
                        }
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
