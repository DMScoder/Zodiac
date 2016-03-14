package com.zodiac.entity;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;

/**
 * Created by SYSTEM on 3/10/2016.
 */

public interface Unit {



    Behavior getBehavior();

    ArrayList<Turret> getTurrets();

    void draw(Batch batch);
}
