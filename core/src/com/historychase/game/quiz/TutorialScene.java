package com.historychase.game.quiz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.historychase.core.event.OnButtonListener;
import com.historychase.core.widget.ImageButton;
import com.historychase.game.HistoryChase;

public class TutorialScene implements Disposable {
    private Viewport viewport;
    private OrthographicCamera camera;
    private Table table;
    public final Stage stage;
    private HistoryChase game;
    private Label.LabelStyle ordinaryLabelStyle;
    public final Label continueLabel;

    public TutorialScene(HistoryChase game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH, HistoryChase.VIEW_HEIGHT, camera);
        table = new Table();

        BitmapFont font = new BitmapFont();
        font.getData().setScale(0.9f);
        ordinaryLabelStyle = new Label.LabelStyle(font, Color.BLACK);

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
        String instructions = "There are 15 questions that will test your\nknowledge in historical places of cavite.\n\nEach question provides 2 choices.\nPress the \"Submit\" to finalize your answer.\nPress \"Continue\" to proceed to the next question.";
        table.add(new Label("INSTRUCTIONS:",ordinaryLabelStyle)).padTop(20);
        table.row().padTop(10);
        Label lblInstructions = new Label(instructions,ordinaryLabelStyle);
        lblInstructions.setAlignment(Align.center);
        table.add(lblInstructions).padTop(10);
        table.row();
        table.add(continueLabel).padTop(15);
        table.row();
    }

    public TutorialScene init() {
        initTable();
        return this;
    }

    @Override
    public void dispose() {

    }
}
