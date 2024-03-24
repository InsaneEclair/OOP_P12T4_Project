package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.GameEngine;

public class PauseScreen implements Screen {
    private final GameEngine game;
    private final SpriteBatch batch;
    private final Texture dinoTexture;
    private final BitmapFont font;
    private final FreeTypeFontGenerator generator;
    private final OrthographicCamera camera;
    private final Texture backgroundTexture;

    public PauseScreen(final GameEngine game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Create font to draw text
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/PressStart2P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);

        dinoTexture = new Texture("entity/main-character1_dark.png");
        backgroundTexture = new Texture("background/background.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.resume();
        }

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, camera.viewportWidth, camera.viewportHeight);

        float centerX = camera.viewportWidth / 2f;
        float centerY = camera.viewportHeight / 2f;

        batch.draw(dinoTexture, centerX - (float) dinoTexture.getWidth() / 2, centerY - 50 - (float) dinoTexture.getHeight() / 2);
        font.draw(batch, "Game Paused", centerX - 50, centerY + 100);
        font.draw(batch, "Press Space to Resume the Game", centerX - 150, centerY + 50);


        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 800; // You may adjust this according to your desired initial viewport width
        camera.viewportHeight = (float) (800 * height) / width; // Maintain aspect ratio
        camera.update();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        generator.dispose();
        dinoTexture.dispose();
        font.dispose();
    }
}
