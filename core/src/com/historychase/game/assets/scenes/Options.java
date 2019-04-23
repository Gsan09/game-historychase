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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.historychase.core.GameScreen;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.Settings;

public class Options implements Disposable {

    private Viewport viewport;
    private OrthographicCamera camera;
    public final Stage stage;
    private GameScreen screen;
    private ShapeRenderer shape;
    private HistoryChase game;
    private Action onDone;

    public Options(GameScreen screen,Action onDone){
        this.screen = screen;
        this.onDone = onDone;
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
        TextureRegion resumeImg = new TextureRegion(game.resource.buttons,42,45,37,40);


        Table table = new Table();
        final Settings settings = Settings.instance.load();

        final Label musicConfig = new Label(settings.musicEnabled?"On":"Off",style);
        musicConfig.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                settings.musicEnabled = !settings.musicEnabled;
                settings.save();
                musicConfig.setText(settings.musicEnabled?"On":"Off");
                game.resource.music.update();
            }
        });

        final Label soundConfig = new Label(settings.soundEnabled?"On":"Off",style);
        soundConfig.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                settings.soundEnabled = !settings.soundEnabled;
                settings.save();
                soundConfig.setText(settings.soundEnabled?"On":"Off");
            }
        });

        table.top();
        table.setFillParent(true);

        table.add(new Label("Game Settings",style)).colspan(3).padTop(20).center();
        table.row();
        table.add(new Label("Music",style)).padTop(20).left();
        table.add().width(100);
        table.add(musicConfig).padTop(20).center();
        table.row();
        table.add(new Label("Sound",style)).padTop(5).left();
        table.add().width(100);
        table.add(soundConfig).padTop(5).center();
        table.row();

        Label done = new Label("Done",style);
        done.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                onDone.act();
            }
        });
        table.add(done).padTop(20).colspan(3).center();

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
