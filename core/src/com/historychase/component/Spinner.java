package com.historychase.component;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Spinner extends Actor {
    private float progress;
    private ShapeRenderer shaper;

    public Spinner() {
        this(100);
    }

    public Spinner(float progress) {
        super();
        this.progress = progress;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        shaper.begin(ShapeRenderer.ShapeType.Filled);

        shaper.end();
    }


}
