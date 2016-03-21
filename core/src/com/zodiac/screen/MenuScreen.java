package com.zodiac.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.zodiac.Support.Assets;
import com.zodiac.Support.SoundManager;
import com.zodiac.SpaceAssault;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public class MenuScreen extends ScreenAdapter {

    Stage stage;
    OrthographicCamera camera;
    SpaceAssault game;

    static final int GAME = 1;
    static final int LOAD = 2;
    static final int SETTINGS = 3;
    static final int EXIT = 4;

    public class MenuButton extends Actor {

        int option;
        MenuScreen menu;
        Texture texture;

        public MenuButton(float x, float y, int option, MenuScreen menu, Texture texture)
        {
            this.texture = texture;
            setBounds(x,y,texture.getWidth(),texture.getHeight());
            this.option = option;
            this.menu = menu;
            addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    ((MenuButton)event.getTarget()).action();
                    return true;
                }
            });

            SoundManager.PlayMusic(Assets.Main_Menu_Music);
        }

        @Override
        public void draw(Batch batch, float alpha)
        {
            batch.draw(texture,this.getX(),getY(),this.getOriginX(),this.getOriginY(),this.getWidth(),
                    this.getHeight(),this.getScaleX(), this.getScaleY(),this.getRotation(),0,0,
                    texture.getWidth(),texture.getHeight(),false,false);
        }

        public void action()
        {
            menu.optionClicked(option);
        }
    }

    public MenuScreen(SpaceAssault game)
    {
        this.game = game;

        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.position.x = 0 + Gdx.graphics.getWidth()/2;
        camera.position.y = 0 + Gdx.graphics.getHeight()/2;

        stage = new Stage();
        stage.getViewport().setCamera(camera);
        Gdx.input.setInputProcessor(stage);

        initObjects();
    }

    public void draw()
    {
        GL20 gl = Gdx.gl;
        gl.glClearColor(0,0,0,0);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(Assets.Menu_Background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        game.batch.end();
        stage.draw();
    }

    private void optionClicked(int option)
    {
        switch(option){
            case(GAME):
                this.dispose();
                SoundManager.StopMusic();
                game.setScreen(new GameScreen(game));
                break;
            case(SETTINGS):
                break;
            case(LOAD):
                break;
            case(EXIT):
                Gdx.app.exit();
                break;
        }
    }

    private void initObjects()
    {
        MenuButton game = new MenuButton(Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()-300,GAME,this,Assets.Test_Texture);
        stage.addActor(game);

        MenuButton load = new MenuButton(Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()-400,LOAD,this,Assets.Test_Texture);
        stage.addActor(load);

        MenuButton settings = new MenuButton(Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()-500,SETTINGS,this,Assets.Test_Texture);
        stage.addActor(settings);

        MenuButton exit = new MenuButton(Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()-600,EXIT,this,Assets.Test_Texture);
        stage.addActor(exit);
    }

    public void update()
    {
    }

    public void render (float delta)
    {
        update();
        draw();
    }
}