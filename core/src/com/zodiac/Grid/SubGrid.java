package com.zodiac.Grid;

import com.zodiac.Game.Player;
import com.zodiac.entity.Unit;

import java.util.ArrayList;

/**
 * Created by Damian Suski on 3/14/2016.
 */
public class SubGrid {

    Cell[][] cells;
    private ArrayList <Unit> arrayList;

    public SubGrid(int width, int height)
    {
        cells = new Cell[width][height];
        for(int i=0;i<width;i++)
            for(int j=0;j<height;j++)
                cells[i][j] = new Cell();
    }

    public void addUnit(Unit unit)
    {
        if(arrayList == null)
            arrayList = new ArrayList<Unit>();
        arrayList.add(unit);
    }

    public void removeUnit(Unit unit)
    {
        if(arrayList==null)
            return;
        for(Unit arrayUnit : arrayList)
            if(unit.equals(arrayUnit))
            {
                arrayList.remove(unit);
                return;
            }
    }

    public Unit getEnemy(Player player)
    {
        if(!containsUnit())
            return null;
        for(Unit unit : arrayList)
            if(unit.getPlayer().isEnemy(player))
                return unit;
        return null;
    }

    public boolean containsUnit()
    {
        if(arrayList==null)
            return false;

        return !arrayList.isEmpty();
    }
}
