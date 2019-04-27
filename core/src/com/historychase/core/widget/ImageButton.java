package com.historychase.core.widget;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.historychase.core.event.OnButtonListener;

public class ImageButton extends Image {

    private OnButtonListener listener;

    public ImageButton(Texture texture) {
        super(texture);
        init();
    }

    private void init(){
        this.addListener(new InputListener(){
            private boolean isTouched = false;

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(isTouched)listener.click(event);
                isTouched = false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                listener.hover(event);
                isTouched = true;
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                isTouched = x > 0 && x < getImageWidth() && y > 0 && y < getImageHeight();
                if(isTouched){
                    listener.hover(event);
                }
            }
        });
    }

    public void setButtonListener(OnButtonListener listener) {
        this.listener = listener;
    }
}
