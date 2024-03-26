package com.mygdx.game.manager;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameEngine;
import com.mygdx.game.screen.*;

public class ScreenManager {
    private final GameEngine game;
    private final SpriteBatch batch;
    private Screen previousScreen;

    public ScreenManager(GameEngine game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;
    }

    public void goToState(GameEngine.GameState newState) {
        // current state (case) to new state (parameter)
        switch (game.getGameState()) {
            case START:
                // show start screen
                game.setScreen(new StartScreen(game, batch));
                break;
            case RUNNING:
                // show game screen
                if (newState == GameEngine.GameState.PAUSED) {
                    // pause game and transition to PauseScreen
                    previousScreen = game.getScreen();
                    game.setScreen(new PauseScreen(game, batch));
                } else if (newState == GameEngine.GameState.GAME_OVER) {
                    // end game and transition to GameOverScreen
                    game.setScreen(new GameOverScreen(game, batch));
                } else {
                    // resume game or transition to a new GameScreen
                    game.setScreen(new GameScreen(game, batch));
                }
                break;
            case PAUSED:
                // Handle resume or return to previous screen
                if (newState == GameEngine.GameState.RUNNING) {
                    // Resume game by setting the previous screen
                    game.setScreen(previousScreen);
                }
                break;
            case GAME_OVER:
                if (newState == GameEngine.GameState.RUNNING) {
                    game.setScreen(new GameScreen(game, batch));
                }
                break;

            case INSTRUCTIONS:
                // Transition to the InstructionScreen
                game.setScreen(new InstructionScreen(game, batch));
                break;
            case HIGHSCORES:
                // Transition to the InstructionScreen
                game.setScreen(new HighScoresScreen(game, batch));
                break;
            default:
                game.setScreen(new StartScreen(game, batch));
        }

        // Update current state
        game.setGameState(newState);


    }
}