package com.zodiac.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;
import com.zodiac.Support.Assets;
import com.zodiac.Support.Constant_Names;
import com.zodiac.Support.IO;
import com.zodiac.Support.Utilities;

/**
 * Created by Immortan on 3/19/2016.
 */
public class Unit extends GameObject implements Moveable, Killable, Selectable{

    //
    private float hull;
    private float shields;
    private float armor;
    private Unit target;

    //List of all attached turrets
    private Turret[] turrets;

    //Movement variables
    private Vector2 moveCoordinates = null;
    private float velocityX=0;
    private float velocityY=0;
    private float maxSpeed = 100;
    private float maxAcceleration = 50f;
    private float acceleration = 0;
    private float accelerationX = 0;
    private float accelerationY = 0;
    private float delta_acceleration = 0.2f;
    private float turnRate = 1f;
    private int size = 1;
    private boolean turnInPlace = false;

    private int type;

    public Unit(float x, float y, int type)
    {
        super(x,y, Assets.getType(type));
        this.type = type;
        Stats();
    }

    public void Stats()
    {

    }

    @Override
    public void hit(Projectile projectile) {

    }

    @Override
    public void die() {

    }

    public void update()
    {
        move();
        if(turrets!=null)
            for(int i=0;i<turrets.length;i++)
                turrets[i].update();
    }

    private void move()
    {
        if(moveCoordinates==null)
            return;

        float targetAngle = MathUtils.atan2(moveCoordinates.x-getPolygon().getX(),moveCoordinates.y-getPolygon().getY());
        targetAngle = MathUtils.radiansToDegrees*targetAngle;
        targetAngle = 360 - Utilities.convertToPositiveAngle(targetAngle);

        if(!turnInPlace||getPolygon().getRotation()==targetAngle)
        {
            this.getPolygon().setPosition(getPolygon().getX()+velocityX, getPolygon().getY()+velocityY);

            if(maxSpeed>Math.abs(velocityX)+Math.abs(velocityY))
            {
                velocityX = MathUtils.cosDeg(getPolygon().getRotation()+90)*acceleration;
                velocityY = MathUtils.sinDeg(getPolygon().getRotation()+90)*acceleration;
                //velocityX += accelerationX;
                //velocityY += accelerationY;
            }
        }

        if(targetAngle != getPolygon().getRotation())
        {

            float turnLeft = getPolygon().getRotation()+turnRate;
            float turnRight = Math.abs(Utilities.convertToPositiveAngle(getPolygon().getRotation()-turnRate));

            if(Math.abs(turnLeft-targetAngle)<turnRate||Math.abs(turnRight-targetAngle)<turnRate)
                getPolygon().setRotation(targetAngle);

            else
            {
                if((Math.abs(targetAngle-turnRight)> Math.abs(targetAngle-turnLeft)
                        &&Math.abs(targetAngle-turnRight-360)> Math.abs(targetAngle-turnLeft)))

                    getPolygon().setRotation(turnLeft);
                else
                    getPolygon().setRotation(turnRight);
            }
        }

        accelerationX = acceleration * MathUtils.cosDeg(getPolygon().getRotation()-90);
        accelerationY = acceleration * MathUtils.sinDeg(getPolygon().getRotation()-90);

        if(acceleration<maxAcceleration)
            acceleration+=delta_acceleration;

        if(Utilities.distanceHeuristic(this,moveCoordinates.x,moveCoordinates.y)<100)
        {
            moveCoordinates = null;
        }

        /*
        if(moveCoordinates.x< getPolygon().getX())
        {
            if (Math.abs(targetAngle) > getPolygon().getRotation())
                getPolygon().setRotation(getPolygon().getRotation() + turnRate);
            else
                getPolygon().setRotation(getPolygon().getRotation() - turnRate);
        }

        else
        {
            if (360-targetAngle > getPolygon().getRotation())
                getPolygon().setRotation(getPolygon().getRotation() + turnRate);
            else
                getPolygon().setRotation(getPolygon().getRotation() - turnRate);
        }*/
    }

    /*private void move()
    {
        if(moveCoordinates==null)
            return;

        float targetAngle = MathUtils.atan2(moveCoordinates.x-getPolygon().getX(),moveCoordinates.y- getPolygon().getY());
        targetAngle = MathUtils.radiansToDegrees*targetAngle;

        float dx = speed * -MathUtils.cosDeg(targetAngle+90);
        float dy = speed * MathUtils.sinDeg(targetAngle+90);

        if(moveCoordinates.x< getPolygon().getX())
        {
            if (Math.abs(targetAngle) > getPolygon().getRotation())
                getPolygon().setRotation(getPolygon().getRotation() + turnRate);
            else
                getPolygon().setRotation(getPolygon().getRotation() - turnRate);
        }

        else
        {
            if (360-targetAngle > getPolygon().getRotation())
                getPolygon().setRotation(getPolygon().getRotation() + turnRate);
            else
                getPolygon().setRotation(getPolygon().getRotation() - turnRate);
        }

        this.getPolygon().setPosition(getPolygon().getX()+dx, getPolygon().getY()+dy);

        if(turrets!=null)
            for(int i = 0; i< turrets.length;i++)
            {
                turrets[i].getPolygon().setPosition(turrets[i].getPolygon().getX()+dx,turrets[i].getPolygon().getY()+dy);
                if(target==null)
                    turrets[i].getPolygon().setRotation(getPolygon().getRotation());
            }

        if(Utilities.distanceHeuristic(this,moveCoordinates.x,moveCoordinates.y)<speed*10)
        {
            speed/=2;
            acceleration = 0;

            if(speed < 1)
                moveCoordinates = null;
        }

        else
        {
            acceleration+=delta_acceleration;
            if(acceleration>maxAcceleration)
                acceleration = maxAcceleration;

            speed+=acceleration;
            if(speed>maxSpeed)
                speed = maxSpeed;
        }
    }*/

    public void drawTurrets(Batch batch)
    {
        if(turrets==null)
            return;
        for(int i=0;i<turrets.length;i++)
            turrets[i].draw(batch);
    }

    public float getArmor() {
        return armor;
    }

    public void setArmor(float armor) {
        this.armor = armor;
    }

    public float getShields() {
        return shields;
    }

    public void setShields(float shields) {
        this.shields = shields;
    }

    public float getHull() {
        return hull;
    }

    public void setHull(float hull) {
        this.hull = hull;
    }

    public Turret[] getTurrets() {
        return turrets;
    }

    @Override
    public void setMove(Vector2 vector2) {
        moveCoordinates = vector2;
    }

    @Override
    public Vector2 getMove() {
        return moveCoordinates;
    }

    public Unit getTarget() {
        return target;
    }

    public void setTarget(Unit target) {
        this.target = target;
    }



    @Override
    public void select() {

    }
}
