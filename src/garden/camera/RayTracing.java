package camera;

import java.awt.image.BufferedImage;
import java.lang.Thread;
import java.awt.Color;

import geometry.Direction;
import geometry.Point;

public class RayTracing extends Thread{
    
    private Point position;
    private Direction direction;
    private BufferedImage buffer;
    private int x;
    private int y;

    public RayTracing(Point p, Direction d, BufferedImage buffer, int x, int y){
        super();
        this.position = p;
        this.direction = d;
        this.buffer = buffer;
        this.x = x;
        this.y = y;
    }

    @Override
    public void run(){

        Color c;

        if(this.direction.getX() >= 0.0 && this.direction.getY() >= 0.0){
            c = new Color(0,0,0);
        }
        else if(this.direction.getX() >= 0.0){
            c = new Color(255,255,255);
        }
        else if(this.direction.getX() < 0.0 && this.direction.getY() < 0.0){
            c = new Color(0, 0, 255);
        }
        else{
            c = new Color(255,0,0);
        }
        
        this.buffer.setRGB(this.y, this.x, c.getRGB());
    }
}
