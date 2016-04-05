package com.zodiac.Support;

import com.zodiac.entity.GameObject;

import java.util.Random;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public class Utilities {

    public static final int DEBUG_LEVEL = 0;

    public static Random random = new Random();

    //This function may be rendered pointless by LibGdx random library
    public static int getRandomInt(int min, int max)
    {
        return random.nextInt(max+min)-min;
    }

    //This function may be rendered pointless by libgdx random library
    public static float getRandomFloat(int min, int max)
    {
        return random.nextFloat()+getRandomInt(min,max);
    }

    public static float distanceHeuristic(GameObject one, GameObject two)
    {
        return Math.abs(one.getPolygon().getX()- two.getPolygon().getX())+Math.abs(one.getPolygon().getY()- two.getPolygon().getY());
    }

    //Check distance to target point
    //General distance, not incrdibely accurate but fast
    public static float distanceHeuristic(GameObject one, float x, float y)
    {
        return Math.abs(one.getPolygon().getX()-x)+Math.abs(one.getPolygon().getY()-y);
    }

    //This is for critical distance checking with precision
    //Much more expensive
    public static float distanceReal(GameObject one, GameObject two)
    {
        return (float)(Math.sqrt(Math.pow(one.getPolygon().getX()- two.getPolygon().getX(),2)+Math.pow(one.getPolygon().getY()- two.getPolygon().getY(),2)));
    }

    public static float distanceReal(GameObject one, float x, float y)
    {
        return (float)(Math.sqrt(Math.pow(one.getPolygon().getX()- x,2)+Math.pow(one.getPolygon().getY()- y,2)));
    }

    //This function takes a target angle and makes sure it is between 0-359 inclusive
    public static float normalizeAngle(float degrees)
    {
        if(degrees<0)
            return 360+degrees;
        return degrees%360;
    }
}
