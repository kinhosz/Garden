package com.garden.camera;

import com.garden.geometry.Point;
import com.garden.geometry.Direction;
import javax.swing.JLabel;

public class Camera {
    
    private double sensitivity = 1.0;
    private Point location;
    private Direction direction;
    private int width;
    private int height;
    private Vision vision;
    private final int resolutionWidth = 16;
    private final int resolutionHeight = 9;

    public Camera(int height){

        this.setResolution(height);
        this.location = new Point();
        this.resize(width, height);
        this.vision = new Vision(this.width, this.height);
        this.direction = new Direction();
    }

    public void kill(){
        this.vision.kill();
    }

    public void setResolution(int height) {
        /* examples:
        2160p: 3840 x 2160.
        1440p: 2560 x 1440.
        1080p: 1920 x 1080.
        720p: 1280 x 720.
        480p: 854 x 480.
        360p: 640 x 360.
        240p: 426 x 240*/

        this.height = height;
        this.width = (int)this.resolutionWidth*height/this.resolutionHeight;
    }

    public void setLocation(double x, double y, double z){
        this.location.setX(x);
        this.location.setY(y);
        this.location.setZ(z);
    }

    public void setLocation(Point p){
        this.location.setX(p.getX());
        this.location.setY(p.getY());
        this.location.setZ(p.getZ());
    }

    public double getXLocation(){
        return this.location.getX();
    }

    public double getYLocation(){
        return this.location.getY();
    }

    public double getZLocation(){
        return this.location.getZ();
    }

    public void setDirection(double x, double y, double z){
        this.direction.setDirection(x, y, z);
    }

    public void setDirection(Point p){
        this.direction.setDirection(p.getX(), p.getY(), p.getZ());
    }

    public void setDirection(double alpha, double beta){
        this.direction.setDirection(alpha, beta);
    }

    private void resize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public double getSensitivy(){
        return this.sensitivity;
    }

    public void changeSensitivy(double percent){
        this.sensitivity = (percent/100.0);
    }

    public void moveFront(){
        Direction dir2d = new Direction(this.direction.getX(), this.direction.getY(), 0.0);
        double x = this.location.getX();
        double y = this.location.getY();
        x = x + (this.sensitivity * dir2d.getX());
        y = y + (this.sensitivity * dir2d.getY());

        this.location.setX(x);
        this.location.setY(y);
    }

    public void moveBack(){
        Direction dir2d = new Direction(this.direction.getX(), this.direction.getY(), 0.0);
        double x = this.location.getX();
        double y = this.location.getY();

        x = x - (this.sensitivity * dir2d.getX());
        y = y - (this.sensitivity * dir2d.getY());

        this.location.setX(x);
        this.location.setY(y);
    }

    public void moveLeft(){
        Direction dir2d = new Direction(this.direction.getX(), this.direction.getY(), 0.0);
        double x = this.location.getX();
        double y = this.location.getY();

        dir2d.rotationZ(90.0);

        x = x + (this.sensitivity * dir2d.getX());
        y = y + (this.sensitivity * dir2d.getY());

        this.location.setX(x);
        this.location.setY(y);
    }

    public void moveRight(){
        Direction dir2d = new Direction(this.direction.getX(), this.direction.getY(), 0.0);
        double x = this.location.getX();
        double y = this.location.getY();

        dir2d.rotationZ(270.0);

        x = x + (this.sensitivity * dir2d.getX());
        y = y + (this.sensitivity * dir2d.getY());

        this.location.setX(x);
        this.location.setY(y);
    }

    public void moveUp(){
        double z = this.location.getZ();
        z = z + (1.0 * this.sensitivity);
        this.location.setZ(z);
    }

    public void moveDown(){
        double z = this.location.getZ();
        z = z - (1.0 * this.sensitivity);
        this.location.setZ(z);
    }

    public JLabel takePicture(){

        JLabel label = null;
        
        try {
            label = this.vision.takePicture(this.location, this.direction);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return label;
    }

    public void horizontalRotation(double dAlpha){
        this.direction.addAlpha(dAlpha);
    }

    public void verticalRotation(double dBeta){
        this.direction.addBeta(dBeta);
    }

    public double getXDirection(){
        return this.direction.getX();
    }

    public double getYDirection(){
        return this.direction.getY();
    }

    public double getZDirection(){
        return this.direction.getZ();
    }

    public double getAlphaDirection(){
        return this.direction.getAlpha();
    }

    public double getBetaDirection(){
        return this.direction.getBeta();
    }

    public boolean updated(){
        return !this.vision.locked();
    }
}