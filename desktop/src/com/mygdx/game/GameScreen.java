//package com.mygdx.game;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.Random;
//
//public class GameScreen implements Screen {
//    private static final int MOVEMENT = 100;
//    private final GameEngine game;
//    private final SpriteBatch batch;
//    private OrthographicCamera camera;
//    private Dinosaur dinosaur;
//    private Texture[] backgrounds;
//    private float[] backgroundOffset;
//    private ArrayList<EntityManager> obstacles = new ArrayList<EntityManager>();
//    private float timeSinceLastObstacle;
//    private Random random = new Random();
//    private float minTimeBetweenObstacles;
//    private float maxTimeBetweenObstacles;
//    private float timeUntilNextObstacle = getRandomSpawnTime();
//    private String[] obstacleTextures = new String[]{"cactus1.png", "cactus2.png"};
//    private static final float groundYPosition = 0;
//    private static final float airYPosition = 25;
//    private CollisionManager collisionManager;
//    private PlayerController playerController;
//    private SoundManager soundManager;
//
//    public GameScreen(final GameEngine game, SpriteBatch batch) {
//        this.game = game;
//        this.batch = batch;
//
//        camera = new OrthographicCamera();
//        camera.setToOrtho(false, 800, 480);
//        dinosaur = new Dinosaur(0, 0); // Instantiation of the Dinosaur object
//        soundManager = new SoundManager();
//        playerController = new PlayerController(dinosaur, soundManager);
//
//        backgrounds = new Texture[3];
//        backgrounds[0] = new Texture(Gdx.files.internal("land1.png"));
//        backgrounds[1] = new Texture(Gdx.files.internal("land2.png"));
//        backgrounds[2] = new Texture(Gdx.files.internal("land3.png"));
//        backgroundOffset = new float[backgrounds.length];
//
//        for (int i = 0; i < backgrounds.length; i++) {
//            backgroundOffset[i] = -i * camera.viewportWidth;
//        }
//
//        obstacles = new ArrayList<EntityManager>();
//        timeSinceLastObstacle = 0;
//        minTimeBetweenObstacles = 1.0f; // Minimum time in seconds until the next obstacle spawns
//        maxTimeBetweenObstacles = 3.0f; // Maximum time in seconds until the next obstacle spawns
//        timeUntilNextObstacle = getRandomSpawnTime();
//
//        collisionManager = new CollisionManager(dinosaur, obstacles);
//    }
//
//    @Override
//    public void render(float delta) {
//        if (game.isGameStarted() && game.getGameState() != GameEngine.GameState.GAME_OVER) {
//            handleInput();
//
//            Gdx.gl.glClearColor(0, 0, 0, 1);
//            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//            dinosaur.update(delta);
//            camera.update();
//            batch.setProjectionMatrix(camera.combined);
//
//            batch.begin();
//            float scaleFactor = camera.viewportWidth / (backgrounds[0].getWidth());
//            float scaledHeight = backgrounds[0].getHeight() * (2*scaleFactor);
//
//            // Draw and update backgrounds
//            for (int i = 0; i < backgrounds.length; i++) {
//                if (backgroundOffset[i] + backgrounds[i].getWidth() * scaleFactor <= 0) {
//                    backgroundOffset[i] += backgrounds[i].getWidth() * scaleFactor * backgrounds.length;
//                }
//                batch.draw(backgrounds[i], backgroundOffset[i], 0, backgrounds[i].getWidth() * scaleFactor, scaledHeight);
//                backgroundOffset[i] -= MOVEMENT * delta;
//            }
//
//            // Manage obstacles
//            timeSinceLastObstacle += delta;
//            timeUntilNextObstacle -= delta;
//            if (timeUntilNextObstacle <= 0) {
//                EntityManager newObstacle;
//                int obstacleType = random.nextInt(obstacleTextures.length + 1); // Assuming you have 2 cactus types + cloud
//                if (obstacleType < obstacleTextures.length) {
//                    // Create a cactus obstacle
//                    String textureName = obstacleTextures[obstacleType];
//                    newObstacle = new Cactus(camera.viewportWidth, groundYPosition, textureName);
//                } else {
//                    // Create a cloud obstacle
//                    //newObstacle = new Cloud(camera.viewportWidth, Gdx.graphics.getHeight() - 100); // Adjusted y-position
//                    newObstacle = new Balloon(camera.viewportWidth, Gdx.graphics.getHeight() - 60); // Adjusted y-position
//                }
//                obstacles.add(newObstacle);
//                timeSinceLastObstacle = 0;
//                timeUntilNextObstacle = getRandomSpawnTime();
//            }
//
//            Iterator<EntityManager> iter = obstacles.iterator();
//            while (iter.hasNext()) {
//                EntityManager obstacle = iter.next();
//                obstacle.update(delta);
//                if (obstacle.position.x + obstacle.texture.getWidth() < 0) {
//                    iter.remove(); // Remove obstacle if it has passed off the screen
//                    obstacle.dispose();
//                } else {
//                    batch.draw(obstacle.texture, obstacle.position.x, obstacle.position.y);
//                }
//            }
//            batch.draw(dinosaur.getTexture(), dinosaur.position.x, dinosaur.position.y);
//            batch.end();
//
//            Gdx.app.log("Dinosaur Position", dinosaur.position.toString());
//        }
//
//        // Check for collisions
//        if(collisionManager.checkCollision()){
//            collisionManager.stopGame();
//        }
//    }
//
//
//    private void handleInput() {
//        playerController.update();
//    }
//    private float getRandomSpawnTime() {
//        return random.nextFloat() * (maxTimeBetweenObstacles - minTimeBetweenObstacles) + minTimeBetweenObstacles;
//    }
//
//    private float calculateYPositionForObstacle(String textureName) {
//        if ("cactus1.png".equals(textureName)) {
//            return 0; // Ground obstacle
//        } else if ("cactus2.png".equals(textureName)) {
//            return 0; // Ground Obstacle
//        } else if ("cloud.jpg".equals(textureName)) {
//            return 25; // Air Obstacle
//        }
//        return 0; // Default ground position
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        camera.viewportWidth = width;
//        camera.viewportHeight = height;
//        camera.update();
//
//        for (int i = 0; i < backgrounds.length; i++) {
//            backgroundOffset[i] = camera.viewportWidth * i;
//        }
//    }
//
//
//    @Override
//    public void show() {
//        // Initialize resources like textures
//    }
//
//    @Override
//    public void hide() {
//        // Dispose of resources or hide elements
//    }
//
//    @Override
//    public void pause() {
//        // Implement pause logic
//        game.pause();
//    }
//
//    @Override
//    public void resume() {
//        // Implement resume logic
//        game.resume();
//    }
//
//    public void restart() {
//        game.restart();
//    }
//
//    public void end() {
//        dispose();
//        game.end();
//    }
//
//    @Override
//    public void dispose() {
//        // Dispose of game resources
//        soundManager.dispose();
//        dinosaur.dispose();
//        for (Texture background : backgrounds) {
//            background.dispose();
//        }
//        for (EntityManager obstacle : obstacles) {
//            obstacle.dispose();
//        }
//    }
//}
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
    private OrthographicCamera camera;
    private Dinosaur dinosaur;
    private Texture[] backgrounds;
    private float[] backgroundOffset;
    private ArrayList<EntityManager> obstacles = new ArrayList<EntityManager>();
    private float timeSinceLastObstacle;
    private Random random = new Random();
    private float minTimeBetweenObstacles;
    private float maxTimeBetweenObstacles;
    private float timeUntilNextObstacle = getRandomSpawnTime();
    private String[] obstacleTextures = new String[]{"cactus1.png", "cactus2.png"};
    private static final float groundYPosition = 50;
    private static final float airYPosition = 25;
    private CollisionManager collisionManager;
    private PlayerController playerController;
    private SoundManager soundManager;
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
        dinosaur = new Dinosaur(0, 50); // Instantiation of the Dinosaur object
        soundManager = new SoundManager();
        playerController = new PlayerController(dinosaur, soundManager);
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

    public Dinosaur getDinosaur() {
        return dinosaur;
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
            game.batch.draw(groundTexture, 0, 50, Gdx.graphics.getWidth(), groundTexture.getHeight());

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
        playerController.update();
    }

    private float getRandomSpawnTime() {
        return random.nextFloat() * (maxTimeBetweenObstacles - minTimeBetweenObstacles) + minTimeBetweenObstacles;
    }

    private float calculateYPositionForObstacle(String textureName) {
        if ("cactus1.png".equals(textureName)) {
            return 0; // Ground obstacle
        } else if ("cactus2.png".equals(textureName)) {
            return 0; // Ground Obstacle
        } else if ("cloud.jpg".equals(textureName)) {
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
        game.pause();
    }

    @Override
    public void resume() {
        // Implement resume logic
        game.resume();
    }

    public void restart() {
        game.restart();
    }

    public void end() {
        dispose();
        game.end();
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
