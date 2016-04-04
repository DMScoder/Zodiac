package com.zodiac.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.zodiac.Support.Assets;
import com.zodiac.Support.Constant_Names;
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
    private float lastAngle = 0;
    private Vector2 moveCoordinates = null;
    private float velocityX=0;
    private float velocityY=0;
    private float maxVelocity = 700f;
    private float velocity = 0;
    private float acceleration = 0.1f;
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

    //Set the stats for the unit
    //In future builds manual input will be replaced by JSON parsing in IO class
    public void Stats()
    {


        if(type== Constant_Names.FEDERATION_GUNBOAT)
        {
            maxVelocity = 500;
            acceleration = .05f;
            turnRate = .5f;
            size = 4;
            turrets = new Turret[2];
            turrets[0] = new Turret(getWidth()/2,getHeight()/2,getPolygon(),9,45,270,Constant_Names.FEDERATION_500MM);
            turrets[1] = new Turret(getWidth()/2,getHeight()/2,getPolygon(),169,45,90,Constant_Names.FEDERATION_500MM);
        }
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


    //This drives the ship according to current heading and target heading
    private void move()
    {
        //This means the ship has no move command
        if(moveCoordinates==null)
            return;

        //Move the turrets only if the ship moves
        if(turrets!=null)
            for(int i=0;i<turrets.length;i++)
                turrets[i].setPosition();

        //Calculate target angle using inverse tangent
        //lookup table decreases runtime
        float targetAngle = MathUtils.atan2(moveCoordinates.x-getPolygon().getX(),moveCoordinates.y-getPolygon().getY());

        //Convert to degrees
        targetAngle = MathUtils.radiansToDegrees*targetAngle;

        //Make the angle clockwise increasing
        targetAngle = 360 - Utilities.normalizeAngle(targetAngle);

        //Slightly decrease the velocity if the ship is still turning
        if(lastAngle!=0&&Math.abs(lastAngle-targetAngle)>1f)
            velocity*=.99f;

        //Reset last angle to current angle for next frame
        lastAngle = getPolygon().getRotation();

        //Here we apply movement based on angle
        //Only turn if the unit does not have to turn in place or is facing the correct angle
        if(!turnInPlace||getPolygon().getRotation()==targetAngle)
        {
            //Here we nudge the unit regardless of direction to smooth movement
            float boostX = MathUtils.cosDeg(targetAngle+90)*velocity*.1f+1;
            float boostY = MathUtils.sinDeg(targetAngle+90)*velocity*.1f+1;

            //Apply movement differentials
            this.getPolygon().setPosition(getPolygon().getX()+velocityX, getPolygon().getY()+velocityY);
            this.getPolygon().setPosition(getPolygon().getX()+boostX, getPolygon().getY()+boostY);

            //Change velocity depending on current factors
            velocityX = MathUtils.cosDeg(getPolygon().getRotation()+90)* velocity;
            velocityY = MathUtils.sinDeg(getPolygon().getRotation()+90)* velocity;
        }

        //Check if the unit is almost at its destination
        if(Utilities.distanceHeuristic(this,moveCoordinates.x,moveCoordinates.y)< size * 10/turnRate)
        {
            //Apply deceleration
            velocity *=.9;

            //If the unit is barely moving we stop and clear its move coordinates
            if(velocity<=.5f)
            {
                velocity = 0;
                moveCoordinates = null;
            }

            //Return because we don't want to change the approach angle
            return;
        }

        //Apply acceleration
        else if(velocity < maxVelocity)
            velocity += acceleration;

        //Change the heading
        //Check if the unit is on the right heading
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

    //Apply a new target vector
    @Override
    public void setMove(Vector2 vector2) {
        moveCoordinates = vector2;
        lastAngle = 0;
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

    public int getSize()
    {
        return size;
    }

    @Override
    public void select() {

    }
}
