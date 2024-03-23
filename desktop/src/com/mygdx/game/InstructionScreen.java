package com.mygdx.game;

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

public class InstructionScreen implements Screen {
    private final GameEngine game;
    private final SpriteBatch batch;

    private final Texture backgroundTexture;
    private BitmapFont font;
    private FreeTypeFontGenerator generator;

    private final OrthographicCamera camera;
    public InstructionScreen(final GameEngine game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 290);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("PressStart2P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 11;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        font = generator.generateFont(parameter);

        backgroundTexture = new Texture("ImageInstruction.png"); // Load the texture

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.start();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new StartScreen(game, batch));
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Draw the background
        batch.draw(backgroundTexture, 0, 0, camera.viewportWidth, camera.viewportHeight);

        // Calculate the center for the text
        float centerX = camera.viewportWidth / 2f;
        float centerY = camera.viewportHeight / 2f;

        // Set the scale for the font if needed
        // font.getData().setScale(yourScale);

        // Draw the game title
        font.draw(batch, "DINO   SPACE   ADVENTURE", centerX - 150, centerY + 130);

        // Draw the goal
        font.draw(batch, "Goal: Help the dino to reach 10,000m!!", centerX - 265, centerY + 100);

        // Draw the controls instruction
        font.draw(batch, "Use Up and Down arrow keys to guide Dino.", centerX - 265, centerY + 70);
        font.draw(batch, "Dodge ALL the meteors to survive.", centerX - 265, centerY + 50);
        font.draw(batch, "Score 1000 points to leap to a new planet, where", centerX - 265, centerY + 30);
        font.draw(batch, "altered gravity changes Dino's jump dynamics.", centerX - 265, centerY + 10);

        // Draw the additional information
        font.draw(batch, "Uncover interesting gravity facts", centerX - 265, centerY - 30);
        font.draw(batch, "after your adventure!!", centerX - 265, centerY - 50);
        // Draw the return instruction
        font.draw(batch, "Press Esc to return to the main menu.", centerX - 265, centerY - 70);

        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 800;
        camera.viewportHeight = 480 * (float)height / width;
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
        font.dispose();
    }
}
