package com.mygdx.physics;

public class Translation2d {
    private double x;
    private double y;
    public Translation2d (double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Translation2d () {
        this.x = 0;
        this.y = 0;
    }

    public double x() {
        return x;
    }
    
    public double y() {
        return y;
    }

    public double theta() {
        return Math.atan(y/x);
    }

    public double hypot() {
        return Math.sqrt(x*x+y*y);
    }

    public Translation2d inverse() {
        return new Translation2d(-x, -y);
    }

    public Translation2d scale(double k) {
        return new Translation2d(x*k, y*k);
    }

    public Translation2d add(Translation2d added) {
        return new Translation2d(x + added.x(), y + added.y());
    }
    public Translation2d subtract(Translation2d subtracted) {
        return add(subtracted.scale(-1));
    }
}