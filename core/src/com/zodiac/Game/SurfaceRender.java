package com.zodiac.Game;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Damian Suski on 3/14/2016.
 */
public class SurfaceRender {

    public static void draw(Batch batch)
    {
        drawSurface(batch); //Surface texture
        drawDecals(batch); //Scorch marks and roads
        drawUnits(batch); //Structures and units
        drawFlying(batch); //Includes clouds and transit units
        drawProjectiles(batch);
    }

    public static void drawSurface(Batch batch)
    {

    }

    public static void drawDecals(Batch batch)
    {

    }

    public static void drawUnits(Batch batch)
    {

    }

    public static void drawFlying(Batch batch)
    {

    }

    public static void drawProjectiles(Batch batch)
    {

    }
}
