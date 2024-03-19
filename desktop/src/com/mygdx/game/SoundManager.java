package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.lwjgl3.audio.Mp3;

public class SoundManager {
    private final Sound jumpSound;
    private final Sound scoreUpSound;
    private final Sound deadSound;
    private final Sound starSound;
    private final Mp3.Music menuSound;
    private final Mp3.Music roundstartSound;

    public SoundManager() {
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump1.wav"));
        scoreUpSound = Gdx.audio.newSound(Gdx.files.internal("scoreup.wav"));
        deadSound = Gdx.audio.newSound(Gdx.files.internal("dead.wav"));
        starSound = Gdx.audio.newSound(Gdx.files.internal("star.mp3"));

        menuSound = (Mp3.Music) Gdx.audio.newMusic(Gdx.files.internal("spacemusic.mp3"));
        menuSound.setLooping(true);
        menuSound.setVolume(0.5f);

        roundstartSound = (Mp3.Music) Gdx.audio.newMusic(Gdx.files.internal("ingamemusic.mp3"));
        roundstartSound.setLooping(true);
        roundstartSound.setVolume(0.2f);
    }

    public void playJumpSound() {
        jumpSound.play();
    }

    public void playDeadSound() {
        deadSound.play();
    }

    public void playScoreUpSound() {scoreUpSound.play();}

    public void playStarSound() {starSound.play();}

    public void playRoundStartSound(){
        if (!roundstartSound.isPlaying()) {
            roundstartSound.play();
        }
    }

    public void pauseRoundStartSound() {
        roundstartSound.pause();
    }

    public void resumeRoundStartSound() {
        roundstartSound.play();
    }

    public void stopRoundStartSound () {
        if (roundstartSound.isPlaying()) {
            roundstartSound.stop();
        }
    }

    public void playMenuSound() {
        if (!menuSound.isPlaying()) {
            menuSound.play();
        }
    }
    public void stopMenuSound() {
        if (menuSound.isPlaying()) {
            menuSound.stop();
        }
    }

    public void dispose() {
        jumpSound.dispose();
        deadSound.dispose();
        scoreUpSound.dispose();
        starSound.dispose();
        menuSound.dispose();
        roundstartSound.dispose();
    }
}
