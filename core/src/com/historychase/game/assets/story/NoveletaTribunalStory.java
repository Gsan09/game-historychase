package com.historychase.game.assets.story;

import com.historychase.game.assets.Constants;
import com.historychase.game.assets.Settings;

public class NoveletaTribunalStory implements Story {
    @Override
    public String getTitle() {
        return "Noveleta Tribunal";
    }

    @Override
    public String[] getContents() {
        String content[] = new String[1];
        content[0] = "On August 31, 1896, in the \"bulwagan\" of this building is where Gen. Pascual Alvarez of the Sangguniang Magdiwang killed the Guardia Civil Capt. Antonio Rebolledo. Under the order of Gen. Mariano Alvarez, the head of the group. Liutenant Francisco Naval Ayudante of the Guardia Civil was also killed, while the rest of the Guardia civil was imprisoned. This led to the revolution of Cavite";
        return content;
    }

    @Override
    public boolean isAllowed() {
        return Settings.instance.loadUserData().cleared[8];
    }

    @Override
    public String getDisabledTitle() {
        return "Clear Puzzle to Unlock";
    }

    @Override
    public String getImagePath() {
        return Constants.Path.STORY_9;
    }

    @Override
    public float getMaxScroll() {
        return 315.625f;
    }
}
