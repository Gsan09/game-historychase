package com.historychase.game.assets.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.historychase.core.GameScreen;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Settings;

import java.util.Set;

public class SplashScreen extends GameScreen {
    private HistoryChase game;
    private GameScreen nextScreen;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;

    public SplashScreen(HistoryChase game) {
        super(game);
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,HistoryChase.VIEW_WITDH,HistoryChase.VIEW_HEIGHT);
        camera.update();
        shapeRenderer = new ShapeRenderer();

}

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(game.resource.update()){
            game.resource.loadAssets();
            Gdx.app.log("Splash Screen", "Assets are Loaded!");
//            Settings.instance.resetUserData();
            Settings.instance.demo();
            game.resource.finishLoading();
            game.setScreen(new MainMenuScreen((HistoryChase) game));
        }else{
            Gdx.gl.glClearColor(255, 255, 255, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.rect( 0, 3,HistoryChase.VIEW_WITDH , 6);
            shapeRenderer.end();

            game.batch.setProjectionMatrix(camera.combined);
            game.batch.enableBlending();
            game.batch.begin();
            String progress = "LOADING GAME [ "+Math.round(game.resource.getProgress()*100)+"% ]";
            game.renderer.drawText(progress, 5,8, Color.WHITE, 0.3f);
            progress = null;
            game.batch.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, HistoryChase.VIEW_WITDH, HistoryChase.VIEW_HEIGHT);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void reset() {

    }
}
