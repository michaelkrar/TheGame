package com.mygdx.physics;

import com.mygdx.physics.TrapezoidalProfile.Dimension;
import com.mygdx.physics.TrapezoidalProfile.ShapeState;

public class TrapezoidalProfile2d {
    private TrapezoidalProfile mXTrapezoidalProfile;
    private TrapezoidalProfile mYTrapezoidalProfile;
    private Translation2d finalState;
    private double errorAccept = 5;
    public TrapezoidalProfile2d (TrapezoidalProfile mXTrapezoidalProfile,TrapezoidalProfile mYTrapezoidalProfile) {
        this.mXTrapezoidalProfile = mXTrapezoidalProfile;
        this.mYTrapezoidalProfile = mYTrapezoidalProfile;
    }
    public TrapezoidalProfile2d (Pair<Double,Double> maxAccelXY, Pair<Double,Double> maxVelXY, Translation2d initialState, Translation2d finalState) {
        this(new TrapezoidalProfile(maxAccelXY.first(), maxVelXY.first(), initialState.x(), finalState.x(), Dimension.X),
             new TrapezoidalProfile(maxAccelXY.second(),maxVelXY.second(),initialState.y(),finalState.y(),Dimension.Y));
        this.finalState = finalState;
    }
    public boolean isFinished (LinearKinematics lk) {
        if(Math.abs(lk.position().subtract(finalState).hypot())<errorAccept){
            mXTrapezoidalProfile.kill();
            mYTrapezoidalProfile.kill();
            return true;
        }
        return false;
    }
    public Translation2d getAccel (LinearKinematics lk) {
        return new Translation2d(mXTrapezoidalProfile.getAccel(lk),mYTrapezoidalProfile.getAccel(lk));
    }


}