package com.zodiac.Support;

import com.zodiac.entity.GameObject;

import java.util.Random;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public class Utilities {

    public static final int DEBUG_LEVEL = 0;

    public static Random random = new Random();

    public static int getRandomInt(int min, int max)
    {
        return random.nextInt(max+min)-min;
    }

    public static float getRandomFloat(int min, int max)
    {
        return random.nextFloat()+getRandomInt(min,max);
    }

    public static float distanceHeuristic(GameObject one, GameObject two)
    {
        return Math.abs(one.getPolygon().getX()- two.getPolygon().getX())+Math.abs(one.getPolygon().getY()- two.getPolygon().getY());
    }

    public static float distanceHeuristic(GameObject one, float x, float y)
    {
        return Math.abs(one.getPolygon().getX()-x)+Math.abs(one.getPolygon().getY()-y);
    }

    public static float distanceReal(GameObject one, GameObject two)
    {
        return (float)(Math.sqrt(Math.pow(one.getPolygon().getX()- two.getPolygon().getX(),2)+Math.pow(one.getPolygon().getY()- two.getPolygon().getY(),2)));
    }

    public static float convertToPositiveAngle(float degrees)
    {
        if(degrees<0)
            return degrees+360;
        return degrees;
    }
}
