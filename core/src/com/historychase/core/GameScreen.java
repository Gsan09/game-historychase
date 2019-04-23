package com.historychase.core;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.historychase.core.event.OnBackPressListener;

public abstract class GameScreen implements Screen, InputProcessor {
    public final Game game;
    protected Viewport viewport;
    public GameScreen(Game game){
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {

        if(this instanceof OnBackPressListener){
            OnBackPressListener listener = (OnBackPressListener) this;
            if (keycode == Keys.BACK||keycode== Keys.ESCAPE) {
                return listener.onBackPressed();
            }
        }
        return true;
    }

    public Viewport getViewport() {
        return viewport;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public abstract void reset();

}
