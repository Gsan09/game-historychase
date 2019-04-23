package com.historychase.core;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

public abstract class Atlas<T extends ResourceManager> implements Disposable {
    private T manager;
    private String path;
    private TextureAtlas atlas;

    protected Atlas(T manager,String path){
        this.path = path;
    }

    @Override
    public void dispose() {
        if(atlas != null)atlas.dispose();
    }

    public void load(){
        this.atlas = new TextureAtlas(path);
        load(atlas);
    }

    protected abstract void load(TextureAtlas atlas);
}