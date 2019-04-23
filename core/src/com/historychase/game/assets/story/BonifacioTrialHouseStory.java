package com.historychase.game.assets.story;

import com.historychase.game.assets.Constants;
import com.historychase.game.assets.Settings;

public class BonifacioTrialHouseStory implements Story {
    @Override
    public String getTitle() {
        return "Bonifacio Trial House";
    }

    @Override
    public String[] getContents() {
        String content[] = new String[4];
        content[0] = "The Teodorico Reyes Ancestral House, more commonly known as the Bonifacio Trial House, is a historic house and museum in Maragondon, Cavite, Philippines. It was built in 1889 and served as a military court, wherein it has been a witness to the trial of Andres Bonifacio in 1897.";
        content[1] = "The plan for a new government was established since the emergence of the revolution in August 23, 1896. Its aim was to unite the Katipuneros under a single leadership. The Magdalo faction nominated Emilio Aguinaldo while Magdiwang faction retained Bonifacio, who was the \"Supremo\" of the Katipunan. On March 22, 1897, the revolutionary government was established at the Tejeros Convention. Emilio Aguinaldo was the president of the new government while Bonifacio was declared as the Minister of Interior. However, Daniel Tirona of Magdalo, questioned the Bonifacio's qualifications for the said position. Upon his authority as the presiding officer, he declared all the proceedings null and void.";
        content[2] = "Bonifacio, then, established his own government in Naic, Cavite. He was arrested for refusing the revolutionary government, upon the orders of Emilio Aguinaldo, at Indang, Cavite. His wife, Gregoria de Jesus, and his brother, Procopio, was also arrested. Andres Bonifacio was brought to a military court in Maragondon for a pre-trial hearing. On May 5, 1897, the brothers of Bonifacio were charged by the court with treason and sedition. On May 6, 1897, they were sentenced with death penalty.";
        content[3] = "His brothers were brought by Major Lazaro Macapagal to Mount Tala on May 10, 1897. As soon as they reached Hulog, a barrio within the vicinity of Mount Nagpatong, Major Makapagal opened his sealed orders, upon Bonifacio’s insistence. The order revealed that severe punishment awaits for him if he would fail to execute the two brothers of Andres Bonifacio. The death of the Father of the Revolution, Andres Bonifacio, still remains to be controversial at present.";
        return content;
    }

    @Override
    public boolean isAllowed() {
        return Settings.instance.loadUserData().cleared[3];
    }

    @Override
    public String getDisabledTitle() {
        return "Clear World 4 to Unlock";
    }

    @Override
    public String getImagePath() {
        return Constants.Path.BG_WORLD_4;
    }

    @Override
    public float getMaxScroll() {
        return 396.25f;
    }
}
