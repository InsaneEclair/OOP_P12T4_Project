package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.entity.Dinosaur;
import com.mygdx.game.manager.ScreenManager;
import com.mygdx.game.manager.SoundManager;

public class GameEngine extends Game implements InputProcessor{
    private SpriteBatch batch;
    private Dinosaur dinosaur;
    private boolean gameStarted;
    private GameState gameState;
    private ScreenManager screenManager;
    private SoundManager soundManager;

    // InputProcessor methods
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.SPACE) {
            if (gameState == GameState.START && !gameStarted) {
                start();
            }
            return true;
        } else if (keycode == Keys.ESCAPE) {
            if (gameState == GameState.INSTRUCTIONS) {
                backToStart();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public enum GameState {
        START,
        RUNNING,
        PAUSED,
        GAME_OVER,
        INSTRUCTIONS,
        HIGHSCORES
    }

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.dinosaur = Dinosaur.getInstance(0, 50); // singleton design pattern
        this.gameStarted = true;
        this.gameState = GameState.START;
        this.screenManager = new ScreenManager(this, batch);
        this.soundManager = new SoundManager();

        // Set this class as the input processor to handle key inputs
        Gdx.input.setInputProcessor(this);

        screenManager.goToState(gameState);
        soundManager.playMenuSound();
    }

    public void start() {
        dinosaur.setScore(0);
        gameStarted = true;
        gameState = GameState.RUNNING; // Update gameState to RUNNING
        screenManager.goToState(GameState.RUNNING);
        soundManager.stopMenuSound();
        soundManager.playRoundStartSound();
    }

    public void showInstructions() {
        gameState = GameState.INSTRUCTIONS;
        screenManager.goToState(GameState.INSTRUCTIONS);
    }

    // Method to handle back transition from INSTRUCTIONS to START
    public void backToStart() {
        if (gameState == GameState.INSTRUCTIONS) {
            gameState = GameState.START;
            screenManager.goToState(GameState.START);
            // Ensure gameStarted is false to allow starting the game
            gameStarted = false;
        }
    }

    public void showHighScores() {
        dinosaur.setScore(0);
        gameState = GameState.HIGHSCORES;
        screenManager.goToState(GameState.HIGHSCORES);
    }

    public void pause() {
        screenManager.goToState(GameState.PAUSED);
        soundManager.pauseRoundStartSound();
    }

    public void resume() {
        screenManager.goToState(GameState.RUNNING);
        soundManager.resumeRoundStartSound();
    }

    public void end() {
        screenManager.goToState(GameState.GAME_OVER);
        soundManager.stopRoundStartSound();
        soundManager.playMenuSound();
    }

    public void restart() {
        // reset game variables
        soundManager.stopMenuSound();
        dinosaur.setScore(0);
        screenManager.goToState(GameState.RUNNING);
        soundManager.playRoundStartSound();
    }

    // Method to increment the score by a specified amount
    public void incrementScore(int amount) {
        dinosaur.setScore(dinosaur.getScore() + amount);
    }

    @Override
    public void render() {
        super.render(); // This will render the current screen
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState state) {
        gameState = state;
    }

    public Dinosaur getDinosaur() {
        return dinosaur;
    }
}

