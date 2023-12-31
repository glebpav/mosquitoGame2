package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.utils.MemoryHelper;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.view.BackgroundView;
import com.mygdx.game.view.BaseView;
import com.mygdx.game.view.ImageView;
import com.mygdx.game.view.LabelView;
import com.mygdx.game.view.SliderView;
import com.mygdx.game.view.SwitcherView;

import java.util.ArrayList;

public class SettingsScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;

    SliderView sliderView;

    ArrayList<BaseView> viewArray;

    public SettingsScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        viewArray = new ArrayList<>();

        BackgroundView background = new BackgroundView("backgrounds/settingsBG.jpg");
        LabelView titleLabel = new LabelView(myGdxGame.largeFont.bitmapFont, "Settings", 0, 900);
        LabelView difficultyLabel = new LabelView(myGdxGame.commonFont.bitmapFont, "difficulty: ", 100, 500);
        LabelView soundLabel = new LabelView(myGdxGame.commonFont.bitmapFont, "sounds: ", 100, 300);
        SwitcherView switcher = new SwitcherView(350, 500, MemoryHelper.loadDifficultyLevel().difficultyIdx ,myGdxGame.accentFont.bitmapFont);
        ImageView buttonBack = new ImageView(100, 900, 100, 100, "icons/backIcon.png");
        sliderView = new SliderView(350, 300, 300, 60);

        titleLabel.alignCenter();

        buttonBack.setOnClickListener(onButtonBackClicked);
        sliderView.setOnSliderNewValueListener(onSliderNewValueListener);

        viewArray.add(background);
        viewArray.add(titleLabel);
        viewArray.add(difficultyLabel);
        viewArray.add(switcher);
        viewArray.add(buttonBack);
        viewArray.add(soundLabel);
        viewArray.add(sliderView);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new SliderView.SliderInputProcessor(myGdxGame.camera, sliderView));
    }

    @Override
    public void hide() {
        // System.out.println("screen settings is hided");
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {

        handleInput();

        ScreenUtils.clear(Color.WHITE);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

        myGdxGame.batch.begin();

       for (BaseView view : viewArray)
           view.draw(myGdxGame.batch);

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

    BaseView.OnClickListener onButtonBackClicked = new BaseView.OnClickListener() {
        @Override
        public void onClick() {
            myGdxGame.setScreen(myGdxGame.menuScreen);
        }
    };

    SliderView.OnSliderNewValueListener onSliderNewValueListener = new SliderView.OnSliderNewValueListener() {
        @Override
        public void onNewValue(float newValue) {
            System.out.println("new value: " + newValue);
        }
    };
}
