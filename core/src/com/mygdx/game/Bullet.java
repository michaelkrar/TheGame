package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.physics.LinearKinematics;
import com.mygdx.physics.Translation2d;

public class Bullet extends Entity {
    public LinearKinematics linK;
    private float timeFired = TimeUtils.millis();
    private double initX;
    private boolean down;
    public Bullet (Translation2d pose,boolean down) {
        
        texture = new Texture("bullet.png");
        this.pose = pose;
        this.down = down;
        linK = new LinearKinematics(pose);
        linK.setVelocity(new Translation2d(down?0:50,down?-50:0));
        initX = down?pose.y():pose.x();   
    }
    public void update () {
        linK.loop(.1);
        linK.setVelocity(new Translation2d(down?0:50,down?-50:0));
    }
    public void render (SpriteBatch batch) {
        batch.draw(texture, (float)linK.position().x(), (float)linK.position().y());
    }
    public boolean dead () {
        // System.out.println(Math.abs(initX-pose.x()));
        return down?Math.abs(initX-linK.position().y())>500:Math.abs(initX-linK.position().x())>500;
    }
}
