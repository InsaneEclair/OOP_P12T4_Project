package com.mygdx.game.manager;

import java.util.ArrayList;
import java.util.Iterator;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.GameEngine;
import com.mygdx.game.entity.Dinosaur;
import com.mygdx.game.entity.GameObject;
import com.mygdx.game.entity.Star;

public class CollisionManager {
    private final Dinosaur dinosaur;
    private final ArrayList<GameObject> gameObjects; // Renamed for clarity
    private final GameEngine gameEngine;
    private final SoundManager soundManager;

    public CollisionManager(Dinosaur dinosaur, ArrayList<GameObject> gameObjects, GameEngine gameEngine) {
        this.dinosaur = dinosaur;
        this.gameObjects = gameObjects;
        this.gameEngine = gameEngine;
        this.soundManager = new SoundManager();
    }

    public boolean checkCollision() {
        Rectangle dinosaurBounds = dinosaur.getBounds();

        // Use an iterator to safely remove elements from the list while iterating
        Iterator<GameObject> iterator = gameObjects.iterator();
        while (iterator.hasNext()) {
            GameObject gameObject = iterator.next();
            Rectangle gameObjectBounds = gameObject.getBounds();

            if (dinosaurBounds.overlaps(gameObjectBounds)) {
                if (gameObject instanceof Star) {
                        Star star = (Star) gameObject;
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
