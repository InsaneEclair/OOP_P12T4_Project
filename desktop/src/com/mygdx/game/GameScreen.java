package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class GameScreen implements Screen {
	private static final int MOVEMENT = 100; // Increase the speed from 100 to 150
	final GameEngine game;
    OrthographicCamera camera;
    Dinosaur dinosaur;
    private Texture[] backgrounds;
    private float[] backgroundOffset;


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
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            dinosaur.jump();
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
        game.batch.draw(dinosaur.texture, dinosaur.position.x, dinosaur.position.y);
        game.batch.end();

        Gdx.app.log("Dinosaur Position", dinosaur.position.toString());
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
    }
}
