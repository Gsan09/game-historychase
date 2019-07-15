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
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.historychase.core.GameScreen;
import com.historychase.core.WorldMap;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.scenes.StaticBackground;
import com.historychase.game.assets.story.Story;


public class StoryScreen extends GameScreen {
    private final HistoryChase game;
    private OrthographicCamera camera;
    private Stage stage;
    private Table table;
    private Label.LabelStyle style,styleWhite;
    private Story story;
    private StaticBackground background;
    private float scrollValue = 0;
    private float lastScroll = 0;
    private boolean isScrolling = false;
    private boolean fromMap = false;

    public StoryScreen(HistoryChase game, WorldMap map) {
        this(game,map.getStory());
        fromMap = true;
    }

    public StoryScreen(HistoryChase game, Story story) {

        super(game);
        this.game = game;
        this.story = story;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH,HistoryChase.VIEW_HEIGHT,camera);
        stage = new Stage(viewport,game.batch);
        style= new Label.LabelStyle(new BitmapFont(), Color.BLACK);
        styleWhite= new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        styleWhite.background = new TextureRegionDrawable(new Texture("images/story_select.png"));
        background = new StaticBackground(this);
        background.setImage("images/story.jpg");
        initTable();
    }

    public void initTable(){
        System.out.println(story.getTitle());
        table = new Table();
//        table.setBackground(new TextureRegionDrawable(new Texture("images/stage.jpg")));

        table.top();
        table.left();
        table.setFillParent(true);
        Label title = new Label(story.getTitle(),style);
        title.setAlignment(Align.left);
        table.add(title).left().pad(2).pad(20);
        table.row();

        if(story.getImagePath() != null){
            Image image = new Image(new Texture(story.getImagePath()));
            image.setScaling(Scaling.fit);
            image.setWidth(400);
            image.setHeight(250);
            table.add(image).expand().height(150).padBottom(10);
            table.row();
        }

        Label.LabelStyle small = new Label.LabelStyle(game.resource.defaultFont.scale(0.7f),Color.BLACK);

        for(String line:story.getContents()){
            Label label = new Label(line,small);
            label.setWrap(true);
            table.add(label).width(350).pad(2,30,2,10);
            table.row();
        }

        Label done = new Label("Done",styleWhite);
        done.setAlignment(Align.center);
        done.setWidth(100);
//        done.setPosition((viewport.getWorldWidth()*0.9f)-done.getWidth(),(viewport.getWorldHeight()*0.9f)-done.getHeight());
        done.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                if(fromMap)
                    game.setScreen(new StageSelectScreen(game));
                else
                    game.setScreen(new StorySelectScreen(game));
            }
        });
        table.add(done).right().width(60).pad(20);

        stage.addActor(table);

        stage.act();
        stage.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isScrolling = false;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                float newPosition = lastScroll-(scrollValue-y);
                if(newPosition > 0 &&  newPosition <= story.getMaxScroll() + 50){
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
            if(fromMap)
                game.setScreen(new MainMenuScreen(game));
            else
                game.setScreen(new StorySelectScreen(game));
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
