package com.historychase.game.assets.story;

import com.historychase.game.assets.Constants;
import com.historychase.game.assets.Settings;

public class SangleyPointStory implements Story {

    @Override
    public String getTitle() {
        return "Sangley Point";
    }

    @Override
    public String[] getContents() {
        String content[] = new String[1];
        content[0] = "Naval Station Sangley Point was a communication and hospital facility of the United States Navy which occupied the northern portion of the Cavite City peninsula and is surrounded by Manila Bay, approximately eight miles southwest of Manila, the Philippines. The station was a part of the Cavite Navy Yard across the peninsula. The naval station had a runway that was built after World War II, which was used by U.S. Navy Lockheed P-2 Neptune, Lockheed P-3 Orion, and Martin P4M Mercator maritime patrol and anti-submarine warfare aircraft. An adjacent seaplane runway, ramp area and seaplane tender berths also supported Martin P5M Marlin maritime patrol aircraft until that type's retirement from active naval service in the late 1960s. NAS Sangley Point/NAVSTA Sangley Point was also used extensively during the Vietnam War, primarily for U.S. Navy patrol squadrons forward deployed from the United States on six-month rotations. The naval station was turned over to the Philippine government in 1971. It is now operated by the Philippine Air Force and Philippine Navy.";
        return content;
    }

    @Override
    public boolean isAllowed() {
        return Settings.instance.loadUserData().cleared[1];
    }

    @Override
    public String getDisabledTitle() {
        return "Clear World 2 to Unlock";
    }

    @Override
    public String getImagePath() {
        return Constants.Path.BG_WORLD_2;
    }

    @Override
    public float getMaxScroll() {
        return 218.125f;
    }

}
