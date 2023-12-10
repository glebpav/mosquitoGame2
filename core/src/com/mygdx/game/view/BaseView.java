package com.mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Disposable;

public class BaseView {

    int x;
    int y;
    int width;
    int height;

    OnClickListener onClickListener;

    public BaseView(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public BaseView(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(SpriteBatch batch) {
        // ...
    }

    public boolean isHit(int tx, int ty) {
        return true;
    }

    public interface OnClickListener {
        void onClick();
    }

}
