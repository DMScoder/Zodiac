package com.zodiac.Grid;

import com.zodiac.entity.GameObject;

import java.util.ArrayList;

/**
 * Created by Damian Suski on 3/14/2016.
 */
public class Cell {

    //ArrayList <GameObject> objects = new ArrayList<GameObject>(1);

    //Completely normal pathfinding
    public static final int NORMAL = 0;

    //Space stations
    //Mountains/cliffs
    public static final int IMPASSABLE = 1;

    //Starfighters can fly through small asteroids
    //Some units are amphibious
    public static final int SEMI_PASS = 2;

    public Cell()
    {

    }
}
