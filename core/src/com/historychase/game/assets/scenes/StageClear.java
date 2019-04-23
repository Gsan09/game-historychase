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
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.screen.MainMenuScreen;
import com.historychase.game.assets.screen.PlayScreen;
import com.historychase.game.assets.screen.StageSelectScreen;
import com.historychase.game.assets.screen.StoryScreen;

public class StageClear implements Disposable {

    private Viewport viewport;
    private OrthographicCamera camera;
    public final Stage stage;
    private PlayScreen screen;
    private ShapeRenderer shape;
    private Image btnResume,btnExit,btnReset;
    private HistoryChase game;
    public Table table,scoringTable;
    public float score;
    public float highscore;
    public ScoreValue highScore,currScore,timeDiff;
    public Label newRecord;

    public StageClear(PlayScreen screen){
        this.screen = screen;
        this.game = (HistoryChase)screen.game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH, HistoryChase.VIEW_HEIGHT, camera);
        stage = new Stage(viewport,screen.game.batch);
        shape = new ShapeRenderer();
        score = 0.1f;
        highscore = 0.2f;
        create();
    }

    public void create(){
        final Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        TextureRegion exitImg = new TextureRegion(game.resource.buttons,0,0,37,40);
        TextureRegion resetImg = new TextureRegion(game.resource.buttons,0,45,37,40);
        TextureRegion resumeImg = new TextureRegion(game.resource.buttons,86,45,37,40);

        btnExit = new Image(exitImg);
        btnResume = new Image(resumeImg);
        btnReset = new Image(resetImg);

        table = new Table();

        table.top();
        table.setFillParent(true);
        table.add(new Label("Stage Cleared!",style)).colspan(3).padTop(10).center();
        table.row();

        Image image = new Image(new Texture(Gdx.files.internal("images/scroll.png")));
        image.setScaling(Scaling.fit);
        image.setBounds(0,0,10,10);
        image.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new StoryScreen(game,screen.worldMap));
            }
        });
        table.add(image).colspan(3).center();
        table.row();
        table.add(new Label("(Tap Scroll to read)",style)).colspan(3);
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
        table.setVisible(false);

        highScore = new ScoreValue("World HighScore",highscore);
        currScore = new ScoreValue("Total Running Time",score);
        timeDiff = new ScoreValue("",score-highscore);

        scoringTable = new Table();
        scoringTable.setFillParent(true);
        scoringTable.top();
        scoringTable.add(new Label("Stage Cleared!",style)).colspan(2).pad(10).center();
        scoringTable.row();

        highScore.attach(scoringTable);
        currScore.attach(scoringTable);
        timeDiff.attach(scoringTable);

        newRecord = new Label("NEW RECORD !!!!!!",style);
        newRecord.setVisible(false);
        scoringTable.add(newRecord).pad(5).colspan(2);

        stage.addActor(table);
        stage.addActor(scoringTable);

        stage.act();
    }

    public class ScoreValue{
        public String text;
        public float value;
        private Label.LabelStyle style;
        private Label textLabel,valueLabel;

        public ScoreValue(String text, float value) {
            this.text = text;
            this.value = value;
            style = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
            textLabel = new Label(text,style);
            valueLabel = new Label(String.format("%.3f",value),style);
            hide();
        }

        public void update(){
            valueLabel.setText(String.format("%.3f",value));
            textLabel.setText(text);
        }

        public void attach(Table t){
            t.add(textLabel).pad(5).left();
            t.add(valueLabel).pad(5).right();
            t.row();
        }

        public void hide(){
            textLabel.setVisible(false);
            valueLabel.setVisible(false);
        }

        public void show(){
            textLabel.setVisible(true);
            valueLabel.setVisible(true);
        }
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
        highScore.update();
        currScore.update();
        timeDiff.update();
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
