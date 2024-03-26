package com.mygdx.game.entity;

public class Star extends Entity {

    private final String textureFileName;

    public Star(float x, float y, String textureFileName, float obstacleSpeed) {
        super(x, y, textureFileName);
        this.textureFileName = textureFileName;
        setMovement((int) obstacleSpeed);
    }
    public String getTextureFileName() {
        return textureFileName;
    }
}