package com.zodiac.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.zodiac.screen.GameScreen;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public abstract class GameObject {

    Texture mainTexture = null;
    Texture iconTexture = null;

    private float zHeight = 0;
    private Polygon polygon;
    private float width;
    private float height;
    private int drawHeight = -1; //Default, always draw

    public GameObject(float x, float y,Texture texture)
    {
        this.mainTexture = texture;
        polygon = new Polygon(new float[]{0,0,texture.getWidth(),0,texture.getWidth(),texture.getHeight(),0,texture.getHeight()});
        polygon.setOrigin(texture.getWidth()/2,texture.getHeight()/2);
        polygon.setPosition(x,y);
        width = texture.getWidth();
        height = texture.getHeight();
    }

    public void draw(Batch batch)
    {
        OrthographicCamera camera = GameScreen.getActivePlane().getCamera();

        if(drawHeight!=-1&&camera.zoom > drawHeight && iconTexture==null)
            return;

        else if(drawHeight!=-1&&camera.zoom > drawHeight&&iconTexture!=null)
            batch.draw(iconTexture,polygon.getX(),polygon.getY(),polygon.getOriginX(),polygon.getOriginY(),iconTexture.getWidth(),
                    iconTexture.getHeight(),0, 0,0,0,0,
                    iconTexture.getWidth(),iconTexture.getHeight(),false,false);

        else if(drawHeight==-1||camera.zoom <= drawHeight)
            batch.draw(mainTexture,polygon.getX(),polygon.getY(),polygon.getOriginX(),polygon.getOriginY(),mainTexture.getWidth(),
                    mainTexture.getHeight(),polygon.getScaleX(), polygon.getScaleY(),polygon.getRotation(),0,0,
                    mainTexture.getWidth(),mainTexture.getHeight(),false,false);
    }

    public void setOrigin(float x, float y)
    {
        polygon.setOrigin(x,y);
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public float getzHeight() {
        return zHeight;
    }

    public void setzHeight(float zHeight) {
        this.zHeight = zHeight;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getX()
    {
        return polygon.getX();
    }

    public float getY()
    {
        return polygon.getY();
    }
}
