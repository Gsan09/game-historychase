package com.historychase.game.assets.worlds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.historychase.core.WorldMap;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.Settings;
import com.historychase.game.assets.sprites.DisappearTile;
import com.historychase.game.assets.sprites.Scroll;
import com.historychase.game.assets.sprites.Sign;
import com.historychase.game.assets.story.BaldomeroAguinaldoStory;
import com.historychase.game.assets.story.Story;

public class BaldomeroAguinaldo extends WorldMap {

    private Scroll scroll;
    private Rectangle rectangle;
    private DisappearTile tiles[];
    private Texture platformImg;
    private Sign sign;
    private Sign sign2;
    public BaldomeroAguinaldo() {
        super("Baldomero Aguinaldo Museum", Constants.Path.MAP_WORLD_6, Constants.Path.BG_WORLD_6, HistoryChase.PPM);
    }

    @Override
    protected void onWorldInitialized() {
        setLayerAsFixedBlocks(3,HistoryChase.PPM);
        setLayerAsFixedBlocks(4,HistoryChase.PPM);
        tiles = setLayerAsDisappearingBlocks(5,HistoryChase.PPM);
        setLayerAsDeadlyBlocks(6,HistoryChase.PPM);
        rectangle = new Rectangle(3360.9047f,30,30,30);
        scroll = new Scroll(this,rectangle,HistoryChase.PPM);
        platformImg = new Texture("images/platforms.png");
        platformImg.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);
        sign = new Sign(this, new Rectangle(150,16,40,40),HistoryChase.PPM);
        sign.message = "Stalwart supporter and first cousin of General Emilio Aguinaldo, Baldomero Aguinaldo \nwas born on 26 February 1869 to Cipriano Aguinaldo and Silveria Baloy in \nBinakayan, Kawit, Cavite.";
        sign2 = new Sign(this, new Rectangle(2500,16,40,40),HistoryChase.PPM);
        sign2.message = "With the outbreak of the Philippine Revolution, Baldomero, along with Aguinaldo and brothers\nCandido and Daniel Tirona, established the Katipunans Magdalo Council and was subsequently\nelected as its President.";

    }

    boolean b = false;
    @Override
    public void draw(SpriteBatch batch) {
        scroll.draw(batch);
        sign.draw(batch);
        sign2.draw(batch);

        if(tiles != null)
        for(DisappearTile tile:tiles){
            if(tile.isDisposed())continue;
            float x = tile.body.getPosition().x;
            float y = tile.body.getPosition().y;
            float w = tile.bounds.getWidth()/HistoryChase.PPM;
            float h = tile.bounds.getHeight()/HistoryChase.PPM;
            batch.draw(platformImg,x-(w/2),y-(h/2),w,h);
        }
    }

    @Override
    public Story getStory() {
        return new BaldomeroAguinaldoStory();
    }

    @Override
    public int getID() {
        return 5;
    }
}
