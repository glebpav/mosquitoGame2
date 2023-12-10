package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.utils.GameSettings;
import com.mygdx.game.utils.SoundsHelper;

import java.util.ArrayList;

public class CharacterView extends BaseView implements Movable, Disposable {

    public boolean isAlive;

    long killedTime;

    ArrayList<Texture> textureList;

    OnKillListener onKillListener;
    OnMosquitoClicked onMosquitoClicked;

    Texture deathTile;

    int frameCounter;

    double velocityX;
    double velocityY;

    int frameMultiplexer;

    public CharacterView(int x, int y, int width, int height, double velocityX, double velocityY,
                         ArrayList<String> pathsList, String pathToDeathTile) {
        super(x, y, width, height);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        textureList = new ArrayList<>();

        frameMultiplexer = 2;

        for (String path : pathsList)
            textureList.add(new Texture(path));

        deathTile = new Texture(pathToDeathTile);

        frameCounter = 0;
        isAlive = true;
    }

    public void setOnKillListener(OnKillListener onKillListener) {
        this.onKillListener = onKillListener;
    }

    public void setOnMosquitoClicked(OnMosquitoClicked onMosquitoClicked) {
        this.onMosquitoClicked = onMosquitoClicked;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (isAlive) {
            if (frameCounter / frameMultiplexer == textureList.size()) frameCounter = 0;
            batch.draw(textureList.get(frameCounter / frameMultiplexer), x, y, width, height);
            frameCounter += 1;
        } else {
            batch.draw(deathTile, x, y, width, height);
            if (TimeUtils.millis() - killedTime >= 2000) onKillListener.onKilled(this);
        }
    }

    @Override
    public boolean isHit(int tx, int ty) {
        if (super.isHit(tx, ty) && isAlive) {
            onMosquitoClicked.onClicked(this);
            velocityX = 0;
            velocityY = 0;
            isAlive = false;
            killedTime = TimeUtils.millis();
            return true;
        }
        return false;
    }

    @Override
    public void move() {
        x += velocityX;
        y += velocityY;

        if (x <= 0 || x + width >= GameSettings.SCREEN_WIDTH) velocityX *= -1;
        if (y <= 0 || y + width >= GameSettings.SCREEN_HEIGHT) velocityY *= -1;
    }

    @Override
    public void dispose() {
        for (Texture texture : textureList) {
            texture.dispose();
        }
    }

    public interface OnKillListener {
        void onKilled(CharacterView view);
    }

    public interface OnMosquitoClicked {
        void onClicked(CharacterView view);
    }
}
