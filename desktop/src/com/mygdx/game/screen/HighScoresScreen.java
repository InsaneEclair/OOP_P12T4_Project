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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.GameEngine;
import com.mygdx.game.manager.DataManager;

import java.util.Comparator;
import java.util.List;

public class HighScoresScreen implements Screen {
    private final GameEngine game;
    private final SpriteBatch batch;

    private final Texture backgroundTexture;
    private final BitmapFont font;
    private final FreeTypeFontGenerator generator;

    private final OrthographicCamera camera;

    ShapeRenderer shapeRenderer = new ShapeRenderer();
    List<PlayerScore> playerScores;
    public HighScoresScreen(final GameEngine game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 290);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/PressStart2P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 11;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        font = generator.generateFont(parameter);

        backgroundTexture = new Texture("background/HighScoresBg.png"); // Load the texture

        playerScores= DataManager.get().readScores();
        playerScores.sort(new Comparator<PlayerScore>() {
            @Override
            public int compare(PlayerScore o1, PlayerScore o2) {
                return o2.getScore() - o1.getScore() ;
            }
        });
        System.out.println(playerScores);

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

        // Draw the return instruction
        font.draw(batch, "Press Esc to return to the main menu.", centerX - 265, 50);
        batch.end();

        int y = Gdx.graphics.getHeight() - 100;
        int x= 200;
        int textY = Gdx.graphics.getHeight() - 235;
        for(int i = 0 ; i < 6 ;i++){
            if(i >= playerScores.size()){
                break;
            }
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.rect(x,y,200,50);
            shapeRenderer.rect(x,y,400,50);
            shapeRenderer.end();

            batch.begin();
            font.draw(batch, playerScores.get(i).name, x + 80,textY);
            font.draw(batch, String.valueOf(playerScores.get(i).score), x + 250,textY);
            batch.end();
            y -= 50;
            textY -= 28;
        }


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
