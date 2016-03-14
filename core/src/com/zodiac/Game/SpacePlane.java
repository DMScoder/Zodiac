package com.zodiac.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.sun.pisces.Surface;
import com.zodiac.Settings;

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
    OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    Texture planetBackground;
    Sprite sprite;
    static boolean ACTIVE = true;

    public SpacePlane()
    {
        Pixmap pixmap = new Pixmap(4096,4096, Pixmap.Format.RGBA8888);
        pixmap.setColor(70/255f,130/255f,180/255f,1);
        pixmap.fillCircle(pixmap.getWidth()/2,pixmap.getHeight()/2,pixmap.getHeight()/2-1);
        planetBackground = new Texture(pixmap);
        pixmap.dispose();
        sprite = new Sprite(planetBackground);
        sprite.setPosition(0,0);
        sprite.setScale(100);
    }

    @Override
    public void draw(Batch batch) {
        GL20 gl = Gdx.gl;
        gl.glClearColor(0f,.05f,.1f,1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        sprite.draw(batch);
        batch.end();
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
        surface = (SurfacePlane) plane;
    }

    @Override
    public OrthographicCamera getCamera() {
        return camera;
    }
}
