package com.zodiac;

import com.zodiac.entity.GameObject;

import java.util.Random;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public class Utilities {
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
        return Math.abs(one.polygon.getX()-two.polygon.getX())+Math.abs(one.polygon.getY()-two.polygon.getY());
    }

    public static float distanceHeuristic(GameObject one, float x, float y)
    {
        return Math.abs(one.polygon.getX()-x)+Math.abs(one.polygon.getY()-y);
    }

    public static float distanceReal(GameObject one, GameObject two)
    {
        return (float)(Math.sqrt(Math.pow(one.polygon.getX()-two.polygon.getX(),2)+Math.pow(one.polygon.getY()-two.polygon.getY(),2)));
    }
}
