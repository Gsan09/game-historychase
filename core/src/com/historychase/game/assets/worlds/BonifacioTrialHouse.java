package com.historychase.game.assets.worlds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.historychase.core.WorldMap;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.sprites.Scroll;
import com.historychase.game.assets.sprites.Sign;
import com.historychase.game.assets.story.BonifacioTrialHouseStory;
import com.historychase.game.assets.story.Story;

public class BonifacioTrialHouse extends WorldMap {

    private Scroll scroll;
    private Rectangle rectangle;
    private Sign sign;
    private Sign sign2;
    public BonifacioTrialHouse() {
        super("Bonfication Trial House", Constants.Path.MAP_WORLD_4, Constants.Path.BG_WORLD_4, HistoryChase.PPM);
    }

    @Override
    protected void onWorldInitialized() {

        setLayerAsDeadlyBlocks(2,HistoryChase.PPM);
        setLayerAsFixedBlocks(3,HistoryChase.PPM);
        setLayerAsFixedBlocks(4,HistoryChase.PPM);
        setLayerAsFixedBlocks(5,HistoryChase.PPM);
        rectangle = new Rectangle(3560.9047f,30,30,30);
        scroll = new Scroll(this,rectangle,HistoryChase.PPM);
        sign = new Sign(this, new Rectangle(2144.67f,80,40,40),HistoryChase.PPM);
        sign.message = "The Teodorico Reyes Ancestral House, more commonly known as the Bonifacio Trial House \nis a historic house and museum in Maragondon, Cavite, Philippines.";
        sign2 = new Sign(this, new Rectangle(3263.33f,16,40,40),HistoryChase.PPM);
        sign2.message = "It was built in 1889 and served as a military court\nwherein it has been a witness to the trial of Andres Bonifacio in 1897";
    }

    @Override
    public void draw(SpriteBatch batch) {
        scroll.draw(batch);
        sign.draw(batch);
        sign2.draw(batch);
    }

    @Override
    public Story getStory() {
        return new BonifacioTrialHouseStory();
    }

    @Override
    public int getID() {
        return 3;
    }
}