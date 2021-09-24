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
    private int x0, xf;
    private int y0, yf;

    public RayTracing(Point p, Direction d, BufferedImage buffer, int x0, int xf, int y0, int yf){
        super();
        this.position = p;
        this.direction = d;
        this.buffer = buffer;
        this.x0 = x0;
        this.y0 = y0;
        this.xf = xf;
        this.yf = yf;
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
        
        for(int i=this.x0; i<=this.xf;i++){
            for(int j=this.y0; j<=this.yf;j++){
                this.buffer.setRGB(j, i, c.getRGB());
            }
        }
    }
}
