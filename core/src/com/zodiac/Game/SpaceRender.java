package com.zodiac.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.zodiac.Grid.MainGrid;
import com.zodiac.Support.Assets;
import com.zodiac.entity.Projectile;
import com.zodiac.entity.Unit;

import java.util.ArrayList;

/**
 * Created by Damian Suski on 3/14/2016.
 */
public class SpaceRender {

    private static Texture planetTexture;
    private static float planetX;
    private static float planetY;
    private static float planetDistance;
    private static float planetScale;
    private static ShapeRenderer shapeRenderer = new ShapeRenderer();
    public static float OffsetX=0;
    public static float OffsetY=0;

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
        batch.draw(Assets.Space_Background,0+OffsetX-Gdx.graphics.getWidth()*SpacePlane.camera.zoom/2,0+OffsetY-Gdx.graphics.getHeight()*SpacePlane.camera.zoom/2,Gdx.graphics.getWidth()*SpacePlane.camera.zoom,Gdx.graphics.getHeight()*SpacePlane.camera.zoom);
        batch.draw(planetTexture,planetX+OffsetX*planetDistance,planetY+OffsetY*planetDistance,planetTexture.getWidth()/2,
                planetTexture.getHeight()/2,planetTexture.getWidth(),
                planetTexture.getHeight(),planetScale,planetScale,0,0,0,
                planetTexture.getWidth(),planetTexture.getHeight(),false,false);
    }

    public static void drawTransit(Batch batch)
    {
        batch.draw(Assets.Wreckage,1000+OffsetX*.5f,1000+OffsetY*.4f);
        batch.draw(Assets.Wreckage,2000+OffsetX*.5f,3000+OffsetY*.4f);
        batch.draw(Assets.Wreckage,-1000+OffsetX*.5f,1500+OffsetY*.4f);
        batch.draw(Assets.Wreckage,500+OffsetX*.5f,2500+OffsetY*.4f);
    }

    public static void drawGrid(MainGrid mainGrid)
    {
        shapeRenderer.setProjectionMatrix(SpacePlane.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GREEN);
        for(int i=0;i<mainGrid.subgrid.length;i++)
            for(int j=0;j<mainGrid.subgrid[i].length;j++)
            {

                //Fill in subgrid cells
                shapeRenderer.setColor(Color.BLUE);
                for(int k=0;k<10;k++)
                    for(int l=0;l<10;l++)
                        shapeRenderer.rect((i-mainGrid.subgrid.length/2)* mainGrid.getCellSize() *10+ mainGrid.getCellSize() *k,
                                (j-mainGrid.subgrid[i].length/2)* mainGrid.getCellSize() *10+ mainGrid.getCellSize() *l,
                                mainGrid.getCellSize(), mainGrid.getCellSize());

                //Fill in subgrid
                shapeRenderer.setColor(Color.GREEN);
                if(mainGrid.subgrid[i][j].containsUnit())
                    shapeRenderer.setColor(Color.RED);

                shapeRenderer.rect((i-mainGrid.subgrid.length/2)* mainGrid.getCellSize() *10,
                        (j-mainGrid.subgrid[i].length/2)* mainGrid.getCellSize() *10,
                        mainGrid.getCellSize() *10, mainGrid.getCellSize() *10);
            }
        shapeRenderer.end();
    }

    public static void drawUnits(Batch batch) {

        for (int i = 0; i < SpacePlane.Entities[SpacePlane.UNITS].size(); i++) {
            ((Unit) SpacePlane.Entities[SpacePlane.UNITS].get(i)).draw(batch);
            ((Unit) SpacePlane.Entities[SpacePlane.UNITS].get(i)).drawTurrets(batch);

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
        for(int i=0;i<SpacePlane.Entities[SpacePlane.PROJECTILES].size();i++)
            ((Projectile)SpacePlane.Entities[SpacePlane.PROJECTILES].get(i)).draw(batch);
    }
}
