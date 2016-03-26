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
    private float maxVelocity = 500f;
    private float velocity = 0;
    private float acceleration = 0.1f;
    private float turnRate = 3f;
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
        if(type== Constant_Names.FEDERATION_GUNBOAT)
        {
            maxVelocity = 500;
            acceleration = .05f;
            turnRate = .5f;
            size = 4;
            turrets = new Turret[2];
            Rectangle rectangle = getPolygon().getBoundingRectangle();
            turrets[0] = new Turret(getPolygon().getX(),getPolygon().getY(),-75+rectangle.getWidth()/2,-30+rectangle.getHeight()/2,Constant_Names.FEDERATION_500MM);
            turrets[1] = new Turret(getPolygon().getX(),getPolygon().getY(),15+rectangle.getWidth()/2,-30+rectangle.getHeight()/2,Constant_Names.FEDERATION_500MM);
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

    private void move()
    {
        if(moveCoordinates==null)
            return;

        if(turrets!=null)
            for(int i=0;i<turrets.length;i++)
                turrets[i].setPosition(getPolygon());

        float targetAngle = MathUtils.atan2(moveCoordinates.x-getPolygon().getX(),moveCoordinates.y-getPolygon().getY());
        targetAngle = MathUtils.radiansToDegrees*targetAngle;
        targetAngle = 360 - Utilities.convertToPositiveAngle(targetAngle);

        if(lastAngle!=0&&Math.abs(lastAngle-targetAngle)>1f)
            velocity*=.99f;

        lastAngle = getPolygon().getRotation();
        if(!turnInPlace||getPolygon().getRotation()==targetAngle)
        {
            float boostX = MathUtils.cosDeg(targetAngle+90)*velocity*.1f+1;
            float boostY = MathUtils.sinDeg(targetAngle+90)*velocity*.1f+1;

            this.getPolygon().setPosition(getPolygon().getX()+velocityX, getPolygon().getY()+velocityY);
            this.getPolygon().setPosition(getPolygon().getX()+boostX, getPolygon().getY()+boostY);

            velocityX = MathUtils.cosDeg(getPolygon().getRotation()+90)* velocity;
            velocityY = MathUtils.sinDeg(getPolygon().getRotation()+90)* velocity;
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

        if(Utilities.distanceHeuristic(this,moveCoordinates.x,moveCoordinates.y)< size * 100 + 50)
        {
            velocity *=.9;
            if(velocity <= 1f)
            {
                velocity = 0;
                //getPolygon().setPosition(moveCoordinates.x,moveCoordinates.y);
                moveCoordinates = null;
            }
        }

        else if(velocity < maxVelocity)
            velocity += acceleration;
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
