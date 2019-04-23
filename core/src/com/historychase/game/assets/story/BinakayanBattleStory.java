package com.historychase.game.assets.story;

import com.historychase.game.assets.Constants;
import com.historychase.game.assets.Settings;

public class BinakayanBattleStory implements Story {
    @Override
    public String getTitle() {
        return "Battle of Binakayan";
    }

    @Override
    public String[] getContents() {
        String content[] = new String[1];
        content[0] = "Battle of Binakayan fought from November 9-11, 1896, was the first significant Filipino victory against Spanish forces during the Philippine Revolution. The battle took place in Binakayan, Kawit (then known as Cavite el Viejo). General Emilio Aguinaldo the commanding general. Among those who participated were Candido Tria Tirona, Pio del Pilar, Vito Belarmino, Crispulo Aguinaldo, Baldomero Aguinaldo, Baldomero Aguinaldo and Pantaleon Garcia. The monument commemorates the battle.";
        return content;
    }

    @Override
    public boolean isAllowed() {
        return Settings.instance.loadUserData().cleared[0];
    }

    @Override
    public String getDisabledTitle() {
        return "Clear World 1 to Unlock";
    }

    @Override
    public String getImagePath() {
        return Constants.Path.BG_WORLD_1;
    }

    @Override
    public float getMaxScroll() {
        return 115.625f;
    }
}
