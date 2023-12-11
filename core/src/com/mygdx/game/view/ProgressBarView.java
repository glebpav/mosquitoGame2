package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ProgressBarView extends BaseView {

    Texture backBar;
    Texture frontBar;

    int maxWidth;
    float maxValue;

    public ProgressBarView(int x, int y, int width, int height, float maxValue) {
        super(x, y, width, height);

        maxWidth = width;
        this.maxValue= maxValue;

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 0, 0, 1);
        pixmap.fill();
        backBar = new Texture(pixmap);

        pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 1, 0, 1);
        pixmap.fill();
        frontBar = new Texture(pixmap);
    }

    public void update(float newValue) {
        if (newValue <= 0) width = 0;
        else width = (int) (maxWidth * (newValue / maxValue));
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(backBar, x, y, maxWidth, height);
        batch.draw(frontBar, x, y, width, height);
    }
}
