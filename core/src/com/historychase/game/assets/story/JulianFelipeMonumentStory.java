package com.historychase.game.assets.story;

import com.historychase.game.assets.Constants;
import com.historychase.game.assets.Settings;

public class JulianFelipeMonumentStory implements Story {
    @Override
    public String getTitle() {
        return "Julian Felipe Monument";
    }

    @Override
    public String[] getContents() {
        String content[] = new String[1];
        content[0] = "A monument stands proud for a great son of a Caviteño, the composer of the Philippine National Anthem  Professor Julian Felipe. At present, the Filipino lyrics of the stirring composition of Don Julian Felipe was supplied by Jose Palma. January 28, the birth anniversary of Professor Felipe was declared Special Public Holiday in the City of Cavite by virtue of Republic Act 7805 approved on July 26, 1993.";
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
        return Constants.Path.STORY_12;
    }

    @Override
    public float getMaxScroll() {
        return 107.5f;
    }
}
