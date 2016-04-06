package com.zodiac.Grid;

import com.zodiac.entity.Turret;
import com.zodiac.entity.Unit;

/**
 * Created by Damian Suski on 3/14/2016.
 */
public class MainGrid {

    public SubGrid[][] subgrid;
    private int cellSize;
    private int height;
    private int width;

    public MainGrid(int width, int height, int cellSize)
    {
        this.height=height;
        this.width=width;
        this.cellSize=cellSize;
        subgrid = new SubGrid[width][height];
        for(int i=0;i<width;i++)
            for(int j=0;j<height;j++)
                subgrid[i][j] = new SubGrid(10,10);
    }

    public void unitAt(Unit unit)
    {
        int modifierX = 0;
        int modifierY = 0;

        if(unit.getX()<0)
            modifierX = -1;

        if(unit.getY()<0)
            modifierY = -1;

        subgrid[(int)unit.getX()/(cellSize*10)+width/2+modifierX][(int)unit.getY()/(cellSize*10)+height/2+modifierY].addUnit(unit);
    }

    public void unitMove(Unit unit)
    {
        int modifierX = 0;
        int modifierY = 0;

        if(unit.getY()<0)
            modifierY = -1;

        if(unit.getX()<0)
            modifierX = -1;

        subgrid[(int)unit.getX()/(cellSize*10)+width/2+modifierX][(int)unit.getY()/(cellSize*10)+height/2+modifierY].removeUnit(unit);
    }

    public Unit findTarget(Turret seeker)
    {
        float originX = (int)seeker.getX()/cellSize+width/2;
        float originY = (int)seeker.getY()/cellSize+height/2;
        //float maxRange = seeker.getRange();

        //subgrid[][].getEnemy(seeker.getHostUnit().getPlayer());

        return null;
    }

    public int getCellSize() {
        return cellSize;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
