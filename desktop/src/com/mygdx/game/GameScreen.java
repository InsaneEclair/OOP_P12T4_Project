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
    private final ArrayList<Obstacle> obstacles;
    private final String[] obstacleTextures = new String[]{"cactus1.png", "cactus2.png"};
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
        parameter.color = Color.DARK_GRAY;
        font = generator.generateFont(parameter);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        dinosaur = game.getDinosaur();
        soundManager = new SoundManager();
        PlayerController playerController = new PlayerController(dinosaur, soundManager);
        controlsManager = new ControlsManager(playerController);

        background = new Texture(Gdx.files.internal("land1.png"));

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

            // Pause game
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                game.pause();
            }

            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            dinosaur.update(delta);
            aiManager.update(delta); // This will handle obstacle spawning
            camera.update();
            batch.setProjectionMatrix(camera.combined);
            if (collisionManager.checkCollision()) {
                // If there's a collision, you might want to change the game state
                game.setGameState(GameEngine.GameState.GAME_OVER);
                soundManager.playDeadSound();
//
            }

            int newScore = dinosaur.getScore() + (int) (60 * delta); //increment score
            dinosaur.setScore(newScore);
//            dinosaur.setScore((int) (dinosaur.getScore() + (200 * delta))); //increment score
            //dinosaur.setHighScore((int) (dinosaur.getHighScore() + (60 * delta))); //increment score
            // Update high score if current score is greater
            if (newScore > highScore) {
                highScore = newScore;
                prefs.putInteger("highScore", highScore);
                prefs.flush(); // Don't forget to save it
            }

            batch.begin();
            // Always use already loaded textures. Loading textures in render() is inefficient and can lead to performance issues.
            batch.draw(background, 0, 50, Gdx.graphics.getWidth(), background.getHeight());

            // Draw obstacles
            for (Obstacle obstacle : obstacles) {
                obstacle.update(delta);
                batch.draw(obstacle.getTexture(), obstacle.getPosition().x, obstacle.getPosition().y);
            }

            // Draw dinosaur
            batch.draw(dinosaur.getTexture(), dinosaur.getPosition().x, dinosaur.getPosition().y);

            // Draw score

            font.draw(batch, "Score: " + dinosaur.getScore(), camera.viewportWidth - 200, camera.viewportHeight - 20);
            // Draw current score
            font.draw(this.batch, "Score: " + newScore, this.camera.viewportWidth - 200.0F, this.camera.viewportHeight - 20.0F);

            // Draw high score
            font.draw(this.batch, "High Score: " + highScore, this.camera.viewportWidth - 250.0F, this.camera.viewportHeight - 40.0F);

            batch.end();
        }

//        if (game.getGameState() == GameEngine.GameState.GAME_OVER) {
//
//        }

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
