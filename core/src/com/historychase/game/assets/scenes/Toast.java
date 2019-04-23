package com.historychase.game.assets.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.historychase.core.GameScreen;
import com.historychase.game.HistoryChase;

public class Toast implements Disposable {

    private Viewport viewport;
    private OrthographicCamera camera;
    public final Stage stage;
    private GameScreen screen;
    private ShapeRenderer shape;
    private HistoryChase game;
    private Label lblMessage;
    private Table tbl;
    private boolean visible = false;

    public Toast(GameScreen screen){
        this.screen = screen;
        this.game = (HistoryChase)screen.game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH, HistoryChase.VIEW_HEIGHT, camera);
        stage = new Stage(viewport,screen.game.batch);
        shape = new ShapeRenderer();
        create();
    }

    public void clear(){
        visible = false;
    }

    public void setMessage(String message){
        visible = true;
        lblMessage.setText(message);
    }

    public void create(){
        Label.LabelStyle style = new Label.LabelStyle(((HistoryChase)screen.game).resource.defaultFont.small, Color.WHITE);
        tbl = new Table();
        tbl.background(new TextureRegionDrawable(new Texture("images/score_bg.png")));
        tbl.top();
        tbl.setPosition(viewport.getWorldWidth()/2,viewport.getWorldHeight()*6.5f/8);
        lblMessage = new Label("Toast",style);
        tbl.add(lblMessage).center();
        tbl.row().padTop(20);

        stage.addActor(tbl);

        stage.act();
    }


    @Override
    public void dispose() {
        stage.dispose();
        shape.dispose();
        this.screen = null;
        this.viewport = null;
        this.camera = null;
        this.shape = null;
        System.gc();
    }

    public void draw(){
        if(!visible)return;
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(new Color(0,0,0,0.5f));

        if(lblMessage.getText().toString().split("\n").length == 3)
            shape.box(0,(viewport.getWorldHeight()*5.2f/8)-tbl.getHeight(),0,viewport.getWorldWidth(),viewport.getWorldHeight()/6,0);
        else
            shape.box(0,viewport.getWorldHeight()*5.6f/8,0,viewport.getWorldWidth(),viewport.getWorldHeight()/8,0);

        shape.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        stage.draw();
    }

}
