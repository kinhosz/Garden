package camera;

import java.lang.Thread;

import geometry.Direction;
import geometry.Point;

public class RayTracing extends Thread{
    
    private Point position;
    private Direction direction;
    private int[][][] buffer;
    private int x;
    private int y;

    public RayTracing(Point p, Direction d, int[][][] buffer, int x,int y){
        super();
        this.position = p;
        this.direction = d;
        this.buffer = buffer;
        this.x = x;
        this.y = y;
    }

    @Override
    public void run(){
        System.out.println("oi, sou o RT " + this.x + " + " + this.y);
        this.buffer[this.x][this.y][0] = 255;
        this.buffer[this.x][this.y][1] = 0;
        this.buffer[this.x][this.y][2] = 0;
    }
}
