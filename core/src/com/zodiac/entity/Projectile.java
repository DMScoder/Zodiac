package com.zodiac.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.zodiac.Support.Assets;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public class Projectile extends GameObject{

    private float speed=500f;
    private float angle;
    private int type;

    public Projectile(float x, float y, float angle, int type) {
        super(x, y, Assets.getProjectile(type));
        this.angle=angle;
        this.type=type;
        getPolygon().setRotation(angle);
        getPolygon().setScale(5,5);
    }

    public void update()
    {
        getPolygon().setPosition(getX()+ MathUtils.cosDeg(angle+90)*speed,getY()+MathUtils.sinDeg(angle+90)*speed);
    }
}
