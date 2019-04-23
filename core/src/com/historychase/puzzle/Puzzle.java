package com.historychase.puzzle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.Random;

public class Puzzle{

    private Texture display;
    public final int horizontal;
    public final int vertical;
    public final Piece[][] pieces;

    private Puzzle(Texture texture,int horizontal,int vertical) {
        display = texture;
        this.horizontal =horizontal;
        this.vertical = vertical;
        pieces  = new Piece[horizontal][vertical];
        cut();
    }

    private void cut(){
        int w = display.getWidth()/horizontal;
        int h = display.getHeight()/vertical;
        for(int x=0;x<horizontal;x++){
            for(int y=0;y<vertical;y++){
                Drawable d = new TextureRegionDrawable(new TextureRegion(display,x*w,y*h,w,h));
                pieces[x][y]= new Piece(d,x,y);
            }
        }
    }

    public boolean check(){
        for(int x=0;x<horizontal;x++){
            for(int y=0;y<vertical;y++){
                if(pieces[x][y].x != x)
                    return false;
                if(pieces[x][y].y != y)
                    return false;
            }
        }
        return true;
    }

    public void swap(int x1,int y1,int x2,int y2){
        if(x1 == x2 && y1 == y2)return;
        Piece piece = pieces[x1][y1];
        pieces[x1][y1] = pieces[x2][y2];
        pieces[x2][y2] = piece;
    }

    public void print(){
        System.out.println(pieces[0][0]);
    }

    public void shuffle(){
        for(int x=0;x<horizontal;x++){
            for(int y=0;y<vertical;y++){
                Random random = new Random();
                swap(x,y,random.nextInt(horizontal),random.nextInt(vertical));
            }
        }
        if(check())shuffle();
    }

    public static Puzzle create(Texture display,int horizontal,int vertical){
        return new Puzzle(display,horizontal,vertical);
    }

    public class Piece{
        public final Drawable display;
        public final int x;
        public final int y;
        private Piece(Drawable d,int x,int y){
            display = d;
            this.x = x;
            this.y = y;
        }
        @Override
        public String toString() {
            return String.format("(%d,%d)",x,y);
        }
    }

    @Override
    public String toString() {
        String value = "";
        for(int x=0;x<horizontal;x++){
            for(int y=0;y<vertical;y++){
                value += String.format("[%d,%d] = %s \n",x,y,pieces[x][y].toString());
            }
        }
        return value;
    }
}
