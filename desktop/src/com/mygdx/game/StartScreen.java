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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


public class StartScreen implements Screen {
    private final GameEngine game;
    private final SpriteBatch batch;
    private Texture backgroundTexture;
    private Texture[] planetTextures; // Array to hold multiple planet textures
    private final Texture dinoTexture;
    private final BitmapFont font;
    private final FreeTypeFontGenerator generator;
    private final OrthographicCamera camera;



    public StartScreen(final GameEngine game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 500);



        // Improved font creation with better visibility
        generator = new FreeTypeFontGenerator(Gdx.files.internal("PressStart2P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 15; // Adjusted font size for better visibility
        parameter.color = Color.WHITE; // Changed font color for better contrast
        parameter.borderWidth = 2; // Outline width
        parameter.borderColor = Color.BLACK; // Outline color
        font = generator.generateFont(parameter);

        // Loading textures
        backgroundTexture = new Texture("planetspace.jpg"); // Ensure this path is correct
        planetTextures = new Texture[]{ new Texture("planetspace.jpg"), };
        dinoTexture = new Texture("main-character1.png"); // Ensure this path is correct
    }


    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.start();
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen

        camera.update();
        batch.setProjectionMatrix(camera.combined);



        batch.begin();
        // Draw the space background
        batch.draw(backgroundTexture, 0, 0, camera.viewportWidth, camera.viewportHeight);




        camera.update();
        batch.setProjectionMatrix(camera.combined);


        float centerX = camera.viewportWidth / 2f;
        float centerY = camera.viewportHeight / 2f;

        batch.draw(dinoTexture, centerX - (float) dinoTexture.getWidth() / 2, centerY + 10  - (float) dinoTexture.getHeight() / 2);
        font.draw(batch, "Press Space to Start the Game", centerX - 220, centerY + 100);


        batch.end();
        // Check for touch input
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos); // Convert screen coordinates to game world coordinates
}
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 800; // You may adjust this according to your desired initial viewport width
        camera.viewportHeight = (float) (900 * height) / width; // Maintain aspect ratio
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
