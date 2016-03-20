package com.zodiac.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.zodiac.Assets;
import com.zodiac.Utilities;

/**
 * Created by Immortan on 3/19/2016.
 */
public class Unit extends GameObject implements Moveable, Killable, Selectable{

    private float hull;
    private float shields;
    private float armor;
    private Unit target;
    private Vector2 moveCoordinates = null;
    private Turret[] turrets;
    private float speed = 0;
    private float maxSpeed = 10;
    private float maxAcceleration = .5f;
    private float acceleration = 0;
    private float delta_acceleration = 0.001f;
    private float turnRate = 2f;
    private int type;

    public Unit(float x, float y, int type)
    {
        super(x,y, Assets.getType(type));
        this.type = type;
        Stats();
    }

    public void Stats()
    {
        if(type==UnitTypes.FEDERATION_MAIN_TANK)
        {
            turrets = new Turret[1];
            turrets[0] = new Turret(getPolygon().getX(),getPolygon().getY(),UnitTypes.FEDERATION_MAIN_TANK_TURRET);
            maxSpeed = 4;
            turnRate = 1;
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
        if(moveCoordinates==null)
            return;

        float targetAngle = (float)Math.atan2(moveCoordinates.x-polygon.getX(),moveCoordinates.y-polygon.getY());
        targetAngle = (float)Math.toDegrees(targetAngle);

        float dx = speed * -(float)Math.cos(Math.toRadians(targetAngle+90));
        float dy = speed * (float)Math.sin(Math.toRadians(targetAngle+90));


        if(moveCoordinates.x<polygon.getX())
        {
            if (Math.abs(targetAngle) > polygon.getRotation())
                polygon.setRotation(polygon.getRotation() + turnRate);
            else
                polygon.setRotation(polygon.getRotation() - turnRate);
        }

        else
        {
            if (360-targetAngle > polygon.getRotation())
                polygon.setRotation(polygon.getRotation() + turnRate);
            else
                polygon.setRotation(polygon.getRotation() - turnRate);
        }

        this.polygon.setPosition(polygon.getX()+dx,polygon.getY()+dy);

        if(turrets!=null)
            for(int i = 0; i< turrets.length;i++)
                turrets[i].getPolygon().setPosition(turrets[i].getPolygon().getX()+dx,turrets[i].getPolygon().getY()+dy);

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
    public void move(Vector2 vector2) {
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
    public Polygon getPolygon() {
        return polygon;
    }

    @Override
    public void select() {

    }
}
