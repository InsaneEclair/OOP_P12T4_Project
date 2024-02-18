package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

public class GameEngine extends Game {
	public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        super.render(); // This will render the current screen
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
