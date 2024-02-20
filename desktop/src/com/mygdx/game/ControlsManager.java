package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class ControlsManager {
    private InputListener listener;

    public ControlsManager(InputListener listener) {
        this.listener = listener;
    }

    public interface InputListener {
        void onJump();

        void onDuck(boolean isDucking);
    }

    public void update() {
        // Example input handling
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            listener.onJump();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            listener.onDuck(true);
        } else {
            listener.onDuck(false);
        }
    }
}