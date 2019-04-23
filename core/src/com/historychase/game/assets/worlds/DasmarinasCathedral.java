package com.historychase.game.assets.worlds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.historychase.core.WorldMap;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.sprites.Scroll;
import com.historychase.game.assets.sprites.Sign;
import com.historychase.game.assets.story.DasmarinasCathedralStory;
import com.historychase.game.assets.story.Story;

public class DasmarinasCathedral extends WorldMap {

    private Scroll scroll;
    private Rectangle rectangle;
    private Sign sign;
    private Sign sign2;
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
        sign = new Sign(this, new Rectangle(150,16,40,40),HistoryChase.PPM);
        sign.message = "hundreds of Filipino families were killed by Spaniards during the Lachambre offensive to recover\nlost territory in late August, 1897.";
        sign2 = new Sign(this, new Rectangle(2500,16,40,40),HistoryChase.PPM);
        sign2.message = "The event was known as the Battle of Perez-Dasmarinas.";

    }

    @Override
    public void draw(SpriteBatch batch) {

        scroll.draw(batch);
        sign.draw(batch);
        sign2.draw(batch);

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
