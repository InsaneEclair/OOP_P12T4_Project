package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
public class PlayerController {
    private Dinosaur dinosaur;
    private SoundManager soundManager;

    public PlayerController(Dinosaur dinosaur, SoundManager soundManager) {
        this.dinosaur = dinosaur;
        this.soundManager = soundManager;
    }

    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            dinosaur.jump();
            soundManager.playJumpSound();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            dinosaur.duck();
        } else {
            dinosaur.standUp();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
        }
    }
}
