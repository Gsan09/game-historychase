package com.historychase.game.assets.story;

import com.historychase.game.assets.Constants;
import com.historychase.game.assets.Settings;

public class CanacaoBayStory implements Story {
    @Override
    public String getTitle() {
        return "Cañacao Bay";
    }

    @Override
    public String[] getContents() {
        String content[] = new String[3];
        content[0] = "The bay is a good anchorage and it is bordered to the north by the Danilo Atienza Air Base of the Philippine Air Force and by the Naval Base Cavite of the Philippine Navy to the south. Both military bases previously were comprised by the Naval Station Sangley Point of the United States.";
        content[1] = "The Cavite City Hall is on the south side of the bay, with a pier for public ferry service to Metro Manila.";
        content[2] = "The shore of the bay near the former Cavite Royal Arsenal was where the province's patron saint, an icon known as Our Lady of Solitude of Porta Vaga, was found during the Spanish colonial period following an alleged Marian apparition.";
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
        return Constants.Path.STORY_10;
    }

    @Override
    public float getMaxScroll() {
        return 166.25002f;
    }
}
