package com.historychase.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;


public abstract class Game<T extends ResourceManager> implements ApplicationListener {
    public SpriteBatch batch;
    public T resource;
    private Class<T> resourceClass;
    private GameScreen screen;
    private GameScreen nextScreen;

    private float transitionTime;
    private boolean isInitialized;
    private boolean settingScreen;

    private FrameBuffer oldBuffer;
    private FrameBuffer newBuffer;

    public Game(Class<T> resourceClass){
        this.resourceClass = resourceClass;
        this.isInitialized = false;
        settingScreen = false;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        resource = initResource(resourceClass);
        resource.finishLoading();
    }


    @Override
    public void resize(int width, int height) {
        if (screen != null) screen.resize(width, height);
    }

    @Override
    public void pause() {
        if (screen != null) screen.pause();
    }

    @Override
    public void resume() {
        if (screen != null) screen.resume();
    }

    @Override
    public void dispose() {
        batch.dispose();
        resource.dispose();
    }


    private final T initResource(Class<T> type){
        T resource = null;
        try {
            resource = type.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return resource;
    }

    public void setScreen(GameScreen screen){
        if(settingScreen)
            return;
        else
            settingScreen = true;

        if (this.screen != null){
            this.screen.hide();
        }

        if (screen != null) {
            screen.show();
            screen.resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        }
        this.screen = screen;
        settingScreen = false;
        System.gc();
    }

    @Override
    public void render() {
        float deltaTime = Math.min(Gdx.graphics.getDeltaTime(), 1.0f / 60.0f);
        if(screen != null){
            screen.render(deltaTime);
        }
    }

}
