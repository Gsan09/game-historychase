package com.historychase.game.assets.atlas;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.historychase.core.Atlas;
import com.historychase.game.HCResourceManager;
import com.historychase.game.assets.Constants;

public final class ChaserAtlas extends Atlas<HCResourceManager> {

    public TextureRegion jump,dead,run,idle;

    public static final int JUMP_WIDTH = 122;
    public static final int JUMP_HEIGHT = 161;
    public static final int DEAD_WIDTH = 176;
    public static final int DEAD_HEIGHT = 180;
    public static final int IDLE_WIDTH = 96;
    public static final int IDLE_HEIGHT = 146;
    public static final int RUN_WIDTH = 125;
    public static final int RUN_HEIGHT = 152;

    public ChaserAtlas(HCResourceManager manager) {
        super(manager, Constants.Path.ATLAS_SPRITES);
    }

    @Override
    protected void load(TextureAtlas atlas) {
        jump = atlas.findRegion("jump");
        dead = atlas.findRegion("dead");
        run = atlas.findRegion("run");
        idle = atlas.findRegion("idle");
    }

}
