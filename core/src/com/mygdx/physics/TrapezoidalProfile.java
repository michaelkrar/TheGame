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
    public TrapezoidalProfile (double maxAccel, double maxVel, double initialState, double finalState, Dimension dim) {
        this.maxAccel = maxAccel;
        this.maxVel = maxVel;
        this.initialState = initialState;
        this.finalState = finalState;
        this.dim = dim;
        mShapeState = ShapeState.RISE;
        mRisingDeltaX=0;
        mRisenState = 0;
    }
    public double getAccel (LinearKinematics lk) {
            // if (lk.velocity().x()<maxVel) {
            //     return maxAccel;
            // } else {
            //     return 0;
            // }
            switch(mShapeState) {
                case RISE:
                    if(lk.velocity().x()>maxVel){
                        mShapeState = ShapeState.STEADY;
                        mRisenState = lk.position().x();
                        mRisingDeltaX = mRisenState - initialState;
                    }
                    return maxAccel;
                case FALL:
                    return -maxAccel;
                case STEADY:
                    if ((lk.position().x()-mRisenState)+2*mRisingDeltaX>finalState-initialState) {
                        mShapeState = ShapeState.FALL;
                    }
                    return 0;
                default:
                    return 0;
            }

    }

    public boolean isFinished (LinearKinematics lk) {
        if(Math.abs(lk.position().x()-finalState)<errorAccept){
            mShapeState = ShapeState.DEAD;
            return true;
        }
        return false;
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
