package com.zodiac.entity;

import com.badlogic.gdx.math.Polygon;

/**
 * Created by Immortan on 3/19/2016.
 */
public interface Selectable {

    Polygon getPolygon();
    void select();
}
