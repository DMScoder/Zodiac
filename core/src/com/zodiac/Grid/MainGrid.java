package com.zodiac.Grid;

import com.zodiac.entity.GameObject;
import com.zodiac.entity.Turret;
import com.zodiac.entity.Unit;

/**
 * Created by Damian Suski on 3/14/2016.
 */
public class MainGrid {

    public SubGrid[][] subgrid;
    private int cellSize;
    private int gridSize;
    private int height;
    private int width;

    public MainGrid(int width, int height, int cellSize)
    {
        this.cellSize=cellSize;
        this.gridSize=cellSize*10;
        subgrid = new SubGrid[width][height];
        this.height=height;
        this.width=width;
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

        subgrid[(int)unit.getX()/(gridSize)+width/2+modifierX][(int)unit.getY()/(gridSize)+height/2+modifierY].addUnit(unit);
    }

    public void unitMove(Unit unit)
    {
        int modifierX = 0;
        int modifierY = 0;

        if(unit.getY()<0)
            modifierY = -1;

        if(unit.getX()<0)
            modifierX = -1;

        subgrid[(int)unit.getX()/(gridSize)+width/2+modifierX][(int)unit.getY()/(gridSize)+height/2+modifierY].removeUnit(unit);
    }

    public Unit findTarget(Turret seeker)
    {
        int originX = getGridXCoordinates(seeker);
        int originY = getGridYCoordinates(seeker);

        //Check for enemies within own cell
        //This would be very close range!
        Unit closeEnemy = subgrid[originX][originY].getEnemy(seeker.getHostUnit().getPlayer());
        if(closeEnemy!=null)
            return closeEnemy;

        //Cap will determine the distance from the turret that we are searching
        int cap = 1;

        while(cap<seeker.getRange())
        {
            int i;
            int j=-cap;

            //Bottom left to bottom right
            for(i=-cap;i<=cap;i++)
            {
                if(Math.abs(i+originX)>=width||Math.abs(j+originY)>=height||j+originY<0||j+originY<0)
                    break;
                Unit enemy = subgrid[i+originX][j+originY].getEnemy(seeker.getHostUnit().getPlayer());
                if(enemy!=null)
                    return enemy;
            }

            i=-cap;

            //Bottom left to top left
            for(j=-cap;j<=cap;j++)
            {
                if(Math.abs(i+originX)>=width||Math.abs(j+originY)>=height||j+originY<0||j+originY<0)
                    break;
                Unit enemy = subgrid[i+originX][j+originY].getEnemy(seeker.getHostUnit().getPlayer());
                if(enemy!=null)
                    return enemy;
            }

            //Top left to top right
            for(i=-cap;i<=cap;i++)
            {
                if(Math.abs(i+originX)>=width||Math.abs(j+originY)>=height||j+originY<0||j+originY<0)
                    break;
                Unit enemy = subgrid[i+originX][j+originY].getEnemy(seeker.getHostUnit().getPlayer());
                if(enemy!=null)
                    return enemy;
            }

            //Bottom right to top right
            for(j=-cap;j<=cap;j++)
            {
                if(Math.abs(i+originX)>=width||Math.abs(j+originY)>=height||j+originY<0||j+originY<0)
                    break;
                Unit enemy = subgrid[i+originX][j+originY].getEnemy(seeker.getHostUnit().getPlayer());
                if(enemy!=null)
                    return enemy;
            }

            cap++;
        }

        //No enemy ships within range
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

    public int getGridXCoordinates(GameObject gameObject)
    {
        return ((int)gameObject.getX())/gridSize+width/2;
    }

    public int getGridYCoordinates(GameObject gameObject)
    {
        return ((int)gameObject.getY())/gridSize+height/2;
    }

    public int getGridSize() {
        return gridSize;
    }
}
