package com.historychase.game.assets.atlas;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.historychase.core.Atlas;
import com.historychase.game.HCResourceManager;
import com.historychase.game.assets.Constants;

public class StageAtlas extends Atlas<HCResourceManager> {
    public TextureAtlas atlas;

    public StageAtlas(HCResourceManager manager) {
        super(manager, Constants.Path.ATLAS_STAGE);
    }

    @Override
    protected void load(TextureAtlas atlas) {
        this.atlas = atlas;
    }

}
