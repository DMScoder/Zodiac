package com.zodiac.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.zodiac.Assets;
import com.zodiac.Game.Plane;
import com.zodiac.Game.SpacePlane;
import com.zodiac.Game.SurfacePlane;
import com.zodiac.Settings;
import com.zodiac.SoundManager;
import com.zodiac.SpaceAssault;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public class GameScreen extends ScreenAdapter implements InputProcessor {

    ShapeRenderer renderer = new ShapeRenderer();
    SpaceAssault game;
    SurfacePlane surface;
    SpacePlane space;
    Plane activePlane;
    boolean dragging;
    int startDragX;
    int startDragY;
    int endDragX;
    int endDragY;

    public GameScreen(SpaceAssault game)
    {
        SoundManager.PlayMusic(Assets.Game_Music);
        this.game=game;
        init();
    }

    private void init()
    {
        initPlane();
        Gdx.input.setInputProcessor(this);
    }

    private void initPlane()
    {
        surface = new SurfacePlane();
        space = new SpacePlane();
        activePlane = space;
        surface.addPlane(space);
        space.addPlane(surface);
    }

    public void draw()
    {
        activePlane.draw(game.batch);
    }

    public void update()
    {
        surface.update();
        space.update();
    }

    public void render(float alpha)
    {
        draw();
        update();
        if(dragging)
        {
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(Color.GREEN);
            renderer.rect(startDragX,startDragY,endDragX-startDragX,endDragY-startDragY);
            renderer.end();
        }
    }

    public void planeSwitch()
    {
        surface.planeSwitch();
        space.planeSwitch();
        if(activePlane==space)
            activePlane = surface;
        else
            activePlane = space;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Settings.TOGGLE_PLANE)
            planeSwitch();
        //else if(keycode == Settings.TOGGLE_OVERLAY)
            //activePlane.toggleOverlay();

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        activePlane.clicked(screenX,screenY,button);
        startDragX = screenX;
        startDragY = Gdx.graphics.getHeight()-screenY;
        endDragX = startDragX;
        endDragY = startDragY;

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if(dragging)
        {
            dragging = false;
            activePlane.boxSelect(startDragX,startDragY,endDragX-startDragX,endDragY-startDragY);
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            dragging = true;
            endDragX = screenX;
            endDragY = Gdx.graphics.getHeight() - screenY;
        }

        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
