package com.historychase.game.assets.story;

import com.historychase.game.assets.Constants;
import com.historychase.game.assets.Settings;

public class CaviteCityHallStory implements Story {
    @Override
    public String getTitle() {
        return "Cavite City Hall";
    }

    @Override
    public String[] getContents() {
        String content[] = new String[1];
        content[0] = "Located in pre- World War ll site of Dreamland Cabaret and the Pantalan de Yangco, this imposing building sits on one end of the City Park. Paseo de Barangay is located at the back of City Hall.";
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
        return Constants.Path.STORY_14;
    }

    @Override
    public float getMaxScroll() {
        return 95.0f;
    }
}
