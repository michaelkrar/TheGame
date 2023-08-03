package com.mygdx.game;

import java.awt.image.BufferedImage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.physics.Translation2d;

public class Entity {
    
    public int speed;
    public int jumpVel;
    public int grav;
    public Translation2d pose;
    public Sprite sprite;

    public Texture texture;
}
