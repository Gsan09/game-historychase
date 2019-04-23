package com.historychase.game.assets.worlds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.historychase.core.WorldMap;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.sprites.DisappearTile;
import com.historychase.game.assets.sprites.Scroll;
import com.historychase.game.assets.story.CorregidorIslandStory;
import com.historychase.game.assets.story.Story;

public class CorregidorIsland extends WorldMap {

    private Scroll scroll;
    private Rectangle rectangle;
    private DisappearTile tiles[];
    private Texture platformImg;
    public CorregidorIsland() {
        super("Corregidor Island", Constants.Path.MAP_WORLD_7, Constants.Path.BG_WORLD_7, HistoryChase.PPM);
    }

    @Override
    protected void onWorldInitialized() {
        setLayerAsFixedBlocks(3,HistoryChase.PPM);
        setLayerAsFixedBlocks(4,HistoryChase.PPM);
        setLayerAsDeadlyBlocks(5,HistoryChase.PPM);
        tiles = setLayerAsDisappearingBlocks(6,HistoryChase.PPM);
        rectangle = new Rectangle(3660.9047f,30,30,30);
        scroll = new Scroll(this,rectangle,HistoryChase.PPM);
        platformImg = new Texture("images/w8_collapse.png");
        platformImg.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);
    }

    @Override
    public void draw(SpriteBatch batch) {

        scroll.draw(batch);
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
        return new CorregidorIslandStory();
    }

    @Override
    public int getID() {
        return 6;
    }
}
