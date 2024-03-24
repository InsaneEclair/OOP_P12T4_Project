package com.mygdx.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class ControlsManager {
    private final InputListener listener;

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
        listener.onDuck(Gdx.input.isKeyPressed(Input.Keys.DOWN));
    }
}