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
import com.mygdx.physics.LinearKinematics;
import com.mygdx.physics.Translation2d;


public class Player extends Entity {

    public LinearKinematics linK;

    public Player () {
        setDefaultValues();
    }

    public void setDefaultValues () {

        speed = 4;
        jumpVel = 40;
        grav = -20;
        pose = new Translation2d(100,100);
        linK = new LinearKinematics(pose);
    }


    public void update () {
        System.out.println(linK.position().x() + "," + linK.position().y() + " and " + linK.velocity().y() + linK.acceleration().y());
        linK.loop(.1);
  

            if(Gdx.input.isKeyPressed(Keys.A)) {            
                linK.position().setX(linK.position().x()-speed);
            }
            if(Gdx.input.isKeyPressed(Keys.D)) {            
                linK.position().setX(linK.position().x()+speed);
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

    
}
