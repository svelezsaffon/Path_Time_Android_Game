package com.time.path.svs.testnew.Settings;

import android.content.Context;
import android.content.SharedPreferences;

import com.time.path.svs.testnew.R;


/**
 * Created by santiago on 3/11/15.
 */
public class settings {


    private static  String PLAYER_NAME="name_edit_pref";

    private static  String PLAY_ANIMATION="animation_checkbox";

    private static String VIBRATE_MODE="vibrate_checkbox";

    private static boolean DEFAULT_PLAY_ANIMATION=true;

    private static boolean DEFAULT_VIBRATE=true;


    private static settings instance=null;


    private static SharedPreferences preferences;


    private settings(){

    }

    public void setPlayerNameString(String name){
        PLAYER_NAME=name;
    }

    public void setPlayAnimationString(String name){
        PLAY_ANIMATION=name;
    }


    public static void create_first(SharedPreferences shared){
        if(instance==null){
            instance=new settings();
            preferences=shared;
        }
    }


    public static settings getInstance(){
        return instance;
    }

    public boolean animate(){
        return preferences.getBoolean(settings.PLAY_ANIMATION,settings.DEFAULT_PLAY_ANIMATION);

    }

    public boolean vibrate(){
        return preferences.getBoolean(settings.VIBRATE_MODE,settings.DEFAULT_VIBRATE);

    }


    private String readString(String name,String Default){
        return preferences.getString(name,Default);
    }




    public String getPlayerName(){
        return this.readString(settings.PLAYER_NAME,"PATH FINDER");
    }



}
