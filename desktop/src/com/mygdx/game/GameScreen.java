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
import java.util.Iterator;
import java.util.Random;

public class GameScreen implements Screen {
    private static final int MOVEMENT = 100;
    private final GameEngine game;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final Dinosaur dinosaur;
    private final Texture[] backgrounds;
    private final float[] backgroundOffset;
    private ArrayList<EntityManager> obstacles = new ArrayList<EntityManager>();
    private float timeSinceLastObstacle;
    private final Random random = new Random();
    private final float minTimeBetweenObstacles;
    private final float maxTimeBetweenObstacles;
    private float timeUntilNextObstacle = getRandomSpawnTime();
    private final String[] obstacleTextures = new String[]{"cactus1.png", "cactus2.png"};
    private static final float groundYPosition = 50;
    private static final float airYPosition = 25;
    private final CollisionManager collisionManager;
    private final PlayerController playerController;
    private final ControlsManager controlsManager;
    private final SoundManager soundManager;
    private final BitmapFont font;
    private final FreeTypeFontGenerator generator;


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
        playerController = new PlayerController(dinosaur, soundManager);
        controlsManager = new ControlsManager(playerController);
        Texture groundTexture = new Texture("land1.png");

        backgrounds = new Texture[1];
        backgrounds[0] = new Texture(Gdx.files.internal("land1.png"));
//        backgrounds[1] = new Texture(Gdx.files.internal("land2.png"));
//        backgrounds[2] = new Texture(Gdx.files.internal("land3.png"));
//        backgrounds[3] = new Texture(Gdx.files.internal("land1.png"));
//        backgrounds[4] = new Texture(Gdx.files.internal("land2.png"));
//        backgrounds[5] = new Texture(Gdx.files.internal("land3.png"));
        backgroundOffset = new float[backgrounds.length];

        for (int i = 0; i < backgrounds.length; i++) {
            backgroundOffset[i] = -i * camera.viewportWidth;
        }

        obstacles = new ArrayList<EntityManager>();
        timeSinceLastObstacle = 0;
        minTimeBetweenObstacles = 1.0f; // Minimum time in seconds until the next obstacle spawns
        maxTimeBetweenObstacles = 3.0f; // Maximum time in seconds until the next obstacle spawns
        timeUntilNextObstacle = getRandomSpawnTime();

        // Inside GameScreen's constructor
        collisionManager = new CollisionManager(dinosaur, obstacles, game); // Pass 'game' which is the GameEngine instance

        groundTexture = new Texture(Gdx.files.internal("land1.png"));

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
            camera.update();
            batch.setProjectionMatrix(camera.combined);

            batch.begin();
//            float scaleFactor = camera.viewportWidth / (backgrounds[0].getWidth() * 4);
//            float scaledHeight = backgrounds[0].getHeight() * scaleFactor;
//
//            // Update and draw backgrounds
//            for (int i = 0; i < backgrounds.length; i++) {
//                backgroundOffset[i] -= MOVEMENT * delta; // Update background offset based on movement
//                if (backgroundOffset[i] + backgrounds[i].getWidth() * scaleFactor <= 0) {
//                    // Reset background offset if it goes off-screen
//                    backgroundOffset[i] += backgrounds[i].getWidth() * scaleFactor * backgrounds.length;
//                }
//                batch.draw(backgrounds[i], backgroundOffset[i], (-scaledHeight / 2) + 5, backgrounds[i].getWidth() * scaleFactor, scaledHeight);
//            }



            Texture groundTexture = new Texture("land1.png"); // Replace "ground.png" with your ground texture file
            batch.draw(groundTexture, 0, 50, Gdx.graphics.getWidth(), groundTexture.getHeight());

            // Manage obstacles
            timeSinceLastObstacle += delta;
            timeUntilNextObstacle -= delta;
            if (timeUntilNextObstacle <= 0) {
                // Create and add new obstacle
                EntityManager newObstacle;
                int obstacleType = random.nextInt(obstacleTextures.length + 1);
                if (obstacleType < obstacleTextures.length) {
                    String textureName = obstacleTextures[obstacleType];
                    newObstacle = new Cactus(camera.viewportWidth, groundYPosition, textureName);
                } else {
                   // newObstacle = new Cloud(camera.viewportWidth, Gdx.graphics.getHeight() - 100);
                    newObstacle = new Balloon(camera.viewportWidth, 150); // Adjusted y-position
                }
                obstacles.add(newObstacle);
                timeSinceLastObstacle = 0;
                timeUntilNextObstacle = getRandomSpawnTime();
            }

            // Update and draw obstacles
            Iterator<EntityManager> iter = obstacles.iterator();
            while (iter.hasNext()) {
                EntityManager obstacle = iter.next();
                obstacle.update(delta);
                if (obstacle.position.x + obstacle.texture.getWidth() < 0) {
                    iter.remove(); // Remove obstacle if it has passed off the screen
                    obstacle.dispose();
                } else {
                    batch.draw(obstacle.texture, obstacle.position.x, obstacle.position.y);
                }
            }
            // Draw dinosaur
            batch.draw(dinosaur.getTexture(), dinosaur.position.x, dinosaur.position.y);

            // Draw score
            dinosaur.setScore((int) (dinosaur.getScore() + (60 * delta)));//increment score
            font.draw(batch, "Score: " + dinosaur.getScore(), camera.viewportWidth - 150, camera.viewportHeight - 20);

            batch.end();


            // Log dinosaur position
            Gdx.app.log("Dinosaur Position", dinosaur.position.toString());
        }

        // Check for collisions
//        if (collisionManager.checkCollision()) {
//            collisionManager.stopGame();
//        }
        if (collisionManager.checkCollision()) {

            return;
        }
    }


    private void handleInput() {
        controlsManager.update();
    }

    private float getRandomSpawnTime() {
        return random.nextFloat() * (maxTimeBetweenObstacles - minTimeBetweenObstacles) + minTimeBetweenObstacles;
    }

    private float calculateYPositionForObstacle(String textureName) {
        if ("cactus1.png".equals(textureName)) {
            return 0; // Ground obstacle
        } else if ("cactus2.png".equals(textureName)) {
            return 0; // Ground Obstacle
        } else if ("oldballoon.jpg".equals(textureName)) {
            return 25; // Air Obstacle
        }
        return 0; // Default ground position
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();

        for (int i = 0; i < backgrounds.length; i++) {
            backgroundOffset[i] = camera.viewportWidth * i;
        }
    }

    @Override
    public void show() {
        // Initialize resources like textures
    }

    @Override
    public void hide() {
        // Dispose of resources or hide elements
    }

    @Override
    public void pause() {
        // Implement pause logic
    }

    @Override
    public void resume() {
        // Implement resume logic
    }

    @Override
    public void dispose() {
        // Dispose of game resources
        soundManager.dispose();
        dinosaur.dispose();
        font.dispose();
        generator.dispose();
        for (Texture background : backgrounds) {
            background.dispose();
        }
        for (EntityManager obstacle : obstacles) {
            obstacle.dispose();
        }
    }
}
