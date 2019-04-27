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

import java.util.HashMap;
import java.util.Map;


public class QuizScreen extends GameScreen {
    private final HistoryChase game;
    public final Stage stage;
    private Table menuTable;
    private QuizTable quizTable;
    private Table summaryTable,scoreTable,ratingTable;
    private OrthographicCamera camera;
    private Label.LabelStyle ordinaryLabelStyle;
    private int lastHighScore = 0;

    public QuizScreen(HistoryChase game) {
        super(game);
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH,HistoryChase.VIEW_HEIGHT,camera);
        stage = new Stage(viewport,game.batch);
        initStyles();
        initMain();
        initQuiz();
        initSummary();
        initRating();
//        demo();
        Gdx.input.setInputProcessor(stage);
        stage.act();
    }

    private void demo(){
        Quiz quiz = quizTable.quiz;

        for(Question question :quiz){
            question.get(0).choose();
        }
        quizTable.quizNo = 15;
    }

    private void initMain(){

        Image logo = new Image(new Texture("images/quiz/quiz_logo.png"));
        ImageButton back = new ImageButton(new Texture("images/quiz/btn_back.png"));
        back.setScaling(Scaling.fit);
        ImageButton start = new ImageButton(new Texture("images/quiz/btn_start.png"));
        start.setScaling(Scaling.fit);

        start.setButtonListener(new OnButtonListener() {
            @Override
            public void click(InputEvent v) {
                game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                doQuiz();
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

        menuTable = new Table();
        menuTable.setVisible(true);
        menuTable.setFillParent(true);
        menuTable.top();
        menuTable.add(logo).colspan(2).padTop(30);
        menuTable.row().padTop(50);
        menuTable.add(start).height(18).width(70);
        menuTable.add(back).height(18).width(70);
        menuTable.row();

        stage.addActor(menuTable);
    }

    private void initQuiz(){
        quizTable = new QuizTable();
        quizTable.setVisible(false);
        quizTable.setFillParent(true);
        quizTable.top();
        quizTable.nextQuestion();
        stage.addActor(quizTable.controlTable);
        stage.addActor(quizTable);
    }

    private void initSummary(){
        summaryTable = new Table();
        summaryTable.setFillParent(true);
        summaryTable.top();
        summaryTable.setVisible(false);
        summaryTable.add(newOrdinaryLabel("Quiz Summary")).left().pad(10).colspan(2);
        summaryTable.row();
        scoreTable = new Table();
        summaryTable.add(scoreTable);
        stage.addActor(summaryTable);
    }

    private void initRating(){
        ratingTable = new Table();
        ratingTable.setFillParent(true);
        ratingTable.top();
        ratingTable.setVisible(false);
        ratingTable.pad(30);
        stage.addActor(ratingTable);
    }

    private void doSummary(){
        quizTable.setVisible(false);
        menuTable.setVisible(false);
        quizTable.controlTable.setVisible(true);

        scoreTable.clearChildren();
        for(int i=0;i<8;i++){
            scoreTable.add(newOrdinaryLabel(String.format("Question # %d :  ",i+1)));
            scoreTable.add(newOrdinaryLabel(quizTable.quiz.get(i).getChosen().isCorrect()?"Correct":"Wrong")).padRight(10);
            if(i!=7){
                scoreTable.add(newOrdinaryLabel(String.format("Question # %d :  ",(i+7)+1)));
                scoreTable.add(newOrdinaryLabel(quizTable.quiz.get(i+7).getChosen().isCorrect()?"Correct":"Wrong"));
            }
            scoreTable.row();
        }
        int score = 0;

        for(Question question:quizTable.quiz)
            if(question.getChosen().isCorrect())
                score += 1;

        Settings settings = Settings.instance.load();
            lastHighScore = settings.quizScore;
            if(score > settings.quizScore)
                settings.quizScore = score;

            settings.saveUserData();

        summaryTable.setVisible(true);
    }

    private void doRating(){
        summaryTable.setVisible(false);
        menuTable.setVisible(false);
        quizTable.setVisible(false);
        ratingTable.clearChildren();
        int score = 0;
        for(Question question:quizTable.quiz)
            if(question.getChosen().isCorrect())
                score += 1;

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

        ratingTable.add(newOrdinaryLabel("Total Score")).pad(10);
        ratingTable.add(newOrdinaryLabel(score+"")).pad(10);
        ratingTable.row();
        ratingTable.add(newOrdinaryLabel("Rating")).pad(10);
        ratingTable.add(newOrdinaryLabel(rating)).pad(10);
        ratingTable.row();
        if(lastHighScore < score){
            ratingTable.add(newOrdinaryLabel("NEW HIGH SCORE!!!!!!!!")).padTop(20).colspan(2);
            ratingTable.row();
        }
        ratingTable.setVisible(true);
    }

    private void doQuiz(){
        quizTable.setVisible(true);
        menuTable.setVisible(false);
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
            if(menuTable.isVisible()){
                game.setScreen(new MainMenuScreen(game));
                return;
            }
            if(quizTable.isVisible()){
                quizTable.quiz.initQuiz();
                quizTable.quizNo = -1;
                quizTable.nextQuestion();
                quizTable.setVisible(false);
                menuTable.setVisible(true);
                return;
            }
            if(summaryTable.isVisible()){
                summaryTable.setVisible(false);
                quizTable.setVisible(false);
                menuTable.setVisible(true);
                return;
            }
            if(ratingTable.isVisible()){
                game.setScreen(new MainMenuScreen(game));
            }
        }
    }

    @Override
    public void render(float delta) {
        handleInput(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(new Texture("images/story.jpg"),0,0,viewport.getWorldWidth(),viewport.getWorldHeight());
        game.batch.end();
        drawRect(quizTable.getSelectedLabel(),quizTable.choicesTable.getX(),quizTable.choicesTable.getY());
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

    private class QuizTable extends Table{
        private Label questionNoLabel;
        private Table choicesTable;
        private Table questionTable;
        private Quiz quiz;
        private int quizNo = -1;
        private Map<Choice,Label> map;
        private Table controlTable;

        public QuizTable() {
            super();
            quiz = new Quiz();
            quiz.initQuiz();
            init();
        }

        private void init() {
            questionNoLabel = newOrdinaryLabel("");
            choicesTable = new Table();
            controlTable = new Table();
            questionTable = new Table();
            Label submit = newOrdinaryLabel("Continue");
            submit.addListener(new InputListener(){
                private boolean isTouched = false;
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if(isTouched){
                        game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                        quizTable.nextQuestion();
                    }
                    isTouched = false;
                }
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    isTouched = true;
                    return true;
                }

                @Override
                public void touchDragged(InputEvent event, float x, float y, int pointer) {
                    isTouched = x > 0 && x < getWidth() && y > 0 && y < getHeight();
                }
            });
            controlTable.add(submit).pad(5);
            controlTable.setVisible(false);
            controlTable.setPosition(viewport.getWorldWidth()-controlTable.getWidth()-50,20);
            this.add(questionNoLabel).padTop(10).padBottom(5);
            this.row();
            this.add(questionTable);
            this.row();
            this.add(choicesTable).padTop(5);
        }

        public void nextQuestion(){
            if(ratingTable != null && ratingTable.isVisible()){
                game.setScreen(new MainMenuScreen(game));
                return;
            }

            if(summaryTable != null && summaryTable.isVisible()){
                doRating();
                return;
            }

            quizNo +=1;
            if(quizNo >= quiz.size()){
                quizNo = 0;
                doSummary();
                return;
            }

            final Question question = quiz.get(quizNo);
            map = new HashMap<Choice,Label>();
            questionNoLabel.setText(String.format("Question # %d",quizNo+1));
            questionTable.clearChildren();
            for(String line : question.getText())
                questionTable.add(newOrdinaryLabel(line)).row();
            choicesTable.clearChildren();
            controlTable.setVisible(false);

            for(final Choice choice : question){
                Label label = newOrdinaryLabel(choice.getMaskedText());
                map.put(choice,label);
                label.addListener(new InputListener(){
                    private boolean isTouched = false;
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        if(isTouched){
                            choice.choose();
                            controlTable.setVisible(true);
                        }
                        isTouched = false;
                    }
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        isTouched = true;
                        return true;
                    }

                    @Override
                    public void touchDragged(InputEvent event, float x, float y, int pointer) {
                        isTouched = x > 0 && x < getWidth() && y > 0 && y < getHeight();
                    }
                });
                choicesTable.add(label).left().pad(2);
                choicesTable.row();
            }
        }

        public Label getSelectedLabel(){
            if(quizNo < 0 || quizNo >= quiz.size())return null;
            if(quiz.get(quizNo).getChosen() == null)return null;
            return map.get(quiz.get(quizNo).getChosen());
        }
    }
}
