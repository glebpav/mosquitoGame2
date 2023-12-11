package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.utils.Difficulty;
import com.mygdx.game.utils.GameSettings;
import com.mygdx.game.utils.MemoryHelper;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.SoundsHelper;
import com.mygdx.game.view.BackgroundView;
import com.mygdx.game.view.BaseView;
import com.mygdx.game.view.Blackout;
import com.mygdx.game.view.CharacterView;
import com.mygdx.game.view.LabelView;
import com.mygdx.game.view.ProgressBarView;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;

    GameState gameState;
    Difficulty difficulty;

    long sessionTimeBegin;
    float healthPoints;

    ArrayList<BaseView> viewGameArray;
    ArrayList<BaseView> viewPassedArray;
    ArrayList<BaseView> viewLoosedArray;
    ArrayList<BaseView> viewPausedArray;

    ArrayList<String> mosquitoTilesPathArray;
    ArrayList<String> butterflyTilesPathArray;

    ArrayList<CharacterView> mosquitoArray;
    ArrayList<CharacterView> butterflyArray;

    LabelView timeView;
    ProgressBarView progressBarView;

    public GameScreen(final MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        viewGameArray = new ArrayList<>();
        viewLoosedArray = new ArrayList<>();
        viewPassedArray = new ArrayList<>();
        viewPausedArray = new ArrayList<>();

        butterflyArray = new ArrayList<>();
        mosquitoArray = new ArrayList<>();
        butterflyTilesPathArray = new ArrayList<>();
        mosquitoTilesPathArray = new ArrayList<>();

        for (int i = 0; i < 10; i++)
            mosquitoTilesPathArray.add("tiles/mosq" + i + ".png");

        for (int i = 0; i < 5; i++)
            butterflyTilesPathArray.add("tiles/butterFly" + i + ".png");

        viewPassedArray.add(new Blackout());
        viewLoosedArray.add(new Blackout());

        viewPassedArray.add(new LabelView(
                myGdxGame.largeFont.bitmapFont,
                "Our congratulations!",
                -1, 900
        ));

        viewLoosedArray.add(new LabelView(
                myGdxGame.largeFont.bitmapFont,
                "Whooops, you loosed(((",
                -1, 900
        ));

        LabelView buttonReturnMenu = new LabelView(
                myGdxGame.accentFont.bitmapFont,
                "Return home",
                200, 300
        );

        buttonReturnMenu.setOnClickListener(new BaseView.OnClickListener() {
            @Override
            public void onClick() {
                myGdxGame.setScreen(myGdxGame.menuScreen);
            }
        });

        viewPassedArray.add(buttonReturnMenu);
        viewLoosedArray.add(buttonReturnMenu);

        timeView = new LabelView(
                myGdxGame.commonFont.bitmapFont,
                "session time: ",
                200, 500
        );

        viewPassedArray.add(timeView);
    }

    @Override
    public void show() {
        difficulty = MemoryHelper.loadDifficultyLevel();
        mosquitoArray.clear();
        viewGameArray.clear();

        viewGameArray.add(new BackgroundView("backgrounds/gameBG.jpg"));

        progressBarView = new ProgressBarView(
                100, 30,
                300, 40,
                difficulty.healthPoints
        );

        viewGameArray.add(progressBarView);

        for (int i = 0; i < difficulty.countOfMosquito; i++) {

            double angle = Math.random() * 2 * Math.PI;

            CharacterView mosquitoView = new CharacterView(
                    GameSettings.SCREEN_WIDTH / 2,
                    GameSettings.SCREEN_HEIGHT / 2,
                    150, 150,
                    Math.cos(angle) * difficulty.getSpeed(),
                    Math.sin(angle) * difficulty.getSpeed(),
                    mosquitoTilesPathArray,
                    "tiles/mosq10.png"
            );

            angle = Math.random() * 2 * Math.PI;

            CharacterView butterflyView = new CharacterView(
                    GameSettings.SCREEN_WIDTH / 2,
                    GameSettings.SCREEN_HEIGHT / 2,
                    150, 150,
                    Math.cos(angle) * difficulty.getSpeed(),
                    Math.sin(angle) * difficulty.getSpeed(),
                    butterflyTilesPathArray
            );

            butterflyView.setOnClickListener(onButterflyClickListener);
            mosquitoView.setOnKillListener(onKillListener);
            mosquitoView.setOnMosquitoClicked(onClickMosquitoListener);

            mosquitoArray.add(mosquitoView);
            butterflyArray.add(butterflyView);
            viewGameArray.add(mosquitoView);
            viewGameArray.add(butterflyView);
        }

        gameState = GameState.IS_PLAYING;
        healthPoints = difficulty.healthPoints;
        sessionTimeBegin = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {

        handleInput();

        for (CharacterView character : mosquitoArray)
            character.move();

        for (CharacterView characterView : butterflyArray)
            characterView.move();

        ScreenUtils.clear(Color.WHITE);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

        myGdxGame.batch.begin();

        if (gameState == GameState.ON_PAUSE) {
            for (BaseView view : viewPausedArray)
                view.draw(myGdxGame.batch);
        }

        if (gameState == GameState.IS_PLAYING
                || gameState == GameState.PASSED
                || gameState == GameState.LOOSED) {
            int previousSize = viewGameArray.size();
            for (int i = 0; i < viewGameArray.size(); i++) {
                viewGameArray.get(i).draw(myGdxGame.batch);
                if (previousSize != viewGameArray.size()) {
                    i--;
                    previousSize = viewGameArray.size();
                }
            }
            if (gameState == GameState.PASSED) {
                for (BaseView view : viewPassedArray)
                    view.draw(myGdxGame.batch);
            }
            if (gameState == GameState.LOOSED) {
                for (BaseView view : viewLoosedArray)
                    view.draw(myGdxGame.batch);
            }
        }

        myGdxGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        myGdxGame.viewport.update(width, height);
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            int previousSize = viewGameArray.size();
            for (int i = 0; i < viewGameArray.size(); i++) {
                viewGameArray.get(i).isHit((int) myGdxGame.touch.x, (int) myGdxGame.touch.y);
                if (previousSize != viewGameArray.size()) {
                    i--;
                    previousSize = viewGameArray.size();
                }
            }

            if (gameState == GameState.PASSED) {
                for (BaseView view : viewPassedArray)
                    view.isHit((int) myGdxGame.touch.x, (int) myGdxGame.touch.y);
            }

            if (gameState == GameState.LOOSED) {
                for (BaseView view : viewLoosedArray)
                    view.isHit((int) myGdxGame.touch.x, (int) myGdxGame.touch.y);
            }

        }
    }

    CharacterView.OnKillListener onKillListener = new CharacterView.OnKillListener() {
        @Override
        public void onKilled(CharacterView view) {
            mosquitoArray.remove(view);
            viewGameArray.remove(view);
            view.dispose();
            if (mosquitoArray.size() == 0) {
                gameState = GameState.PASSED;
                timeView.setMessage("session time: " + (TimeUtils.millis() - sessionTimeBegin) / 1000);
            }
        }
    };

    CharacterView.OnMosquitoClicked onClickMosquitoListener = new CharacterView.OnMosquitoClicked() {
        @Override
        public void onClicked(CharacterView view) {
            if (view.isAlive) {
                SoundsHelper.playMosquitoSound();
            }
        }
    };

    BaseView.OnClickListener onButterflyClickListener = new BaseView.OnClickListener() {
        @Override
        public void onClick() {
            healthPoints -= GameSettings.BUTTERFLY_DAMAGE;
            if (healthPoints <= 0) gameState = GameState.LOOSED;
            progressBarView.update(healthPoints);
        }
    };
}
