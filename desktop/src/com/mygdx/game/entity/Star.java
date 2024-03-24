package com.mygdx.game.entity;

public class Star extends Obstacle {

    private final String textureFileName;

    public Star(float x, float y, String textureFileName, float speed) {
        super(x, y, textureFileName);
        this.setSpeed(speed); // Assign speed to a class field or directly to the movement logic
        this.textureFileName = textureFileName;
    }

    public String getTextureFileName() {
        return textureFileName;
    }
}