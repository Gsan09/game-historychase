package com.historychase.game.assets.worlds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.historychase.core.WorldMap;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.sprites.Scroll;
import com.historychase.game.assets.sprites.Sign;
import com.historychase.game.assets.story.Story;
import com.historychase.game.assets.story.TejeroConventionStory;

public class TejerosConvention extends WorldMap {
    private Scroll scroll;
    private Rectangle rectangle;
    private Sign sign;
    private Sign sign2;

    public TejerosConvention() {
        super("Tejeros Convention", Constants.Path.MAP_WORLD_3, Constants.Path.BG_WORLD_3, HistoryChase.PPM);
    }

    @Override
    protected void onWorldInitialized() {
        setLayerAsFixedBlocks(2,HistoryChase.PPM);
        setLayerAsDeadlyBlocks(4,HistoryChase.PPM);
        rectangle = new Rectangle(3660.9047f,30,30,30);
        scroll = new Scroll(this,rectangle,HistoryChase.PPM);

        sign = new Sign(this, new Rectangle(670f,125,40,40),HistoryChase.PPM);
        sign.message = "(known as Tejeros Assembly and Tejeros Congress) was the meeting held on March 22, 1897.";
        sign2 = new Sign(this, new Rectangle(2950,16,40,40),HistoryChase.PPM);
        sign2.message = "The convention was called to discuss the defense of Cavite against the Spaniards \nduring the Philippine Revolution.";
    }

    @Override
    public void draw(SpriteBatch batch) {
        scroll.draw(batch);
        sign.draw(batch);
        sign2.draw(batch);
    }

    @Override
    public Story getStory() {
        return new TejeroConventionStory();
    }

    @Override
    public int getID() {
        return 2;
    }
}
