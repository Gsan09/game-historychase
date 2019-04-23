package com.historychase.game.assets.sprites;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.historychase.core.InteractiveTile;
import com.historychase.core.WorldMap;

public class KillerTile extends InteractiveTile {

    public KillerTile(WorldMap worldMap, Rectangle bounds, float scale) {
        super(worldMap, bounds, scale,true);
    }

    @Override
    public void collide(Fixture head) {
    }
}
