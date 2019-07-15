package com.historychase.game.assets.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
    private int mockProgress = 0;
    private float elapsed = 0;
    private boolean mockTimeoutActive = false;
    private boolean suspend = false;
    private Texture logo = null;
    private float x,y,w,h;

    public SplashScreen(HistoryChase game) {
        super(game);
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,HistoryChase.VIEW_WITDH,HistoryChase.VIEW_HEIGHT);
        camera.update();
        shapeRenderer = new ShapeRenderer();
        logo = new Texture(Gdx.files.internal("images/logo.png"));
        w = HistoryChase.VIEW_WITDH/2;
        h = HistoryChase.VIEW_HEIGHT/2;
        x = (HistoryChase.VIEW_WITDH - w)/2;
        y = (HistoryChase.VIEW_HEIGHT - h)/2;
}

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(game.resource.update()){
            if(!suspend) {
                if(mockTimeoutActive) {

                    if (mockProgress >= 100) {
                        Gdx.gl.glClearColor(0, 0, 0, 1);
                        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                        game.setScreen(new MainMenuScreen((HistoryChase) game));
                        suspend = true;
                        return;
                    }

                    elapsed += delta;
                    Gdx.app.log("Splash Screen", elapsed + ":" + mockProgress);

                    Gdx.gl.glClearColor(255, 255, 255, 1);
                    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                    shapeRenderer.setProjectionMatrix(camera.combined);
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                    shapeRenderer.setColor(Color.BLACK);
                    shapeRenderer.rect( 0, 3,HistoryChase.VIEW_WITDH , 8);
                    shapeRenderer.end();
                    game.batch.setProjectionMatrix(camera.combined);
                    game.batch.enableBlending();
                    game.batch.begin();

                    if(elapsed < 1 ) {
                        mockProgress += 1;
                    }else if(elapsed >= 1.1f && elapsed <= 1.3f ) {
                        mockProgress += 1;
                    }else if(elapsed >= 1.7f && elapsed <= 2f ) {
                        mockProgress += 1;
                    }else if(elapsed >= 2.6f && elapsed <= 3f ) {
                        mockProgress += 1;
                    }

                    String progress = "LOADING GAME [ "+mockProgress+"% ]";
                    game.renderer.drawText(progress, 5,9, Color.WHITE, 0.4f);

                    game.batch.draw(logo,x,y,w,h);

                    progress = null;
                    game.batch.end();

                } else {
                    game.resource.loadAssets();
                    Gdx.app.log("Splash Screen", "Assets are Loaded!");
                    //            Settings.instance.resetUserData();
                    //            Settings.instance.demo();
                    game.resource.finishLoading();
                    mockTimeoutActive = true;
                }
            }
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
