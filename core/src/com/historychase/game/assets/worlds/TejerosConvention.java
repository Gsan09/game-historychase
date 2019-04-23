package com.historychase.game.assets.worlds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.historychase.core.WorldMap;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.sprites.Scroll;
import com.historychase.game.assets.story.Story;
import com.historychase.game.assets.story.TejeroConventionStory;

public class TejerosConvention extends WorldMap {
    private Scroll scroll;
    private Rectangle rectangle;
    public TejerosConvention() {
        super("Tejeros Convention", Constants.Path.MAP_WORLD_3, Constants.Path.BG_WORLD_3, HistoryChase.PPM);
    }

    @Override
    protected void onWorldInitialized() {
        setLayerAsFixedBlocks(2,HistoryChase.PPM);
        setLayerAsDeadlyBlocks(4,HistoryChase.PPM);
        rectangle = new Rectangle(3660.9047f,30,30,30);
        scroll = new Scroll(this,rectangle,HistoryChase.PPM);
    }

    @Override
    public void draw(SpriteBatch batch) {
        scroll.draw(batch);

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
