package com.historychase.game.assets.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.historychase.core.GameScreen;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.Settings;
import com.historychase.game.assets.scenes.Options;
import com.historychase.game.assets.scenes.Unlock;


public class MainMenuScreen extends GameScreen {
    private final HistoryChase game;
    private OrthographicCamera camera;
    private Stage stage;
    private Skin skin;
    private GameScreen prev;
    private ShapeRenderer shape;
    private Unlock unlock;
    private Options options;
    private boolean isOptionShowed;
    public MainMenuScreen(HistoryChase game) {
        this(game,null);
    }
    public MainMenuScreen(HistoryChase game,GameScreen screen) {
        super(game);
        this.prev = screen;
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH,HistoryChase.VIEW_HEIGHT,camera);
        stage = new Stage(viewport,game.batch);
        skin = new Skin();
        if(screen != null){
            if(screen instanceof CreditScreen){
                System.out.println("Here");
                if(((CreditScreen)screen).showUnlock){
                    unlock = new Unlock(game, new Unlock.Action() {
                        @Override
                        public void act() {
                            unlock = null;
                            Gdx.input.setInputProcessor(stage);
                        }
                    });
                    shape = new ShapeRenderer();
                }
            }
        }
        options = new Options(this, new Options.Action() {
            @Override
            public void act() {
                isOptionShowed = false;
                Gdx.input.setInputProcessor(stage);
            }
        });
        isOptionShowed = false;

        init();
        Gdx.input.setCatchBackKey(false);
    }

    public void init(){

        game.resource.music.playMusic(Constants.Path.MAIN_MENU_MUSIC);

        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Texture background = new Texture(Gdx.files.internal("images/bg.jpg"));
        Image image = new Image(background);
        image.setBounds(0,0,viewport.getWorldWidth(),viewport.getWorldHeight());

        Texture logo = new Texture(Gdx.files.internal("images/logo.png"));
        Image logoImg = new Image(logo);
        logoImg.setBounds(viewport.getWorldWidth()/20,viewport.getWorldHeight()/3,200,140);

        Table table = new Table();
        table.setBounds((viewport.getWorldWidth()/2)+40,30,viewport.getWorldWidth()/2,viewport.getWorldHeight()/2);

        Label lblNewGame = new Label("New Game",style);
        Label lblSettings = new Label("Settings",style);
        Label lblExit = new Label("Exit",style);

        Label.LabelStyle small = new Label.LabelStyle(game.resource.defaultFont.scale(0.7f),Color.WHITE);
        Image imgStories = new Image(new Texture(Gdx.files.internal("images/scroll.png")));
        imgStories.setScaling(Scaling.fit);

        Image imgScores = new Image(new Texture(Gdx.files.internal("images/award.png")));
        imgScores.setScaling(Scaling.fit);

        Label lblStories = new Label("View Stories",small);
        Label lblScores = new Label("High Score",small);

        Image imgQuiz = new Image(new Texture(Gdx.files.internal("images/quiz.png")));
        imgQuiz.setScaling(Scaling.fit);
        Label lblQuiz = new Label("Quiz",small);

        Table tblStories = new Table();
        tblStories.setVisible(Settings.instance.hasCleared());
        tblStories.defaults().height(30);
        tblStories.add(imgStories).padRight(10);
        tblStories.add(imgScores).padRight(10);
        if(Settings.instance.cleared[8])
            tblStories.add(imgQuiz);

        tblStories.row();
        tblStories.add(lblStories).padRight(10);
        tblStories.add(lblScores).padRight(10);
        if(Settings.instance.cleared[8])
        tblStories.add(lblQuiz);
        tblStories.setBounds(50,20,90,30);

        lblNewGame.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Settings.instance.resetUserData();
                game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                game.setScreen(new StageSelectScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        lblSettings.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                isOptionShowed = true;
                Gdx.input.setInputProcessor(options.stage);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        lblExit.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                Gdx.app.exit();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        table.defaults().width(100f).height(30f);

        if(!Settings.instance.newGame){
            Label lblContinue = new Label("Continue",style);
            lblContinue.addListener(new InputListener(){
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

            table.add(lblContinue);
            table.row();
        }


        InputListener storiesListener = new  InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                game.setScreen(new StorySelectScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        };

        imgStories.addListener(storiesListener);
        lblStories.addListener(storiesListener);

        InputListener scoresListener = new  InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                game.setScreen(new ScoreScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        };

        imgScores.addListener(scoresListener);
        lblScores.addListener(scoresListener);

        if(Settings.instance.cleared[8]){
            InputListener quizListener = new  InputListener(){
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                    game.setScreen(new QuizScreen(game));
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            };

            imgQuiz.addListener(quizListener);
            lblQuiz.addListener(quizListener);
        }


        table.add(lblNewGame);
        table.row();
        table.add(lblSettings);
        table.row();
        table.add(lblExit);
        stage.addActor(image);
        stage.addActor(tblStories);
        stage.addActor(logoImg);
        stage.addActor(table);


        if(unlock != null){
            Gdx.input.setInputProcessor(unlock.stage);
        }else{
            Gdx.input.setInputProcessor(stage);
        }

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK) && unlock != null){
            unlock = null;
            Gdx.input.setInputProcessor(stage);
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        stage.draw();
        if(unlock != null){
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
            shape.setProjectionMatrix(camera.combined);
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(new Color(0,0,0,0.5f));
            shape.box(0,0,0,viewport.getWorldWidth(),viewport.getWorldHeight(),0);
            shape.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
            unlock.stage.draw();
        }
        if(isOptionShowed){
//            System.out.println(Gdx.input.getInputProcessor() == options.stage);
            options.draw();
        }
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
