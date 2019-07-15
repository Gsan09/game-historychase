package com.historychase.game.assets.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.historychase.core.GameScreen;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.Settings;
import com.historychase.game.assets.scenes.StaticBackground;
import com.historychase.game.assets.story.AguinaldoShrineStory;
import com.historychase.game.assets.story.BaldomeroAguinaldoStory;
import com.historychase.game.assets.story.BinakayanBattleStory;
import com.historychase.game.assets.story.BonifacioTrialHouseStory;
import com.historychase.game.assets.story.CorregidorIslandStory;
import com.historychase.game.assets.story.DasmarinasCathedralStory;
import com.historychase.game.assets.story.SangleyPointStory;
import com.historychase.game.assets.story.Story;
import com.historychase.game.assets.story.TejeroConventionStory;


public class ScoreScreen extends GameScreen {
    private final HistoryChase game;
    private OrthographicCamera camera;
    private Stage stage;
    private Table table;
    private Label.LabelStyle style,styleWhite;
    private StaticBackground background;
    private float scrollValue = 0;
    private float lastScroll = 0;
    private boolean isScrolling = false;
    private boolean fromMap = false;

    public ScoreScreen(HistoryChase game) {

        super(game);
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH,HistoryChase.VIEW_HEIGHT,camera);
        stage = new Stage(viewport,game.batch);
        style= new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        styleWhite= new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        styleWhite.background = new TextureRegionDrawable(new Texture("images/story_select.png"));
        background = new StaticBackground(this);
        background.setImage("images/bg.jpg");
        initTable();
    }

    public void initTable(){
        table = new Table();
//        table.setBackground(new TextureRegionDrawable(new Texture("images/stage.jpg")));

        table.top();
        table.left();
        table.setFillParent(true);
        Label title = new Label("High Scores",style);
        title.setAlignment(Align.left);
        table.add(title).left().pad(20).colspan(3);
        table.row();

        final Story stories[] = {
                new BinakayanBattleStory(),
                new SangleyPointStory(),
                new TejeroConventionStory(),
                new BonifacioTrialHouseStory(),
                new DasmarinasCathedralStory(),
                new BaldomeroAguinaldoStory(),
                new CorregidorIslandStory(),
                new AguinaldoShrineStory()
        };

        Settings settings = Settings.instance.loadUserData();

        table.add(new Label("Stage Name",style)).left().padLeft(20).padTop(10);
        table.add().width(100);
        table.add(new Label("Time",style)).padTop(10).width(100);
        table.add().row();

        for(int i=0;i<8;i++){
            if(!settings.cleared[i])continue;
            table.add(new Label(stories[i].getTitle(),style)).left().padLeft(20).padTop(10);
            table.add().width(100);
            table.add(new Label(String.format("%.3f",settings.time[i]),style)).padTop(10).width(100);
            table.add().row();
        }

        if(settings.cleared[8]){
            table.add(new Label("Chaser Puzzle",style)).left().padLeft(20).padTop(10);
            table.add().width(100);
            int time = Math.round(settings.time[8]);
            table.add(new Label(String.format("%.3f",settings.time[8]),style)).padTop(10).width(100);
            table.add().row();
        }

        if(settings.quizScore>-1){
            table.add(new Label("Quiz score",style)).left().padLeft(20).padTop(10);
            table.add().width(100);
            int time = Math.round(settings.time[8]);
            table.add(new Label(String.format("%d",settings.quizScore),style)).padTop(10).width(100);
            table.add().row();
        }

        Label done = new Label("Back",styleWhite);
        done.setAlignment(Align.center);
        done.setWidth(50);
        done.setPosition((viewport.getWorldWidth()*0.9f)-done.getWidth(),(viewport.getWorldHeight()*0.9f)-done.getHeight());
        done.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(table);

        stage.addActor(done);
        stage.act();
        stage.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isScrolling = false;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                float newPosition = lastScroll-(scrollValue-y);
                if(newPosition > 0 &&  newPosition <= 161.875f){
                    table.setPosition(table.getX(),newPosition);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                lastScroll = table.getY();
                scrollValue = y;
                isScrolling = true;
                return true;
            }
        });

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){

        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            game.setScreen(new MainMenuScreen(game));
        }

        camera.update();
    }

    @Override
    public void render(float delta) {
        handleInput(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        background.stage.draw();
        game.batch.setProjectionMatrix(camera.combined);
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
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

    private class PuzzleImage extends Image{
        private ShapeRenderer renderer;
        public boolean selected = false;

        public PuzzleImage(){
            super();
            renderer = new ShapeRenderer();
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            if(selected){
                batch.draw(game.resource.border,this.getX(),this.getY(),this.getImageWidth(),this.getImageHeight());
            }
        }

    }
}
