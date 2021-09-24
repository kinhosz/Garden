package camera;

import java.lang.Thread;
import java.awt.image.BufferedImage;

import bits.Queue;
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
    private BufferedImage image;
    private boolean close;
    private long blockSize;

    public Pool(BufferedImage bi, Point p, Direction d, long block){
        super();
        this.height = bi.getHeight();
        this.width = bi.getWidth();
        this.image = bi;

        this.point = p;
        this.direction = d;
        this.close = false;
        this.blockSize = block;
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

        long lx = (this.xf - this.x0 + 1);
        long ly = (this.yf - this.y0 + 1);
        long area = lx*ly;

        if(this.x0 == this.xf && this.yf == this.y0){

            Direction d = new Direction(0.0, 1.0, 0.0);
            d.eulerRotation(this.direction.getAlpha(), this.direction.getBeta() + this.v0, this.h0);

            RayTracing rt = new RayTracing(this.point, d, this.image, this.x0, this.xf, this.y0, this.yf);
            rt.start();

            try {
                rt.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if(this.x0 <= this.xf && this.y0 <= this.yf && area <= this.blockSize){

            Direction d = new Direction(0.0, 1.0, 0.0);
            d.eulerRotation(this.direction.getAlpha(), this.direction.getBeta() + this.v0, this.h0);

            RayTracing rt = new RayTracing(this.point, d, this.image, this.x0, this.xf, this.y0, this.yf);
            rt.start();

            try {
                rt.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if(this.x0 <= this.xf && this.y0 <= this.yf){

            Pool p1, p2, p3, p4;

            double vmid = (this.v0 + this.vf)/2;
            double hmid = (this.h0 + this.hf)/2;

            int xmid = (int)(this.xf + this.x0)/2;
            int ymid = (int)(this.yf + this.y0)/2;

            // first
            p1 = new Pool(this.image, this.point, this.direction, this.blockSize);
            p1.setAngleRange(this.v0, vmid, this.h0, hmid);
            p1.setShape(this.x0, xmid, this.y0, ymid);
            p1.start();

            // second
            p2 = new Pool(this.image, this.point, this.direction, this.blockSize);
            p2.setAngleRange(this.v0, vmid, hmid, this.hf);
            p2.setShape(this.x0, xmid, ymid+1, this.yf);
            p2.start();

            // third
            p3 = new Pool(this.image, this.point, this.direction, this.blockSize);
            p3.setAngleRange(vmid, this.vf, this.h0, hmid);
            p3.setShape(xmid+1, this.xf, this.y0, ymid);
            p3.start();

            // fourth
            p4 = new Pool(this.image, this.point, this.direction, this.blockSize);
            p4.setAngleRange(vmid, this.vf, hmid, this.hf);
            p4.setShape(xmid+1, this.xf, ymid+1, this.yf);
            p4.start();

            try {
                p1.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            try{
                p2.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            try{
                p3.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            try{
                p4.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        this.close = true;
    }

    public boolean closed(){
        return this.close;
    }
}
