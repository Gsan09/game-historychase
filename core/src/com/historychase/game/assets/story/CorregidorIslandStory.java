package com.historychase.game.assets.story;

import com.historychase.game.assets.Constants;
import com.historychase.game.assets.Settings;

public class CorregidorIslandStory implements Story {
    @Override
    public String getTitle() {
        return "Corregidor Island History";
    }

    @Override
    public String[] getContents() {
        String content[] = new String[6];
        content[0] = "Corregidor comes from the Spanish word “corregir,” meaning to correct. One story states that due to the Spanish system wherein all ships entering Manila Bay were required to stop and have their documents checked and corrected, the island was called \"Isla del Corregidor\" (Island of the Correction). Another version claims that the island was used a penitentiary or correctional institution by the Spanish and came to be called \"El Corregidor.\"";
        content[1] = "In early and pre-hispanic times, it was likely populated by fishermen and no doubt provided a base for pirates who could easily launch an attack against any vessel entering Manila Bay. During the Spanish era this tadpole-shaped island was a signal station where bonfires were lit to alert Manila of a home-coming galleon. Later on, Spaniards built a lighthouse on the island.";
        content[2] = "The Spaniards set up a naval dockyard on the island in 1795. This was followed by a naval hospital and a signal station which was used primarily to warn Manila of approaching enemies. In 1836 a lighthouse was built and in 1853 a stronger light was installed. This was replaced in 1897 and remained in use until the outbreak of the Pacific War, during which it was heavily damaged and rebuilt to the same specifications. During the Spanish times, the small town of San Jose emerged to become the seat of government on the island. Later under the Americans, it evolved into a small community with its paved streets lined with the houses of the Philippine Scouts who constituted the bulk of the garrison in Corregidor.";
        content[3] = "After the defeat of the Spanish forces by Admiral George Dewey in May of 1898, Spain ceded Cuba, Puerto Rico, and the Philippines to the Americans under the Treaty of Paris which was signed on December 10, 1898. In 1903 a former Spanish garrison building there was converted to a convalescent hospital. The island was designated as a U.S. Military Reservation in 1907 and the army post on Corregidor was named Fort Mills, after Brig. Gen. Samuel M. Mills, chief of artillery of the U.S. Army in 1905-1906. A regular army post was later established in 1908.";
        content[4] = "The following year army engineers of “H” company, 2nd Battalion of the U.S. Corps of Engineers began to build fortifications on the island to secure the seaward approach to Manila Bay. This was part of the planned \"Harbor Defenses of Manila and Subic Bay\" due to the strategic location of Corregidor. Concrete emplacements and bomb-proof shelters were constructed and trails and roads were laid out on the island. This engineer contingent left on March 15, 1912, after laying down the groundwork to make Corregidor a great military bastion. ";
        content[5] = "The big guns of Corregidor in 1941 were used in support of Filipino and American defenders of Bataan until the island itself was invaded by Japanese Forces. The restless pounding by Japanese guns including intermittent bombings reduced its defenses and compelled its surrender. On January 22, 1945, Corregidor was once again caught in the fury of war as the Americans retook the island after a bloody battle.";
        return content;
    }

    @Override
    public boolean isAllowed() {
        return Settings.instance.loadUserData().cleared[6];
    }

    @Override
    public String getDisabledTitle() {
        return "Clear World 7 to Unlock";
    }

    @Override
    public String getImagePath() {
        return Constants.Path.BG_WORLD_7;
    }

    @Override
    public float getMaxScroll() {
        return 617.5f;
    }
}
