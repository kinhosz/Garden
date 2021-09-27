package camera;

import java.lang.Thread;

import geometry.Point;
import geometry.Direction;

public class Pool extends Thread{
    
    private Point point;
    private Direction direction;
    private double v0;
    private double vf;
    private double h0;
    private double hf;
    private int x0;
    private int xf;
    private int y0;
    private int yf;
    private int height;
    private int width;
    private int[] image;

    public Pool(int[] bi, Point p, Direction d, int height, int width){
        super();
        this.height = height;
        this.width = width;
        this.image = bi;

        this.point = p;
        this.direction = d;
    }

    public void setAngleRange(double v0, double vf, double h0, double hf){
        this.v0 = v0;
        this.vf = vf;
        this.h0 = h0;
        this.hf = hf;
    }

    public void setShape(int x0, int xf, int y0, int yf){
        this.x0 = x0;
        this.xf = xf;
        this.y0 = y0;
        this.yf = yf;
    }

    @Override
    public void run(){

        double vf = this.vf;
        int largeX = (this.xf - this.x0);
        int largeY = (this.yf - this.y0);

        if(largeX == 0) largeX++;
        if(largeY == 0) largeY++;

        double dv = (this.vf - this.v0)/largeX;
        double dh = (this.hf - this.h0)/largeY;

        for(int i=this.x0; i<=this.xf; i++){
            double hf = this.hf;
            for(int j=this.y0; j<=this.yf; j++){
                Direction myD = new Direction(this.direction.getAlpha(), this.direction.getBeta());
                myD.addBeta(vf);

                try {
                    myD = myD.getAnotherVectorZ(hf);
                } catch (Exception e){
                    e.printStackTrace();
                }

                RayTracing rt = new RayTracing(this.point, myD, this.image, i, i, j, j, this.height, this.width);
                rt.start();
                hf -= dh;
            }
            vf -= dv;
        }
    }
}
