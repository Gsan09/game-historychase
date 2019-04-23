package com.historychase.game.assets.worlds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.historychase.core.WorldMap;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.sprites.Scroll;
import com.historychase.game.assets.story.BinakayanBattleStory;
import com.historychase.game.assets.story.Story;

public class BinakayanBattle extends WorldMap {
    private Scroll scroll;
    private Rectangle rectangle;

    public BinakayanBattle() {
        super("Binakayan Battle", Constants.Path.MAP_WORLD_1, Constants.Path.BG_WORLD_1,HistoryChase.PPM);
    }

    @Override
    protected void onWorldInitialized() {
        rectangle = new Rectangle(3360.9047f,30,30,30);
        setLayerAsFixedBlocks(2,HistoryChase.PPM);
        setLayerAsFixedBlocks(3,HistoryChase.PPM);
        scroll = new Scroll(this,rectangle,HistoryChase.PPM);
    }

    @Override
    public void draw(SpriteBatch batch) {
        scroll.draw(batch);
    }

    @Override
    public Story getStory() {
        return new BinakayanBattleStory();
    }

    @Override
    public int getID() {
        return 0;
    }
}
