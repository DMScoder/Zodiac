package com.zodiac.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.zodiac.Grid.MainGrid;
import com.zodiac.Support.Assets;
import com.zodiac.Support.Constant_Names;
import com.zodiac.Support.Utilities;

import java.util.ArrayList;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public class Turret extends GameObject{

    //Weapon
    private Unit tempTarget=null;
    private Unit primaryTarget =null;
    private float range = 30;
    private boolean engagingPrimary = false;
    private boolean targetLocked = false;
    private float magazineSize = 1;
    private float fireRate = 0; //0 Represents all rounds expelled at once
    private float reloadTime = 2; //Seconds to reload
    private float reloadCurrent = 0;
    private float ammo = 100;

    //Position
    float angle;
    float radius;
    float hostCenterX;
    float hostCenterY;
    float defaultRotation=0;
    float turnRate = 2f;

    //boolean lockRotation = false;
    private Polygon hostPolygon;
    private Unit hostUnit;

    public Turret(float hostCenterX, float hostCenterY,Unit hostUnit, float angle, float radius, float defaultRotation, int type) {
        super(hostCenterX, hostCenterY, Assets.getTurretType(type));
        this.hostCenterX = hostCenterX;
        this.hostCenterY = hostCenterY;
        this.radius = radius;
        this.angle = angle;
        this.defaultRotation=defaultRotation;
        this.hostUnit = hostUnit;
        this.hostPolygon = hostUnit.getPolygon();
        setPosition();
    }

    public ArrayList<Projectile> update(MainGrid mainGrid)
    {
        reloadCurrent = reloadCurrent - (1f/60f);

        if(reloadCurrent<0)
            reloadCurrent=0;

        acquireTarget(mainGrid);
        rotate();
        return targetingSolution();
    }

    public ArrayList<Projectile> targetingSolution()
    {
        ArrayList <Projectile> projectiles = new ArrayList<Projectile>();

        if(reloadCurrent==0&&targetLocked)
        {
            Projectile projectile = new Projectile(getX(),getY(),getPolygon().getRotation(), Constant_Names.FEDERATION_ROUND_500MM);
            projectiles.add(projectile);
            reloadCurrent = reloadTime;
        }

        return projectiles;
    }

    public void acquireTarget(MainGrid mainGrid)
    {
        if(primaryTarget!=null&&Utilities.distanceHeuristic(this,primaryTarget)<mainGrid.getGridSize()*range)
        {
            engagingPrimary=true;
            //lockRotation=false;
            return;
        }

        if(tempTarget==null)
        {
            engagingPrimary=false;
            tempTarget=mainGrid.findTarget(this);
            return;
        }

        //if(tempTarget!=null)
            //lockRotation=false;

        if(Utilities.distanceHeuristic(this,tempTarget)>mainGrid.getGridSize()*range)
            tempTarget=null;
    }

    public void setPosition() {
        //if(lockRotation)
            //getPolygon().setRotation(hostPolygon.getRotation()+defaultRotation);

        float tempX = MathUtils.cosDeg(hostPolygon.getRotation()+angle) * radius;
        float tempY = MathUtils.sinDeg(hostPolygon.getRotation()+angle) * radius;
        getPolygon().setPosition(hostPolygon.getX()+hostCenterX-this.getWidth()/2+tempX,
                hostPolygon.getY()+hostCenterY-this.getHeight()/2+tempY);
    }

    public void rotate()
    {
        //if(lockRotation)
            //return;

        float targetAngle;

        //Change the heading
        //Check if the unit is on the right heading
        if(primaryTarget != null&&engagingPrimary)
        {
            targetAngle = MathUtils.atan2(primaryTarget.getPolygon().getX()-getPolygon().getX(),
                    primaryTarget.getPolygon().getY()-getPolygon().getY())*MathUtils.radiansToDegrees;
        }

        else if(tempTarget!=null)
        {
            targetAngle = (float)Math.atan2(tempTarget.getPolygon().getX()-getPolygon().getX(),
                    tempTarget.getPolygon().getY()-getPolygon().getY())*MathUtils.radiansToDegrees;
        }

        else
            targetAngle = defaultRotation - hostPolygon.getRotation();

        targetAngle = 360-Utilities.normalizeAngle(targetAngle);

        if(targetAngle!=getPolygon().getRotation())
        {
            //If the unit is almost on the right heading we just set it to the right heading
            if(Math.abs(targetAngle-getPolygon().getRotation())<=turnRate+.05f)
            {
                getPolygon().setRotation(targetAngle);

                if(tempTarget!=null)
                    targetLocked = true;

                //if(tempTarget==null)
                    //lockRotation = true;
                return;
            }

            targetLocked = false;

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

    public Unit getHostUnit() {
        return hostUnit;
    }

    public float getRange() {
        return range;
    }
}
