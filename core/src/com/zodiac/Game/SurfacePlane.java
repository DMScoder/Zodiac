package com.zodiac.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.*;
import com.zodiac.Support.Settings;
import com.zodiac.entity.Unit;
import com.zodiac.Support.Constant_Names;

import java.util.ArrayList;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public class SurfacePlane implements Plane{

    static OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    SpacePlane space;
    public static ArrayList[] Entities = new ArrayList[4];
    static boolean ACTIVE = false;

    public final static int GROUND_UNITS = 0;
    public final static int PROJECTILES = 1;
    public final static int FLYING_UNITS = 2;
    public final static int DECALS = 3;

    ArrayList <Unit> selected = new ArrayList<Unit>();

    public SurfacePlane()
    {
        Entities[GROUND_UNITS] = new ArrayList<Unit>();
        //Entities[1] = Projectiles;
        //Entities[2] = Flying_Units;
        //Entities[3] = Decals;

        Entities[GROUND_UNITS].add(new Unit(0,0, Constant_Names.FEDERATION_MAIN_TANK));
    }

    @Override
    public void draw(Batch batch)
    {
        GL20 gl = Gdx.gl;
        gl.glClearColor(240/255f,230/255f,140/255f,1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        SurfaceRender.draw(batch);
        batch.end();
        SurfaceRender.drawSelection(selected);
    }
    @Override
    public void update() {
        if(ACTIVE)
            cameraMovement();
        for(int i=0;i<Entities[GROUND_UNITS].size();i++)
            ((Unit)Entities[GROUND_UNITS].get(i)).update();
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

        for(int i=0;i<Entities[GROUND_UNITS].size();i++) {
            Unit unit = ((Unit) Entities[GROUND_UNITS].get(i));

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

        for (int i = 0; i < Entities[GROUND_UNITS].size(); i++) {

            Unit unit = (Unit) Entities[GROUND_UNITS].get(i);

            if (unit.getPolygon().contains(vector2)) {

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
            for(int i = 0; i < selected.size();i++)
            {
                Unit unit = selected.get(i);
                unit.setMove(vector2);
            }
        }
    }

    private void selectionClick(Vector2 vector2)
    {

        boolean added = false;

        for (int i = 0; i < Entities[GROUND_UNITS].size(); i++) {

            Unit unit = (Unit) Entities[GROUND_UNITS].get(i);

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
            camera.translate(0,-cameraSpeed*camera.zoom);
        if(Gdx.input.isKeyPressed(Settings.PAN_UP))
            camera.translate(0,cameraSpeed*camera.zoom);
        if(Gdx.input.isKeyPressed(Settings.PAN_RIGHT))
            camera.translate(cameraSpeed*camera.zoom,0);
        if(Gdx.input.isKeyPressed(Settings.PAN_LEFT))
            camera.translate(-cameraSpeed*camera.zoom,0);
        if(Gdx.input.isKeyPressed(Input.Keys.Q))
            camera.zoom *= 1.05;
        if(Gdx.input.isKeyPressed(Input.Keys.E))
            camera.zoom *= 0.95;
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
        space = (SpacePlane) plane;
    }

    @Override
    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public void scrolled(float amount) {

    }
}
