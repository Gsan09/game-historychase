package com.historychase.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.historychase.core.Game;
import com.historychase.game.assets.screen.SplashScreen;

public class HistoryChase extends Game<HCResourceManager>{
    public static final int VIEW_WITDH = 400;       //Game viewport width
    public static final int VIEW_HEIGHT = 200;      //Game viewport height
    public static final float PPM = 100;

    public GameRenderer renderer;

    public HistoryChase() {
        super(HCResourceManager.class);
            renderer = new GameRenderer(this);
    }

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        super.create();
        setScreen(new SplashScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        renderer.dispose();
    }
}
