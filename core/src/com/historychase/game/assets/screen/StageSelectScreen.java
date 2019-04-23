package com.historychase.game.assets.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.historychase.core.GameScreen;
import com.historychase.core.WorldMap;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.Settings;
import com.historychase.game.assets.worlds.AguinaldoShrine;
import com.historychase.game.assets.worlds.BaldomeroAguinaldo;
import com.historychase.game.assets.worlds.BinakayanBattle;
import com.historychase.game.assets.worlds.BonifacioTrialHouse;
import com.historychase.game.assets.worlds.CorregidorIsland;
import com.historychase.game.assets.worlds.DasmarinasCathedral;
import com.historychase.game.assets.worlds.SangleyPoint;
import com.historychase.game.assets.worlds.TejerosConvention;


public class StageSelectScreen extends GameScreen {
    private final HistoryChase game;
    private OrthographicCamera camera;
    private Stage stage;
    private Table table;
    private Label toPuzzle;

    public StageSelectScreen(HistoryChase game) {
        super(game);
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH,HistoryChase.VIEW_HEIGHT,camera);
        stage = new Stage(viewport,game.batch);
        init();
        Gdx.input.setCatchBackKey(true);
    }

    public void init(){
        table = new Table();
        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Label.LabelStyle storyStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        storyStyle.background = new TextureRegionDrawable(new Texture("images/story_select.png"));

        Texture background = new Texture(Gdx.files.internal("images/stage.jpg"));
        table.setBackground(new TextureRegionDrawable(background));
        table.top();
        table.setFillParent(true);

        table.add(new Label("Select Stage",style)).colspan(4).expandX().padTop(10).padBottom(10);

        table.padLeft(10).padRight(10);
        table.row();
        Image image;

        Settings.instance.loadUserData();

        for(int i=0;i<8;i++){
            String path = "";
            if(Settings.instance.maxLevel <= i)
                path = "world_"+(i+1)+"_locked";
            else if(Settings.instance.cleared[i])
                path = "world_"+(i+1)+"_clear";
            else
                path = "world_"+(i+1)+"_open";
            image = new Image(game.resource.stageAtlas.atlas.findRegion(path));
            image.setScaling(Scaling.fit);

            if(path != "world_"+(i+1)+"_locked")
                image.addListener(new GameSelectListener(i));

            table.add(image).height(50).pad(2);

            if(i == 3){
                table.row();
            }
        }
        table.row();

        toPuzzle = new Label("Puzzle Unlocked: Tap To Play",storyStyle);
        toPuzzle.setVisible(Settings.instance.maxLevel >=9);
        toPuzzle.setAlignment(Align.center);
        toPuzzle.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.resource.music.playSound(Constants.Path.CLICK_SOUND);
                game.setScreen(new PuzzleScreen(game));
            }
        });



        table.add(toPuzzle).pad(5).width(300).colspan(4);

        stage.addActor(table);
        stage.act();

        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            game.setScreen(new MainMenuScreen(game));
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
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

    public final class GameSelectListener extends InputListener {
        private int level;
        public GameSelectListener(int i) {
            level = i;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            game.resource.music.playSound(Constants.Path.CLICK_SOUND);
            if(level >= Settings.instance.loadUserData().maxLevel)return;
            WorldMap map;

            switch(level){
                case 0:
                    map = new BinakayanBattle();
                    break;
                case 1:
                    map = new SangleyPoint();
                    break;
                case 2:
                    map = new TejerosConvention();
                    break;
                case 3:
                    map = new BonifacioTrialHouse();
                    break;
                case 4:
                    map = new DasmarinasCathedral();
                    break;
                case 5:
                    map = new BaldomeroAguinaldo();
                    break;
                case 6:
                    map = new CorregidorIsland();
                    break;
                case 7:
                    map = new AguinaldoShrine();
                    break;
                default:
                    map = null;
                    break;
            }

            game.setScreen(new PlayScreen(game,map));
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            return true;
        }
    }
}
