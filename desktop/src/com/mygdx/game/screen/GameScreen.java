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
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Preferences;
import com.mygdx.game.*;
import com.mygdx.game.entity.Dinosaur;
import com.mygdx.game.entity.GameObject;
import com.mygdx.game.manager.*;

public class GameScreen implements Screen {
    private final GameEngine game;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final Dinosaur dinosaur;
    private final Texture background;
    private final Texture land;
    private final String[] planetNames = new String[]{"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
    private final float[] jumpVelocities = {480, 380, 380, 490, 350, 370, 390, 360};
    private final String[] realLifeGravityValues = {"0.38 g", "0.904 g", "1 g", "0.376 g", "2.528 g", "1.065 g", "0.886 g", "1.14 g"};
    private final float[] modifyGravityValues = new float[]{-300,-750,-770,-300,-850,-700,-300,-800};
    private final Texture[] planetTextures;
    private int currentPlanetIndex;
    private int scoreSinceLastPlanetChange;
    private final ArrayList<GameObject> gameObjects;
    private static final float groundYPosition = 50; // Example value
    private static final float airYPosition = 85; // Example value
    private final ControlsManager controlsManager;
    private final SoundManager soundManager;
    private final BitmapFont font;
    private final FreeTypeFontGenerator generator;
    private final AIManager aiManager;
    private final CollisionManager collisionManager;
    private int highScore;
    private final Preferences prefs;


    public GameScreen(final GameEngine game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;

        // Create font to draw text
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/PressStart2P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 14;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        dinosaur = game.getDinosaur();
        soundManager = new SoundManager();
        PlayerController playerController = new PlayerController(dinosaur, soundManager);
        controlsManager = new ControlsManager(playerController);

        background = new Texture(Gdx.files.internal("background/background.png"));
        land = new Texture(Gdx.files.internal("background/land1_dark.png"));

        String[] planetTextureNames = new String[]{"background/planets/mercury.png", "background/planets/venus.png", "background/planets/earth.png", "background/planets/mars.png", "background/planets/jupiter.png", "background/planets/saturn.png", "background/planets/uranus.png", "background/planets/neptune.png"};
        planetTextures = new Texture[planetTextureNames.length];
        for (int i = 0; i < planetTextures.length; i++) {
            planetTextures[i] = new Texture(Gdx.files.internal(planetTextureNames[i]));
        }
        currentPlanetIndex = 0;
        scoreSinceLastPlanetChange = 0;

        gameObjects = new ArrayList<>();
        float minTimeBetweenObjects = 1.0f; // Minimum time in seconds until the next game object spawns
        float maxTimeBetweenObjects = 3.0f; // Maximum time in seconds until the next game object spawns

        this.aiManager = new AIManager(game,this, minTimeBetweenObjects, maxTimeBetweenObjects);
        this.collisionManager = new CollisionManager(dinosaur, gameObjects, game);
        prefs = Gdx.app.getPreferences("DinoGamePreferences");
        highScore = prefs.getInteger("highScore", 0); // Load, default to 0 if not found
       // dinosaur.setScore(0);
    }

    @Override
    public void render(float delta) {

        if (game.isGameStarted() && game.getGameState() != GameEngine.GameState.GAME_OVER) {
            handleInput();
            updateGravityAndVelocityBasedOnPlanet();

            // Existing game pause logic
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                game.pause();
            }

            // Clear the screen
            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            // Update game entities
            dinosaur.update(delta);
            aiManager.update(delta); // Handles game object spawning
            camera.update();
            batch.setProjectionMatrix(camera.combined);

            // Collision check
            if (collisionManager.checkCollision()) {
                game.setGameState(GameEngine.GameState.GAME_OVER);
                soundManager.playDeadSound();
            }

            // Score update logic based on delta time
            float scoreIncrement = 60 * delta;
            int newScore = dinosaur.getScore() + (int)scoreIncrement;
            dinosaur.setScore(newScore);
            Gdx.app.log("Score", "Current score: " + newScore);
            Gdx.app.log("Delta Time", "Delta: " + delta);

            // Check and update high score
            if (newScore > highScore) {
                highScore = newScore;
                prefs.putInteger("highScore", highScore);
                prefs.flush();
            }

            batch.begin();

            // Draw background
            float backgroundX = camera.position.x - camera.viewportWidth / 2f;
            float backgroundY = camera.position.y - camera.viewportHeight / 2f;
            batch.draw(background, backgroundX, backgroundY, camera.viewportWidth, camera.viewportHeight);
            batch.draw(land, 0, 50, Gdx.graphics.getWidth(), land.getHeight());

            // Draw planets
            int planetWidth = planetTextures[currentPlanetIndex].getWidth() * 4;
            int planetHeight = planetTextures[currentPlanetIndex].getHeight() * 4;
            float centerX = camera.viewportWidth / 2f - planetWidth / 2f;
            float centerY = camera.viewportHeight / 2f - planetHeight / 2f;

            batch.draw(planetTextures[currentPlanetIndex], centerX, centerY, planetWidth, planetHeight);
            String welcomeMessage = "Welcome to " + planetNames[currentPlanetIndex] + "!";
            font.draw(batch, welcomeMessage, 20, camera.viewportHeight - 20);

            // Display real-life gravity value for the current planet
            String gravityText = "Real-life Gravity: " + realLifeGravityValues[currentPlanetIndex];
            font.draw(batch, gravityText, 20, camera.viewportHeight - 45);

            // Change planet every defined number of points
            int rotation = 1000;
            int currentScore = dinosaur.getScore();
            if (currentScore / rotation > scoreSinceLastPlanetChange) {
                scoreSinceLastPlanetChange = currentScore / rotation; // Update the last score tracker
//                currentPlanetIndex = Math.min(currentPlanetIndex + 1, planetTextures.length - 1); // stop at the last planet
                currentPlanetIndex = (currentPlanetIndex + 1) % planetTextures.length; // loop around
                welcomeMessage = "Welcome to " + planetNames[currentPlanetIndex] + "!";
                font.draw(batch, welcomeMessage, 20, camera.viewportHeight - 20);
            }

            // Draw game objects
            for (GameObject gameObject : gameObjects) {
                gameObject.update(delta);
                batch.draw(gameObject.getTexture(), gameObject.getPosition().x, gameObject.getPosition().y);
            }

            // Draw dinosaur
            batch.draw(dinosaur.getTexture(), dinosaur.getPosition().x, dinosaur.getPosition().y);

            // Draw score, high score and gravity
            font.draw(batch, "Score: " + dinosaur.getScore(), camera.viewportWidth - 200, camera.viewportHeight - 20);
            font.draw(batch, "High Score: " + highScore, camera.viewportWidth - 250, camera.viewportHeight - 40);
            //font.draw(batch, "Gravity: " + currentGravity, 20, camera.viewportHeight - 40); testing purposes

            batch.end();
        }
    }

    private void updateGravityAndVelocityBasedOnPlanet() {
        // Use currentPlanetIndex to set gravity and velocity
        float currentGravity = modifyGravityValues[currentPlanetIndex];
        dinosaur.setGravity(currentGravity);

        float currentVelocity = jumpVelocities[currentPlanetIndex];
        dinosaur.setJumpVelocity(currentVelocity);

        // Optionally log these changes for debugging
        System.out.println("Updated gravity to: " + currentGravity + " for " + planetNames[currentPlanetIndex]);
        System.out.println("Updated velocity to: " + currentVelocity + " for " + planetNames[currentPlanetIndex]);
    }

    public float getViewportWidth() {
        return camera.viewportWidth;
    }

    public float getViewportHeight() {
        return camera.viewportHeight;
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public float getAirYPosition() {
        return airYPosition;
    }

    public float getGroundYPosition() {
        return groundYPosition;
    }

    private void handleInput() {
        controlsManager.update();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void show() {

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

    public void playScoreUpSound() {
        soundManager.playScoreUpSound();
    }
//    public void resetHighScore() {
//        prefs.putInteger("highScore", 0);
//        prefs.flush();
//    }

    @Override
    public void dispose() {
//        resetHighScore(); // Reset high score when disposing the game screen
        // Dispose of other resources...
        soundManager.dispose();
        dinosaur.dispose();
        font.dispose();
        generator.dispose();
        background.dispose();
        land.dispose();

        for (Texture texture : planetTextures) {
            texture.dispose();
        }

        for (GameObject gameObject : gameObjects) {
            gameObject.dispose();
        }
//        batch.dispose();
//        Preferences prefs = Gdx.app.getPreferences("DinoGamePreferences");
//        prefs.putInteger("highScore", 0);
//        prefs.flush();
    }
}
