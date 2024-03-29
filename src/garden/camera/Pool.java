package camera;

import java.lang.Thread;

import geometry.Point;
import geometry.Direction;

public class Pool extends Thread{
    
    private Point point;
    private Direction direction;
    private double verticalAngleRange;
    private double horizontalAngleRange;
    private int x0;
    private int xf;
    private int y0;
    private int yf;
    private int height;
    private int width;
    private int[] image;
    private boolean on;
    private boolean alive;

    public Pool(int[] bi, int height, int width){
        super();
        this.height = height;
        this.width = width;
        this.image = bi;

        this.alive = true;
        this.on = false;
    }

    public void setPosition(Point p, Direction d){
        this.point = p;
        this.direction = d;
    }

    public void setAngleRange(double verticalAngleRange, double horizontalAngleRange){
        this.verticalAngleRange = verticalAngleRange;
        this.horizontalAngleRange = horizontalAngleRange;
    }

    public void setShape(int x0, int xf, int y0, int yf){
        this.x0 = x0;
        this.xf = xf;
        this.y0 = y0;
        this.yf = yf;
    }

    public void turnOn(){
        this.on = true;
    }

    private void turnOff(){
        this.on = false;
    }

    public boolean isOn(){
        return this.on;
    }

    public void kill(){
        this.alive = false;
    }

    @Override
    public void run(){

        while(this.alive){

            while(this.on){

                double dv = this.verticalAngleRange/this.height;
                int xmid = this.height/2;
                int ymid = this.width/2;

                for(int i=this.x0; i<=this.xf; i++){

                    double beta = (double)(xmid - i)*dv;
                    double horizontalAngleRange = this.horizontalAngleRange*Math.cos(Math.toRadians(beta));
                    double dh = horizontalAngleRange/this.width;

                    for(int j=this.y0; j<=this.yf; j++){
                        Direction myD = new Direction(this.direction.getAlpha(), this.direction.getBeta());
                        myD.addBeta(beta);

                        double theta = (double)(ymid - j)*dh;

                        myD = myD.getAnotherVectorZ(theta);

                        RayTracing rt = new RayTracing(this.point, myD, this.image, i, i, j, j, this.height, this.width);
                        rt.start();
                    }
                }

                this.turnOff();
            }

            Pool.pause();
        }
    }

    public static void pause(){
        try { Thread.sleep(1); } catch(InterruptedException e) {}
    }
}
