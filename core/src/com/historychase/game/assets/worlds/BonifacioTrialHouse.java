package com.historychase.game.assets.worlds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.historychase.core.WorldMap;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.sprites.Scroll;
import com.historychase.game.assets.story.BonifacioTrialHouseStory;
import com.historychase.game.assets.story.Story;

public class BonifacioTrialHouse extends WorldMap {

    private Scroll scroll;
    private Rectangle rectangle;
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
    }

    @Override
    public void draw(SpriteBatch batch) {
        scroll.draw(batch);
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