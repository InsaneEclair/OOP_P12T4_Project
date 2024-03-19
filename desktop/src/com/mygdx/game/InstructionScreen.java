//package com.mygdx.game;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
//
//public class InstructionScreen implements Screen {
//    private final GameEngine game;
//    private final SpriteBatch batch;
//    private final BitmapFont font;
//    private final FreeTypeFontGenerator generator;
//    private final OrthographicCamera camera;
//    private final Texture backgroundTexture; // Make backgroundTexture a class member
//
//    public InstructionScreen(final GameEngine game, SpriteBatch batch) {
//        this.game = game;
//        this.batch = batch;
//
//        camera = new OrthographicCamera();
//        camera.setToOrtho(false, 800, 480);
//
//        // Initialize the font for instructions
//        generator = new FreeTypeFontGenerator(Gdx.files.internal("PressStart2P.ttf"));
//        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        parameter.size = 20; // Slightly larger size for better readability
//        parameter.color = Color.FIREBRICK; // Choosing a color that stands out
//        font = generator.generateFont(parameter);
//
//        // Initialize the background texture
//        backgroundTexture = new Texture("instructionback.jpg");
//    }
//
//    @Override
//    public void show() {
//    }
//
//    @Override
//    public void render(float delta) {
//        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
//            game.setScreen(new StartScreen(game, batch));
//        }
//
//        Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        camera.update();
//        batch.setProjectionMatrix(camera.combined);
//
//        batch.begin();
//        // Draw the background texture first
//        batch.draw(backgroundTexture, 0, 0, camera.viewportWidth, camera.viewportHeight);
//
//        // Display the game instructions
//        font.draw(batch, "Dino's Space Adventure", 180, 110);
//        font.draw(batch, "Help Dino reach the goal of 10,000 meters!", 180, 140);
//        font.draw(batch, "Use arrow keys to guide Dino through the land.", 200, 170);
//        font.draw(batch, "Press 'Space' to make Dino jump over obstacles.", 220, 220);
//        font.draw(batch, "Dodge the falling meteors and survive the journey.", 230, 250);
//        font.draw(batch, "Reach the rocket at the end to escape to space!", 240, 280);
//        font.draw(batch, "Press 'ESCAPE' to return to the main menu.", 240, 310);
//
//        batch.end();
//    }
//
//    @Override
//    public void resize(int width, int height) {
////        camera.viewportWidth = 800;
////        camera.viewportHeight = 480 * height / width;
////        camera.update();
//        // Calculate the aspect ratio of the window
//        float aspectRatio = (float) width / height;
//        float viewportHeight = 480;
//        float viewportWidth = viewportHeight * aspectRatio;
//
//        // Update the camera's viewport to match the new window size while maintaining the background image's aspect ratio
//        camera.viewportWidth = viewportWidth;
//        camera.viewportHeight = viewportHeight;
//        camera.update();
//
//        // Optionally, center the camera if the viewport size doesn't match the window size exactly
//        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
//    }
//
//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void resume() {
//
//    }
//
//    @Override
//    public void hide() {
//
//    }
//
//
//    @Override
//    public void dispose() {
//        // Dispose of all the resources
//        if (backgroundTexture != null) backgroundTexture.dispose();
//        if (font != null) font.dispose();
//        if (generator != null) generator.dispose();
//    }
//}
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InstructionScreen implements Screen {
    private final GameEngine game;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final Texture instructionImage; // The full instruction image

    public InstructionScreen(final GameEngine game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 320);

        // Load the full instruction image
        instructionImage = new Texture("final_instruction.png"); // Replace with your instruction image file name
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            // Go back to the main screen if ESCAPE is pressed
            game.setScreen(new StartScreen(game, batch));
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        // Draw the full instruction image to cover the entire screen
        batch.draw(instructionImage, 0, 0, camera.viewportWidth, camera.viewportHeight);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
//        camera.viewportWidth = 800;
//        camera.viewportHeight = 480 * (float)height / width;
//        camera.update();
        // Calculate the aspect ratio of the window
        float windowRatio = (float) width / height;
        float viewportRatio = 800f / 480f;

        float scale = windowRatio > viewportRatio ? (float)height/480f : (float)width/800f;

        camera.viewportWidth = 800f * scale;
        camera.viewportHeight = 480f * scale;
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
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
        // Dispose of the instruction image
        instructionImage.dispose();
    }
}
