package com.zodiac.Grid;

/**
 * Created by Damian Suski on 3/14/2016.
 */
public class SubGrid {

    Cell[][] cells;

    public SubGrid(int width, int height)
    {
        cells = new Cell[width][height];
        for(int i=0;i<width;i++)
            for(int j=0;j<height;j++)
                cells[i][j] = new Cell();
    }
}
