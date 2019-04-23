package com.historychase.game.assets.story;

import com.historychase.game.assets.Constants;
import com.historychase.game.assets.Settings;

public class DonLadislawStory implements Story {
    @Override
    public String getTitle() {
        return "Don Ladislaw Diwa Monument";
    }

    @Override
    public String[] getContents() {
        String content[] = new String[1];
        content[0] = "Don Ladislao Diwa is well remembered as the co-founder of the KKK (Kataastaasang, Kagalanggalangan, Katipunan). The National Historical Institute(NHI) declared his ancestral home as a national shrine. On November 30, l996, his mortal remains were transferred to the Ladislao Diwa Mausoleum on the grounds of the Shrine.";
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
        return Constants.Path.STORY_13;
    }

    @Override
    public float getMaxScroll() {
        return 95.0f;
    }
}
