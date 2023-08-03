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


public class Player extends Entity {
    

    public Player () {
        setDefaultValues();
    }

    public void setDefaultValues () {
        x = 100;
        y = 100;
        speed = 4;
    }


    public void update () {
        if(Gdx.input.isKeyPressed(Keys.W)) {            
            y += speed;
        }
        if(Gdx.input.isKeyPressed(Keys.A)) {            
            x -= speed;
        }
        if(Gdx.input.isKeyPressed(Keys.S)) {            
            y -= speed;
        }
        if(Gdx.input.isKeyPressed(Keys.D)) {            
            x += speed;
        }
    }

    
}
