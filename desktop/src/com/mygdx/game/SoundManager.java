package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    private final Sound jumpSound;
    private final Sound scoreUpSound;
    private final Sound deadSound;

    public SoundManager() {
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump1.wav"));
        scoreUpSound = Gdx.audio.newSound(Gdx.files.internal("scoreup.wav"));
        deadSound = Gdx.audio.newSound(Gdx.files.internal("dead.wav"));
    }

    public void playJumpSound() {
        jumpSound.play();
    }

    public void playDeadSound() {
        deadSound.play();
    }

    public void playScoreUpSound() {
        scoreUpSound.play();
    }

    public void dispose() {
        jumpSound.dispose();
        deadSound.dispose();
        scoreUpSound.dispose();
    }
}
