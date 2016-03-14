package com.zodiac.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public class GameObject extends Actor{

    Texture texture;

    public GameObject(float x, float y,Texture texture)
    {
        this.texture = texture;
        setBounds(x,y,texture.getWidth(),texture.getHeight());
    }

    @Override
    public void draw(Batch batch, float alpha)
    {
        batch.draw(texture,this.getX(),getY(),this.getOriginX(),this.getOriginY(),this.getWidth(),
                this.getHeight(),this.getScaleX(), this.getScaleY(),this.getRotation(),0,0,
                texture.getWidth(),texture.getHeight(),false,false);
    }
}
