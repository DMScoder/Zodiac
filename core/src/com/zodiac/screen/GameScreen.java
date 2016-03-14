package com.zodiac.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.zodiac.Game.Plane;
import com.zodiac.Game.SpacePlane;
import com.zodiac.Game.SurfacePlane;
import com.zodiac.Settings;
import com.zodiac.SpaceAssault;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public class GameScreen extends ScreenAdapter implements InputProcessor {

    SpaceAssault game;
    SurfacePlane surface;
    SpacePlane space;
    Plane activePlane;
    Stage stage;

    public GameScreen(SpaceAssault game)
    {
        this.game=game;
        init();
    }

    private void init()
    {
        initPlane();
        initStage();
    }

    private void initStage()
    {
        stage = new Stage();
        InputMultiplexer plexor = new InputMultiplexer();
        plexor.addProcessor(stage);
        plexor.addProcessor(this);
        Gdx.input.setInputProcessor(plexor);
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
    }

    public void planeSwitch()
    {
        switchAnimation();
        activePlane.planeSwitch();
        stage.clear();
        if(activePlane==space)
        {
            activePlane = surface;
        }
        else
        {
            activePlane = space;
        }
        stage.getViewport().setCamera(activePlane.getCamera());
        stage.addActor(activePlane.planeSwitch());
    }

    private void switchAnimation()
    {
        if(activePlane == surface)
        {

        }

        else
        {

        }
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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
