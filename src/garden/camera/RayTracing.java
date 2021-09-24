package camera;

import java.awt.Color;

import geometry.Direction;
import geometry.Point;

public class RayTracing{
    
    private Point position;
    private Direction direction;
    private int[] buffer;
    private int x0, xf;
    private int y0, yf;
    private int height;
    private int width;

    public RayTracing(Point p, Direction d, int[] buffer, int x0, int xf, int y0, int yf, int height, int width){
        this.position = p;
        this.direction = d;
        this.buffer = buffer;
        this.x0 = x0;
        this.y0 = y0;
        this.xf = xf;
        this.yf = yf;
        this.height = height;
        this.width = width;
    }
    
    public void start(){

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
                this.buffer[i*this.width + j] = (int)c.getRGB();
            }
        }
    }
}
