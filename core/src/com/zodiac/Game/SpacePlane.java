package com.zodiac.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.*;
import com.zodiac.Support.Assets;
import com.zodiac.Support.Settings;
import com.zodiac.entity.Unit;
import com.zodiac.Support.Constant_Names;

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

public class SpacePlane implements Plane{

    SurfacePlane surface;
    static OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    static boolean ACTIVE = true;
    final static float UNIT_SPACING = 100;

    final static float MAX_ZOOM = 500;
    final static float MIN_ZOOM = 2;
    public static ArrayList[] Entities = new ArrayList[4];
    ArrayList<Unit> selected = new ArrayList<Unit>();

    final static int SHIPS = 0;
    final static int PROJECTILES = 1;
    final static int IN_TRANSIT = 2;
    final static int WRECKAGE = 3;

    public SpacePlane()
    {
        Entities[0] = new ArrayList<Unit>();
        //Entities[1] = Projectiles;
        //Entities[2] = InTransit;
        //Entities[3] = Wreckage;

        SpaceRender.setPlanet(Assets.Planet_Background,0,0,0,10);
        Entities[SHIPS].add(new Unit(10,10, Constant_Names.FEDERATION_SCOUT));
        Entities[SHIPS].add(new Unit(150,10, Constant_Names.FEDERATION_SCOUT));
        Entities[SHIPS].add(new Unit(10,150, Constant_Names.FEDERATION_SCOUT));
        Entities[SHIPS].add(new Unit(150,150, Constant_Names.FEDERATION_SCOUT));
        Entities[SHIPS].add(new Unit(10,10, Constant_Names.FEDERATION_SCOUT));
        Entities[SHIPS].add(new Unit(150,10, Constant_Names.FEDERATION_SCOUT));
        Entities[SHIPS].add(new Unit(10,150, Constant_Names.FEDERATION_SCOUT));
        Entities[SHIPS].add(new Unit(150,150, Constant_Names.FEDERATION_SCOUT));
        Entities[SHIPS].add(new Unit(10,10, Constant_Names.FEDERATION_SCOUT));
        Entities[SHIPS].add(new Unit(150,10, Constant_Names.FEDERATION_SCOUT));
        Entities[SHIPS].add(new Unit(10,150, Constant_Names.FEDERATION_SCOUT));
        Entities[SHIPS].add(new Unit(150,150, Constant_Names.FEDERATION_SCOUT));
        Entities[SHIPS].add(new Unit(10,10, Constant_Names.FEDERATION_SCOUT));
        Entities[SHIPS].add(new Unit(150,10, Constant_Names.FEDERATION_SCOUT));
        Entities[SHIPS].add(new Unit(10,150, Constant_Names.FEDERATION_SCOUT));
        Entities[SHIPS].add(new Unit(150,150, Constant_Names.FEDERATION_SCOUT));
        Entities[SHIPS].add(new Unit(10,10, Constant_Names.FEDERATION_SCOUT));
        Entities[SHIPS].add(new Unit(150,10, Constant_Names.FEDERATION_SCOUT));
        Entities[SHIPS].add(new Unit(10,150, Constant_Names.FEDERATION_SCOUT));
        Entities[SHIPS].add(new Unit(150,150, Constant_Names.FEDERATION_SCOUT));
    }

    @Override
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
    }

    @Override
    public void update() {
        if(ACTIVE)
            cameraMovement();
        for(int i=0;i<Entities[SHIPS].size();i++)
            ((Unit)Entities[SHIPS].get(i)).update();
    }

    private void manageSounds()
    {
        /*if(camera.zoom>300)
        {
            SoundManager.StopAllSounds();
            return;
        }
        int shipCount=0;
        for(int i=0;i<Entities[SHIPS].size();i++)
        {
            Unit unit = (Unit)Entities[SHIPS].get(i);
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

    @Override
    public void clicked(int x, int y, int button) {
        Vector3 vector3 = new Vector3(x,y,0);
        camera.unproject(vector3);
        Vector2 vector2 = new Vector2(vector3.x,vector3.y);

        if(button==Input.Buttons.LEFT)
            selectionClick(vector2);

        if(button==Input.Buttons.RIGHT)
            commandClick(vector2);
    }

    @Override
    public void boxSelect(int startX, int startY, int endX, int endY) {
        Vector3 start = new Vector3(startX,startY,0);
        Vector3 end = new Vector3(endX,endY,0);

        camera.unproject(start);
        camera.unproject(end);

        Polygon rPoly = new Polygon(new float[] { 0, 0, end.x-start.x, 0, end.x-start.x,
                end.y-start.y, 0, end.y-start.y });

        rPoly.setPosition(start.x, start.y);

        if(!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            selected.clear();

        for(int i=0;i<Entities[SHIPS].size();i++) {
            Unit unit = ((Unit) Entities[SHIPS].get(i));

            if(Intersector.overlapConvexPolygons(unit.getPolygon(),rPoly))
            {
                selected.add(unit);
            }
        }
    }

    private void commandClick(Vector2 vector2)
    {
        if(selected.size()==0)
            return;

        boolean targeted = false;

        for (int i = 0; i < Entities[SHIPS].size(); i++) {

            Unit unit = (Unit) Entities[SHIPS].get(i);

            if (unit.getPolygon().contains(vector2)) {

                int k = 0;
                for(int j=0;j<selected.size();j++)
                {
                        Unit unit2 = selected.get(j);
                        unit2.setTarget(unit);
                }

                targeted = true;
                break;
            }
        }

        if(!targeted)
        {
            int l = 0;
            for(int j=0;l<selected.size();j++)
            {
                for(int k=0;l<selected.size()&&k<=j;k++)
                {
                    Vector2 tempVector = new Vector2(vector2.x,vector2.y);
                    tempVector.x += UNIT_SPACING * j;
                    tempVector.y += UNIT_SPACING * k;
                    Unit unit2 = selected.get(l);
                    unit2.move(tempVector);
                    l++;
                }
            }
        }
    }

    private void selectionClick(Vector2 vector2)
    {

        boolean added = false;

        for (int i = 0; i < Entities[SHIPS].size(); i++) {

            Unit unit = (Unit) Entities[SHIPS].get(i);

            if (unit.getPolygon().contains(vector2)) {

                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
                    selected.add(unit);
                else {
                    selected.clear();
                    selected.add(unit);
                }

                added = true;
                break;
            }
        }

        if (!added)
            selected.clear();
    }

    private void cameraMovement()
    {
        float cameraSpeed = 5;

        if(Gdx.input.isKeyPressed(Settings.TOGGLE_CAMERA_SPEED))
            cameraSpeed = 10;

        if(Gdx.input.isKeyPressed(Settings.PAN_DOWN))
        {
            camera.translate(0,-cameraSpeed*camera.zoom);
            SpaceRender.OffsetY-=cameraSpeed*camera.zoom;
        }

        if(Gdx.input.isKeyPressed(Settings.PAN_UP))
        {
            camera.translate(0,cameraSpeed*camera.zoom);
            SpaceRender.OffsetY+=cameraSpeed*camera.zoom;
        }

        if(Gdx.input.isKeyPressed(Settings.PAN_RIGHT))
        {
            camera.translate(cameraSpeed*camera.zoom,0);
            SpaceRender.OffsetX+=cameraSpeed*camera.zoom;
        }

        if(Gdx.input.isKeyPressed(Settings.PAN_LEFT))
        {
            camera.translate(-cameraSpeed*camera.zoom,0);
            SpaceRender.OffsetX-=cameraSpeed*camera.zoom;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.Q))
        {
            camera.zoom *= 1.05;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.E))
        {
            camera.zoom *= 0.95;
        }
        if(camera.zoom<2)
            camera.zoom=2;
        if(camera.zoom>500)
            camera.zoom=500;
    }

    @Override
    public void planeSwitch() {
        if(ACTIVE)
            ACTIVE = false;
        else
            ACTIVE = true;
    }

    @Override
    public void addPlane(Plane plane) {
        surface = (SurfacePlane) plane;
    }

    @Override
    public OrthographicCamera getCamera() {
        return camera;
    }
}
