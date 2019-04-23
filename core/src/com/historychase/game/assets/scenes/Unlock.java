package com.historychase.game.assets.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.screen.StorySelectScreen;


public class Unlock implements Disposable {

    private Viewport viewport;
    private OrthographicCamera camera;
    public final Stage stage;
    private HistoryChase game;
    public Label see,cancel;
    public Action hide;

    public Unlock(HistoryChase game,Action hide){
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH, HistoryChase.VIEW_HEIGHT, camera);
        stage = new Stage(viewport,game.batch);
        this.hide = hide;
        initLabels();
    }

    private void initLabels(){
        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.setFillParent(true);
        table.top().center();
        table.add(new Label("CONGRATULATIONS",style)).pad(10).expandX().colspan(2);
        table.row();
        table.add(new Label("You Have Cleared The Game!",style)).expandX().colspan(2);
        table.row();
        table.add(new Label("All Stories are now unlocked",style)).padTop(10).expandX().colspan(2);
        table.row();
        see = new Label("See",style);
        see.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new StorySelectScreen(game));
            }
        });
        table.add(see).padTop(10).right().padRight(10);
        cancel = new Label("Cancel",style);
        cancel.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                hide.act();
            }
        });
        table.add(cancel).padTop(10).left().padLeft(20);
        stage.addActor(table);
        stage.act();
        table = null;
    }

    public void update(float dt){

    }

    @Override
    public void dispose() {
        stage.dispose();
        this.game = null;
        this.viewport = null;
        this.camera = null;
    }

    public interface Action{
        public void act();
    }

}
