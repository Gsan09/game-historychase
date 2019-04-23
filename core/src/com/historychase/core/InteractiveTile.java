package com.historychase.core;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Disposable;

public abstract class InteractiveTile implements Disposable {
    private boolean disposed;
    private WorldMap worldMap;
    private TiledMap map;
    public final Rectangle bounds;
    private PolygonShape shape;
    private float scale;
    public final Body body;

    protected InteractiveTile(WorldMap worldMap, Rectangle bounds,float scale) {
        this(worldMap,bounds,scale,false);
    }
    protected InteractiveTile(WorldMap worldMap, Rectangle bounds,float scale,boolean sensor) {

        this.worldMap = worldMap;
        this.bounds = bounds;
        this.scale = scale;
        this.disposed = false;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        shape  = new PolygonShape();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX()+bounds.getWidth()/2)/scale,(bounds.getY()+bounds.getHeight()/2)/scale);
        body = worldMap.world.createBody(bodyDef);
        shape.setAsBox(bounds.getWidth()/2/scale,bounds.getHeight()/2/scale);
        fixtureDef.shape = shape;
        fixtureDef.isSensor =sensor;
        body.createFixture(fixtureDef).setUserData(this);
    }

    public abstract void collide(Fixture head);

    @Override
    public void dispose() {
        if(disposed)
            return;
        shape.dispose();
        worldMap.world.destroyBody(body);
        this.disposed = true;
    }

    public boolean isDisposed(){
        return disposed;
    }
}
