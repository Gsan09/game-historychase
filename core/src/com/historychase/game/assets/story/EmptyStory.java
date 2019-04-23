package com.historychase.game.assets.story;

public class EmptyStory implements Story{

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public String[] getContents() {
        return new String[0];
    }

    @Override
    public boolean isAllowed() {
        return false;
    }

    @Override
    public String getDisabledTitle() {
        return null;
    }

    @Override
    public String getImagePath() {
        return null;
    }

    @Override
    public float getMaxScroll() {
        return 0;
    }
}
