package com.mygdx.game;

public class PlayerController implements ControlsManager.InputListener {
    private final Dinosaur dinosaur;
    private final SoundManager soundManager;

    public PlayerController(Dinosaur dinosaur, SoundManager soundManager) {
        this.dinosaur = dinosaur;
        this.soundManager = soundManager;
    }

    @Override
    public void onJump() {
        // uses the getter methods
        if (dinosaur.getPosition().y == dinosaur.getGroundLevel() && !dinosaur.isDucking()) {
            dinosaur.jump();
            soundManager.playJumpSound();
        }
    }

    @Override
    public void onDuck(boolean isDucking) {
        if (isDucking) {
            dinosaur.duck();
        } else {
            dinosaur.standUp();
        }
    }
}
