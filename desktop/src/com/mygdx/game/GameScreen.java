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
import java.util.ArrayList;
import com.badlogic.gdx.Preferences;
public class GameScreen implements Screen {
    private final GameEngine game;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final Dinosaur dinosaur;
    private final Texture background;
    private final Texture land;
    private final Texture[] planetTextures;
    private int currentPlanetIndex;
    private int scoreSinceLastPlanetChange;

    private final ArrayList<Obstacle> obstacles;
    private final String[] obstacleTextures = new String[]{"cactus1_dark.png", "cactus2_dark.png"};
    private static final float groundYPosition = 50; // Example value
    private static final float airYPosition = 85; // Example value
    private final ControlsManager controlsManager;
    private final SoundManager soundManager;
    private final BitmapFont font;
    private final FreeTypeFontGenerator generator;
    private final AIManager aiManager;
    private final CollisionManager collisionManager;
    private int highScore;
    private Preferences prefs;

    public GameScreen(final GameEngine game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;

        // Create font to draw text
        generator = new FreeTypeFontGenerator(Gdx.files.internal("PressStart2P.ttf"));
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

        land = new Texture(Gdx.files.internal("land1_dark.png"));
        background = new Texture(Gdx.files.internal("background.png"));
        String[] planetTextureNames = new String[]{"sun.png", "mercury.png", "venus.png", "earth.png", "mars.png", "jupiter.png", "saturn.png", "uranus.png", "neptune.png"};
        planetTextures = new Texture[planetTextureNames.length];
        for (int i = 0; i < planetTextures.length; i++) {
            planetTextures[i] = new Texture(Gdx.files.internal(planetTextureNames[i]));
        }
        currentPlanetIndex = 0;
        scoreSinceLastPlanetChange = 0;


        obstacles = new ArrayList<>();
        float minTimeBetweenObstacles = 1.0f; // Minimum time in seconds until the next obstacle spawns
        float maxTimeBetweenObstacles = 3.0f; // Maximum time in seconds until the next obstacle spawns

        this.aiManager = new AIManager(game,this, minTimeBetweenObstacles, maxTimeBetweenObstacles);
        this.collisionManager = new CollisionManager(dinosaur, obstacles, game);
        prefs = Gdx.app.getPreferences("DinoGamePreferences");
        highScore = prefs.getInteger("highScore", 0); // Load, default to 0 if not found
    }

    @Override
    public void render(float delta) {

        if (game.isGameStarted() && game.getGameState() != GameEngine.GameState.GAME_OVER) {
            handleInput();

            // Existing game pause logic
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                game.pause();
            }

            // Clear the screen
            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            // Update game entities
            dinosaur.update(delta);
            aiManager.update(delta); // Handles obstacle spawning
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

            // Change planet every defined number of points
            int rotation = 1000;
            int currentScore = dinosaur.getScore();
            if (currentScore / rotation > scoreSinceLastPlanetChange) {
                scoreSinceLastPlanetChange = currentScore / rotation; // Update the last score tracker
                currentPlanetIndex = Math.min(currentPlanetIndex + 1, planetTextures.length - 1);
            }

            // Draw obstacles
            for (Obstacle obstacle : obstacles) {
                obstacle.update(delta);
                batch.draw(obstacle.getTexture(), obstacle.getPosition().x, obstacle.getPosition().y);
            }

            // Draw dinosaur
            batch.draw(dinosaur.getTexture(), dinosaur.getPosition().x, dinosaur.getPosition().y);

            // Draw score & highscore
            font.draw(batch, "Score: " + dinosaur.getScore(), camera.viewportWidth - 200, camera.viewportHeight - 20);
            font.draw(batch, "High Score: " + highScore, camera.viewportWidth - 250, camera.viewportHeight - 40);

            batch.end();
        }
    }

    public float getViewportWidth() {
        return camera.viewportWidth;
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }

    public String[] getObstacleTextures() {
        return obstacleTextures;
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
        if (background != null) background.dispose(); // Ensure background texture is disposed

        for (Obstacle obstacle : obstacles) {
            obstacle.dispose();
        }
//        batch.dispose();
//        Preferences prefs = Gdx.app.getPreferences("DinoGamePreferences");
//        prefs.putInteger("highScore", 0);
//        prefs.flush();
    }
}
