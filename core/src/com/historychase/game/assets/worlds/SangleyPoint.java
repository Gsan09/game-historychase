package com.historychase.game.assets.worlds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.historychase.core.WorldMap;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants.Path;
import com.historychase.game.assets.Settings;
import com.historychase.game.assets.sprites.Scroll;
import com.historychase.game.assets.sprites.Sign;
import com.historychase.game.assets.story.SangleyPointStory;
import com.historychase.game.assets.story.Story;

public class SangleyPoint extends WorldMap {
    private Scroll scroll;
    private Rectangle rectangle;

    private Sign sign;
    private Sign sign2;

    public SangleyPoint() {
        super("Sangley Point", Path.MAP_WORLD_2,Path.BG_WORLD_2,HistoryChase.PPM);
    }

    @Override
    protected void onWorldInitialized() {

        setLayerAsFixedBlocks(3,HistoryChase.PPM);
        setLayerAsFixedBlocks(4,HistoryChase.PPM);
        setLayerAsFixedBlocks(5,HistoryChase.PPM); // Moving Platform


        rectangle = new Rectangle(3360.9047f,30,30,30);
        scroll = new Scroll(this,rectangle,HistoryChase.PPM);

        sign = new Sign(this, new Rectangle(200,16,40,40),HistoryChase.PPM);
        sign.message = "Naval Station Sangley Point was a communication and hospital facility of the United States Navy.";
        sign2 = new Sign(this, new Rectangle(2101.754f,16,40,40),HistoryChase.PPM);
        sign2.message = "Sangley Point was also used extensively during the Vietnam War.";
    }

    @Override
    public void draw(SpriteBatch batch) {
        scroll.draw(batch);
        sign.draw(batch);
        sign2.draw(batch);
    }

    @Override
    public Story getStory() {
        return new SangleyPointStory();
    }

    @Override
    public int getID() {
        return 1;
    }
}
