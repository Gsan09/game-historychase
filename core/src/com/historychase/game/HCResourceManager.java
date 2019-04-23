package com.historychase.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.historychase.core.GameFont;
import com.historychase.core.ResourceManager;
import com.historychase.game.assets.Constants;
import com.historychase.game.assets.atlas.ChaserAtlas;
import com.historychase.game.assets.atlas.StageAtlas;

public class HCResourceManager extends ResourceManager{

    public final ChaserAtlas chaserAtlas;
    public final StageAtlas stageAtlas;
    public final GameFont defaultFont;
    public final Texture buttons;
    public final Texture puzzle;
    public final Texture border;
    public final Drawable pieces[];
    public final SoundManager music;

    public HCResourceManager(){
        super();
        chaserAtlas = new ChaserAtlas(this);
        stageAtlas = new StageAtlas(this);

        defaultFont = new GameFont();
        buttons = new Texture(Gdx.files.internal(Constants.Path.BUTTONS));
        puzzle = new Texture(Gdx.files.internal(Constants.Path.PUZZLE));
        border = new Texture(Gdx.files.internal(Constants.Path.BORDER));
        pieces = new TextureRegionDrawable[16];
        music = new SoundManager(this);
    }

    public void loadPuzzle(){
        int w = puzzle.getWidth()/4;
        int h = puzzle.getHeight()/4;

        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
                pieces[x+(y*4)] = new TextureRegionDrawable(new TextureRegion(puzzle,w*x,h*y,w,h));
            }
        }
    }

    public void loadAssets(){
        chaserAtlas.load();
        stageAtlas.load();
        loadPuzzle();
        loadSounds();
    }

    public void loadSounds(){
        System.out.println("Loading Sounds");
        this.load(Constants.Path.MAIN_MENU_MUSIC, Music.class);
        this.load(Constants.Path.GAME_MUSIC, Music.class);
        this.load(Constants.Path.GAME_OVER_MUSIC, Music.class);
        this.load(Constants.Path.JUMP_SOUND, Sound.class);
        this.load(Constants.Path.CLICK_SOUND, Sound.class);
    }

    @Override
    public void dispose() {
        super.dispose();
        chaserAtlas.dispose();
    }

}
