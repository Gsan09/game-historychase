package com.historychase.game.assets.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.historychase.core.GameScreen;
import com.historychase.game.HistoryChase;


public class StaticBackground implements Disposable {

    private Viewport viewport;
    private OrthographicCamera camera;
    public final Stage stage;
    private GameScreen screen;
    private Table table;


    public StaticBackground(GameScreen screen){
        this.screen = screen;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH, HistoryChase.VIEW_HEIGHT, camera);
        stage = new Stage(viewport,screen.game.batch);
        table = new Table();
        table.top();
        table.setFillParent(true);
        stage.addActor(table);
    }

    public void setImage(String path){
        Texture texture = new Texture(Gdx.files.internal(path));
        TextureRegionDrawable drawable = new TextureRegionDrawable(texture);
        table.setBackground(drawable);
    }

    @Override
    public void dispose() {
        stage.dispose();
        this.screen = null;
        this.viewport = null;
        this.camera = null;
        this.table = null;
    }

}
