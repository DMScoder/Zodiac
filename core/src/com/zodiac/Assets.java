package com.zodiac;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by SYSTEM on 3/10/2016.
 */

//Contains all textures and sounds
public class Assets {

    public static Texture Test_Texture;
    public static Texture Menu_Background;
    public static Texture Federation_Scout;
    public static Texture Planet_Background;
    public static Texture[] Ships;
    public static Texture[] Turrets;

    public static Music Main_Menu_Music = Gdx.audio.newMusic(Gdx.files.internal("Sound/Music/MainTheme.mp3"));
    public static Music Game_Music = Gdx.audio.newMusic(Gdx.files.internal("Sound/Music/RiskRelief.mp3"));

    public static void Load()
    {
        Ships = new Texture[200];
        Turrets = new Texture[200];
        Menu_Background = new Texture(Gdx.files.internal("stars.jpg"));
        Test_Texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        Planet_Background = new Texture(Gdx.files.internal("Planets/VolcanicPlanet1.png"));

        Ships[0] = new Texture(Gdx.files.internal("Ship/Federation_Scout.png"));
        Ships[1] = new Texture(Gdx.files.internal("Ship/Federation_MainBattle_Tank.png"));
        Turrets[0] = new Texture(Gdx.files.internal("Ship/Federation_MainBattle_TankTurret.png"));
    }

    public static Texture getType(int type) {
        return Ships[type];
    }
    public static Texture getTurretType(int type) {
        return Turrets[type];
    }
}