package com.zodiac.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.zodiac.Assets;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public class Turret extends GameObject{

    public Turret(float x, float y, int type) {
        super(x, y, Assets.getTurretType(type));
    }

    public Polygon getPolygon()
    {
        return polygon;
    }
}
