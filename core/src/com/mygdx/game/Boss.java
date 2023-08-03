package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.physics.LinearKinematics;
import com.mygdx.physics.Translation2d;

public class Boss extends Entity {
    public int hp;
    public int hpMax;
    public ImmuneState mImmuneState;
    public long timeLastHit;
    public Texture shieldingText;
    // public ShapeRenderer barDrawer;
    public Boss (Translation2d pose) {
        texture = new Texture("boss.png");
        shieldingText = new Texture("bossshield.png");
        this.pose = pose;
        hp = 100;
        hpMax = 100;
        mImmuneState = ImmuneState.NONE;
        timeLastHit = 0;
        // barDrawer = new ShapeRenderer();
    }
    public void update () {
    }
    public void damage (int dmg) {
        if(TimeUtils.timeSinceMillis(timeLastHit)>1000){
            hp-=dmg;
            timeLastHit= TimeUtils.millis();
        }
    }
    public void render (SpriteBatch batch) {
        if(hp>0) {
            if(TimeUtils.timeSinceMillis(timeLastHit)>1000){
                batch.draw(texture, (float)pose.x(), (float)pose.y());
            } else {
                batch.draw(shieldingText, (float)pose.x(), (float)pose.y());
            }
        }
    }

    public enum FightState {
        SKY,
        GROUNDRUSH,
        UNKNOWN,
    }
    public enum ImmuneState {
        ALL,
        NONE,
    }
    
}
