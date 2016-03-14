package com.zodiac.Game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public interface Plane {

    void draw(Batch batch);
    void update();
    void clicked();
    void boxSelect();
    void planeSwitch();
    void addPlane(Plane plane);
    OrthographicCamera getCamera();
}
