package com.historychase.game.assets.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.historychase.core.Collidable;
import com.historychase.core.GameScreen;
import com.historychase.core.WorldMap;
import com.historychase.core.event.OnBackPressListener;
import com.historychase.game.HistoryChase;
import com.historychase.game.WorldContactListener;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.Settings;
import com.historychase.game.assets.scenes.Controls;
import com.historychase.game.assets.scenes.Dead;
import com.historychase.game.assets.scenes.Hud;
import com.historychase.game.assets.scenes.Instruction;
import com.historychase.game.assets.scenes.Options;
import com.historychase.game.assets.scenes.Pause;
import com.historychase.game.assets.scenes.StageClear;
import com.historychase.game.assets.scenes.StaticBackground;
import com.historychase.game.assets.scenes.Toast;
import com.historychase.game.assets.sprites.Chaser;
import com.historychase.game.assets.sprites.DisappearTile;
import com.historychase.game.assets.sprites.KillerTile;
import com.historychase.game.assets.sprites.Scroll;
import com.historychase.game.assets.sprites.Sign;
import com.historychase.game.assets.worlds.AguinaldoShrine;
import com.historychase.game.assets.worlds.BinakayanBattle;
import com.historychase.game.assets.worlds.BonifacioTrialHouse;
import com.historychase.game.assets.worlds.CorregidorIsland;
import com.historychase.game.assets.worlds.DasmarinasCathedral;
import com.historychase.game.assets.worlds.SangleyPoint;
import com.historychase.game.assets.worlds.TejerosConvention;

import java.util.ArrayList;
import java.util.List;

import static com.historychase.game.assets.Constants.Path.GAME_OVER_MUSIC;
import static com.historychase.game.assets.Constants.Path.JUMP_SOUND;

public class PlayScreen extends GameScreen implements OnBackPressListener, Instruction.OnEndListener {

    public final HistoryChase game;
    public final WorldMap worldMap;

    private OrthographicCamera camera;

    private boolean debugEnable;
    private Box2DDebugRenderer boxDebugRenderer;
    private float clearAnimTimer = 0;

    private Viewport viewport;
    private Chaser chaser;
    private Controls controls;
    private Pause pauseScene;
    private Dead deadScene;
    private StaticBackground background;
    private StageClear clearScene;
    private boolean isPaused,showPaused,showDead;
    private WorldContactListener contactListener;
    private float deathTimer=0;
    private float gameTimer=0;
    private boolean cleared;
    private List<Act> acts = new ArrayList<Act>();
    private List<Act> remove = new ArrayList<Act>();
    private Instruction instructionScene;
    private Instruction forDispose;
    private boolean isCreated = false;
    private Hud hud;
    private float worldHighScore;
    private Pause.Action onSettings;
    private Options options;
    private boolean isOptionShowed;
    private Toast toast;

    private float ctStart = 0;

    public PlayScreen(final HistoryChase game, WorldMap map) {
        super(game);
        game.resource.music.playMusic(Constants.Path.GAME_MUSIC,true);
        this.game = game;
        worldMap = map;

        camera = new OrthographicCamera();
        viewport = new FitViewport(HistoryChase.VIEW_WITDH/HistoryChase.PPM,HistoryChase.VIEW_HEIGHT/HistoryChase.PPM,camera);

        camera.position.set(viewport.getWorldWidth()/2,viewport.getWorldHeight()/2,0);
        controls = new Controls(this);
        options = new Options(this, new Options.Action() {
            @Override
            public void act() {
                isOptionShowed = false;
                Gdx.input.setInputProcessor(pauseScene.stage);
            }
        });

        pauseScene = new Pause(this,new Pause.Action() {
            @Override
            public void act() {
                isOptionShowed = true;
                Gdx.input.setInputProcessor(options.stage);
            }
        });

        deadScene = new Dead(this);
        background = new StaticBackground(this);
        clearScene = new StageClear(this);
        hud = new Hud(game);
        toast = new Toast(this);

        Settings settings = Settings.instance;

        background.setImage(map.background);
        chaser = new Chaser(game,map.world);
        cleared = false;

        chaser.addCollisionListener(new Collidable() {
            @Override
            public void collide(Object o) {
                if(o instanceof KillerTile)
                    chaser.die();
                else if(o instanceof Scroll)
                    gameClear();
                else if(o instanceof Sign) {
                    toast.setMessage(((Sign)o).message);
                }
                else if(o instanceof DisappearTile){
                    final DisappearTile tile = (DisappearTile)o;
                    System.out.println("Disappear");

                    acts.add(new Act(0.2f) {
                        @Override
                        public void act(float dt) {
                            if (tile.isDisposed())return;
                            if(this.decrement(dt) <0){
                                tile.body.setType(BodyDef.BodyType.DynamicBody);
                                tile.body.getFixtureList().get(0).setSensor(true);
                            }
                            if(tile.body.getPosition().y < 0){
                                remove.add(this);
                                tile.dispose();
                            }
                        }
                    });
                }
//                    ((DisappearTile)o).body.applyLinearImpulse(new Vector2(0,-4.5f),((DisappearTile)o).body.getWorldCenter(),true);
            }

            @Override
            public void end(Object o) {
                if(o instanceof Sign) {
                    toast.clear();
                }
            }
        });

        contactListener = new WorldContactListener();
        boxDebugRenderer = new Box2DDebugRenderer();
        isPaused = false;
        showPaused = false;
        showDead = false;

        Gdx.input.setCatchBackKey(true);

        instructionScene = new Instruction(this);
        instructionScene.setListener(this);
        Gdx.input.setInputProcessor(instructionScene.stage);

        worldHighScore = Settings.instance.time[worldMap.getID()];

        worldMap.world.setContactListener(contactListener);
    }


    private void handleInput(float dt){

        if(chaser.getState() != Chaser.State.DEAD) {
            if (controls.isUpKeyPressed) {
                if(chaser.getState()!= Chaser.State.JUMPING && chaser.getState() != Chaser.State.JUMPING)
                game.resource.music.playSound(JUMP_SOUND);
                chaser.jump();
                controls.isUpKeyPressed = false;
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){

                if(chaser.getState()!= Chaser.State.JUMPING && chaser.getState() != Chaser.State.JUMPING)
                game.resource.music.playSound(JUMP_SOUND);
                chaser.jump();
            }

            if (controls.isRightKeyPressed || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                chaser.run(dt);
            else if (controls.isLeftKeyPressed || Gdx.input.isKeyPressed(Input.Keys.LEFT))
                chaser.run(dt, true);
            else
                chaser.idle(dt);
        }

    }

    public void masterControl(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            if(cleared)return;
            if(isPaused)resume();
            else {
                showPaused = true;
                pause();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)){
            chaser.reset();
            resume();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.X))
            chaser.die();
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)){
            showPaused = true;
            pause();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)){
            gameClear();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S))
            resume();

        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
                game.setScreen(new PlayScreen(game, new BinakayanBattle()));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
                game.setScreen(new PlayScreen(game, new SangleyPoint()));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
                game.setScreen(new PlayScreen(game, new TejerosConvention()));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
                game.setScreen(new PlayScreen(game, new BonifacioTrialHouse()));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
                game.setScreen(new PlayScreen(game, new DasmarinasCathedral()));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
                game.setScreen(new PlayScreen(game, new AguinaldoShrine()));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) {
                game.setScreen(new PlayScreen(game, new CorregidorIsland()));
            }
        }
    }

    public void update(float dt){
        handleInput(dt);
        worldMap.world.step(1 / 60f, 6, 2);
        chaser.update(dt);

        if(!cleared)
            gameTimer+=dt;
        float time = gameTimer;

        hud.getWorldLabel().setText(String.format("%.3f",time));

        if(chaser.getY() < 0)
            chaser.die();

        if(chaser.body.getPosition().x > viewport.getWorldWidth()/2){
            camera.position.x = chaser.body.getPosition().x;
        }else{
            camera.position.x = viewport.getWorldWidth()/2;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.F1)){
            System.out.println(worldMap.mapRenderer.getViewBounds());
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.F2)){
            System.out.println(chaser.body.getPosition().x);
        }

        camera.update();
        worldMap.mapRenderer.setView(camera);

        if(chaser.died()){
            showPaused = false;
            if((deathTimer += dt) >= 1){
                game.resource.music.playMusic(GAME_OVER_MUSIC,false);
                showDead = true;
                pause();
            }
        }

        if(cleared){
            showPaused = false;
            showDead = false;
            pause();
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float dt) {
        masterControl();
        for (Act act: acts) {
            act.act(dt);
        }
        for (Act act: remove) {
            acts.remove(act);
        }
        remove.clear();

        if(!isPaused)
            update(dt);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        background.stage.draw();
        worldMap.render(dt);
        camera.update();
//        boxDebugRenderer.render(worldMap.world,camera.combined);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
            worldMap.draw(game.batch);
            chaser.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(controls.stage.getCamera().combined);
        if(!isPaused)
            hud.stage.draw();


        if(instructionScene != null){
            if(!isPaused)pause();
            instructionScene.draw();
        }

        if(!isPaused)
        controls.stage.draw();


        if(isPaused && showPaused)
            pauseScene.draw();

        if(isPaused && showDead)
            deadScene.draw();

        if(cleared) {
            float anim = clearAnimTimer+=dt;
            float animCtr = anim;
            if(animCtr >= 0.5 && animCtr <0.8){
                clearScene.highScore.value = worldHighScore;
                if(worldHighScore > 0)
                clearScene.highScore.show();
            }
            if(animCtr >= 0.8 && animCtr <1){
                clearScene.currScore.value = gameTimer;
                clearScene.currScore.show();
            }
            if(animCtr >= 1.5 && animCtr <2){
                clearScene.timeDiff.value = gameTimer - worldHighScore;

                if(worldHighScore > 0)
                clearScene.timeDiff.show();
            }
            if(animCtr >= 2.5 && animCtr < 3){
                clearScene.newRecord.setVisible(gameTimer < worldHighScore || worldHighScore == 0);
            }
            if(animCtr >= 3){
                clearScene.table.setVisible(true);
                clearScene.scoringTable.setVisible(false);
            }

            clearScene.draw();
        }

        if(isOptionShowed){
//            System.out.println(Gdx.input.getInputProcessor() == options.stage);
            options.draw();
        }

        if(!isPaused)
            toast.draw();
    }

    @Override
    public void resize(int w, int h) {
        viewport.update(w,h);
    }

    @Override
    public void pause() {
        isPaused = true;
        if(showPaused){
            game.resource.music.pause();
            Gdx.input.setInputProcessor(pauseScene.stage);
        }
        else if(showDead)
            Gdx.input.setInputProcessor(deadScene.stage);
    }

    @Override
    public void resume() {
        game.resource.music.resume();
        isPaused = false;
        showPaused = false;
        showDead = false;
        Gdx.input.setInputProcessor(controls.stage);
    }

    @Override
    public void reset(){
        try {
            resume();
            game.setScreen(new PlayScreen(game,worldMap.getClass().newInstance()));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        boxDebugRenderer.dispose();
        background.dispose();
        worldMap.dispose();
        controls.dispose();
    }

    @Override
    public boolean onBackPressed() {
        if(isPaused)
            pause();
        else
            resume();
        return false;
    }

    public void gameClear(){
        game.resource.music.playMusic(Constants.Path.SUCCESS_MUSIC);
        cleared = true;
        Gdx.input.setInputProcessor(clearScene.stage);
        Settings.instance.cleared[worldMap.getID()] = true;
        if(gameTimer < worldHighScore || worldHighScore ==0)
            Settings.instance.time[worldMap.getID()] = gameTimer;
        Settings.instance.newGame = false;
        Settings.instance.saveUserData();
    }

    @Override
    public void onEnd() {
        Instruction i = instructionScene;
        instructionScene = null;
        i.dispose();
        i = null;
        resume();
    }

    private abstract class Act{
        public float delay=0;
        public Act(){
            this.delay = 0;
        }
        public Act(float delay){
            this.delay = delay;
        }
        public float decrement(float d){
            return delay -= d;
        }
        public abstract void act(float dt);
    }

}
