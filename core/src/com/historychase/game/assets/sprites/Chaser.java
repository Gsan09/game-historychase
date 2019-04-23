package com.historychase.game.assets.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.historychase.core.Collidable;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.atlas.ChaserAtlas;

import java.util.ArrayList;
import java.util.List;

public class Chaser extends Sprite implements Collidable{

    public List<Collidable> collidables = new ArrayList<Collidable>();
    public enum State{
        IDLE,RUNNING,JUMPING,FALLING,DEAD
    }

    @Override
    public void collide(Object o) {
       for(Collidable collide:collidables)
           collide.collide(o);
    }

    public void addCollisionListener(Collidable listener){
        collidables.add(listener);
    }

    public void removeCollisionListener(Collidable listener){
        collidables.remove(listener);
    }

    private Animation<TextureRegion> idle,jumping,falling,running,dying;

    private State currState;
    private State prevState;

    public HistoryChase game;
    public Body body;
    public World world;

    private float stateTimer;

    private boolean isFacingRight;
    private boolean isDead;
    private boolean isRunning;

    private ChaserAtlas atlas;

    public Chaser(HistoryChase game,World world) {
        super(game.resource.chaserAtlas.run);

        this.game = game;
        this.world = world;

        atlas = game.resource.chaserAtlas;

        stateTimer =0;
        isFacingRight = true;
        isDead = false;

        prevState = State.IDLE;
        currState = State.IDLE;

        initAnimation();

        define();
    }

    private void initAnimation(){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 0;i<10;i++)
            frames.add(new TextureRegion(atlas.idle,0,ChaserAtlas.IDLE_HEIGHT*i,ChaserAtlas.IDLE_WIDTH,ChaserAtlas.IDLE_HEIGHT));
        idle = new Animation(0.1f,frames);

        frames.clear();
        for(int i = 0;i<10;i++)
            frames.add(new TextureRegion(atlas.run,0,ChaserAtlas.RUN_HEIGHT*i,ChaserAtlas.RUN_WIDTH,ChaserAtlas.RUN_HEIGHT));
        running = new Animation(0.1f,frames);
        frames.clear();

        for(int i = 0;i<10;i++)
            frames.add(new TextureRegion(atlas.dead,ChaserAtlas.DEAD_WIDTH*i,0,ChaserAtlas.DEAD_WIDTH,ChaserAtlas.DEAD_HEIGHT));
        dying = new Animation(0.1f,frames);
        frames.clear();

        for(int i = 0;i<10;i++)
            frames.add(new TextureRegion(atlas.jump,0,ChaserAtlas.JUMP_HEIGHT*i,ChaserAtlas.JUMP_WIDTH,ChaserAtlas.JUMP_HEIGHT));
        jumping = new Animation(0.1f,frames);
        frames.clear();

        for(int i = 5;i<10;i++)
            frames.add(new TextureRegion(atlas.jump,0,ChaserAtlas.JUMP_HEIGHT*i,ChaserAtlas.JUMP_WIDTH,ChaserAtlas.JUMP_HEIGHT));
        falling = new Animation(0.1f,frames);
        frames.clear();

    }

    private void define(){
        define(120/HistoryChase.PPM,40/HistoryChase.PPM);
    }

    private void define(float x, float y){
        if(body != null)world.destroyBody(body);
        isDead = false;
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x,y);
//        bodyDef.position.set(24.845f,1.205f);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10/ HistoryChase.PPM);
        fixtureDef.friction = 0.5f;
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-3/HistoryChase.PPM,30/HistoryChase.PPM),new Vector2(3/HistoryChase.PPM,30/HistoryChase.PPM));
        fixtureDef.shape = head;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData("head");
        head = null;
        bodyDef = null;
        fixtureDef = null;
        shape = null;
        System.gc();
    }

    public State getState(){
        if(isDead)
            return State.DEAD;
        else if((body.getLinearVelocity().y > 0 && currState == State.JUMPING) || (body.getLinearVelocity().y < 0 && prevState == State.JUMPING))
            return State.JUMPING;
        else if(body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(isRunning)
            return State.RUNNING;
        else
            return State.IDLE;
    }

    private TextureRegion getFrame(float dt){
        currState = getState();

        TextureRegion region;

        float w=0,h=0,x=0,y=0;
        float offset = 0;
        switch(currState){
            case DEAD:
                w = ChaserAtlas.DEAD_WIDTH;
                h = ChaserAtlas.DEAD_HEIGHT;
                offset = (15/HistoryChase.PPM);
                region = dying.getKeyFrame(stateTimer,false);
                break;
            case RUNNING:
                w = ChaserAtlas.RUN_WIDTH;
                h= ChaserAtlas.RUN_HEIGHT;
                region = running.getKeyFrame(stateTimer,true);
                break;
            case JUMPING:
                w = ChaserAtlas.JUMP_WIDTH;
                h= ChaserAtlas.JUMP_HEIGHT;
                region = jumping.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
                w = ChaserAtlas.JUMP_WIDTH;
                h= ChaserAtlas.JUMP_HEIGHT;
                region = falling.getKeyFrame(stateTimer,false);
                break;
            case IDLE:
            default:
                w = ChaserAtlas.IDLE_WIDTH;
                h= ChaserAtlas.IDLE_HEIGHT;
                region = idle.getKeyFrame(stateTimer,true);
                break;
        }

        if(!isFacingRight && !region.isFlipX())
            region.flip(true, false);
        else if(isFacingRight && region.isFlipX())
            region.flip(true, false);

        setBounds(0, 0, w/3/ HistoryChase.PPM, h/3/ HistoryChase.PPM);
        setPosition(body.getPosition().x-getWidth()/2+offset,body.getPosition().y-getHeight()/2+(15/HistoryChase.PPM) );

        stateTimer = currState == prevState ? stateTimer + dt : 0;

        prevState = currState;

        return region;

    }

    public void update(float dt){
        setRegion(getFrame(dt));
    }

    public void idle(float dt){
        isRunning = false;
    }

    public void jump(){
        if(currState != State.JUMPING && currState != State.FALLING){
            body.applyLinearImpulse(new Vector2(0,4.5f),body.getWorldCenter(),true);
            currState = State.JUMPING;
        }
    }

    public void reset(){
        define();
    }

    public void run(float dt){
        run(dt,false);
    }

    public void run(float dt,boolean reverse){
        isRunning = true;
        isFacingRight = !reverse;
        float velocity = 0;
        if(!reverse && body.getLinearVelocity().x <=2)
            velocity = 1;

        if(reverse && body.getLinearVelocity().x >= -2)
            velocity = body.getPosition().x <= 0.4?0:-1;

        body.applyLinearImpulse(new Vector2(0.1f * velocity,0),body.getWorldCenter(),true);
    }

    public void die(){
        if(!isDead){
            isDead = true;
        }
    }

    public boolean died(){
        return isDead;
    }

}
