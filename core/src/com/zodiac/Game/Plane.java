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
    void clicked(int x, int y, int button);
    void boxSelect(int startX, int startY, int endX, int endY);
    void planeSwitch();
    void addPlane(Plane plane);
    OrthographicCamera getCamera();

    void scrolled(float amount);
}
