package com.zodiac.Game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.zodiac.Assets;
import com.zodiac.entity.Unit;

import java.util.ArrayList;

/**
 * Created by Damian Suski on 3/14/2016.
 */
public class SpaceRender {

    private static Texture planetTexture;
    public static float planetX;
    public static float planetY;
    private static float planetDistance;
    private static float planetScale;
    private static ShapeRenderer shapeRenderer = new ShapeRenderer();


    public static void setPlanet(Texture texture, float x, float y, float distance, float scale)
    {
        planetTexture = texture;
        planetX = x;
        planetY = y;
        planetDistance = distance;
        planetScale = scale;
    }

    public static void draw(Batch batch)
    {
        drawBackground(batch); //Planet
        drawTransit(batch); //In transit to/from planet
        drawUnits(batch); //Starships
        drawProjectiles(batch); //Missiles, explosions
    }

    public static void drawBackground(Batch batch)
    {
        batch.draw(planetTexture,0,0,planetTexture.getWidth()/2,planetTexture.getHeight()/2,planetTexture.getWidth(),
                planetTexture.getHeight(),planetScale,planetScale,0,0,0,
                planetTexture.getWidth(),planetTexture.getHeight(),false,false);
    }

    public static void drawTransit(Batch batch)
    {

    }

    public static void drawUnits(Batch batch)
    {
        for(int i = 0;i<SpacePlane.Entities[SpacePlane.SHIPS].size();i++)
        {
            ((Unit)SpacePlane.Entities[SpacePlane.SHIPS].get(i)).draw(batch);
            ((Unit)SpacePlane.Entities[SpacePlane.SHIPS].get(i)).drawTurrets(batch);
        }
    }

    public static void drawSelection(ArrayList<Unit> units)
    {
        shapeRenderer.setProjectionMatrix(SpacePlane.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 1, 0, 1);
        for(int i = 0;i<units.size();i++)
        {
            shapeRenderer.polygon(units.get(i).getPolygon().getTransformedVertices());
        }
        shapeRenderer.end();
    }

    public static void drawProjectiles(Batch batch)
    {

    }
}
