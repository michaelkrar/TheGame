package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.physics.LinearKinematics;
import com.mygdx.physics.Translation2d;
import com.mygdx.physics.TrapezoidalProfile;
import com.mygdx.physics.TrapezoidalProfile.Dimension;

public class Boss extends Entity {
    public int hp;
    public int hpMax;
    public ImmuneState mImmuneState;
    public FightState mFightState;
    public long timeLastHit;
    public Texture shieldingText;
    public boolean mIsFightStateComplete;
    public LinearKinematics mLK;
    private TrapezoidalProfile mProfile;
    private boolean startedProfile;
    private double setpoint;
    public ArrayList<Bullet> bullets;
    private long timeLastBulletFired;
    private boolean skyphase;
    // public ShapeRenderer barDrawer;
    public Boss (Translation2d pose) {
        texture = new Texture("boss.png");
        shieldingText = new Texture("bossshield.png");
        this.pose = pose;
        hp = 100;
        hpMax = 100;
        mImmuneState = ImmuneState.NONE;
        mFightState = FightState.GROUNDRUSH;
        timeLastHit = 0;
        mLK = new LinearKinematics(pose);
        mProfile = new TrapezoidalProfile(0, 0, 0,0, Dimension.X);
        setpoint = 250;
        bullets = new ArrayList<Bullet>();
        timeLastBulletFired=0;
        skyphase= false;
        // barDrawer = new ShapeRenderer();
    }
    public void update () {
    }
    public void damage (int dmg) {
        if(mImmuneState==ImmuneState.NONE){
            hp-=dmg;
            timeLastHit= TimeUtils.millis();
            setpoint = 100;
            mFightState = FightState.GETHIGH;
        }
    }
    private void fireBullet () {
        if(bullets.size()<10&&TimeUtils.timeSinceMillis(timeLastBulletFired)>200){
            bullets.add(new Bullet(pose));
            timeLastBulletFired=TimeUtils.millis();
        }
    }
    public void render (SpriteBatch batch) {
        if(hp>0) {
            if(mImmuneState==ImmuneState.NONE){
                batch.draw(texture, (float)mLK.position().x(), (float)mLK.position().y());
            } else {
                batch.draw(shieldingText, (float)mLK.position().x(), (float)mLK.position().y());
            }
        }
        updateStates();
        handleFightState();
        mLK.loop(0.1);
    }
    private void handleFightState() {
        switch(mFightState) {
            case DEAD:
                break;
            case GROUNDRUSH:
                // System.out.println("ground rush!!1");
                if(!startedProfile) {
                    System.out.println("started profile i have not");
                    mProfile = new TrapezoidalProfile(10, 10, mLK.position().x(), setpoint, Dimension.X);
                    startedProfile = true;
                } else if(!mProfile.isFinished(mLK)){
                    System.out.println("i havent finished");
                    mLK.setAcceleration(new Translation2d(mProfile.getAccel(mLK),0));
                } else {
                    mLK.setVelocity(new Translation2d(0,0));
                    mLK.setAcceleration(new Translation2d(0,0));
                    startedProfile = false;
                    mFightState = FightState.UNKNOWN;
                }
                break;
            case GETHIGH:
                setpoint = 350;
                if(!startedProfile) {
                        System.out.println("started profile i have not");
                        mProfile = new TrapezoidalProfile(10, 30, mLK.position().y(), setpoint, Dimension.Y);
                        startedProfile = true;
                    } else if(!mProfile.isFinished(mLK)){
                        System.out.println("i havent finished");
                        mLK.setAcceleration(new Translation2d(0,mProfile.getAccel(mLK)));
                    } else {
                        mLK.setVelocity(new Translation2d(0,0));
                        mLK.setAcceleration(new Translation2d(0,0));
                        startedProfile = false;
                        mFightState = FightState.SKY;
                    }
                break;
            case SKY:
                setpoint = skyphase?50:400;
                if(!startedProfile) {
                        System.out.println("started profile i have not");
                        mProfile = new TrapezoidalProfile(10, 30, mLK.position().y(), setpoint, Dimension.X);
                        startedProfile = true;
                    } else if(!mProfile.isFinished(mLK)){
                        System.out.println("i havent finished");
                        mLK.setAcceleration(new Translation2d(mProfile.getAccel(mLK),0));
                    } else {
                        mLK.setVelocity(new Translation2d(0,0));
                        mLK.setAcceleration(new Translation2d(0,0));
                        startedProfile = false;
                        mFightState = FightState.SKY;
                        skyphase=skyphase?false:true;
                    }
                    fireBullet();
                break;
            case UNKNOWN:
                break;
            default:
                break;
            
        }
    }
    private void updateStates () {
        if(TimeUtils.timeSinceMillis(timeLastHit) > 1000) {
            mImmuneState = ImmuneState.NONE;
        } else {
            mImmuneState = ImmuneState.ALL;
        }
    }

    public enum FightState {
        SKY,
        GETHIGH,
        GROUNDRUSH,
        UNKNOWN,
        DEAD,
    }
    public enum ImmuneState {
        ALL,
        NONE,
    }
    
}
