package com.mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;

import java.awt.ScrollPane;

public class Label {

    BitmapFont font;
    String message;

    int x;
    private int y;

    int width;
    int height;

    public Label(BitmapFont font, String message, int x, int y) {
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

    public void draw(SpriteBatch spriteBatch) {
        font.draw(spriteBatch, message, x, y);
    }

    public boolean hit(int tx, int ty) {
        return tx > x && tx < x + width && ty < y && ty > y - height;
    }

    public void dispose() {
        // ...
    }
}
