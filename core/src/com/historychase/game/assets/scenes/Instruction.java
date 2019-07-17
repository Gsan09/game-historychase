package com.historychase.game.assets.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.screen.PlayScreen;

public class Instruction implements Disposable {

    private Viewport viewport;
    private OrthographicCamera camera;
    public final Stage stage;
    private PlayScreen screen;
    private ShapeRenderer shape;
    private HistoryChase game;
    private Table table;
    private boolean hasNext = true;
    private OnEndListener listener;
    private boolean allowTap = false;

    public Instruction(PlayScreen screen){
        this.screen = screen;
        this.game = (HistoryChase)screen.game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH, HistoryChase.VIEW_HEIGHT, camera);
        stage = new Stage(viewport,screen.game.batch);
        shape = new ShapeRenderer();
        create();
    }

    public void create(){
        table = new Table();

        table.top();
        table.setFillParent(true);

        table.setBackground(new TextureRegionDrawable(new Texture("images/trt1.png")));

        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                next();
            }
        });

        stage.addActor(table);

        stage.act();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                allowTap = true;
            }
        },0.5f);
    }

    public void next() {
        allowTap = false;
        if (hasNext) {
            hasNext = false;
            table.setBackground(new TextureRegionDrawable(new Texture("images/trt2.png")));
        } else {
            if(listener != null)
                listener.onEnd();
        }
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                allowTap = true;
            }
        },1f);
    }

    public void setListener(OnEndListener listener) {
        this.listener = listener;
    }

    @Override
    public void dispose() {
        System.out.println("Disposing");
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

    public interface OnEndListener {
        public void onEnd();
    }
}
