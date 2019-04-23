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
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.historychase.core.GameScreen;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.screen.MainMenuScreen;
import com.historychase.game.assets.screen.StageSelectScreen;

public class Dead implements Disposable {

    private Viewport viewport;
    private OrthographicCamera camera;
    public final Stage stage;
    private GameScreen screen;
    private ShapeRenderer shape;
    private Image btnResume,btnExit,btnReset;
    private HistoryChase game;

    public Dead(GameScreen screen){
        this.screen = screen;
        this.game = (HistoryChase)screen.game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH, HistoryChase.VIEW_HEIGHT, camera);
        stage = new Stage(viewport,screen.game.batch);
        shape = new ShapeRenderer();
        create();
    }

    public void create(){
        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        TextureRegion exitImg = new TextureRegion(game.resource.buttons,0,0,37,40);
        TextureRegion resetImg = new TextureRegion(game.resource.buttons,0,45,37,40);
        TextureRegion resumeImg = new TextureRegion(game.resource.buttons,86,45,37,40);

        btnExit = new Image(exitImg);
        btnResume = new Image(resumeImg);
        btnReset = new Image(resetImg);

        Table table = new Table();

        table.top();
        table.setFillParent(true);
        table.add(new Label("You died!",style)).colspan(3).padTop(20).center();
        table.row();
        Image image = new Image(new Texture(Gdx.files.internal("images/dead_screen.png")));
        image.setScaling(Scaling.fit);
        image.setBounds(0,0,10,10);
        table.add(image).colspan(3).center();
        table.row().padTop(10);


        btnResume.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                game.setScreen(new StageSelectScreen(game));
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
}
