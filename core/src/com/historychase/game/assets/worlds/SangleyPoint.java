package com.historychase.game.assets.worlds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.historychase.core.WorldMap;
import com.historychase.game.HistoryChase;
import com.historychase.game.assets.Constants.Path;
import com.historychase.game.assets.Settings;
import com.historychase.game.assets.sprites.Scroll;
import com.historychase.game.assets.story.SangleyPointStory;
import com.historychase.game.assets.story.Story;

public class SangleyPoint extends WorldMap {
    private Scroll scroll;
    private Rectangle rectangle;
    public SangleyPoint() {
        super("Sangley Point", Path.MAP_WORLD_2,Path.BG_WORLD_2,HistoryChase.PPM);
    }

    @Override
    protected void onWorldInitialized() {

        setLayerAsFixedBlocks(3,HistoryChase.PPM);
        setLayerAsFixedBlocks(4,HistoryChase.PPM);
        setLayerAsFixedBlocks(5,HistoryChase.PPM); // Moving Platform
//
//        setBlocks(5, 0, new BlockLoader() {
//
//            private BodyDef bodyDef = new BodyDef();
//            private FixtureDef fixtureDef = new FixtureDef();
//            private Rectangle rect;
//            private PolygonShape shape = new PolygonShape();
//            private float scale = HistoryChase.PPM;
//            private Body body;
//
//            @Override
//            public void load(MapObject mapObj) {
//                rect = ((RectangleMapObject) mapObj).getRectangle();
//                bodyDef.type = BodyDef.BodyType.KinematicBody;
//                bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / scale, (rect.getY() + rect.getHeight() / 2) / scale);
//                body = world.createBody(bodyDef);
//                shape.setAsBox(rect.getWidth() / 2 / scale, rect.getHeight() / 2 / scale);
//                fixtureDef.shape = shape;
//                body.createFixture(fixtureDef);
//                addRenderer(new Renderer() {
//                    float startPosition = body.getPosition().x;
//                    boolean isMovingRight = true;
//
//                    @Override
//                    public void render(float dt) {
//                        if (isMovingRight)
//                            body.setLinearVelocity(0.3f, 0);
//                        else
//                            body.setLinearVelocity(-0.3f, 0);
//
//
//                        if (body.getPosition().x >= startPosition + 160 / scale)
//                            isMovingRight = false;
//                        if (body.getPosition().x <= startPosition)
//                            isMovingRight = true;
//                    }
//                });
//                shape.dispose();
//                fixtureDef = null;
//                bodyDef = null;
//
//                System.gc();
//            }
//
//        });


        rectangle = new Rectangle(3360.9047f,30,30,30);
        scroll = new Scroll(this,rectangle,HistoryChase.PPM);
    }

    @Override
    public void draw(SpriteBatch batch) {
        scroll.draw(batch);
    }

    @Override
    public Story getStory() {
        return new SangleyPointStory();
    }

    @Override
    public int getID() {
        return 1;
    }
}
