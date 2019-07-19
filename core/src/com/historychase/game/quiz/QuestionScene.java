package com.historychase.game.quiz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.historychase.game.HistoryChase;
import com.historychase.quiz.Question;
import com.historychase.quiz.Quiz;

public class QuestionScene implements Disposable {
    private final float NEXT_SCREEN_INTERVAL = 1f;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Table table, controls;
    public final Stage stage;
    private HistoryChase game;
    private Label.LabelStyle ordinaryLabelStyle,selectedC,selectedW,wrong,correct;
    private Quiz quiz;
    private Label lblQuestion,lblChoice1,lblChoice2,lblCorrect;
    private int current = 0;
    private QuizListener listener;

    public QuestionScene(HistoryChase game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH, HistoryChase.VIEW_HEIGHT, camera);
        table = new Table();
        controls = new Table();

        BitmapFont font = new BitmapFont();
        font.getData().setScale(0.9f);
        ordinaryLabelStyle = new Label.LabelStyle(font, Color.BLACK);

        font = new BitmapFont();
        font.getData().setScale(0.9f);
        selectedC = new Label.LabelStyle(font, Color.LIME);
        selectedW = new Label.LabelStyle(font, Color.FIREBRICK);

        font = new BitmapFont();
        font.getData().setScale(1.2f);
        wrong = new Label.LabelStyle(font, Color.FIREBRICK);
        correct = new Label.LabelStyle(font, Color.LIME);

        quiz = new Quiz();
        quiz.initQuiz();
        stage = new Stage(viewport,game.batch);
        stage.addActor(table);
        stage.addActor(controls);
        stage.act();
    }

    private void initTable() {
        initControls();
        lblQuestion = new Label("",ordinaryLabelStyle);
        lblQuestion.setAlignment(Align.center);
        lblChoice1 = new Label("Answer1",ordinaryLabelStyle);
        lblChoice1.setAlignment(Align.center);
        lblChoice2 = new Label("Answer2",ordinaryLabelStyle);
        lblChoice2.setAlignment(Align.center);
        table.setFillParent(true);
        table.top();
        table.add(lblQuestion).colspan(2).padTop(20);
        table.row().padTop(10);
        table.add(lblChoice1).padTop(20).padRight(10);
        table.add(lblChoice2).padTop(20).padLeft(10);
        table.row();
        lblChoice1.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(!lblCorrect.isVisible()){
                    lblCorrect.setVisible(true);
                    quiz.get(current-1).get(0).choose();
                    if(quiz.get(current-1).get(0).isCorrect()){
                        lblCorrect.setStyle(correct);
                        lblCorrect.setText("You are Correct!");
                        lblChoice1.setStyle(selectedC);
                    }else {
                        lblCorrect.setStyle(wrong);
                        lblCorrect.setText("You are Incorrect.");
                        lblChoice1.setStyle(selectedW);
                    }
                    Timer.instance().scheduleTask(new Timer.Task() {
                        @Override
                        public void run() {
                            next();
                        }
                    },NEXT_SCREEN_INTERVAL);
                }
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        lblChoice2.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(!lblCorrect.isVisible()){
                    lblCorrect.setVisible(true);
                    quiz.get(current-1).get(1).choose();
                    if(quiz.get(current-1).get(1).isCorrect()){
                        lblCorrect.setStyle(correct);
                        lblCorrect.setText("You are Correct!");
                        lblChoice2.setStyle(selectedC);
                    }else {
                        lblCorrect.setStyle(wrong);
                        lblCorrect.setText("You are Incorrect.");
                        lblChoice2.setStyle(selectedW);
                    }
                    Timer.instance().scheduleTask(new Timer.Task() {
                        @Override
                        public void run() {
                            next();
                        }
                    },NEXT_SCREEN_INTERVAL);
                }
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        next();
    }

    private void initControls() {
        lblCorrect = new Label("",ordinaryLabelStyle);
        controls.setFillParent(true);
        controls.bottom();
        controls.add(lblCorrect).padBottom(20);
    }

    public void next() {
        if(current <15 ){
            Question question = quiz.get(current++);
            String lines[] = question.getText();
            String questionText = "";
            for (String line: lines ) {
                if(questionText.length() > 0) {
                    questionText += "\n";
                }
                questionText += line;
            }
            lblCorrect.setVisible(false);
            lblChoice1.setStyle(ordinaryLabelStyle);
            lblChoice2.setStyle(ordinaryLabelStyle);
            lblQuestion.setText(String.format("%d.)  %s",current,questionText));
            lblChoice1.setText(question.get(0).getText());
            lblChoice2.setText(question.get(1).getText());

        } else{
            if(listener != null) {
                listener.onQuizSubmit(quiz);
            }
        }
    }

    public void setListener(QuizListener listener) {
        this.listener = listener;
    }

    public QuestionScene init() {
        initTable();
        return this;
    }

    @Override
    public void dispose() {

    }

    public interface QuizListener{
        public void onQuizSubmit(Quiz quiz);
    }
}
