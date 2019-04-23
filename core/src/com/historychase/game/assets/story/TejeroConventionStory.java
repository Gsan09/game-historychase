package com.historychase.game.assets.story;

import com.historychase.game.assets.Constants;
import com.historychase.game.assets.Settings;

public class TejeroConventionStory implements Story {
    @Override
    public String getTitle() {
        return "Tejero Convention";
    }

    @Override
    public String[] getContents() {
        String content[] = new String[3];
        content[0] = "The Tejeros Convention (alternate names include Tejeros Assembly and Tejeros Congress) was the meeting held on March 22, 1897 between the Magdiwang and Magdalo factions of the Katipunan at San Francisco de Malabon, Cavite. These are the first presidential and vice presidential elections in Philippine history, although only the Katipuneros (members of the Katipunan) were able to take part, and not the general populace.";
        content[1] = "The convention was called to discuss the defense of Cavite against the Spaniards during the Philippine Revolution. The contemporary Governor General, Camilo de Polavieja, had regained much of Cavite itself. Instead, the convention became an election to decide the leaders of the revolutionary movement, bypassing the Supreme Council.";
        content[2] = "The revolutionary leaders held an important meeting in a friar estate residence in Tejeros to resume their discussions regarding the escalating tension between the Magdalo and Magdiwang forces; And also to settle once and for all the issue of governance within the Katipunan through an election. Amidst implications on whether the government of the \"Katipunan\" should be established as a monarchy or as a republic, Bonifacio defended that it should be maintained as a republic. According to him, all of its members of any given rank shall serve under the principle of liberty, equality and fraternity, upon which republicanism was founded. Despite Bonifacio's concern on the lack of officials and representatives from other provinces, he was obliged to proceed with the election.";
        return content;
    }

    @Override
    public boolean isAllowed() {
        return Settings.instance.loadUserData().cleared[2];
    }

    @Override
    public String getDisabledTitle() {
        return "Clear World 3 to Unlock";
    }

    @Override
    public String getImagePath() {
        return Constants.Path.BG_WORLD_3;
    }

    @Override
    public float getMaxScroll() {
        return 315.625f;
    }
}
