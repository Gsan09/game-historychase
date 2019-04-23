package com.historychase.game.assets.sprites;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.historychase.core.InteractiveTile;
import com.historychase.core.WorldMap;

public class DisappearTile extends InteractiveTile {

    public DisappearTile(WorldMap worldMap, Rectangle bounds, float scale) {
        super(worldMap, bounds, scale,false);
        System.out.println("Disappearing Tile Created");
    }

    @Override
    public void collide(Fixture head) {
        System.out.println("here");
//        if(head.getUserData()instanceof Chaser){
//            body.applyLinearImpulse(new Vector2(0,-4.5f),body.getWorldCenter(),true);
//        }
    }

}
