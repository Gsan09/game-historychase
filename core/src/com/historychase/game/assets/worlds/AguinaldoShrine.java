package com.historychase.game.assets.worlds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.historychase.core.WorldMap;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.sprites.DisappearTile;
import com.historychase.game.assets.sprites.Scroll;
import com.historychase.game.assets.sprites.Sign;
import com.historychase.game.assets.story.AguinaldoShrineStory;
import com.historychase.game.assets.story.Story;

public class AguinaldoShrine extends WorldMap {

    private Scroll scroll;
    private Rectangle rectangle;
    private DisappearTile tiles[];
    private DisappearTile collapse[];
    private Texture tileImg,collapseImg;
    private Sign sign;
    private Sign sign2;

    public AguinaldoShrine() {
        super("Aguinaldo Shrine", Constants.Path.MAP_WORLD_8, Constants.Path.BG_WORLD_8, HistoryChase.PPM);
    }

    @Override
    protected void onWorldInitialized() {

        setLayerAsFixedBlocks(2,HistoryChase.PPM);
        setLayerAsFixedBlocks(3,HistoryChase.PPM);
        tiles = setLayerAsDisappearingBlocks(4,HistoryChase.PPM);
        collapse = setLayerAsDisappearingBlocks(5,HistoryChase.PPM);
        setLayerAsDeadlyBlocks(6,HistoryChase.PPM);
        rectangle = new Rectangle(3560.9047f,30,30,30);
        scroll = new Scroll(this,rectangle,HistoryChase.PPM);

        tileImg = new Texture("images/w8_tile.png");
        collapseImg = new Texture("images/w8_collapse.png");

        sign = new Sign(this, new Rectangle(150,16,40,40),HistoryChase.PPM);

        sign.message = "this Aguinaldo ancestral home where Gen. Emilio Aguinaldo proclaimed\nPhilippine Independence from Spain on June 12,1898.";
        sign2 = new Sign(this, new Rectangle(2301.754f,16,40,40),HistoryChase.PPM);
        sign2.message = "Gen. Emilio Aguinaldo himself donated the mansion and the lot to the Philippine Government on June 12,1963\n to perpetuate the spirit of the Philippine Revolution of 1896 that put an end to Spanish colonization of the country";

    }

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
                batch.draw(tileImg,x-(w/2),y-(h/2),w,h);
            }

        if(collapse != null)
            for(DisappearTile tile:collapse){
                if(tile.isDisposed())continue;
                float x = tile.body.getPosition().x;
                float y = tile.body.getPosition().y;
                float w = tile.bounds.getWidth()/HistoryChase.PPM;
                float h = tile.bounds.getHeight()/HistoryChase.PPM;
                batch.draw(collapseImg,x-(w/2),y-(h/2),w,h);
            }
    }

    @Override
    public Story getStory() {
        return new AguinaldoShrineStory();
    }

    @Override
    public int getID() {
        return 7;
    }

}
