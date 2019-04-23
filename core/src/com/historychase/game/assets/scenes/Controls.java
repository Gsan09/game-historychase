package com.historychase.game.assets.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.historychase.core.GameScreen;
import com.historychase.game.HistoryChase;

public class Controls implements Disposable {

    public final Stage stage;
    private final GameScreen screen;
    private Viewport viewport;
    private OrthographicCamera camera;

    public boolean isUpKeyPressed = false;
    public boolean isLeftKeyPressed = false;
    public boolean isRightKeyPressed = false;

    public Controls(GameScreen screen){
        this.screen = screen;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH, HistoryChase.VIEW_HEIGHT, camera);
        stage = new Stage(viewport,screen.game.batch);
        init();
    }

    private void init(){
        Table table = new Table();
        table.bottom();
        table.padBottom(5);
        table.setFillParent(true);

        Texture logo = new Texture(Gdx.files.internal("images/controls.png"));

        final TextureRegion rgnRight = new TextureRegion(logo,0,0,38,24);
        Image buttonRight = new Image(rgnRight);
        buttonRight.setScaling(Scaling.fit);

        final TextureRegion rgnLeft = new TextureRegion(logo,38,0,38,24);
        Image buttonLeft = new Image(rgnLeft);
        buttonLeft.setScaling(Scaling.fit);

        final TextureRegion rgnUp = new TextureRegion(logo,76,0,38,24);
        Image buttonUp = new Image(rgnUp);
        buttonUp.setScaling(Scaling.fit);

        buttonLeft.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rgnLeft.setRegion(38,0,38,24);
                isLeftKeyPressed = false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rgnLeft.setRegion(38,48,38,24);
                isLeftKeyPressed = true;
                return true;
            }
        });

        buttonRight.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rgnRight.setRegion(0,48,38,24);
                isRightKeyPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rgnRight.setRegion(0,0,38,24);
                isRightKeyPressed = false;
            }

        });

        buttonUp.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rgnUp.setRegion(76,48,38,24);
                isUpKeyPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rgnUp.setRegion(76,0,38,24);
                isUpKeyPressed = false;
            }

        });


        table.add(buttonLeft).padLeft(5);
        table.add(buttonRight).padLeft(10);
        table.add(buttonUp).expandX().right().padRight(10);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        stage.dispose();
        this.viewport = null;
        this.camera = null;
    }

}
