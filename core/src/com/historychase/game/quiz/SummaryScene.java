package com.historychase.game.quiz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Settings;
import com.historychase.quiz.Choice;
import com.historychase.quiz.Quiz;

public class SummaryScene implements Disposable {

    private final int SHOW_ITEM_DURATION = 3;

    private Viewport viewport;
    private OrthographicCamera camera;
    private Table table;
    public final Stage stage;
    private HistoryChase game;
    private Label.LabelStyle ordinaryLabelStyle,correct,wrong;
    public final Label continueLabel;
    private int lastHighScore = 0;
    private DoneListener listener;

    public SummaryScene(HistoryChase game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH, HistoryChase.VIEW_HEIGHT, camera);
        table = new Table();

        BitmapFont font = new BitmapFont();
        font.getData().setScale(0.9f);
        ordinaryLabelStyle = new Label.LabelStyle(font, Color.BLACK);
        correct = new Label.LabelStyle(font, Color.LIME);
        wrong = new Label.LabelStyle(font, Color.FIREBRICK);

        font = new BitmapFont();
        font.getData().setScale(1f);
        continueLabel = new Label("( Press Here To Continue )",new Label.LabelStyle(font, Color.WHITE));
        continueLabel.setVisible(false);

        stage = new Stage(viewport,game.batch);
        stage.addActor(table);
        stage.act();
    }

    private void initTable() {
        table.setFillParent(true);
        table.top();
    }

    public void showSummary(final Quiz quiz) {
        Settings settings = Settings.instance;
        settings.load();
        lastHighScore = settings.quizScore;
        System.out.println(quiz.getScore());
        settings.quizScore = quiz.getScore();
        settings.saveUserData();
        showScore(quiz);
    }

    private void showScore(final Quiz quiz) {
        Timer.instance().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                BitmapFont font = new BitmapFont();
                font.getData().setScale(1.2f);
                Label l = new Label("Quiz Summary:",new Label.LabelStyle(font, Color.BLACK));
                l.setAlignment(Align.center);
                table.add(l).colspan(6).padTop(20).padBottom(10);
                showItem(quiz);
            }
        },0.5f);
    }

    private void showSummaryTable(final Quiz quiz) {
        table.clearChildren();
        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.2f);
        Label l = new Label("Quiz Summary:",new Label.LabelStyle(font, Color.BLACK));
        l.setAlignment(Align.center);
        table.add(l).colspan(2).padTop(20).padBottom(10);
        table.row();
        Timer.instance().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                table.add(new Label("Total Score",ordinaryLabelStyle)).pad(10).width(200);
                table.add(new Label(quiz.getScore()+"",ordinaryLabelStyle)).pad(10);
                table.row();
            }
        },1f);
        Timer.instance().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                table.add(new Label("Rating",ordinaryLabelStyle)).width(200).pad(10);
                table.add(new Label(quiz.getRating()+"",ordinaryLabelStyle)).pad(10);
                table.row();
            }
        },2f);


        Timer.instance().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if(quiz.getScore() > lastHighScore) {
                    BitmapFont font = new BitmapFont();
                    font.getData().setScale(1.2f);
                    Label.LabelStyle style = new Label.LabelStyle(font, Color.BLACK);
                    table.add(new Label("NEW HIGHSCORE!",style)).colspan(2).pad(10);
                    table.row();
                    Timer.instance().scheduleTask(new Timer.Task() {
                        @Override
                        public void run() {
                            done();
                        }
                    },3);
                }else {
                    Timer.instance().scheduleTask(new Timer.Task() {
                        @Override
                        public void run() {
                            done();
                        }
                    },2);
                }
            }
        },3f);

    }

    private void done() {
        Timer.instance().scheduleTask(new Timer.Task() {
            @Override
            public void run() {

            }
        },1);
        if(listener != null) {
            listener.onDone();
        }
    }

    public void setOnDoneListener(DoneListener listener) {
        this.listener = listener;
    }

    private void showItem(final Quiz quiz) {
        table.row().padTop(20);
        table.row();
        for(int i=0;i<5;i++){
            table.add(new Label(String.format("%d.)",i+1),ordinaryLabelStyle)).pad(3);
            table.add(getAnswerLabel(quiz,i)).pad(3);
            table.add(new Label(String.format("%d.)",i+6),ordinaryLabelStyle)).pad(3);
            table.add(getAnswerLabel(quiz,i+5)).pad(5);
            table.add(new Label(String.format("%d.)",i+11),ordinaryLabelStyle)).pad(3);
            table.add(getAnswerLabel(quiz,i+10)).pad(5);
            table.row().padTop(2);
        }
        Timer.instance().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
               showSummaryTable(quiz);
            }
        },SHOW_ITEM_DURATION);
    }

    private Label getAnswerLabel(Quiz quiz,int position) {
        Label label = new Label("",wrong);
        if(position < quiz.size()) {
            String status = "Incorrect";
            if(quiz.get(position).getChosen() != null) {
                if(quiz.get(position).getChosen().isCorrect()) {
                    status = "Correct";
                    label.setStyle(correct);
                }
            }
            label.setText(status);
        }
        return label;
    }

    public SummaryScene init() {
        initTable();
        return this;
    }

    @Override
    public void dispose() {

    }

    public interface DoneListener {
        public void onDone();
    }
}
