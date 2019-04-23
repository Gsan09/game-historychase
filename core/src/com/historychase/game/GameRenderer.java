package com.historychase.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

public class GameRenderer implements Disposable {

    private HistoryChase game;

    public GameRenderer(HistoryChase g){
        game = g;
    }

    public void drawText(String s, int x, int y, Color color){
        drawText(s,x,y,color, Align.left,FontSize.Normal);
    }

    public void drawText(String s, int x, int y, Color color, int alignment){
        drawText(s,x,y,color,alignment,FontSize.Normal);
    }

    public void drawText(String s, int x, int y, Color color, FontSize size){
        drawText(s,x,y,color,Align.left,size);
    }

    public void drawText(String s, int x, int y, Color color, int alignment, FontSize size){
        BitmapFont font = game.resource.defaultFont.normal;
        switch(size){
            case Small:
                font = game.resource.defaultFont.small;
                break;
            case Big:
                font = game.resource.defaultFont.big;
                break;
        }
        font.draw(game.batch,s,x,y,0,s.length(),0,alignment,false);
        font.setColor(color);
        font = null;
    }

    public void drawText(String s, int x, int y, Color color, float size){
        drawText(s,x,y,color,Align.left,size);
    }

    public void drawText(String s, int x, int y, Color color, int alignment, float size){
        BitmapFont font;
        if(size == 0.6f)
            font = game.resource.defaultFont.small;
        if(size == 1f)
            font = game.resource.defaultFont.normal;
        else if(size == 1.5f)
            font = game.resource.defaultFont.big;
        else
            font = game.resource.defaultFont.scale(size);

        font.draw(game.batch,s,x,y,0,s.length(),0,alignment,false);
        font.setColor(color);
        font = null;
    }

    @Override
    public void dispose() {
        game = null;

    }


    public static enum FontSize{
        Small,Normal,Big
    }

}
