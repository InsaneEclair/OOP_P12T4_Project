package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class GameEngine extends Game implements InputProcessor{
    private SpriteBatch batch;
    private Dinosaur dinosaur;
    private boolean gameStarted;
    private GameState gameState;
    private ScreenManager screenManager;
    private SoundManager soundManager;

    // Method to increment the score by a specified amount
    public void incrementScore(int amount) {
        dinosaur.setScore(dinosaur.getScore() + amount);
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

        INSTRUCTIONS
    }

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.dinosaur = new Dinosaur(0,50);
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
        // Only allow the game to start if it's currently on the START screen
        if (gameState == GameState.START) {
            gameStarted = true;
            gameState = GameState.RUNNING; // Update gameState to RUNNING
            screenManager.goToState(GameState.RUNNING);
            soundManager.stopMenuSound();
            soundManager.playRoundStartSound();
        }
    }

    public void showInstructions() {
        // Transition to INSTRUCTIONS state only from START state
        if (gameState == GameState.START) {
            gameState = GameState.INSTRUCTIONS;
            screenManager.goToState(GameState.INSTRUCTIONS);
        }
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
    }

    public void restart() {
        // reset game variables
        dinosaur.setScore(0);
        screenManager.goToState(GameState.RUNNING);
        soundManager.playRoundStartSound();
    }

    @Override
    public void render() {
        // Consider checking gameState and handling inputs based on the current state here
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

