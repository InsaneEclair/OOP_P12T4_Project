package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameScreen implements Screen {
	private static final int MOVEMENT = 100;
	final GameEngine game;
    OrthographicCamera camera;
    Dinosaur dinosaur;
    private Texture[] backgrounds;
    private float[] backgroundOffset;
    private ArrayList<EntityManager> obstacles = new ArrayList<EntityManager>();
    private float timeSinceLastObstacle;
    private Random random = new Random();
    private float minTimeBetweenObstacles;
    private float maxTimeBetweenObstacles;
    private float timeUntilNextObstacle = getRandomSpawnTime();
    private String[] obstacleTextures = new String[]{"cactus1.png", "cactus2.png"};
    private static final float groundYPosition = 0;
    private static final float airYPosition = 25;

    public GameScreen(final GameEngine game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        dinosaur = new Dinosaur(0, 0); // Instantiation of the Dinosaur object
        backgrounds = new Texture[3];
        backgrounds[0] = new Texture(Gdx.files.internal("land1.png"));
        backgrounds[1] = new Texture(Gdx.files.internal("land2.png"));
        backgrounds[2] = new Texture(Gdx.files.internal("land3.png"));
        backgroundOffset = new float[backgrounds.length];
        
        for (int i = 0; i < backgrounds.length; i++) {
            backgroundOffset[i] = -i * camera.viewportWidth;
        }

        obstacles = new ArrayList<EntityManager>();
        timeSinceLastObstacle = 0;
        minTimeBetweenObstacles = 1.0f; // Minimum time in seconds until the next obstacle spawns
        maxTimeBetweenObstacles = 3.0f; // Maximum time in seconds until the next obstacle spawns
        timeUntilNextObstacle = getRandomSpawnTime();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            dinosaur.jump();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            dinosaur.duck();
        } else {
            dinosaur.standUp();
        }

        dinosaur.update(delta);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        
        game.batch.begin();
        float scaleFactor = camera.viewportWidth / (backgrounds[0].getWidth());
        float scaledHeight = backgrounds[0].getHeight() * (2*scaleFactor);

        // Draw and update backgrounds
        for (int i = 0; i < backgrounds.length; i++) {
            if (backgroundOffset[i] + backgrounds[i].getWidth() * scaleFactor <= 0) {
                backgroundOffset[i] += backgrounds[i].getWidth() * scaleFactor * backgrounds.length;
            }
            game.batch.draw(backgrounds[i], backgroundOffset[i], 0, backgrounds[i].getWidth() * scaleFactor, scaledHeight);
            backgroundOffset[i] -= MOVEMENT * delta;
        }

        // Manage obstacles
        timeSinceLastObstacle += delta;
        timeUntilNextObstacle -= delta;
        if (timeUntilNextObstacle <= 0) {
            EntityManager newObstacle;
            int obstacleType = random.nextInt(obstacleTextures.length + 1); // Assuming you have 2 cactus types + cloud
            if (obstacleType < obstacleTextures.length) {
                // Create a cactus obstacle
                String textureName = obstacleTextures[obstacleType];
                newObstacle = new Cactus(camera.viewportWidth, groundYPosition, textureName);
            } else {
                // Create a cloud obstacle
                newObstacle = new Cloud(camera.viewportWidth, airYPosition);
            }
            obstacles.add(newObstacle);
            timeSinceLastObstacle = 0;
            timeUntilNextObstacle = getRandomSpawnTime();
        }

        Iterator<EntityManager> iter = obstacles.iterator();
        while (iter.hasNext()) {
            EntityManager obstacle = iter.next();
            obstacle.update(delta);
            if (obstacle.position.x + obstacle.texture.getWidth() < 0) {
                iter.remove(); // Remove obstacle if it has passed off the screen
                obstacle.dispose();
            } else {
                game.batch.draw(obstacle.texture, obstacle.position.x, obstacle.position.y);
            }
        }
        game.batch.draw(dinosaur.getTexture(), dinosaur.position.x, dinosaur.position.y);
        game.batch.end();

        Gdx.app.log("Dinosaur Position", dinosaur.position.toString());
    }

    private float getRandomSpawnTime() {
        return random.nextFloat() * (maxTimeBetweenObstacles - minTimeBetweenObstacles) + minTimeBetweenObstacles;
    }

    private float calculateYPositionForObstacle(String textureName) {
        if ("cactus1.png".equals(textureName)) {
            return 0; // Ground obstacle
        } else if ("cactus2.png".equals(textureName)) {
            return 0; // Ground Obstacle
        } else if ("cloud.png".equals(textureName)) {
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
    	dinosaur.dispose();
        for (Texture background : backgrounds) {
            background.dispose();
        }
        for (EntityManager obstacle : obstacles) {
            obstacle.dispose();
        }
    }
}
