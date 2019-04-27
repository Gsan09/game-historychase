package com.historychase.core.event;

import com.badlogic.gdx.scenes.scene2d.InputEvent;

public interface OnButtonListener {
    public void click(InputEvent v);
    public void hover(InputEvent v);
}
