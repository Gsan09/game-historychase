package com.historychase.game.assets.story;

public interface Story {
    public String getTitle();
    public String[] getContents();
    public boolean isAllowed();
    public String getDisabledTitle();
    public String getImagePath();
    public float getMaxScroll();
}
