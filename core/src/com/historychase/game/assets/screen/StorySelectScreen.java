package com.historychase.game.assets.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.historychase.core.GameScreen;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.story.AguinaldoShrineStory;
import com.historychase.game.assets.story.BaldomeroAguinaldoStory;
import com.historychase.game.assets.story.BinakayanBattleStory;
import com.historychase.game.assets.story.BonifacioTrialHouseStory;
import com.historychase.game.assets.story.CanacaoBayStory;
import com.historychase.game.assets.story.CaviteCityHallStory;
import com.historychase.game.assets.story.CorregidorIslandStory;
import com.historychase.game.assets.story.DasmarinasCathedralStory;
import com.historychase.game.assets.story.DonLadislawStory;
import com.historychase.game.assets.story.EmptyStory;
import com.historychase.game.assets.story.GeneralTriasStory;
import com.historychase.game.assets.story.ImusCathedralStory;
import com.historychase.game.assets.story.JulianFelipeMonumentStory;
import com.historychase.game.assets.story.NoveletaTribunalStory;
import com.historychase.game.assets.story.SangleyPointStory;
import com.historychase.game.assets.story.Story;
import com.historychase.game.assets.story.TejeroConventionStory;
import com.historychase.util.Pager;


public class StorySelectScreen extends GameScreen {
    private final HistoryChase game;
    private OrthographicCamera camera;
    private Stage stage;
    private Table table;
    private Label.LabelStyle style;
    private Label.LabelStyle storyStyle;
    private Label.LabelStyle disabledStyle;
    private Pager<Story> storyPager;
    private Image next,previous;

    public StorySelectScreen(HistoryChase game) {

        super(game);
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH,HistoryChase.VIEW_HEIGHT,camera);
        stage = new Stage(viewport,game.batch);

        style= new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        storyStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        disabledStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        storyStyle.background = new TextureRegionDrawable(new Texture("images/story_select.png"));
        disabledStyle.background = new TextureRegionDrawable(new Texture("images/story_select_locked.png"));

        initTable();
    }

    public void initTable(){

        table = new Table();
        table.setBackground(new TextureRegionDrawable(new Texture("images/stage.jpg")));

        table.top();
        table.setFillParent(true);

        table.add(new Label("Obtained Stories",style)).left().expandX().pad(20,15,10,5).colspan(4);
        table.padLeft(10).padRight(10);
        table.row();

        final Story stories[] = {
                new BinakayanBattleStory(),
                new SangleyPointStory(),
                new TejeroConventionStory(),
                new BonifacioTrialHouseStory(),
                new DasmarinasCathedralStory(),
                new BaldomeroAguinaldoStory(),
                new CorregidorIslandStory(),
                new AguinaldoShrineStory(),
                new NoveletaTribunalStory(),
                new CanacaoBayStory(),
                new ImusCathedralStory(),
                new JulianFelipeMonumentStory(),
                new DonLadislawStory(),
                new CaviteCityHallStory(),
                new GeneralTriasStory()
        };

        final StoryButtons buttons[]  = new StoryButtons[4];

        final Story empty = new EmptyStory();

        for(int i=0;i<buttons.length;i++) {
            buttons[i] = new StoryButtons(table,empty);
            buttons[i].attach().padLeft(20).padRight(20).colspan(4);
            table.row();
        }

        table.add().width(270);
        TextureRegion prevImg = new TextureRegion(game.resource.buttons,130,89,38,40);
        previous = new Image(prevImg);
        previous.setScaling(Scaling.fit);
        previous.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                storyPager.prev();
            }
        });

        TextureRegion nextImg = new TextureRegion(game.resource.buttons,130,0,38,40);
        next = new Image(nextImg);
        next.setScaling(Scaling.fit);
        next.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                storyPager.next();
            }
        });

        TextureRegion homeImg = new TextureRegion(game.resource.buttons,87,133,38,40);
        Image home = new Image(homeImg);
        home.setScaling(Scaling.fit);
        home.addListener(new InputListener(){
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

        storyPager = new Pager<Story>(stories,buttons.length);

        storyPager.addListener(new Pager.PageListener() {
            @Override
            public void onSwitchPage(int page, int prev, Object[] items) {
                Story[] stories = storyPager.getPageItems(Story.class);
                for(int i=0;i<buttons.length;i++){
                    buttons[i].setStory(stories[i]!=null?stories[i]:empty);
                }
                next.setVisible(storyPager.hasNextPage());
                previous.setVisible(storyPager.hasPrevPage());

            }
        });

        storyPager.gotoPage(1);

        table.add(home).width(30).height(30).pad(5).left();
        table.add(previous).width(30).height(30).pad(5);
        table.add(next).width(30).height(30).pad(5);
        stage.addActor(table);
        stage.act();

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
    }

    public class StoryButtons {

        private Label title;
        private Table table;
        private Story story;
        private InputListener listener;

        public StoryButtons(Table table, Story story){
            this.table = table;
            this.story = story;
            title = new Label("",disabledStyle);
            title.setAlignment(Align.center);
        }

        private StoryButtons update(){

            if(listener != null)
                title.removeListener(listener);

            title.setText((!story.isAllowed() && story.getDisabledTitle() != null)?story.getDisabledTitle():story.getTitle());
            title.setStyle(story.isAllowed()?storyStyle:disabledStyle);

            if(story instanceof EmptyStory)
                title.setStyle(style);

            listener = new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                    game.setScreen(new StoryScreen(game,story));
                }
            };

            if(story.isAllowed())
                title.addListener(listener);

            return this;
        }

        public StoryButtons setStory(Story story){
            this.story = story;
            return update();
        }

        public Cell attach(){
            return table.add(title)
                    .height(22)
                    .expandX()
                    .fill()
                    .pad(2);
        }

    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){

        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                game.setScreen(new MainMenuScreen(game));
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            storyPager.prev();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            storyPager.next();
        }

        camera.update();
    }

    @Override
    public void render(float delta) {
        handleInput(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

}
