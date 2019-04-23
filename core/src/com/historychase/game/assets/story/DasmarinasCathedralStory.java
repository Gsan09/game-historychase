package com.historychase.game.assets.story;

import com.historychase.game.assets.Constants;
import com.historychase.game.assets.Settings;

public class DasmarinasCathedralStory implements Story {
    @Override
    public String getTitle() {
        return "Dasmarinas Cathedral";
    }

    @Override
    public String[] getContents() {
        String content[] = new String[2];
        content[0] = "Inside this church, now renovated, hundreds of Filipino families were killed by Spaniards during the Lachambre offensive to recover lost territory in late August, 1897.";
        content[1] = "During the Philippine Revolution, gobernadorcillo Don Placido Campos and his secretary Francisco Barzaga lead the uprising against the Spaniards which eventually freed their town in 1896. The Spaniards tried to recapture the town of Perez-Dasmariñas from the revolutionaries. On February 25, 1897, Filipino revolutionaries took refuge in the convent that was later set on fire by the Spaniards. These men were shot as they emerged from the church. Others had shut themselves up in the church. With the church surrounded, the mountain artillery was brought out into position and from a distance of 35 metres (115 ft), the strong doors of the church were bombarded and the troops went in through the breach. Many lost their lives in the sanctuary. The event was known as the Battle of Perez-Dasmarinas.During that bloody battle, the Spaniards burned all structures in the town proper except the Catholic Church which was later used as a garrison. And the Japanese Occupation. After a similar event in the church of Imus a day before, several men of the town who were also suspected of being members of guerrilla forces were detained by Japanese soldiers inside the church on December 17, 1944. Many residents were killed or executed during the Japanese occupation.";
        return content;
    }

    @Override
    public boolean isAllowed() {
        return Settings.instance.loadUserData().cleared[4];
    }

    @Override
    public String getDisabledTitle() {
        return "Clear World 5 to Unlock";
    }

    @Override
    public String getImagePath() {
        return Constants.Path.BG_WORLD_5;
    }

    @Override
    public float getMaxScroll() {
        return 284.375f;
    }
}
