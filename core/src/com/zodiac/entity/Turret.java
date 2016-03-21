package com.zodiac.entity;

import com.zodiac.Support.Assets;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public class Turret extends GameObject{

    public Turret(float x, float y, int type) {
        super(x, y, Assets.getTurretType(type));
    }

}
