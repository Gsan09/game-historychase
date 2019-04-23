package com.historychase.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.historychase.game.assets.Settings;
import com.historychase.game.assets.sprites.DisappearTile;
import com.historychase.game.assets.sprites.KillerTile;
import com.historychase.game.assets.story.Story;

import java.util.ArrayList;
import java.util.List;

public abstract class WorldMap implements Disposable {

    private final String mapPath;

    private TmxMapLoader mapLoader;
    public final OrthogonalTiledMapRenderer mapRenderer;
    private TiledMap map;
    private String name;
    private float scale;
    private List<Renderer> renderers;
    public final World world;
    public String background;

    protected WorldMap(String name,String tileMapPath,String background) {
        this(name,tileMapPath,background,1);
    }

    protected WorldMap(String name,String tileMapPath,String background,float unitScale) {
        this.name = name;
        this.mapPath = tileMapPath;
        this.scale = unitScale;
        this.background = background;
        world = new World(new Vector2(0, -10), true);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load(mapPath);
        mapRenderer = new OrthogonalTiledMapRenderer(map,1f/scale);
        renderers = new ArrayList<Renderer>();
        Settings.instance.loadUserData();
        onWorldInitialized();
    }

    public void render(float dt){
        mapRenderer.render();
        for(Renderer renderer:renderers)
            renderer.render(dt);
    }

    @Override
    public void dispose() {
        if(map!=null)
            map.dispose();
        if(mapRenderer!=null)
            mapRenderer.dispose();
        name = null;
        map = null;
        renderers.clear();
        renderers = null;
    }

    protected WorldMap addRenderer(Renderer renderer){
        renderers.add(renderer);
        return this;
    }

    protected void removeRenderer(Renderer renderer){
        renderers.remove(renderer);
    }

    protected <T extends MapObject> WorldMap setBlocks(int layer, int index, BlockLoader loader){
        loader.load(map.getLayers().get(layer).getObjects().get(index));
        return this;
    }

    protected  WorldMap setLayerBlocks(int layer, LayerLoader init) {
        MapObjects objects = map.getLayers().get(layer).getObjects();

        for (int i = 0;i<objects.getCount();i++)
            init.onEachLayerObject(i,objects.get(i));

        init.dispose();

        return this;
    }

    protected WorldMap setLayerAsFixedBlocks(int layer, float scale){
        setLayerBlocks(layer,new GroundLayerLoader(scale));
        return this;
    }

    protected WorldMap setLayerAsDeadlyBlocks(int layer, final float scale){

        setLayerBlocks(layer, new LayerLoader() {
            @Override
            public void onEachLayerObject(int index, MapObject object) {
                Rectangle rect  = ((RectangleMapObject)object).getRectangle();
                KillerTile tile = new KillerTile(WorldMap.this,rect,scale);
            }

            @Override
            public void dispose() {

            }
        });

        return this;
    }

    protected DisappearTile[] setLayerAsDisappearingBlocks(int layer, final float scale){
        final List<DisappearTile> tiles = new ArrayList<DisappearTile>();
        setLayerBlocks(layer, new LayerLoader() {
            @Override
            public void onEachLayerObject(int index, MapObject object) {
                Rectangle rect  = ((RectangleMapObject)object).getRectangle();
                DisappearTile tile = new DisappearTile(WorldMap.this,rect,scale);
                tiles.add(tile);
            }

            @Override
            public void dispose() {

            }
        });

        return tiles.toArray(new DisappearTile[tiles.size()]);
    }

    public interface BlockLoader {
        public void load(MapObject mapObj);
    }

    protected interface Renderer{
        public void render(float dt);
    }

    protected interface LayerLoader extends Disposable{
        public void onEachLayerObject(int index,MapObject object);
    }

    protected class GroundLayerLoader implements LayerLoader{
        private BodyDef bodyDef = new BodyDef();
        private FixtureDef fixtureDef = new FixtureDef();
        private Rectangle rect;
        private PolygonShape shape = new PolygonShape();
        private float scale;

        public GroundLayerLoader(float scale) {
            this.scale = scale;
        }

        @Override
        public void onEachLayerObject(int index,MapObject object) {
            rect= ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / scale,(rect.getY() + rect.getHeight() / 2) / scale);
            Body body = world.createBody(bodyDef);
            shape.setAsBox(rect.getWidth()/2 / scale,rect.getHeight()/2/ scale);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }

        @Override
        public void dispose() {
            bodyDef = null;
            fixtureDef = null;
            rect = null;
            shape = null;
        }
    }

    protected abstract void onWorldInitialized();

    public abstract void draw(SpriteBatch batch);

    public abstract Story getStory();

    public abstract int getID();
}
