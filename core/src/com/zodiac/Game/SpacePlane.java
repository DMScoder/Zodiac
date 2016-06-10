package com.zodiac.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.*;
import com.zodiac.Grid.MainGrid;
import com.zodiac.Support.Assets;
import com.zodiac.entity.Projectile;
import com.zodiac.entity.Unit;
import com.zodiac.Support.Constant_Names;
import com.zodiac.entity.Wreckage;

import java.util.ArrayList;

/**
 * Created by SYSTEM on 3/10/2016.
 */

/*
Code for use with snapshot array

 SnapshotArray array = new SnapshotArray();
 // ...
 Object[] items = array.begin();
 for (int i = 0, n = array.size; i < n; i++) {
        Object item = items[i];
        // ...
 }
 array.end();
 */

public class SpacePlane extends Plane{

    SurfacePlane surface;
    final static int UNITS = 0;
    final static int PROJECTILES = 1;
    final static int IN_TRANSIT = 2;
    final static int WRECKAGE = 3;

    public SpacePlane()
    {
        mainGrid = new MainGrid(50,50,5000);
        Entities[UNITS] = new ArrayList<Unit>();
        Entities[PROJECTILES] = new ArrayList<Projectile>();
        //Entities[2] = inTransit;
        Entities[WRECKAGE] = new ArrayList<Wreckage>();

        testSetup();
    }

    public void testSetup()
    {
        Player.THE_PLAYER.setColor(Color.BLUE);
        Player enemy = new Player(Color.RED);

        Player.THE_PLAYER.addEnemy(enemy);
        enemy.addEnemy(Player.THE_PLAYER);

        SpaceRender.setPlanet(Assets.Planet_Background,0,0,.5f,100);

        Entities[UNITS].add(new Unit(0,0,Constant_Names.FEDERATION_GUNBOAT,enemy,mainGrid));
        Entities[UNITS].add(new Unit(3*mainGrid.getGridSize(),3*mainGrid.getGridSize(),Constant_Names.FEDERATION_GUNBOAT,Player.THE_PLAYER,mainGrid));

        //for(int i=0;i<10000;i++)
        //    Entities[UNITS].add(new Unit(0,0, Constant_Names.FEDERATION_SCOUT,Player.THE_PLAYER,mainGrid));
    }


    public void draw(Batch batch) {
        GL20 gl = Gdx.gl;
        gl.glClearColor(0f,.05f,.1f,1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        manageSounds();

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        SpaceRender.draw(batch);
        batch.end();
        SpaceRender.drawSelection(selected);
        SpaceRender.drawGrid(mainGrid);
    }


    public void update() {
        if(ACTIVE)
            this.cameraMovement();
        for(int i = 0; i<Entities[UNITS].size(); i++)
            Entities[PROJECTILES].addAll(((Unit)Entities[UNITS].get(i)).update(mainGrid));
        for(int i=0;i<Entities[PROJECTILES].size();i++)
            ((Projectile)Entities[PROJECTILES].get(i)).update();
    }

    private void manageSounds()
    {
        /*if(camera.zoom>300)
        {
            SoundManager.StopAllSounds();
            return;
        }
        int shipCount=0;
        for(int i=0;i<Entities[UNITS].size();i++)
        {
            Unit unit = (Unit)Entities[UNITS].get(i);
            if(camera.frustum.pointInFrustum(unit.getPolygon().getX(),unit.getPolygon().getY(),0))
            {
                shipCount++;
                if(SoundManager.getInstances(Constant_Names.FIGHTER_SOUND)<5)
                {
                    SoundManager.PlaySound(Constant_Names.FIGHTER_SOUND,true);
                }
            }
        }
        if(shipCount<4)
        {
            SoundManager.StopSound(Constant_Names.FIGHTER_SOUND,5-shipCount);
        }

        SoundManager.setGlobalVolume(1-(camera.zoom/500f*10));*/
    }



    public void addPlane(Plane plane) {
        surface = (SurfacePlane) plane;
    }
}
