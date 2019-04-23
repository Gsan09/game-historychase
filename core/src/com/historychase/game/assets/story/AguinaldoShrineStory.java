package com.historychase.game.assets.story;

import com.historychase.game.assets.Constants;
import com.historychase.game.assets.Settings;

public class AguinaldoShrineStory implements Story {
    @Override
    public String getTitle() {
        return "General Emilio Aguinaldo Shrine";
    }

    @Override
    public String[] getContents() {
        String content[] = new String[1];
        content[0] = "It was in this Aguinaldo ancestral home where Gen. Emilio Aguinaldo proclaimed Philippine Independence from Spain on June 12,1898. It was also here where the Philippine Flag made by Marcella Agoncillo in Hongkong was officially hoisted for the first time, and the Philippine National Anthem composed by Julian Felipe was played by Banda Malabon. Measuring 1,324 sq. m. with a five-storey tower, this building is actually a mansion renaissance architecture, combining Baroque, Romanesque, and Malayan influences. It stands on a sprawling ground of 4,864 sq.m. Gen. Emilio Aguinaldo himself donated the mansion and the lot to the Philippine Government on June 12,1963, “to perpetuate the spirit of the Philippine Revolution of 1896 that put an end to Spanish colonization of the country”. And by virtue of Republic Act No. 4039 dated June 18,1964 issued by then President Diosdado Macapagal, the Aguinaldo Mansion was declared national shrine.";
        return content;
    }

    @Override
    public boolean isAllowed() {
        return Settings.instance.loadUserData().cleared[7];
    }

    @Override
    public String getDisabledTitle() {
        return "Clear World 8 to Unlock";
    }

    @Override
    public String getImagePath() {
        return Constants.Path.BG_WORLD_8;
    }

    @Override
    public float getMaxScroll() {
        return 199.375f;
    }
}
