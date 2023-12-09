package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.view.Background;
import com.mygdx.game.view.Label;

public class MenuScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;

    Background background;
    Label titleLabel;
    Label buttonStart;

    public MenuScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        background = new Background("backgrounds/homeBG.jpg");
        titleLabel = new Label(
                myGdxGame.largeFont.bitmapFont,
                "Welcome to Mosquito game!",
                0, 700
        );

        buttonStart = new Label(
                myGdxGame.largeFont.bitmapFont,
                "Start",
                100, 0);

        titleLabel.alignCenter();



    }

    @Override
    public void render(float delta) {

        if (Gdx.input.justTouched()) {

            myGdxGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.touch = myGdxGame.camera.unproject(myGdxGame.touch);

            System.out.println("pressed");
            System.out.println("x: " + myGdxGame.touch.x);
            System.out.println("y: " + myGdxGame.touch.y);

            if (buttonStart.hit((int) myGdxGame.touch.x, (int) myGdxGame.touch.y)) {
                System.out.println("button start was pressed");
                myGdxGame.setScreen(myGdxGame.gameScreen);
            }
        }

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

        ScreenUtils.clear(1, 1, 1, 1);

        myGdxGame.batch.begin();
        background.draw(myGdxGame.batch);
        titleLabel.draw(myGdxGame.batch);
        buttonStart.draw(myGdxGame.batch);
        myGdxGame.batch.end();

    }
}
