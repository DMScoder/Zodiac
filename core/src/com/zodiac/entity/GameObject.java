package com.zodiac.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public class GameObject {

    Texture texture;
    private float zHeight = 0;
    private Polygon polygon;

    public GameObject(float x, float y,Texture texture)
    {
        this.texture = texture;
        polygon = new Polygon(new float[]{0,0,texture.getWidth(),0,texture.getWidth(),texture.getHeight(),0,texture.getHeight()});
        polygon.setOrigin(texture.getWidth()/2,texture.getHeight()/2);
        polygon.setPosition(x,y);
    }

    public void draw(Batch batch)
    {
        batch.draw(texture,polygon.getX(),polygon.getY(),polygon.getOriginX(),polygon.getOriginY(),texture.getWidth(),
                texture.getHeight(),polygon.getScaleX(), polygon.getScaleY(),polygon.getRotation(),0,0,
                texture.getWidth(),texture.getHeight(),false,false);
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
}
