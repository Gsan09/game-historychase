package com.historychase.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.historychase.game.assets.Settings;

public class SoundManager {

    private HCResourceManager manager;

    public Music music;

    public SoundManager(HCResourceManager manager){
        this.manager = manager;
    }

    public void playMusic(String path){
        playMusic(path,true);
    }

    public void playMusic(String path,boolean isLoop){
        Settings settings = Settings.instance;
        settings.load();
        if(manager.get(path,Music.class).equals(music))return;

        if(music != null){
            music.stop();
        }

        music = manager.get(path,Music.class);

        music.setVolume(settings.musicEnabled?settings.musicVolume:0);
        music.setLooping(isLoop);
        music.play();
    }

    public void update(){
        Settings settings = Settings.instance;
        if(music != null)
            music.setVolume(settings.musicEnabled?settings.musicVolume:0);
    }

    public void playSound(String path){
        Settings settings = Settings.instance;
        Sound sound = manager.get(path, Sound.class);
        if(settings.soundEnabled)sound.play();
    }

    public void pause(){
        music.pause();
    }

    public void resume(){
        music.play();
    }

}
