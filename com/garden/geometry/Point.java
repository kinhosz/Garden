package com.garden.geometry;

public class Point {
    
    private double x;
    private double y;
    private double z;

    public Point(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(){
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    public synchronized void setX(double x){
        this.x = x;
    }

    public synchronized void setY(double y){
        this.y = y;
    }

    public synchronized void setZ(double z){
        this.z = z;
    }

    public synchronized double getX(){
        return this.x;
    }

    public synchronized double getY(){
        return this.y;
    }

    public synchronized double getZ(){
        return this.z;
    }
}
