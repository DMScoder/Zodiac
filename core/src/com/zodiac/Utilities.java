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
        return Math.abs(one.getX()-two.getX())+Math.abs(one.getY()-two.getY());
    }

    public static float distanceReal(GameObject one, GameObject two)
    {
        return (float)(Math.sqrt(Math.pow(one.getX()-two.getX(),2)+Math.pow(one.getY()-two.getY(),2)));
    }
}
