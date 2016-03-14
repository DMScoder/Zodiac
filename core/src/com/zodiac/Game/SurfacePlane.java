package com.zodiac.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;

import com.zodiac.Assets;
import com.zodiac.Settings;
import com.zodiac.entity.GameObject;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public class SurfacePlane implements Plane{

    OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    SpacePlane space;
    GameObject scout;
    static boolean ACTIVE = false;

    public SurfacePlane()
    {
        scout = new GameObject(0,0,Assets.federationScout);
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
        drawSurface(batch);
        drawDecals(batch);
        drawUnits(batch);
        drawFlying(batch); //Includes clouds
        batch.end();
    }

    public void drawSurface(Batch batch)
    {

    }

    public void drawDecals(Batch batch)
    {

    }

    public void drawUnits(Batch batch)
    {

    }

    public void drawFlying(Batch batch)
    {

    }

    @Override
    public void update() {
        if(ACTIVE)
            cameraMovement();
    }

    private void cameraMovement()
    {
        if(Gdx.input.isKeyPressed(Settings.PAN_DOWN))
            camera.translate(0,-10f*camera.zoom);
        if(Gdx.input.isKeyPressed(Settings.PAN_UP))
            camera.translate(0,10f*camera.zoom);
        if(Gdx.input.isKeyPressed(Settings.PAN_RIGHT))
            camera.translate(10*camera.zoom,0);
        if(Gdx.input.isKeyPressed(Settings.PAN_LEFT))
            camera.translate(-10*camera.zoom,0);
        if(Gdx.input.isKeyPressed(Input.Keys.Q))
            camera.zoom += 1;
        if(Gdx.input.isKeyPressed(Input.Keys.E))
            camera.zoom -= 1;
    }

    @Override
    public void clicked() {

    }

    @Override
    public void boxSelect() {

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
}
