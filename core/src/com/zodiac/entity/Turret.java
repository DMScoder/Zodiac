package com.zodiac.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.zodiac.Support.Assets;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public class Turret extends GameObject{

    Unit target=null;
    float offsetX = 0;
    float offsetY = 0;
    float defaultRotation=0;
    
    public Turret(float hostCenterX, float hostCenterY, float offsetX, float offsetY, int type) {
        super(hostCenterX+offsetX, hostCenterY+offsetY, Assets.getTurretType(type));
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public void update()
    {
        getPolygon().setRotation(getPolygon().getRotation()+1);
    }

    public void setPosition(Polygon hostPolygon) {
        //if(target==null)
            //getPolygon().setRotation(hostPolygon.getRotation());
        //float tempX = offsetX;//hostPolygon.getX();
        //float tempY = offsetY;//hostPolygon.getY();
        float tempX = MathUtils.cosDeg(Math.abs(hostPolygon.getRotation()-180)) * offsetX;
        float tempY = MathUtils.sinDeg(Math.abs(hostPolygon.getRotation()-180)) * offsetY;
        System.out.println(tempX+" "+tempY+ " "+hostPolygon.getRotation());
        getPolygon().setPosition(tempX+hostPolygon.getX(),tempY+hostPolygon.getY());
    }
}
