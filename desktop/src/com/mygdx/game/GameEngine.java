package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

public class GameEngine extends Game {
    private SpriteBatch batch;
    private Dinosaur dinosaur;
    private boolean gameStarted;
    private GameState gameState;
    private ScreenManager screenManager;

    public enum GameState {
        START,
        RUNNING,
        PAUSED,
        GAME_OVER
    }

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.dinosaur = new Dinosaur(0,50);
        this.gameStarted = true;
        this.gameState = GameState.START;
        this.screenManager = new ScreenManager(this, batch);
        screenManager.goToState(gameState);
    }

    public void start() {
        if (!gameStarted) {
            gameStarted = true;
            screenManager.goToState(GameState.START);
        } else {
            screenManager.goToState(GameState.RUNNING);
        }
    }

    public void pause() {
        screenManager.goToState(GameState.PAUSED);
    }

    public void resume() {
        screenManager.goToState(GameState.RUNNING);
    }

    public void end() {
        screenManager.goToState(GameState.GAME_OVER);
    }

    public void restart() {
        // reset game variables
        dinosaur.setScore(0);
        screenManager.goToState(GameState.RUNNING);
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

    public Dinosaur getDinosaur() {
        return dinosaur;
    }
}
