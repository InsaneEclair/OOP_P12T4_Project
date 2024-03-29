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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameEngine;

public class StartScreen implements Screen {
    private final GameEngine game;
    private final SpriteBatch batch;
    private final Texture backgroundTexture;
    private final Texture dinoTexture;
    private final BitmapFont font;
    private final FreeTypeFontGenerator generator;
    private final OrthographicCamera camera;
    private final Texture instructionsButtonTexture;
    private final Texture highscoresBtnTexture;


    public StartScreen(final GameEngine game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 515);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/PressStart2P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 15;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        font = generator.generateFont(parameter);

        backgroundTexture = new Texture("background/planetspace_v2.jpg");
        dinoTexture = new Texture("entity/main-character1_dark.png");
        instructionsButtonTexture = new Texture("background/instructions.png");

        highscoresBtnTexture = new Texture("background/leaderboard.png");
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.start();
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Draw the background
        batch.draw(backgroundTexture, 0, 0, camera.viewportWidth, camera.viewportHeight);

        // Scale and draw the instructions button
        float scale = 0.5f; // Scale factor for the button
        float buttonWidth = instructionsButtonTexture.getWidth() * scale;
        float buttonHeight = instructionsButtonTexture.getHeight() * scale;
        float buttonX = camera.viewportWidth / 2f - buttonWidth / 2;
        float buttonY = 80; // Position the button 110 pixels up from the bottom
        batch.draw(instructionsButtonTexture, buttonX, buttonY, buttonWidth, buttonHeight);

        // Scale and draw the highscores button
        float scaleFactor = 0.15f;
       float btnWidth = highscoresBtnTexture.getWidth() * scaleFactor;
        float btnHeight = highscoresBtnTexture.getHeight() * scaleFactor;
        float highScoreBtnX = camera.viewportWidth / 1.84f - buttonWidth / 2;
        float highScoreBtnY = 150; // Position the button 110 pixels up from the bottom
        batch.draw(highscoresBtnTexture, highScoreBtnX, highScoreBtnY, btnWidth, btnHeight);

        // Draw the dino and the text
        float centerX = camera.viewportWidth / 2f;
        float centerY = camera.viewportHeight / 2f;
        batch.draw(dinoTexture, centerX - (float) dinoTexture.getWidth() / 2, centerY + 10 - (float) dinoTexture.getHeight() / 2);
        font.draw(batch, "Press Space to Start the Game", centerX - 250, centerY + 100);

        batch.end();

        // Check for touch input on the instructions button
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            Rectangle buttonBounds = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
            if (buttonBounds.contains(touchPos.x, touchPos.y)) {
                // Instead of directly setting the screen, we call the method in GameEngine
                game.showInstructions();
            }

            Rectangle highscoresBtnRect = new Rectangle(highScoreBtnX, highScoreBtnY, btnWidth, btnHeight);
            if (highscoresBtnRect.contains(touchPos.x, touchPos.y)) {
                Gdx.app.log("StartScreen", "Highscores button touched.");
                //System.out.println("what");
                game.showHighScores();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) height / width;
        camera.viewportWidth = 800;
        camera.viewportHeight = 872 * aspectRatio;
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
        backgroundTexture.dispose();
        dinoTexture.dispose();
        instructionsButtonTexture.dispose();
        font.dispose();
    }
}
