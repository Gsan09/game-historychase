package com.historychase.game.assets.worlds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.historychase.core.WorldMap;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.sprites.Scroll;
import com.historychase.game.assets.sprites.Sign;
import com.historychase.game.assets.story.BinakayanBattleStory;
import com.historychase.game.assets.story.Story;

public class BinakayanBattle extends WorldMap {
    private Scroll scroll;
    private Rectangle rectangle;
    private Sign sign;
    private Sign sign2;

    public BinakayanBattle() {
        super("Binakayan Battle", Constants.Path.MAP_WORLD_1, Constants.Path.BG_WORLD_1,HistoryChase.PPM);
    }

    @Override
    protected void onWorldInitialized() {
        rectangle = new Rectangle(3360.9047f,30,30,30);
        setLayerAsFixedBlocks(2,HistoryChase.PPM);
        setLayerAsFixedBlocks(3,HistoryChase.PPM);
        scroll = new Scroll(this,rectangle,HistoryChase.PPM);
        scroll = new Scroll(this,rectangle,HistoryChase.PPM);
        sign = new Sign(this, new Rectangle(200,16,40,40),HistoryChase.PPM);
        sign.message = "Battle of Binakayan fought from November 9-11, 1896";
        sign2 = new Sign(this, new Rectangle(2101.754f,16,40,40),HistoryChase.PPM);
        sign2.message = "It is known as Cavite el Viejo";
    }

    @Override
    public void draw(SpriteBatch batch) {
        scroll.draw(batch);
        sign.draw(batch);
        sign2.draw(batch);
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
