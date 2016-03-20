package com.zodiac.Game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.zodiac.entity.Unit;

import java.util.ArrayList;

/**
 * Created by Damian Suski on 3/14/2016.
 */
public class SurfaceRender {

    private static ShapeRenderer shapeRenderer = new ShapeRenderer();

    public static void draw(Batch batch)
    {
        drawSurface(batch); //Surface texture
        drawDecals(batch); //Scorch marks and roads
        drawUnits(batch); //Structures and units
        drawFlying(batch); //Includes clouds and transit units
        drawProjectiles(batch); //Missiles and explosions
    }

    public static void drawSurface(Batch batch)
    {

    }

    public static void drawDecals(Batch batch)
    {

    }

    public static void drawUnits(Batch batch)
    {
        for(int i=0;i<SurfacePlane.Entities[SurfacePlane.GROUND_UNITS].size();i++)
        {
            ((Unit)SurfacePlane.Entities[SurfacePlane.GROUND_UNITS].get(i)).draw(batch);
            ((Unit)SurfacePlane.Entities[SurfacePlane.GROUND_UNITS].get(i)).drawTurrets(batch);
        }
    }

    public static void drawFlying(Batch batch)
    {

    }

    public static void drawProjectiles(Batch batch)
    {

    }

    public static void drawSelection(ArrayList<Unit> units)
    {
        shapeRenderer.setProjectionMatrix(SurfacePlane.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 1, 0, 1);
        for(int i = 0;i<units.size();i++)
        {
            shapeRenderer.polygon(units.get(i).getPolygon().getTransformedVertices());
        }
        shapeRenderer.end();
    }
}
