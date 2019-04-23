package com.historychase.game.assets.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PuzzlePiece{
    public final TextureRegion region;
    public final int position;
    public PuzzlePiece(TextureRegion region,int position){
        this.region = region;
        this.position = position;
    }
}