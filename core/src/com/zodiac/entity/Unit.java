package com.zodiac.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.zodiac.Game.Player;
import com.zodiac.Grid.MainGrid;
import com.zodiac.Support.Assets;
import com.zodiac.Support.Constant_Names;
import com.zodiac.Support.Utilities;

/**
 * Created by Immortan on 3/19/2016.
 */
public class Unit extends GameObject implements Moveable, Killable, Selectable{

    //Controlling player
    private Player player;

    //Ship stats
    private float hull;
    private float shields;
    private float armor;
    private Unit target;
    private int type;

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
    private boolean slowing = false;

    public Unit(float x, float y, int type, Player player,MainGrid mainGrid)
    {
        super(x,y, Assets.getType(type));
        this.type = type;
        this.player = player;
        mainGrid.unitAt(this);
        Stats();
    }

    //Set the stats for the unit
    //In future builds manual input will be replaced by JSON parsing in IO class
    public void Stats()
    {


        if(type== Constant_Names.FEDERATION_GUNBOAT)
        {
            maxVelocity = 100;
            acceleration = .1f;
            turnRate = 1f;
            size = 4;
            turrets = new Turret[2];
            turrets[0] = new Turret(getWidth()/2,getHeight()/2,this,9,45,270,Constant_Names.FEDERATION_500MM);
            turrets[1] = new Turret(getWidth()/2,getHeight()/2,this,169,45,90,Constant_Names.FEDERATION_500MM);
        }
    }

    @Override
    public void hit(Projectile projectile) {

    }

    @Override
    public void die() {

    }

    public void update(MainGrid mainGrid)
    {
        if(move(mainGrid))
            mainGrid.unitAt(this);

        if(turrets!=null)
            for(int i=0;i<turrets.length;i++)
                turrets[i].update();
    }


    //This drives the ship according to current heading and target heading
    private boolean move(MainGrid mainGrid)
    {
        //This means the ship has no move command
        if(moveCoordinates==null)
            return false;

        //Move the turrets only if the ship moves
        if(turrets!=null)
            for(int i=0;i<turrets.length;i++)
                turrets[i].setPosition();

        //Calculate target angle using inverse tangent
        //lookup table decreases runtime
        float targetAngle = MathUtils.atan2(moveCoordinates.x-getPolygon().getX(),moveCoordinates.y-getPolygon().getY());

        mainGrid.unitMove(this);

        if(slowing)
        {
            finalApproach();
            return true;
        }

        //Convert to degrees
        targetAngle = MathUtils.radiansToDegrees*targetAngle;

        //Make the angle clockwise increasing
        targetAngle = 360 - Utilities.normalizeAngle(targetAngle);

        //Slightly decrease the velocity if the ship is still turning
        if(lastAngle!=0&&Math.abs(lastAngle-targetAngle)>1f)
            velocity*=.975f;

        //Reset last angle to current angle for next frame
        lastAngle = getPolygon().getRotation();

        rotate(targetAngle);

        //Here we apply movement based on angle
        //Only turn if the unit does not have to turn in place or is facing the correct angle
        if(!turnInPlace||getPolygon().getRotation()==targetAngle)
        {
            //Here we nudge the unit regardless of direction to smooth movement
            float boostX = MathUtils.cosDeg(targetAngle+90)*velocity*.1f;
            float boostY = MathUtils.sinDeg(targetAngle+90)*velocity*.1f;

            //Apply movement differentials
            this.getPolygon().setPosition(getPolygon().getX()+velocityX, getPolygon().getY()+velocityY);
            //this.getPolygon().setPosition(getPolygon().getX()+boostX, getPolygon().getY()+boostY);

            //Change velocity depending on current factors
            velocityX = MathUtils.cosDeg(getPolygon().getRotation()+90)* velocity;
            velocityY = MathUtils.sinDeg(getPolygon().getRotation()+90)* velocity;
        }

        //Check if the unit is almost at its destination
        if(Utilities.distanceHeuristic(this,moveCoordinates.x,moveCoordinates.y)< size * 100)
        {
            slowing = true;
        }

        //Apply acceleration
        else if(velocity < maxVelocity)
            velocity += acceleration;

        return true;
    }

    private void rotate(float targetAngle)
    {
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

    private void finalApproach()
    {
        float boostX = velocity * .5f;
        float boostY = velocity * .5f;

        if (moveCoordinates.x - getPolygon().getX() < 0)
            boostX*=-1;
        if (moveCoordinates.y - getPolygon().getY() < 0)
            boostY*=-1;


        //rotate(targetAngle);
        getPolygon().setPosition(getPolygon().getX()+boostX,getPolygon().getY()+boostY);

        //Apply deceleration
        velocity *=.9;

        //If the unit is barely moving we stop and clear its move coordinates
        if(Utilities.distanceHeuristic(this,moveCoordinates.x,moveCoordinates.y)<size*5)
        {
            velocity = 0;
            getPolygon().setPosition(moveCoordinates.x,moveCoordinates.y);
            //Move the turrets only if the ship moves
            if(turrets!=null)
                for(int i=0;i<turrets.length;i++)
                    turrets[i].setPosition();
            moveCoordinates = null;
            slowing = false;
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
        slowing = false;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
