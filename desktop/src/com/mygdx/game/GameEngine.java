package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

public class GameEngine extends Game {
    public SpriteBatch batch;
    private boolean gameStarted;
    private GameState gameState;
    private GameScreen gameScreen;

    public enum GameState {
        RUNNING,
        PAUSED,
        GAME_OVER
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        gameStarted = true; // Consider setting this to true if you're immediately showing the start screen
        gameState = GameState.RUNNING; // Or PAUSED, depending on your game's initial state requirements
        this.setScreen(new StartScreen(this, batch));
    }


    public void start() {
        if (!gameStarted) {
            this.setScreen(new StartScreen(this, batch));
            gameStarted = true;
        } else {
            gameState = GameState.RUNNING;
            gameScreen = new GameScreen(this, batch);
            this.setScreen(gameScreen);
        }
    }

    public void pause() {
        gameState = GameState.PAUSED;
        // Store the current screen so that it can be resumed later
        gameScreen = (GameScreen) getScreen();
        this.setScreen(new PauseScreen(this, batch));
    }

    public void resume() {
        gameState = GameState.RUNNING;
        // Check if the game was paused
        if (gameScreen != null) {
            // Resume the game from the state it was paused
            this.setScreen(gameScreen);
        }
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
        this.setScreen(new GameOverScreen(this, batch,gameScreen.getDinosaur()));
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
