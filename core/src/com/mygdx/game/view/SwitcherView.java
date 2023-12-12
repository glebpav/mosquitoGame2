package com.mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.utils.Difficulty;
import com.mygdx.game.utils.MemoryHelper;

public class SwitcherView extends BaseView {

    String[] switcherStates = {"easy", "medium", "hard"};
    LabelView label;
    int stateIdx;

    public SwitcherView(int x, int y, int difficultyLevel, BitmapFont bitmapFont) {
        super(x, y);
        stateIdx = difficultyLevel;
        label = new LabelView(bitmapFont, switcherStates[stateIdx], x, y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        label.setMessage(switcherStates[stateIdx]);
        label.draw(batch);
    }

    @Override
    public boolean isHit(int tx, int ty) {

        if (label.isHit(tx, ty)) {
            if (stateIdx == 0) stateIdx = 1;
            else if(stateIdx == 1) stateIdx = 2;
            else if (stateIdx == 2) stateIdx = 0;

            // System.out.println(stateIdx);
            MemoryHelper.saveDifficultyLevel(Difficulty.getDifficultyByIdx(stateIdx));

            // System.out.println("loaded value: " + MemoryHelper.loadDifficultyLevel());
            return true;
        }

        return false;

    }
}
