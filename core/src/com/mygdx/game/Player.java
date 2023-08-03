package com.mygdx.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.physics.LinearKinematics;
import com.mygdx.physics.Translation2d;


public class Player extends Entity {

    public LinearKinematics linK;

    private RenderState mRenderState;
    private Face mFace;

    public Player () {
        setDefaultValues();
    }

    public void setDefaultValues () {

        speed = 4;
        jumpVel = 40;
        grav = -20;
        pose = new Translation2d(100,100);
        linK = new LinearKinematics(pose);
        texture = new Texture("walmartio.png");
        mRenderState = RenderState.STILL;
        mFace = Face.RIGHT;
        sprite = new Sprite(texture, (int)pose.x(), (int)pose.y(), 16, 16);
    }


    public void update () {
        System.out.println(linK.position().x() + "," + linK.position().y() + " and " + linK.velocity().y() + linK.acceleration().y());
        linK.loop(.1);
        sprite.setPosition((float)pose.x(), (float)pose.y());
        sprite.setTexture(texture);
        updateRenderState();
  

            if(Gdx.input.isKeyPressed(Keys.A)) {            
                linK.position().setX(linK.position().x()-speed);
                mFace = Face.LEFT;
            }
            if(Gdx.input.isKeyPressed(Keys.D)) {            
                linK.position().setX(linK.position().x()+speed);
                mFace = Face.RIGHT;
            }
            //falling
            if(linK.position().y()>32) {
                linK.acceleration().setY(grav);
            } else {
                if(Gdx.input.isKeyPressed(Keys.W)) {            
                linK.velocity().setY(jumpVel);
                } else {
                    linK.velocity().setY(0);
                    linK.acceleration().setY(0);
                }
            }
    }

    private void updateRenderState () {
        if (linK.velocity().y() > 2) {
            mRenderState = RenderState.JUMP;
        } else if (linK.velocity().y() < -2) {
            mRenderState = RenderState.FALL;
        } else {
            mRenderState = RenderState.STILL;
        }
    }

    public void render (SpriteBatch batch) {
        switch(mRenderState) {
            case STILL:
                texture = new Texture("walmartio.png");
                break;
            case JUMP:
                texture = new Texture("walmartiojump.png");
                break;
            case FALL:
                texture = new Texture("walmartiofall.png");
            default:
                break;

        }
        sprite.setTexture(texture);
        if(mFace==Face.LEFT){
            sprite.flip(true, false);
        } else {
            sprite.flip(false,false);        
        }

		// batch.draw(texture, (float)linK.position().x(), (float)linK.position().y());
        sprite.draw(batch);
    }

    public enum RenderState {
        STILL,
        JUMP,
        FALL,
    }

    public enum Face {
        LEFT,
        RIGHT,
    }
    
}
