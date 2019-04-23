package com.historychase.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GameFont implements Disposable {
    public BitmapFont big;
    public BitmapFont small;
    public BitmapFont normal;

    private Map<Float,BitmapFont> sizes;
    private String path;

    public GameFont(){
        this(null);
    }

    public GameFont(String path) {
        this.path = path;
        sizes = new HashMap<Float, BitmapFont>();

        small = scale(0.6f);
        normal = scale(1f);
        big = scale(1.5f);
    }

    public BitmapFont scale(float scale){
        if(sizes.get(scale) == null){
            BitmapFont scaled;
            scaled = (path == null)?new BitmapFont(): new BitmapFont(Gdx.files.internal(path));
            scaled.getData().setScale(scale);
            scaled.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            sizes.put(scale,scaled);
        }
        return  sizes.get(scale);
    }

    @Override
    public void dispose() {
        small.dispose();
        normal.dispose();
        big.dispose();

        Set<Float> keys = sizes.keySet();

        for (Float key:keys) {
            sizes.get(key).dispose();
        }

        keys = null;
        sizes = null;
        small = null;
        normal = null;
        big =null;
    }
}
