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
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Boss.FightState;
import com.mygdx.game.Boss.ImmuneState;
import com.mygdx.physics.LinearKinematics;
import com.mygdx.physics.Translation2d;


public class Player extends Entity {

    public LinearKinematics linK;

    private RenderState mRenderState;
    private Face mFace;

    public ArrayList<Bullet> bullets;
    private long timeLastBulletFired;
    private Texture gun;
    public int hp;
    private ImmuneState mImmuneState;
    public long timeLastHit;


    public Player () {
        setDefaultValues();
    }

    public void setDefaultValues () {

        speed = 4;
        jumpVel = 40;
        grav = -20;
        pose = new Translation2d(100,100);
        linK = new LinearKinematics(pose);
        mRenderState = RenderState.STILL;
        mFace = Face.RIGHT;
        texture = new Texture("walmartio.png");
        sprite = new Sprite(texture, (int)pose.x(), (int)pose.y(), 16, 16);
        sprite.setRotation(0);
        bullets = new ArrayList<Bullet>();
        timeLastBulletFired=TimeUtils.millis();
        gun = new Texture("gun.png");
        hp = 100;
        mImmuneState = ImmuneState.NONE;
        timeLastHit = 0;
    }

    private void updateStates () {
        if(TimeUtils.timeSinceMillis(timeLastHit) > 1000) {
            mImmuneState = ImmuneState.NONE;
        } else {
            mImmuneState = ImmuneState.ALL;
        }
    }


    public void damage(int dmg) {
        if(mImmuneState==ImmuneState.NONE){
            hp-=dmg;
            timeLastHit= TimeUtils.millis();
        }
    }
    public enum ImmuneState {
        ALL,
        NONE,
    }

    public void update () {
        updateStates();
        // System.out.println(linK.position().x() + "," + linK.position().y() + " and " + linK.velocity().y() + linK.acceleration().y());
        linK.loop(.1);
        // System.out.println(TimeUtils.timeSinceMillis(timeLastBulletFired));
        pose = linK.position();
        sprite.setPosition((float)pose.x(), (float)pose.y());
        updateTexture();
        updateRenderState();
  
        for (Bullet b : bullets) {
            if(!b.dead()){
                b.update();
            } else {
                bullets.remove(b);
                break;
            }
        }

            if(Gdx.input.isKeyPressed(Keys.A)) {            
                linK.position().setX(linK.position().x()-speed);
                mFace = Face.LEFT;
            }
            if(Gdx.input.isKeyPressed(Keys.D)) {            
                linK.position().setX(linK.position().x()+speed);
                mFace = Face.RIGHT;
            }
            if(Gdx.input.isKeyPressed(Keys.K)) {            
                fireBullet();
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

    private void fireBullet () {
        if(bullets.size()<10&&TimeUtils.timeSinceMillis(timeLastBulletFired)>200){
            bullets.add(new Bullet(pose));
            timeLastBulletFired=TimeUtils.millis();
        }
    }

    private void updateTexture () {
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
        // if(mFace==Face.LEFT){
        //     sprite.flip(true, false);
        // } else {
        //     sprite.flip(false,false);        
        // }
    }

    public void render (SpriteBatch batch) {
        // sprite.draw(batch);
        for(Bullet bullet : bullets) {
            bullet.render(batch);
        }

		batch.draw(texture, (float)linK.position().x(), (float)linK.position().y());
        if(mRenderState!=RenderState.STILL){
            batch.draw(gun,(float)linK.position().x()+13, (float)linK.position().y()+4);

        } else {
            batch.draw(gun,(float)linK.position().x()+11, (float)linK.position().y()+2);
        }
        // batch.draw(sprite, (float)linK.position().x(), (float)linK.position().y());
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

    public void dispose () {
        texture.dispose();
    }
    
}
