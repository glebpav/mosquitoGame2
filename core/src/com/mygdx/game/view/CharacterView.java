package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class CharacterView extends BaseView {

    ArrayList<Texture> textureList;

    ArrayList<String> pathsList;

    int frameCounter;

    int velocityX;
    int velocityY;

    int frameMultiplexer;

    public CharacterView(int x, int y, int width, int height, int velocityX, int velocityY,
                         ArrayList<String> pathsList) {
        super(x, y, width, height);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.pathsList = pathsList;
        textureList = new ArrayList<>();

        frameMultiplexer = 20;

        for (String path : pathsList)
            textureList.add(new Texture(path));

        frameCounter = 0;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (frameCounter / frameMultiplexer == textureList.size()) frameCounter = 0;
        batch.draw(textureList.get(frameCounter / frameMultiplexer), x, y, width, height);
        frameCounter += 1;
    }

    @Override
    public boolean isHit(int tx, int ty) {
        return super.isHit(tx, ty);
    }
}
