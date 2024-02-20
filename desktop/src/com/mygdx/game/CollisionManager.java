package com.mygdx.game;
import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Rectangle;

public class CollisionManager {
    private Dinosaur dinosaur;
    private ArrayList<EntityManager> cacti;
    protected GameScreen gameScreen;

    public CollisionManager(Dinosaur dinosaur, ArrayList<EntityManager> cacti) {
        this.dinosaur = dinosaur;
        this.cacti = cacti;
    }

    public boolean checkCollision() {
        Rectangle dinosaurBounds = dinosaur.getBounds();

        for (EntityManager cactus : cacti) {
            Rectangle cactusBounds = cactus.getBounds();

            if (dinosaurBounds.overlaps(cactusBounds)) {
                stopGame();
                break; //breaks the loop when collision occurs
            }
        }
        return false;
    }

    protected void stopGame() {
        //gameScreen.end(); //closes the window when collision occurs
    }
}
