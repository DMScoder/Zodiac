package com.zodiac.Grid;

/**
 * Created by Damian Suski on 3/14/2016.
 */
public class MainGrid {

    SubGrid[][] subgrid;

    public MainGrid(int width, int height)
    {
        subgrid = new SubGrid[width][height];
        for(int i=0;i<width;i++)
            for(int j=0;j<height;j++)
                subgrid[i][j] = new SubGrid();
    }
}
