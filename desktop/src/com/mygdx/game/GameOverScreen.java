package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.Preferences;

public class GameOverScreen implements Screen {
    private final GameEngine game;
    private final SpriteBatch batch;
    private final Texture dinoTexture;
    private final Texture restartTexture;
    private final BitmapFont font;
    private final FreeTypeFontGenerator generator;
    private float imgX;
    private float imgY;
    private final OrthographicCamera camera;
    private final Dinosaur dinosaur;
    private int highScore;
    private int currentHighScore;
    private Preferences prefs;

    public GameOverScreen(final GameEngine game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;
        this.dinosaur = game.getDinosaur();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Create font to draw text
        generator = new FreeTypeFontGenerator(Gdx.files.internal("PressStart2P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 14;
        parameter.color = Color.DARK_GRAY;
        font = generator.generateFont(parameter);

        dinoTexture = new Texture("main-character1.png");
        restartTexture = new Texture("replay_button.png");

        // Load the high score
        prefs = Gdx.app.getPreferences("DinoGamePreferences");
        highScore = prefs.getInteger("highScore", 0); // Load the high score, default to 0 if not found
        currentHighScore = prefs.getInteger("highScore", 0);
    }

    @Override
    public void show() {
        // Get the updated high score when showing the screen
        highScore = prefs.getInteger("highScore", 0);
        currentHighScore = prefs.getInteger("highScore", 0);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        int currentHighScore = prefs.getInteger("highScore", 0);
        if (dinosaur.getScore() > currentHighScore) {
            highScore = dinosaur.getScore();
            prefs.putInteger("highScore", highScore);
            prefs.flush();
        }

        //int currentHighScore = prefs.getInteger("highScore", 0);

        batch.begin();

        float centerX = camera.viewportWidth / 2f;
        float centerY = camera.viewportHeight / 2f;

        imgX = centerX - (float) restartTexture.getWidth() / 2;
        imgY = centerY - ((float) restartTexture.getHeight() / 2) - 50;

        batch.draw(dinoTexture, centerX - (float) dinoTexture.getWidth() / 2, centerY - 120 - (float) dinoTexture.getHeight() / 2);
        batch.draw(restartTexture, imgX, imgY);
        font.draw(batch, "Game Over", centerX - 50, centerY + 70);
        font.draw(batch, "Your score: " + dinosaur.getScore(), centerX - 100, centerY + 30);
//         Draw the high score
        font.draw(batch, "High Score: " + currentHighScore, centerX - 110, centerY - 10);

        // Set up input processor
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                // Convert screen coordinates to world coordinates
                float worldY = Gdx.graphics.getHeight() - screenY;

                // Check if the touch position is within the bounds of the restart button
                if ((float) screenX >= imgX && (float) screenX <= imgX + restartTexture.getWidth() &&
                        worldY >= imgY && worldY <= imgY + restartTexture.getHeight()) {
                    game.restart();
                }
                return true;
            }
        });

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 800; // You may adjust this according to your desired initial viewport width
        camera.viewportHeight = (float) (800 * height) / width; // Maintain aspect ratio
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
        restartTexture.dispose();
    }
}
