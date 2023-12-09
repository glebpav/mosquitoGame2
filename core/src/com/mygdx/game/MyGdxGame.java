package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.screens.AboutScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.SettingsScreen;

public class MyGdxGame extends Game {
    public SpriteBatch batch;

    public OrthographicCamera camera;
    public Vector3 touch;

    public FontHelper largeFont;
    public FontHelper commonFont;

    public MenuScreen menuScreen;
    public GameScreen gameScreen;
    public AboutScreen aboutScreen;
    public SettingsScreen settingsScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();
        touch = new Vector3();
        camera = new OrthographicCamera(GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
        camera.setToOrtho(false);

        commonFont = new FontHelper(50, "fonts/arnamu.ttf", Color.WHITE);
        largeFont = new FontHelper(100, "fonts/arnamu.ttf", Color.WHITE);

        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);
        aboutScreen = new AboutScreen(this);
        settingsScreen = new SettingsScreen(this);

        setScreen(menuScreen);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
