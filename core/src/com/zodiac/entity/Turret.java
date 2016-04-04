package com.zodiac.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.zodiac.Support.Assets;
import com.zodiac.Support.Utilities;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public class Turret extends GameObject{

    Unit target=null;
    Polygon hostPolygon;
    float angle;
    float radius;
    float hostCenterX;
    float hostCenterY;
    float defaultRotation=0;
    float turnRate = 2f;
    
    public Turret(float hostCenterX, float hostCenterY,Polygon hostPolygon, float angle, float radius, float defaultRotation, int type) {
        super(hostCenterX, hostCenterY, Assets.getTurretType(type));
        this.hostCenterX = hostCenterX;
        this.hostCenterY = hostCenterY;
        this.radius = radius;
        this.angle = angle;
        this.defaultRotation=defaultRotation;
        this.hostPolygon = hostPolygon;
        setPosition();
    }

    public void update()
    {
        rotate();
    }

    public void setPosition() {
        float tempX = MathUtils.cosDeg(hostPolygon.getRotation()+angle) * radius;
        float tempY = MathUtils.sinDeg(hostPolygon.getRotation()+angle) * radius;
        getPolygon().setPosition(hostPolygon.getX()+hostCenterX-this.getWidth()/2+tempX,hostPolygon.getY()+hostCenterY-this.getHeight()/2+tempY);
    }

    public void rotate()
    {
        float targetAngle;

        //Change the heading
        //Check if the unit is on the right heading
        if(target!=null)
            targetAngle = MathUtils.atan2(target.getPolygon().getX()-getPolygon().getX(),target.getPolygon().getY()-getPolygon().getY());

        else
            targetAngle = Utilities.normalizeAngle(defaultRotation + hostPolygon.getRotation());

        if(targetAngle!=getPolygon().getRotation())
        {
            //If the unit is almost on the right heading we just set it to the right heading
            if(Math.abs(targetAngle-getPolygon().getRotation())<=turnRate+.05f)
            {
                getPolygon().setRotation(targetAngle);
                return;
            }

            //The angle is counter clockwise (most likely)
            if(targetAngle-getPolygon().getRotation()<0)
            {
                //Check if the angle is actually closer clockwise
                if(360-getPolygon().getRotation()+targetAngle<getPolygon().getRotation()-targetAngle)
                    getPolygon().setRotation(Utilities.normalizeAngle(getPolygon().getRotation()+turnRate));

                    //Nope, angle is closer counterclockwise
                else
                    getPolygon().setRotation(Utilities.normalizeAngle(getPolygon().getRotation()-turnRate));
            }

            //The angle is clockwise (most likely)
            else
            {
                //Check if the angle is actually closer counterclockwise
                if(360-targetAngle+getPolygon().getRotation()<targetAngle-getPolygon().getRotation())
                    getPolygon().setRotation(Utilities.normalizeAngle(getPolygon().getRotation()-turnRate));
                    //Nope, the angle is closer clockwise
                else
                    getPolygon().setRotation(Utilities.normalizeAngle(getPolygon().getRotation()+turnRate));
            }
        }
    }
}
