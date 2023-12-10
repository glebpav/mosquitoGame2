package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.utils.GameSettings;

public class BackgroundView extends BaseView implements Disposable {

    Texture texture;
    String pathToTexture;

    public BackgroundView(String pathToTexture) {
        super(0, 0, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
        this.pathToTexture = pathToTexture;
        texture = new Texture(pathToTexture);
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, x, y, width, height);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

}
