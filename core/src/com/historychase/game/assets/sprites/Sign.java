package com.historychase.game.assets.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.historychase.core.WorldMap;

public class Sign {
    private Rectangle bounds;
    private WorldMap worldMap;
    private float scale;
    public final Body body;
    private Texture texture;
    public String message;
    public Sign(WorldMap worldMap, Rectangle bounds, float scale) {
        this.bounds = bounds;
        this.worldMap = worldMap;
        this.scale = scale;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape  = new PolygonShape();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX()+bounds.getWidth()/2)/scale,(bounds.getY()+bounds.getHeight()/2)/scale);
        body = worldMap.world.createBody(bodyDef);
        shape.setAsBox(bounds.getWidth()/2/scale,bounds.getHeight()/2/scale);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);

        texture = new Texture(Gdx.files.internal("images/sign.png"));
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture,bounds.getX()/scale,bounds.getY()/scale,bounds.getWidth()/ scale,bounds.height/scale);
    }
}
