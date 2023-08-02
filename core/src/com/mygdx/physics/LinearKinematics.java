package com.mygdx.physics;

import com.mygdx.physics.Translation2d;

public class LinearKinematics {
    private Translation2d mPosition;
    private Translation2d mVelocity;
    private Translation2d mAcceleration;
    
    public LinearKinematics (Translation2d position, Translation2d velocity, Translation2d accel) {
        this.mPosition = position;
        this.mVelocity = velocity;
        this.mAcceleration = accel;
    }

    public LinearKinematics (Translation2d position) {
        this.mPosition = position;
        this.mVelocity = new Translation2d();
        this.mAcceleration = new Translation2d();
    }

    public LinearKinematics () {
        this.mPosition = new Translation2d();
        this.mVelocity = new Translation2d();
        this.mAcceleration = new Translation2d();
    }

    public Translation2d position () {
        return mPosition;
    }

    public Translation2d velocity () {
        return mVelocity;
    }

    public Translation2d acceleration () {
        return mAcceleration;
    }

    public double dx (double dt) {
        return mVelocity.x() * dt + 0.5 * mAcceleration.x() * dt * dt;
    }

    public double dy (double dt) {
        return mVelocity.y() * dt + 0.5 * mAcceleration.y() * dt * dt;
    }

    public Translation2d displacement (double dt) {
        return new Translation2d(dx(dt), dy(dt));
    }

    public void updatePosition (double dt) {
        mPosition = mPosition.add(displacement(dt));
    }

    public double dvx (double dt) {
        return mAcceleration.x() * dt;
    }

    public double dvy (double dt) {
        return mAcceleration.y() * dt;
    }

    public Translation2d dv (double dt) {
        return new Translation2d(dvx(dt), dvy(dt));
    }

    public void updateVelocity (double dt) {
        mVelocity = mVelocity.add(dv(dt));

    }

    public void loop (double dt) {
        updateVelocity(dt);
        updatePosition(dt);
    }

    public void setAcceleration (Translation2d accel) {
        mAcceleration = accel;
    }



}