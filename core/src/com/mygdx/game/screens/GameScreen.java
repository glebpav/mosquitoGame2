package com.mygdx.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.mygdx.game.Difficulty;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MemoryHelper;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.view.BackgroundView;
import com.mygdx.game.view.BaseView;
import com.mygdx.game.view.CharacterView;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;

    Difficulty difficulty;

    ArrayList<BaseView> viewArray;

    ArrayList<CharacterView> mosquitoArray;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        viewArray = new ArrayList<>();

        mosquitoArray = new ArrayList<>();

        viewArray.add(new BackgroundView("backgrounds/gameBG.jpg"));
    }

    @Override
    public void show() {
        difficulty = MemoryHelper.loadDifficultyLevel();
        mosquitoArray.clear();
        for (int i = 0; i < difficulty.countOfMosquito; i++) {

            double angle = Math.random() * 2 * Math.PI;

           /* mosquitoArray.add(new CharacterView(
                    GameSettings.SCREEN_WIDTH / 2,
                    GameSettings.SCREEN_HEIGHT / 2,
                    150, 150,
                    Math.cos(angle) * difficulty.speed
            ));*/
        }
    }

    @Override
    public void render(float delta) {

    }
}
