package com.historychase.game.quiz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.historychase.core.GameScreen;
import com.historychase.core.event.OnButtonListener;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.screen.MainMenuScreen;
import com.historychase.quiz.Question;
import com.historychase.quiz.Quiz;

public class QuizScreen extends GameScreen implements QuestionScene.QuizListener, SummaryScene.DoneListener {
    private final HistoryChase hcgame;
    public final Stage stage;
    private OrthographicCamera camera;
    private Texture background;
    private MainScene mainScene;
    private TutorialScene tutorialScene;
    private QuestionScene questionScene;
    private SummaryScene summaryScene;
    private PauseScene pause;
    private DoneScene doneScene;

    @Override
    public void onQuizSubmit(Quiz quiz) {
        state = State.Summary;
        summaryScene.showSummary(quiz);
    }

    @Override
    public void onDone() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                hcgame.setScreen(new QuizScreen(hcgame));
            }
        },1);
    }

    public enum State {
        Main, Tutorial, Question, Summary, Done, Pause
    }

    private State state = State.Main;

    public QuizScreen(HistoryChase game) {
        super(game);
        this.hcgame = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH,HistoryChase.VIEW_HEIGHT,camera);
        stage = new Stage(viewport,game.batch);
        background = new Texture("images/story.jpg");
        pause = new PauseScene(this);
        doneScene = new DoneScene(this);
        initMainScene();
        initTutorialScene();
        initQuestionScene();
        initSummaryScene();
        Gdx.input.setInputProcessor(mainScene.stage);
    }

    private void initMainScene() {
        mainScene = new MainScene(hcgame);
        mainScene.setOnStartButtonPress(new OnButtonListener() {
            @Override
            public void click(InputEvent v) {
                hcgame.resource.music.playSound(Constants.Path.CLICK_SOUND);
                state = State.Tutorial;
                Gdx.input.setInputProcessor(tutorialScene.stage);
            }
            @Override
            public void hover(InputEvent v) {

            }
        });
        mainScene.setOnBackButtonPress(new OnButtonListener() {
            @Override
            public void click(InputEvent v) {
                hcgame.resource.music.playSound(Constants.Path.CLICK_SOUND);
                hcgame.setScreen(new MainMenuScreen(hcgame));
            }
            @Override
            public void hover(InputEvent v) {

            }
        });
        mainScene.init();
    }

    private void initTutorialScene() {
        tutorialScene = new TutorialScene(hcgame);
        tutorialScene.continueLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                tutorialScene.continueLabel.setVisible(false);
                Gdx.input.setInputProcessor(questionScene.stage);
                state = State.Question;
            }
        });
        tutorialScene.init();
        Timer.instance().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                tutorialScene.continueLabel.setVisible(true);
            }
        },1);
    }

    private void initQuestionScene() {
        questionScene = new QuestionScene(hcgame);
        questionScene.setListener(this);
        questionScene.init();
    }

    private void initSummaryScene() {
        summaryScene = new SummaryScene(hcgame);
        summaryScene.setOnDoneListener(this);
        summaryScene.init();
    }

    @Override
    public void reset() {

    }

    @Override
    public void show() {

    }

    private void handleInput(float dt) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.A)){
            if(state == State.Question)
                pause();
        }
    }

    @Override
    public void render(float v) {
        handleInput(v);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background,0,0,viewport.getWorldWidth(),viewport.getWorldHeight());
        game.batch.end();
        switch (state) {
            case Main:
                mainScene.stage.draw();
                break;
            case Tutorial:
                tutorialScene.stage.draw();
                break;
            case Question:
                questionScene.stage.draw();
                break;
            case Summary:
                summaryScene.stage.draw();
                break;
            case Pause:
                pause.stage.draw();
                break;
            case Done:
                doneScene.stage.draw();
                break;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    private InputProcessor lastInputProc = null;
    private State lastState = null;

    @Override
    public void pause() {
        lastState = state;
        state = State.Pause;
        hcgame.resource.music.pause();
        lastInputProc = Gdx.input.getInputProcessor();
        Gdx.input.setInputProcessor(pause.stage);
    }

    @Override
    public void resume() {
        hcgame.resource.music.resume();
        Stage stage = null;
        if(lastInputProc != null)
        Gdx.input.setInputProcessor(lastInputProc);
        if(lastState != null)
        state = lastState;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.state = null;
        this.background.dispose();
        this.mainScene.dispose();
        this.tutorialScene.dispose();
        this.summaryScene.dispose();
        this.doneScene.dispose();
        this.pause.dispose();
        this.camera = null;
        this.stage.dispose();
    }

}
