package com.historychase.game.assets.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.historychase.game.HistoryChase;

public class Alert implements Disposable {

    private Viewport viewport;
    private OrthographicCamera camera;
    public final Stage stage;
    private HistoryChase game;

    public Alert(HistoryChase game){
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH, HistoryChase.VIEW_HEIGHT, camera);
        stage = new Stage(viewport,game.batch);
    }

    private void initLabels(){
        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();

        Texture background = new Texture(Gdx.files.internal("images/logo.png"));

        table.defaults().width(100f).height(20f);

        table.top();

        table.center();

        stage.addActor(table);

        table = null;
    }

    @Override
    public void dispose() {
        stage.dispose();
        this.game = null;
        this.viewport = null;
        this.camera = null;
    }
}
