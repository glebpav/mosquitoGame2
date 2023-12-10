package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ImageView extends BaseView{

    String pathToImage;
    Texture texture;

    public ImageView(int x, int y, int width, int height, String pathToImage) {
        super(x, y, width, height);
        this.pathToImage = pathToImage;
        texture = new Texture(pathToImage);
    }

    public ImageView(int x, int y, String pathToImage) {
        super(x, y);
        this.pathToImage = pathToImage;
        texture = new Texture(pathToImage);
        height = texture.getHeight();
        width = texture.getWidth();
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    @Override
    public boolean isHit(int tx, int ty) {
        if (tx > x && tx < x + width && ty > y && ty < y + height) {
            if (onClickListener != null) onClickListener.onClick();
            return true;
        }
        return false;
    }
}
