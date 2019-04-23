package com.historychase.game.assets.worlds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.historychase.core.WorldMap;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.sprites.Scroll;
import com.historychase.game.assets.story.DasmarinasCathedralStory;
import com.historychase.game.assets.story.Story;

public class DasmarinasCathedral extends WorldMap {

    private Scroll scroll;
    private Rectangle rectangle;
    public DasmarinasCathedral() {
        super("Dasmarinas Catholic Church", Constants.Path.MAP_WORLD_5, Constants.Path.BG_WORLD_5, HistoryChase.PPM);
    }

    @Override
    protected void onWorldInitialized() {

        setLayerAsDeadlyBlocks(7,HistoryChase.PPM);
        setLayerAsDeadlyBlocks(8,HistoryChase.PPM);
        setLayerAsFixedBlocks(4,HistoryChase.PPM);
        setLayerAsFixedBlocks(5,HistoryChase.PPM);
        setLayerAsFixedBlocks(6,HistoryChase.PPM);
        rectangle = new Rectangle(3660.9047f,30,30,30);
        scroll = new Scroll(this,rectangle,HistoryChase.PPM);
    }

    @Override
    public void draw(SpriteBatch batch) {

        scroll.draw(batch);

    }

    @Override
    public Story getStory() {
        return new DasmarinasCathedralStory();
    }

    @Override
    public int getID() {
        return 4;
    }
}
