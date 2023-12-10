package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.loaders.MusicLoader;
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

import java.util.ArrayList;

import javax.swing.text.View;

public class GameScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;

    GameState gameState;
    Difficulty difficulty;

    long sessionTimeBegin;

    ArrayList<BaseView> viewGameArray;
    ArrayList<BaseView> viewPassedArray;
    ArrayList<BaseView> viewLoosedArray;
    ArrayList<BaseView> viewPausedArray;

    ArrayList<String> mosquitoTilesPathArray;

    ArrayList<CharacterView> mosquitoArray;

    LabelView timeView;

    public GameScreen(final MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        viewGameArray = new ArrayList<>();
        viewLoosedArray = new ArrayList<>();
        viewPassedArray = new ArrayList<>();
        viewPausedArray = new ArrayList<>();

        mosquitoArray = new ArrayList<>();
        mosquitoTilesPathArray = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            mosquitoTilesPathArray.add("tiles/mosq" + i + ".png");
        }

        viewPassedArray.add(new Blackout());

        viewPassedArray.add(new LabelView(
                myGdxGame.largeFont.bitmapFont,
                "Our congratulations!",
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

        for (int i = 0; i < difficulty.countOfMosquito; i++) {
            double angle = Math.random() * 2 * Math.PI;
            CharacterView characterView = new CharacterView(
                    GameSettings.SCREEN_WIDTH / 2,
                    GameSettings.SCREEN_HEIGHT / 2,
                    150, 150,
                    Math.cos(angle) * difficulty.speed,
                    Math.sin(angle) * difficulty.speed,
                    mosquitoTilesPathArray,
                    "tiles/mosq10.png"
            );
            characterView.setOnKillListener(onKillListener);
            characterView.setOnMosquitoClicked(onClickMosquitoListener);
            mosquitoArray.add(characterView);
            viewGameArray.add(characterView);
        }

        gameState = GameState.IS_PLAYING;
        sessionTimeBegin = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {

        handleInput();

        for (CharacterView character : mosquitoArray)
            character.move();

        ScreenUtils.clear(Color.WHITE);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

        myGdxGame.batch.begin();

        if (gameState == GameState.LOOSED) {
            for (BaseView view : viewLoosedArray)
                view.draw(myGdxGame.batch);
        }

        if (gameState == GameState.ON_PAUSE) {
            for (BaseView view : viewPausedArray)
                view.draw(myGdxGame.batch);
        }

        if (gameState == GameState.IS_PLAYING || gameState == GameState.PASSED) {
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
}
