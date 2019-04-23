package com.historychase.game.assets.story;

import com.historychase.game.assets.Constants;
import com.historychase.game.assets.Settings;

public class ImusCathedralStory implements Story {
    @Override
    public String getTitle() {
        return "Imus Cathedral";
    }

    @Override
    public String[] getContents() {
        String content[] = new String[3];
        content[0] = "Gen. Emilio Aguinaldos revolutionary army laid siege on the Imus Church (now Cathedral) to capture the friars but found to have fled to the recollect Estate House after the capture of the Tribunal of Kawit on August 31, 1896.";
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
        return Constants.Path.STORY_11;
    }

    @Override
    public float getMaxScroll() {
        return 69.375f;
    }
}
