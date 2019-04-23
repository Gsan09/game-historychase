package com.historychase.game.assets.story;

import com.historychase.game.assets.Constants;
import com.historychase.game.assets.Settings;

public class BaldomeroAguinaldoStory implements Story {
    @Override
    public String getTitle() {
        return "Baldomero Aguinaldo Museum";
    }

    @Override
    public String[] getContents() {
        String content[] = new String[3];
        content[0] = "Stalwart supporter and first cousin of General Emilio Aguinaldo, Baldomero Aguinaldo was born on 26 February 1869 to Cipriano Aguinaldo and Silveria Baloy in Binakayan, Kawit, Cavite. ";
        content[1] = "With the outbreak of the Philippine Revolution, Baldomero, along with Aguinaldo and brothers Candido and Daniel Tirona, established the Katipunan’s Magdalo Council and was subsequently elected as its President. His law education and administrative capabilities proved to be indispensable to running the Revolutionary government that he was appointed to various posts such as Auditor General, Director of Finance, Secretary of the Treasury of the Biak-na-Bato Republic, Secretary of War and Public Works of the First Philippine Republic.";
        content[2] = "Baldomero’s heroic mettle as a revolutionary general was tested by his involvement in the battles of Binakayan, Dalahican, and Noveleta on 9-10 November 1896; Zapote on 17 February 1897; Salitran on 7 March 1897; and Alapan, Imus on 28 May 1898. During the Philippine-American War, Aguinaldo appointed him as Commanding General of the forces in the Southern Luzon provinces. At the end of hostilities with the United States, Baldomero Aguinaldo retired to his farmlands in Silang, Cavite. In 1912, the Asociacion de los Veteranos de la Revolucion Filipina was organized with Baldomero as its President, a position he held until his demise on 14 February 1915.";
        return content;
    }

    @Override
    public boolean isAllowed() {
        return Settings.instance.loadUserData().cleared[5];
    }

    @Override
    public String getDisabledTitle() {
        return "Clear World 6 to Unlock";
    }

    @Override
    public String getImagePath() {
        return Constants.Path.BG_WORLD_6;
    }

    @Override
    public float getMaxScroll() {
        return 297.5f;
    }
}
