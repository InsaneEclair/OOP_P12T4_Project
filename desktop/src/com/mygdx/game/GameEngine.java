package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

public class GameEngine extends Game {
	private SpriteBatch batch;
    private boolean gameStarted;
    private GameState gameState;
    public enum GameState {
        RUNNING,
        PAUSED,
        GAME_OVER
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        gameStarted = false;
        gameState = GameState.RUNNING;
    }

    public void start() {
        this.setScreen(new GameScreen(this, batch));
        gameStarted = true;
    }

    public void pause() {
        gameState = GameState.PAUSED;
    }

    public void resume() {
        gameState = GameState.RUNNING;
    }

    public void restart() {
        // reset game variables
        gameStarted = false;
        gameState = GameState.RUNNING;

        // create a new GameScreen instance
        GameScreen newScreen = new GameScreen(this, batch);

        // set the new screen as the current screen
        this.setScreen(newScreen);
    }

    public void end() {
        gameState = GameState.GAME_OVER;
        dispose();
    }

    @Override
    public void render() {
        if (!gameStarted) {
            start();
        }
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
}
