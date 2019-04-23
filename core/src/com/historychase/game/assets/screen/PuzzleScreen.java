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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.historychase.core.GameScreen;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.scenes.Pause;
import com.historychase.puzzle.Puzzle;


public class PuzzleScreen extends GameScreen {
    private final HistoryChase game;
    private OrthographicCamera camera;
    private Stage stage;
    private Table table;
    private Label.LabelStyle style;
    private Drawable onHand;
    private PuzzleImage imageMap[][];
    private Puzzle puzzle;
    private Point selected;
    private Pause pause;
    private boolean isPaused;

    private class Point{
        public final int x;
        public final int y;
        public Point(int x,int y){
            this.x = x;
            this.y = y;
        }
    }

    public PuzzleScreen(HistoryChase game) {

        super(game);
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH,HistoryChase.VIEW_HEIGHT,camera);
        stage = new Stage(viewport,game.batch);
        pause = new Pause(this);
        style= new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        isPaused = false;
        initTable();
    }

    public void initTable(){

        table = new Table();
        table.setBackground(new TextureRegionDrawable(new Texture("images/stage.jpg")));

        table.top();
        table.setFillParent(true);

        table.add(new Label("Build The Chaser Puzzle",style)).left().pad(2).colspan(5);
        table.pad(20);
        table.row();

        puzzle = Puzzle.create(game.resource.puzzle,4,4);

        imageMap = new PuzzleImage[puzzle.horizontal][puzzle.vertical];

        for(int y=0;y<puzzle.vertical;y++){
            for(int x=0;x<puzzle.horizontal;x++){
                final Point coord = new Point(x,y);
                PuzzleImage image = new PuzzleImage();
                image.addListener(new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                        if(selected == null){
                            selected = coord;
                            imageMap[coord.x][coord.y].selected = true;
                        }else{
                            imageMap[selected.x][selected.y].selected = false;
                            puzzle.swap(coord.x,coord.y,selected.x,selected.y);
                            update();
                            selected = null;
                        }
                    }
                });
                imageMap[x][y] = image;
                table.add(image).expand().pad(1);
            }
//            table.add().width(70);
            table.row();
        }
        puzzle.shuffle();
        update();

        stage.addActor(table);
        stage.act();

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
    }

    public void update(){
        for(int y=0;y<puzzle.vertical;y++){
            for(int x=0;x<puzzle.horizontal;x++){
                imageMap[x][y].setDrawable(puzzle.pieces[x][y].display);
            }
        }
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){

        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                game.setScreen(new MainMenuScreen(game));
        }
        camera.update();
    }

    @Override
    public void render(float delta) {
        if(puzzle.check()){
            game.setScreen(new CreditScreen(game));
        }
        handleInput(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        stage.draw();

        if(isPaused){
            pause.stage.draw();
            game.batch.setProjectionMatrix(pause.stage.getCamera().combined);
        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void pause() {
        isPaused = true;
        Gdx.input.setInputProcessor(pause.stage);
    }

    @Override
    public void resume() {
        isPaused = false;
        Gdx.input.setInputProcessor(stage);
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
