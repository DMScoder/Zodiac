package com.zodiac.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.zodiac.Grid.MainGrid;
import com.zodiac.Support.Settings;
import com.zodiac.entity.Unit;

import java.util.ArrayList;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public class Plane {

    static OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

    MainGrid mainGrid;

    final static int UNITS = 0;

    static boolean ACTIVE = true;
    final static float UNIT_SPACING = 100;
    public static ArrayList[] Entities = new ArrayList[4];
    ArrayList<Unit> selected = new ArrayList<Unit>();
    final static float MAX_ZOOM = 500;
    final static float MIN_ZOOM = 2;


    public void draw(Batch batch)
    {

    }

    public void clicked(int x, int y, int button) {
        Vector3 vector3 = new Vector3(x,y,0);
        camera.unproject(vector3);
        Vector2 vector2 = new Vector2(vector3.x,vector3.y);

        if(button==Input.Buttons.LEFT)
            selectionClick(vector2);

        if(button==Input.Buttons.RIGHT)
            commandClick(vector2);
    }

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

        for(int i = 0; i<Entities[UNITS].size(); i++) {
            Unit unit = ((Unit) Entities[UNITS].get(i));

            if(Intersector.overlapConvexPolygons(unit.getPolygon(),rPoly)&&unit.getPlayer().equals(Player.THE_PLAYER))
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

        for (int i = 0; i < Entities[UNITS].size(); i++) {

            Unit unit = (Unit) Entities[UNITS].get(i);

            if (unit.getPolygon().contains(vector2)&&!unit.getPlayer().equals(Player.THE_PLAYER)) {

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

            int square = (int)Math.sqrt(selected.size())/2;

            for(int j=0;l<selected.size();j++)
            {
                int k;
                for(k=0;l<selected.size()&&k<=j;k++)
                {
                    Unit unit2 = selected.get(l);
                    Vector2 tempVector = new Vector2(vector2.x,vector2.y);
                    tempVector.x += unit2.getSize() * UNIT_SPACING * (j-square);
                    tempVector.y += unit2.getSize() * UNIT_SPACING * (k-square);
                    unit2.setMove(tempVector);
                    l++;
                }

                int tempK = k;

                for(k = j; l<selected.size() && k >=0; k--)
                {
                    Unit unit2 = selected.get(l);
                    Vector2 tempVector = new Vector2(vector2.x,vector2.y);
                    tempVector.x += unit2.getSize()* UNIT_SPACING * (k-square);
                    tempVector.y += unit2.getSize()* UNIT_SPACING * (tempK-square);
                    unit2.setMove(tempVector);
                    l++;
                }
            }
        }
    }

    private void selectionClick(Vector2 vector2)
    {

        boolean added = false;

        for (int i = 0; i < Entities[UNITS].size(); i++) {

            Unit unit = (Unit) Entities[UNITS].get(i);

            if (unit.getPolygon().contains(vector2)&&unit.getPlayer().equals(Player.THE_PLAYER)) {

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

    public void planeSwitch() {
        if(ACTIVE)
            ACTIVE = false;
        else
            ACTIVE = true;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void scrolled(float amount) {
        if(amount<0)
            amount = .9f;
        else
            amount = 1.1f;
        camera.zoom*=amount;
        if(camera.zoom<2)
            camera.zoom=2;
        if(camera.zoom>500)
            camera.zoom=500;
    }

    void cameraMovement()
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
}
