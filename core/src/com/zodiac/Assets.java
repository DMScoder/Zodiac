package com.zodiac;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by SYSTEM on 3/10/2016.
 */

//Contains all textures and sounds
public class Assets {

    public static Texture testTexture;
    public static Texture menuBackground;
    public static Texture federationScout;

    public static void Load()
    {
        menuBackground = new Texture(Gdx.files.internal("stars.jpg"));
        testTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
        federationScout = new Texture(Gdx.files.internal("Ship/Federation_Scout.png"));
    }
}