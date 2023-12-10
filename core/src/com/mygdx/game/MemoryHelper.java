package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class MemoryHelper {

    private static final Preferences prefs = Gdx.app.getPreferences("User saves");


    public static void saveDifficultyLevel(int difficultyLevel) {
        System.out.println("save difficulty level: " + difficultyLevel);
        prefs.putString("difficultyLevel", String.valueOf(difficultyLevel));
    }

    public static int loadDifficultyLevel() {
        if (prefs.contains("difficultyLevel")) {
            return Integer.parseInt(prefs.getString("difficultyLevel"));
        }
        saveDifficultyLevel(GameSettings.DEFAULT_DIFFICULTY_LEVEL);
        return GameSettings.DEFAULT_DIFFICULTY_LEVEL;
    }

}
