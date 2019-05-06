package com.historychase.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {
    public static final String PREFERENCE_KEY = "game.historychase.com";
    public static final String USER_KEY = "user.historychase.com";

    public static final Settings instance = new Settings();

    public float musicVolume = 1f;
    public float soundVolume = 1f;
    public boolean musicEnabled = true;
    public boolean soundEnabled = true;

    public int maxLevel = 1;
    public boolean cleared[] = {false,false,false,false,false,false,false,false,false};
    public float time[] = {0,0,0,0,0,0,0,0,0};
    public boolean newGame = true;
    public int quizScore = -1;

    public Settings loadUserData(){
        Preferences prefs = Gdx.app.getPreferences(USER_KEY);
        this.newGame = prefs.getBoolean("isNewGame",true);

        for(int i=0;i<9;i++) {
            cleared[i] = prefs.getBoolean("World_" + (i + 1) + "_Cleared", false);
            time[i] = prefs.getFloat("World_" + (i + 1) + "_BestTime", 0f);
        }

        quizScore = prefs.getInteger("Quiz_Score",-1);

        if(!cleared[0])
           maxLevel = 1;
        else
            maxLevel = 2;
        if(cleared[1])
            maxLevel = 3;
        if(cleared[2])
            maxLevel = 4;
        if(cleared[3])
            maxLevel = 5;
        if(cleared[4])
            maxLevel = 6;
        if(cleared[5])
            maxLevel = 7;
        if(cleared[6])
            maxLevel = 8;
        if(cleared[7])
            maxLevel = 9;

        return saveUserData();
    }

    public boolean hasCleared(){
        loadUserData();
        for(int i=0;i<9;i++)
            if(cleared[i])return true;
        return false;
    }

    public Settings saveUserData(){
        Preferences prefs = Gdx.app.getPreferences(USER_KEY);
        prefs.putBoolean("isNewGame",this.newGame);
        for(int i=0;i<9;i++){
            prefs.putBoolean("World_"+(i+1)+"_Cleared",cleared[i]);
            prefs.putFloat("World_"+(i+1)+"_BestTime",time[i]);
        }
        prefs.putInteger("Quiz_Score",quizScore);
        prefs.flush();
        return this;
    }

    public Settings resetUserData(){
        Preferences prefs = Gdx.app.getPreferences(USER_KEY);
        prefs.clear();
        prefs.flush();
        return loadUserData();
    }


    public Settings load(){
        Preferences prefs = Gdx.app.getPreferences(PREFERENCE_KEY);
        musicVolume = prefs.getFloat("musicVolume",musicVolume);
        soundVolume = prefs.getFloat("soundVolume",soundVolume);
        musicEnabled = prefs.getBoolean("musicEnabled",musicEnabled);
        soundEnabled = prefs.getBoolean("soundEnabled",soundEnabled);
        return save();
    }


    public Settings save(){
        Preferences prefs = Gdx.app.getPreferences(PREFERENCE_KEY);
        prefs.putFloat("musicVolume",musicVolume);
        prefs.putFloat("soundVolume",soundVolume);
        prefs.putBoolean("musicEnabled",musicEnabled);
        prefs.putBoolean("soundEnabled",soundEnabled);
        prefs.flush();
        return this;
    }

    public Settings loadDefaults(){
        Settings settings = new Settings();
        Preferences prefs = Gdx.app.getPreferences(PREFERENCE_KEY);
        prefs.clear();
        prefs.flush();
        return settings.load();
    }

    public Settings demo(){
        this.newGame = false;
        for(int i=0;i<9;i++){
            cleared[i] = true;
            time[i] = 0;
        }
//        quizScore = 0;
        return saveUserData();
    }

}
