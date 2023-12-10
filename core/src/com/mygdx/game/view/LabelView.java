package com.mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.GameSettings;

public class LabelView extends BaseView implements Disposable {

    BitmapFont font;
    String message;

    public LabelView(BitmapFont font, String message, int x, int y) {
        super(x, y);
        this.font = font;
        this.message = message;
        this.x = x;

        GlyphLayout gl = new GlyphLayout(font, message);
        width = (int) gl.width;
        height = (int) gl.height;

        setY(y);
    }

    public void setY(int y) {
        this.y = y;
        this.y += height;
    }

    public void alignCenter() {
        x = GameSettings.SCREEN_WIDTH / 2 - width / 2;
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        font.draw(spriteBatch, message, x, y);
    }

    @Override
    public boolean isHit(int tx, int ty) {
        if (tx > x && tx < x + width && ty < y && ty > y - height) {
            if (onClickListener != null) onClickListener.onClick();
            return true;
        }
        return false;
    }

    @Override
    public void dispose() {
        // ...
    }
}
