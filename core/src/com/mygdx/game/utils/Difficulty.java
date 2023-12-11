package com.mygdx.game.utils;

public enum Difficulty {

    EASY(
            0,
            GameSettings.EASY_COUNT_OF_MOSQUITO,
            GameSettings.EASY_COUNT_OF_BUTTERFLY,
            GameSettings.EASY_COUNT_OF_HP,
            GameSettings.EASY_SPEED
    ),
    MEDIUM(
            1,
            GameSettings.MEDIUM_COUNT_OF_MOSQUITO,
            GameSettings.MEDIUM_COUNT_OF_BUTTERFLY,
            GameSettings.MEDIUM_COUNT_OF_HP,
            GameSettings.MEDIUM_SPEED
    ),
    HARD(
            2,
            GameSettings.HARD_COUNT_OF_MOSQUITO,
            GameSettings.HARD_COUNT_OF_BUTTERFLY,
            GameSettings.HARD_COUNT_OF_HP,
            GameSettings.HARD_SPEED
    );

    public final int difficultyIdx;
    public final int countOfMosquito;
    public final int countOfButterflies;
    public final int healthPoints;
    private final int speed;

    Difficulty(int difficultyIdx, int countOfMosquito, int countOfButterFlies, int healthPoints, int speed) {
        this.difficultyIdx = difficultyIdx;
        this.countOfMosquito = countOfMosquito;
        this.countOfButterflies = countOfButterFlies;
        this.healthPoints = healthPoints;
        this.speed = speed;
    }

    public static Difficulty getDifficultyByIdx(int idx) {
        if (idx == 0) return EASY;
        else if (idx == 1) return MEDIUM;
        else if (idx == 2) return HARD;
        return null;
    }

    public float getSpeed() {
        return  (0.8f + (float) Math.random() * 0.4f) * speed;
    }
}
