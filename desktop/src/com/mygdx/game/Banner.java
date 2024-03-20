//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;

public class Banner {
    private Texture texture;
    private Vector2 pos;
    private Vector2 closePos;
    private Texture closeTexture;
    private final Texture backgroundTexture;
    private final OrthographicCamera camera;

    public Banner(Vector2 pos) {
        this.pos = pos;
        Random random = new Random();
        int r = random.nextInt(6) + 1;
        this.texture = new Texture("banners/funfact" + r + ".png");
        this.closeTexture = new Texture("banners/close.png");
        this.closePos = new Vector2(pos.x - 25.0F, pos.y + 400.0F - 25.0F);
        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 500);
    }

    public Vector2 getPos() {
        return this.pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }


    public void draw(SpriteBatch batch) {
        batch.draw(backgroundTexture, 0, 0, camera.viewportWidth, camera.viewportHeight);
        batch.draw(this.texture, this.pos.x, this.pos.y, 400.0F, 400.0F);
        batch.draw(this.closeTexture, this.closePos.x, this.closePos.y, 50.0F, 50.0F);
//        float backgroundX = camera.position.x - camera.viewportWidth / 2f;
//        float backgroundY = camera.position.y - camera.viewportHeight / 2f;
//        batch.draw(background, backgroundX, backgroundY, camera.viewportWidth, camera.viewportHeight);
//        batch.draw(land, 0, 50, Gdx.graphics.getWidth(), land.getHeight());

    }

    public Texture getTexture() {
        return this.texture;
    }

    public Vector2 getClosePos() {
        return this.closePos;
    }

    public void setClosePos(Vector2 closePos) {
        this.closePos = closePos;
    }
}
