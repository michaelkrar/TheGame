package com.mygdx.physics;

public class TrapezoidalProfile {
    private double maxAccel;
    private double maxVel;
    private double initialState;
    private double finalState;
    private Dimension dim;
    private double errorAccept = 3;
    private ShapeState mShapeState;
    private double mRisingDeltaX;
    private double mRisenState;
    private double currVel;
    private double currPos;
    private boolean right;
    public TrapezoidalProfile (double maxAccel, double maxVel, double initialState, double finalState, Dimension dim) {
        this.maxAccel = maxAccel;
        this.maxVel = maxVel;
        this.initialState = initialState;
        this.finalState = finalState;
        this.dim = dim;
        mShapeState = ShapeState.RISE;
        mRisingDeltaX=0;
        mRisenState = 0;
        currVel = 0;
        right = finalState-initialState>0?true:false;
    }
    public double getAccel (LinearKinematics lk) {
            // if (lk.velocity().x()<maxVel) {
            //     return maxAccel;
            // } else {
            //     return 0;
            // }
            if(dim==Dimension.X) {
                currVel = lk.velocity().x();
                currPos = lk.position().x();
            } else {
                currVel = lk.velocity().y();
                currPos = lk.position().y();
            }
            switch(mShapeState) {
                case RISE:
                    if(currVel>maxVel){
                        mShapeState = ShapeState.STEADY;
                        mRisenState = currPos;
                        mRisingDeltaX = mRisenState - initialState;
                    }
                    return right?maxAccel:-maxAccel;
                case FALL:
                    return right?-maxAccel:maxAccel;
                case STEADY:
                    if ((currPos-mRisenState)+2*mRisingDeltaX>finalState-initialState) {
                        mShapeState = ShapeState.FALL;
                    }
                    return 0;
                default:
                    return 0;
            }

    }

    public boolean isFinished (LinearKinematics lk) {
        if(Math.abs(currPos-finalState)<errorAccept){
            kill();
            return true;
        }
        return false;
    }
    public void kill () {
        mShapeState = ShapeState.DEAD;
    }
    public enum Dimension {
        X,
        Y,
    }
    public enum ShapeState {
        RISE,
        STEADY,
        FALL,
        DEAD
    }
}
