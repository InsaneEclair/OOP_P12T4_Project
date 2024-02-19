package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class ControlsManager {
    public interface InputListener {
        void onJump();
        void onDuck(boolean isDucking);
    }
    private InputListener listener;
    public ControlsManager(InputListener listener) {
        this.listener = listener;
    }

    public void update() {
        // Jumping, only using the UP key
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (listener != null) {
                listener.onJump();
            }
        }

        // Ducking
        boolean isDucking = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        if (listener != null) {
            listener.onDuck(isDucking);
        }
    }
}