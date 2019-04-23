package com.historychase.game.assets.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.historychase.core.GameScreen;
import com.historychase.game.HistoryChase;


public class CreditScreen extends GameScreen {
    private final HistoryChase game;
    private OrthographicCamera camera;
    private Stage stage;
    private Table table,clearTable;
    private Label.LabelStyle style;
    private float stayTimer;
    public final boolean showUnlock;

    public CreditScreen(HistoryChase game,boolean showUnlock) {
        super(game);
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH,HistoryChase.VIEW_HEIGHT,camera);
        stage = new Stage(viewport,game.batch);
        stayTimer = 0;
        style= new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        this.showUnlock = showUnlock;
        initTable();

    }

    public void initTable(){

        table = new Table();
        table.setVisible(false);

        table.top();
        table.setFillParent(true);
        table.row();
        table.add(new Label("History Chase Game",style)).pad(2).padBottom(5).padTop(50).colspan(3);
        table.row();
        table.add(new Label("(Cavite version)",style)).pad(2).padBottom(10).colspan(3);
        table.row();
        table.add(new Label("Created BY ChingChong",style)).pad(2).padBottom(80).colspan(3);
        table.row();
        addName("Game Director","Batousai");
        addName("Producer","Paano Mo Nasabi");
        addName("","Advance Ako Magisip");
        addName("Creative Director","Dalagang Pilipina (Yeh)");
        addName("Music Director","Tanya Markova");
        addName("Animators","Dante Gulapa");
        addFullRow("");
        addFullRow("");
        addFullRow("Special Thanks To");
        addName("Nanay Ko","Jolina");
        addName("Nanay Niyo","Melai");
        addName("Nanay Nating Lahat","Carla");
        addFullRow("");
        addFullRow("");
        addFullRow("");
        addFullRow("");
        addFullRow("");
        addFullRow("History Chase - Cavite Version");
        addFullRow("© All Rights Reserve 2019");


        clearTable = new Table();
        clearTable.top();
        clearTable.setFillParent(true);
        clearTable.row();
        clearTable.add(new Label("GAME CLEARED!",style)).pad(2).padBottom(5).padTop(50).colspan(3);

        stage.addActor(clearTable);
        stage.addActor(table);
        stage.act();

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
    }

    public Cell addName(String designation, String name){
        table.add(new Label(designation,style)).left().pad(2);
        table.add().width(20);
        table.add(new Label(name,style)).right().pad(2);
        return table.row();
    }
    public Cell addFullRow(String row){
        table.add(new Label(row,style)).pad(2).padBottom(10).colspan(3);
        return table.row();
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){

        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                game.setScreen(new MainMenuScreen(game,this));
        }
        camera.update();
    }

    @Override
    public void render(float delta) {
        handleInput(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        if((stayTimer += delta) < 3){
            table.setVisible(false);
        }else{
            table.setVisible(true);
            if((stayTimer += delta) > 5){
                table.setPosition(0,table.getY()+20*delta);
            }

            if(table.getY() > table.getHeight()){
                game.setScreen(new MainMenuScreen(game,this));
            }
            clearTable.setVisible(false);
        }
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
