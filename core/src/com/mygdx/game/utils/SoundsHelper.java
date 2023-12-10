package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

public class SoundsHelper {

    /*

    1. Implement butterfly
    2. Implement Progress bar
    3. State paused
    4. State loosed

     */

    static Music backSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/background.mp3"));

    static Sound[] mosquitoesSounds = {
            Gdx.audio.newSound(Gdx.files.internal("sounds/mosq0.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("sounds/mosq1.mp3"))
    };

    public static void playBackSound() {
        backSound.play();
        backSound.setLooping(true);
    }

    public static void stopPlaying() {
        backSound.stop();
    }

    public static void playMosquitoSound() {
        mosquitoesSounds[MathUtils.random(0, mosquitoesSounds.length - 1)].play();
    }

}
