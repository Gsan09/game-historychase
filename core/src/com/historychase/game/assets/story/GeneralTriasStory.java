package com.historychase.game.assets.story;

import com.historychase.game.assets.Constants;
import com.historychase.game.assets.Settings;

public class GeneralTriasStory implements Story {
    @Override
    public String getTitle() {
        return "General Trias Municipal Town and Old Church";
    }

    @Override
    public String[] getContents() {
        String content[] = new String[1];
        content[0] = "The site of one of the uprisings in Cavite. It was in its old church where the senior band members rehearsed the national anthem, Marcha Filipina before it was played during the declaration of the Philippine Independence on June 12, 1898.";
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
        return Constants.Path.STORY_15;
    }

    @Override
    public float getMaxScroll() {
        return 95.0f;
    }
}
