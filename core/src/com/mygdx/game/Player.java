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

import com.mygdx.game.KeyHandler;

public class Player extends Entity {
    
    KeyHandler keyH;

    public Player (KeyHandler keyH) {
        this.keyH = keyH;
        setDefaultValues();
    }

    public void setDefaultValues () {
        x = 100;
        y = 100;
        speed = 4;
    }


    public void update () {
        // if(keyH.upPressed == true ) {            
            y -= speed;
        // }
        // if(keyH.downPressed == true ) {
        //     y += speed;
        // }
        // if(keyH.leftPressed == true ) {
        //     x -= speed;
        // }
        // if(keyH.rightPressed == true ) {
        //     x += speed;
        // }
    }

    
}
