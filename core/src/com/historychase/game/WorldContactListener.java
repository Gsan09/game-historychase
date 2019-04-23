package com.historychase.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.historychase.game.assets.sprites.Chaser;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        if(contact.getFixtureA().getUserData() instanceof Chaser){
            ((Chaser)contact.getFixtureA().getUserData()).collide(contact.getFixtureB().getUserData());
            return;
        }else  if(contact.getFixtureB().getUserData() instanceof Chaser){
            ((Chaser)contact.getFixtureB().getUserData()).collide(contact.getFixtureA().getUserData());
            return;
        }

    }

    private void collidePlayer(Chaser player,Object collider){

    }



    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
