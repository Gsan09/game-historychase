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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.historychase.core.GameScreen;
import com.historychase.core.event.OnButtonListener;
import com.historychase.core.widget.ImageButton;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.Settings;
import com.historychase.quiz.Choice;
import com.historychase.quiz.Question;
import com.historychase.quiz.Quiz;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class QuizScreen extends GameScreen {
    private final HistoryChase game;
    public final Stage stage;
    private QuizTable quizTable;
    private Table activeTable;
    private OrthographicCamera camera;
    private Label.LabelStyle ordinaryLabelStyle;
    private int lastHighScore = 0;
    private Texture background;
    private ControlTable controls;

    public QuizScreen(HistoryChase game) {
        super(game);
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH,HistoryChase.VIEW_HEIGHT,camera);
        stage = new Stage(viewport,game.batch);
        background = new Texture("images/story.jpg");;

        initStyles();
        activeTable = new MenuTable();
        stage.addActor(activeTable);
//        initMain();
//        initQuiz();
//        initSummary();
//        initRating();
//        demo();
        Gdx.input.setInputProcessor(stage);
        controls = new ControlTable();
        stage.addActor(controls);
        stage.act();
    }

    public void switchTable(Table table){
        System.out.println(activeTable.getX());
        activeTable.remove();
        activeTable = table;
//        table.top();
        stage.addActor(table);
    }

    private class MenuTable extends Table{
        public MenuTable(){
            super();
            setFillParent(true);
            top();
            add(new Image(new Texture("images/quiz/quiz_logo.png"))).colspan(2).padTop(30);
            ImageButton back = new ImageButton(new Texture("images/quiz/btn_back.png"));
            back.setScaling(Scaling.fit);
            ImageButton start = new ImageButton(new Texture("images/quiz/btn_start.png"));
            start.setScaling(Scaling.fit);
            row().padTop(50);
            add(start).height(18).width(70);
            add(back).height(18).width(70);
            row();
            start.setButtonListener(new OnButtonListener() {
                @Override
                public void click(InputEvent v) {
                    game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                    switchTable(new QuizTable());
                }

                @Override
                public void hover(InputEvent v) {

                }
            });
            back.setButtonListener(new OnButtonListener() {
                @Override
                public void click(InputEvent v) {
                    game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                    game.setScreen(new MainMenuScreen(game));
                }

                @Override
                public void hover(InputEvent v) {

                }
            });
        }
    }

    private class SummaryTable extends Table{
        private Table scoreTable;
        private int score;
        public SummaryTable(Quiz quiz){
            super();
            setFillParent(true);
            top();
            add(newOrdinaryLabel("Quiz Summary")).left().pad(10).colspan(2);
            row();
            scoreTable = new Table();
            add(scoreTable);
            fill(quiz);

            Settings settings = Settings.instance;
            settings.load();
            lastHighScore = settings.quizScore;

            if(lastHighScore < score){
                settings.quizScore = score;
                settings.saveUserData();
            }
        }

        private void fill(Quiz quiz){
            for(int i=0;i<8;i++){
                scoreTable.add(newOrdinaryLabel(String.format("Question # %d :  ",i+1)));
                scoreTable.add(newOrdinaryLabel(quiz.get(i).getChosen().isCorrect()?"Correct":"Wrong")).padRight(10);
                score += (quiz.get(i).getChosen().isCorrect()?1:0);
                if(i!=7){
                    scoreTable.add(newOrdinaryLabel(String.format("Question # %d :  ",(i+7)+1)));
                    scoreTable.add(newOrdinaryLabel(quiz.get(i+7).getChosen().isCorrect()?"Correct":"Wrong"));
                    score += (quiz.get(i+7).getChosen().isCorrect()?1:0);
                }
                scoreTable.row();
            }
        }

    }

    private class RatingTable extends Table{
        private RatingTable(int score){
            super();
            setFillParent(true);
            top();
            pad(30);
            String rating = "";

            if(score <= 2)
                rating = "POOR";
            else if(score <=5)
                rating = "FAIR";
            else if(score <=7)
                rating = "GOOD";
            else if(score <=10)
                rating = "GREAT";
            else if(score <=12)
                rating = "IMPRESSIVE";
            else if(score <=14)
                rating = "EXCELLENT";
            else if(score <=15)
                rating = "PERFECT";

            add(newOrdinaryLabel("Total Score")).pad(10);
            add(newOrdinaryLabel(score+"")).pad(10);
            row();
            add(newOrdinaryLabel("Rating")).pad(10);
            add(newOrdinaryLabel(rating)).pad(10);
            row();

            if(lastHighScore < score){
                add(newOrdinaryLabel("NEW HIGH SCORE!!!!!!!!")).padTop(20).colspan(2);
                row();
            }
        }
    }

    private void initStyles(){
        BitmapFont font = new BitmapFont();
        font.getData().setScale(1f);
        ordinaryLabelStyle = new Label.LabelStyle(font,Color.BLACK);
    }

    private Label newOrdinaryLabel(String text){
        return new Label(text,ordinaryLabelStyle);
    }

    @Override
    public void show() {

    }

    private void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            if(activeTable instanceof MenuTable)
             game.setScreen(new MainMenuScreen(game));
            else
                switchTable(new MenuTable());
        }
    }

    @Override
    public void render(float delta) {
        handleInput(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background,0,0,viewport.getWorldWidth(),viewport.getWorldHeight());
        game.batch.end();
        if(activeTable instanceof QuizTable){
            QuizTable qt = (QuizTable) activeTable;
            drawRect(qt.selectedChoice,qt.choicesTable.getX(),qt.choicesTable.getY());
        }
        stage.draw();

//        debug();
    }

    private void debug(){
        for(Cell cell: quizTable.getCells()){
            debugCell(cell.getActor());
        }
    }

    private void debugCell(Actor actor){
        if(actor == null)return;
        float x1 = actor.getX();
        float y1 = actor.getY();
        float x2 = x1 + actor.getWidth();
        float y2 = y1 + actor.getHeight();
        drawDebugLine(new Vector2(x1,y1),new Vector2(x2,y1),camera.combined);
        drawDebugLine(new Vector2(x1,y1),new Vector2(x1,y2),camera.combined);
        drawDebugLine(new Vector2(x1,y2),new Vector2(x2,y2),camera.combined);
        drawDebugLine(new Vector2(x2,y1),new Vector2(x2,y2),camera.combined);
    }

    private void drawRect(Actor actor,float offx,float offy){
        if(actor == null)return;
        float x = 20;
        float y = actor.getY()+offy;
        float w = getViewport().getWorldWidth() - 40;
        float h = actor.getHeight();
        drawRectangle(x,y,w,h,camera.combined);
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

    private static ShapeRenderer debugRenderer = new ShapeRenderer();

    public static void drawDebugLine(Vector2 start, Vector2 end, int lineWidth, Color color, Matrix4 projectionMatrix)
    {
        Gdx.gl.glLineWidth(lineWidth);
        debugRenderer.setProjectionMatrix(projectionMatrix);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(color);
        debugRenderer.line(start, end);
        debugRenderer.end();
        Gdx.gl.glLineWidth(1);
    }

    public static void drawDebugLine(Vector2 start, Vector2 end, Matrix4 projectionMatrix)
    {
        Gdx.gl.glLineWidth(2);
        debugRenderer.setProjectionMatrix(projectionMatrix);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(Color.GREEN);
        debugRenderer.line(start, end);
        debugRenderer.end();
        Gdx.gl.glLineWidth(1);
    }

    public static void drawRectangle(float x, float y, float w, float h ,Matrix4 projectionMatrix){
        Gdx.gl.glLineWidth(2);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
        debugRenderer.setProjectionMatrix(projectionMatrix);
        debugRenderer.begin(ShapeRenderer.ShapeType.Filled);
        debugRenderer.setColor(new Color(0,0,0,0.5f));
        debugRenderer.rect(x,y,w,h);
        debugRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(Color.WHITE);
        debugRenderer.rect(x,y,w,h);
        debugRenderer.end();
        Gdx.gl.glLineWidth(1);
    }

    interface ActionPerfomListener{
        public void actionPerform();
    };

    private class ControlTable extends Table{
        private ActionPerfomListener listener;
        public ControlTable(){
            super();
            Label submit = newOrdinaryLabel("Continue");
            submit.addListener(new InputListener(){

                private boolean isTouched = false;

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                    game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                    if(activeTable instanceof QuizTable){
                        QuizTable qt = ((QuizTable)activeTable);
                        if(qt.quizNo < 14){
                            qt.nextQuestion();
                            System.out.println(qt.quizNo);
                        }
                        else{
                            switchTable(new SummaryTable(qt.quiz));
                        }
                    }else if(activeTable instanceof SummaryTable){
                        SummaryTable st = ((SummaryTable)activeTable);
                        switchTable(new RatingTable(st.score));
                    }
                }
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

            });

            add(submit).pad(5);
            setPosition(viewport.getWorldWidth()-getWidth()-50,20);
            setVisible(false);
        }
    }

    private class QuizTable extends Table{
        private Label questionNoLabel;
        private Table choicesTable;
        private Table questionTable;
        private Quiz quiz;
        private int quizNo = -1;
        private Map<Choice,Label> map;
        private Label selectedChoice;

        public QuizTable() {
            super();
            setFillParent(true);
            top();
            quiz = new Quiz();
            quiz.initQuiz();
            init();
            nextQuestion();
        }
        private void init() {
            questionNoLabel = newOrdinaryLabel("");
            choicesTable = new Table();
            questionTable = new Table();

            this.add(questionNoLabel).padTop(10).padBottom(5);
            this.row();
            this.add(questionTable);
            this.row();
            this.add(choicesTable).padTop(5);
        }

        public void nextQuestion(){
            selectedChoice = null;
            controls.setVisible(false);
            quizNo++;

            final Question question = quiz.get(quizNo);

            questionNoLabel.setText(String.format("Question # %d",quizNo+1));

            questionTable.clearChildren();

            for(String line : question.getText())
                questionTable.add(newOrdinaryLabel(line)).row();

            choicesTable.clearChildren();

            for(final Choice choice : question){
                final Label label = newOrdinaryLabel(choice.getMaskedText());

                label.addListener(new InputListener(){
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                            selectedChoice = label;
                            choice.choose();
                            controls.setVisible(true);
                    }
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                });
                choicesTable.add(label).left().pad(2);
                choicesTable.row();
            }
        }

    }
}
