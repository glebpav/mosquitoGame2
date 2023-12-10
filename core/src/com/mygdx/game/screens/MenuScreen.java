package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.view.BackgroundView;
import com.mygdx.game.view.BaseView;
import com.mygdx.game.view.LabelView;

import java.util.ArrayList;

public class MenuScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;
    ArrayList<BaseView> viewArray;

    public MenuScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        viewArray = new ArrayList<>();

        BackgroundView background = new BackgroundView("backgrounds/homeBG.jpg");
        LabelView titleLabel = new LabelView(
                myGdxGame.largeFont.bitmapFont,
                "Welcome to Mosquito game!",
                0, 900
        );
        LabelView buttonStart = new LabelView(
                myGdxGame.commonFont.bitmapFont,
                "Start",
                100, 500);

        LabelView buttonAbout = new LabelView(
                myGdxGame.commonFont.bitmapFont,
                "About",
                100, 300);

        LabelView buttonSettings = new LabelView(
                myGdxGame.commonFont.bitmapFont,
                "Settings",
                100, 400);

        LabelView buttonExit = new LabelView(
                myGdxGame.commonFont.bitmapFont,
                "Exit",
                100, 200);

        titleLabel.alignCenter();

        buttonAbout.setOnClickListener(buttonAboutClicked);
        buttonExit.setOnClickListener(buttonExitClicked);
        buttonSettings.setOnClickListener(buttonSettingsClicked);
        buttonStart.setOnClickListener(buttonStartClicked);

        viewArray.add(background);
        viewArray.add(titleLabel);
        viewArray.add(buttonExit);
        viewArray.add(buttonAbout);
        viewArray.add(buttonSettings);
        viewArray.add(buttonStart);

    }

    @Override
    public void render(float delta) {

        handleInput();

        ScreenUtils.clear(1, 1, 1, 1);

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

        myGdxGame.batch.begin();

        for (BaseView view : viewArray) {
            view.draw(myGdxGame.batch);
        }

        myGdxGame.batch.end();

    }

    void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.touch = myGdxGame.camera.unproject(myGdxGame.touch);

            for (BaseView view : viewArray) {
                view.isHit((int) myGdxGame.touch.x, (int) myGdxGame.touch.y);
            }
        }
    }

    BaseView.OnClickListener buttonStartClicked = new BaseView.OnClickListener() {
        @Override
        public void onClick() {
            System.out.println("button start was pressed");
            myGdxGame.setScreen(myGdxGame.gameScreen);
        }
    };

    BaseView.OnClickListener buttonExitClicked = new BaseView.OnClickListener() {
        @Override
        public void onClick() {
            Gdx.app.exit();
        }
    };

    BaseView.OnClickListener buttonSettingsClicked = new BaseView.OnClickListener() {
        @Override
        public void onClick() {
            myGdxGame.setScreen(myGdxGame.settingsScreen);
        }
    };

    BaseView.OnClickListener buttonAboutClicked = new BaseView.OnClickListener() {
        @Override
        public void onClick() {
            myGdxGame.setScreen(myGdxGame.aboutScreen);
        }
    };

}
