package com.historychase.game.assets.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.historychase.game.HistoryChase;



public class Hud implements Disposable {

    private Viewport viewport;
    private OrthographicCamera camera;
    public final Stage stage;
    private HistoryChase game;

    private Label worldLabel;

    public Hud(HistoryChase game){
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH, HistoryChase.VIEW_HEIGHT, camera);
        stage = new Stage(viewport,game.batch);
        initLabels();
    }

    private void initLabels(){
        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.setBounds(0,HistoryChase.VIEW_HEIGHT - 25,100,25);
        table.background(new TextureRegionDrawable(new Texture("images/score_bg.png")));
        table.top().left();
        worldLabel = new Label("0",style);
        table.add(new Label("Time",style)).pad(5).left();
        table.add(worldLabel).pad(5).right();
        stage.addActor(table);
        table = null;
    }

    public Label getWorldLabel(){
        return worldLabel;
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

}
