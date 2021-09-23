package camera;

import java.lang.Thread;
import java.awt.Color;
import java.awt.image.BufferedImage;

import geometry.Point;
import geometry.Direction;
import bits.Queue;

public class Picture extends Thread{

    private Point point;
    private Direction direction;
    private Vision vision;

    public Picture(Point p, Direction d, Vision v){
        super();
        this.point = new Point(p.getX(), p.getY(), p.getZ());
        this.direction = new Direction(d.getX(), d.getY(), d.getZ());
        this.vision = v;
    }

    @Override
    public void run(){

        System.out.println("criei");
        double verticalAngleRange = this.vision.getVerticalAngleRange();
        double horizontalAngleRange = this.vision.getHorizontalAngleRange();
        int height = this.vision.getHeight();
        int width = this.vision.getWidth();

        int matrix[][][] = new int[height][width][3];


        double verticalInitial = verticalAngleRange/2;
        double horizontalInitial = -horizontalAngleRange/2;

        double dVert = verticalAngleRange/height;
        double dHoriz = horizontalAngleRange/width;

        double v0 = verticalInitial;

        Queue consumers = new Queue();

        for(int i=0;i<height;i++){
            double h0 = horizontalInitial;
            for(int j=0;j<width;j++){
                Point point = new Point(this.point.getX(), this.point.getY(), this.point.getZ());
                Direction direction = new Direction(0.0, 1.0, 0.0);

                direction.eulerRotation(this.direction.getAlpha() - 90.0, this.direction.getBeta() + v0, h0);

                RayTracing rt = new RayTracing(point, direction, matrix, i,j);
                rt.run();

                consumers.push(rt);

                h0 = h0 + dHoriz;
            }

            v0 = v0 - dVert;
        }

        while(!consumers.empty()){
            RayTracing rt = (RayTracing)consumers.front();
            consumers.pop();

            try {
                rt.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        vision.changePicture(matrix);

        System.out.println("terminei");
    }
}
