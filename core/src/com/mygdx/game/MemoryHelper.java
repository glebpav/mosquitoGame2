package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class MemoryHelper {

    private static final Preferences prefs = Gdx.app.getPreferences("User saves");

    public static void saveDifficultyLevel(Difficulty difficulty) {
        if (difficulty == null) return;
        System.out.println("save difficulty level: " + difficulty.difficultyIdx);
        prefs.putInteger("difficultyLevel", difficulty.difficultyIdx);
    }

    public static Difficulty loadDifficultyLevel() {
        if (prefs.contains("difficultyLevel")) {
            int idx = prefs.getInteger("difficultyLevel");
            if (idx == 0) return Difficulty.EASY;
            else if (idx == 1) return Difficulty.MEDIUM;
            else if (idx == 2) return Difficulty.HARD;
        }
        saveDifficultyLevel(GameSettings.DEFAULT_DIFFICULTY_LEVEL);
        return GameSettings.DEFAULT_DIFFICULTY_LEVEL;
    }

}
