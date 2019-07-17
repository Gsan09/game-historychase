package com.historychase.game.quiz;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.historychase.core.event.OnButtonListener;
import com.historychase.core.widget.ImageButton;
import com.historychase.game.HistoryChase;

public class MainScene implements Disposable {
    private Viewport viewport;
    private OrthographicCamera camera;
    private Table table;
    public final Stage stage;
    private HistoryChase game;
    private OnButtonListener onStart,onBack;

    public MainScene(HistoryChase game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH, HistoryChase.VIEW_HEIGHT, camera);
        table = new Table();
        stage = new Stage(viewport,game.batch);
        stage.addActor(table);
        stage.act();
    }

    private void initTable() {
        ImageButton start = new ImageButton(new Texture("images/quiz/btn_start.png"));
        start.setScaling(Scaling.fit);
        ImageButton back = new ImageButton(new Texture("images/quiz/btn_back.png"));
        back.setScaling(Scaling.fit);
        if(onStart != null)
            start.setButtonListener(onStart);
        if(onBack != null)
            back.setButtonListener(onBack);
        table.setFillParent(true);
        table.top();
        table.add(new Image(new Texture("images/quiz/quiz_logo.png"))).colspan(2).padTop(30);
        table.row().padTop(50);
        table.add(start).height(18).width(70);
        table.add(back).height(18).width(70);
        table.row();
    }

    public MainScene setOnStartButtonPress(OnButtonListener listener) {
        onStart = listener;
        return this;
    }

    public MainScene setOnBackButtonPress(OnButtonListener listener) {
        onBack = listener;
        return this;
    }

    public MainScene init() {
        initTable();
        return this;
    }

    @Override
    public void dispose() {

    }
}
