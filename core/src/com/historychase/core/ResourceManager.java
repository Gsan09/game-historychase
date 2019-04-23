package com.historychase.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ResourceManager extends AssetManager implements AssetErrorListener {
    public static final String TAG = ResourceManager.class.getName();

    public final Skin skin;

    public ResourceManager(){
        super();
        setErrorListener(this);
        skin = new Skin();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception)throwable);
    }

}
