package com.garden.camera;

import javax.swing.JLabel;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.image.DataBufferInt;

import com.garden.geometry.Point;
import com.garden.geometry.Direction;

public class Vision {

    private int width;
    private int height;
    private BufferedImage image;
    private double verticalAngleRange = 60.0;
    private double horizontalAngleRange = 90.0;
    private boolean locked;
    private Pool[] monitor;
    private int threads;

    public Vision(int width, int height){
        this.width = width;
        this.height = height;
        this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        this.image.setAccelerationPriority(0); // idk
        this.createBaseImage();
        this.locked = false;

        this.threads = 30;
        this.createPool();
    }

    public void kill(){

        for(int i=0;i<this.threads;i++){
            monitor[i].kill();
            try {
                monitor[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void createPool(){

        this.monitor = new Pool[this.threads];
        int[] pixels = ((DataBufferInt)this.getImage().getRaster().getDataBuffer()).getData();

        for(int i=0;i<this.threads;i++){
            Pool pool = new Pool(pixels, this.height, this.width);
            pool.start();

            this.monitor[i] = pool;
        }
    }

    private void createBaseImage(){

        for(int i=0;i<this.height;i++){
            for(int j=0;j<this.width;j++){
                Color c = new Color(0,0,0);
                this.image.setRGB(j, i, c.getRGB());
            }
        }
    }

    public synchronized boolean locked(){

        return this.locked;
    }

    public synchronized void lock(){

        this.locked = true;
    }

    public synchronized void unlock(){

        this.locked = false;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public double getVerticalAngleRange(){
        return this.verticalAngleRange;
    }

    public double getHorizontalAngleRange(){
        return this.horizontalAngleRange;
    }

    private synchronized JLabel createImage(){

        JLabel context = new JLabel(new ImageIcon(this.image));
        return context;
    }

    public synchronized JLabel takePicture(Point p, Direction d) throws Exception{

        if(this.locked()){
            throw new Exception("The buffered Image is locked");
        }

        this.splitImage(p, d);
        
        JLabel label = this.createImage();
        
        return label;
    }

    private void splitImage(Point p, Direction d){

        this.lock();

        Point myPoint = new Point(p.getX(), p.getY(), p.getZ());
        Direction myDirection = new Direction(d.getAlpha(), d.getBeta());

        int dx = (int)(this.height + this.threads - 1)/this.threads;
        int initial = 0;

        for(int i=0;i<this.threads;i++){

            int end = Math.min(this.height-1, initial + dx -1);

            monitor[i].setPosition(myPoint, myDirection);
            monitor[i].setAngleRange(this.verticalAngleRange, this.horizontalAngleRange);
            monitor[i].setShape(initial, end, 0, this.width-1);

            monitor[i].turnOn();

            initial = end + 1;
        }

        for(int i=0;i<this.threads;i++){

            while(monitor[i].isOn()){
                Pool.pause();
            }
        }

        this.unlock();
    }

    public BufferedImage getImage(){
        return this.image;
    }
}
