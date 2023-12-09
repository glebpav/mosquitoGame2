package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;
import com.mygdx.game.screens.SettingsScreen;

public class Background {

    Texture texture;
    String pathToTexture;

    public Background(String pathToTexture) {
        this.pathToTexture = pathToTexture;
        texture = new Texture(pathToTexture);
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, 0, 0, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
    }

    public void dispose() {
        texture.dispose();
    }

}
