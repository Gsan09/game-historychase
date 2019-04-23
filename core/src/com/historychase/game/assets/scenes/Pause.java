package com.historychase.game.assets.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.historychase.core.GameScreen;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.screen.MainMenuScreen;

public class Pause implements Disposable {

    private Viewport viewport;
    private OrthographicCamera camera;
    public final Stage stage;
    private GameScreen screen;
    private ShapeRenderer shape;
    private Image btnResume,btnExit,btnReset;
    private HistoryChase game;
    private Action onSettings;

    public Pause(GameScreen screen,Action onSettings){
        this.screen = screen;
        this.game = (HistoryChase)screen.game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH, HistoryChase.VIEW_HEIGHT, camera);
        stage = new Stage(viewport,screen.game.batch);
        shape = new ShapeRenderer();
        this.onSettings = onSettings;
        create();
    }
    public Pause(GameScreen screen){
       this(screen, new Action() {
           @Override
           public void act() {

           }
       });
    }

    public void create(){
        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        TextureRegion exitImg = new TextureRegion(game.resource.buttons,0,0,37,40);
        TextureRegion resetImg = new TextureRegion(game.resource.buttons,0,45,37,40);
        TextureRegion resumeImg = new TextureRegion(game.resource.buttons,42,45,37,40);
        btnExit = new Image(exitImg);
        btnResume = new Image(resumeImg);
        btnReset = new Image(resetImg);

        Table table = new Table();

        table.top();
        table.setFillParent(true);

        table.add(new Label("Game Paused",style)).colspan(3).padTop(80).center();
        table.row().padTop(20);


        btnResume.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                screen.resume();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        btnExit.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                game.setScreen(new MainMenuScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        btnReset.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                screen.reset();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        table.add(btnResume).right();
        table.add(btnReset).center().padLeft(20).padRight(20);
        table.add(btnExit).left();
        Label lblSettings = new Label("Settings",style);
        lblSettings.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                onSettings.act();
            }
        });
        table.row();
        table.add(lblSettings).center().colspan(3).pad(5);

        stage.addActor(table);

        stage.act();

        table = null;
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
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(new Color(0,0,0,0.5f));
        shape.box(0,0,0,viewport.getWorldWidth(),viewport.getWorldHeight(),0);
        shape.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        stage.draw();
    }

    public interface Action{
        public void act();
    }
}
