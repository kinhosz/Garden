package com.garden.camera;

import java.awt.Color;

import com.garden.geometry.Direction;
import com.garden.geometry.Point;

public class RayTracing{
    
    private Point position;
    private Direction direction;
    private int[] buffer;
    private int x0, xf;
    private int y0, yf;
    private int width;

    public RayTracing(Point p, Direction d, int[] buffer, int x0, int xf, int y0, int yf, int height, int width){
        this.position = p;
        this.direction = d;
        this.buffer = buffer;
        this.x0 = x0;
        this.y0 = y0;
        this.xf = xf;
        this.yf = yf;
        this.width = width;
    }
    
    public void start(){

        int r = 0;
        int g = 0;
        int b = 0;

        double len = 100;

        double mini = 1e9;

        double t;

        double px = this.position.getX();
        double py = this.position.getY();
        double pz = this.position.getZ();

        double dx = this.direction.getX();
        double dy = this.direction.getY();
        double dz = this.direction.getZ();

        double x;
        double y;
        double z;

        if(dx != 0.0){

            // face 1
            t = (-len - px)/dx;
            y = py + dy*t;
            z = pz + dz*t;

            if(t >= 0.0 && (y >= -len && y <= len) && (z >= -len && z <= len) && t <= mini){
                mini = t;
                r = 255;
                g = 0;
                b = 0;
            }

            // face 2
            t = (len - px)/dx;
            y = py + dy*t;
            z = pz + dz*t;

            if(t >= 0.0 && (y >= -len && y <= len) && (z >= -len && z <= len) && t <= mini){
                mini = t;
                r = 0;
                g = 255;
                b = 0;
            }
        }

        if(dy != 0){

            // face 3
            t = (-len - py)/dy;
            x = px + dx*t;
            z = pz + dz*t;

            if(t >= 0.0 && (x >= -len && x <= len) && (z >= -len && z <= len) && t <= mini){
                mini = t;
                r = 0;
                g = 0;
                b = 255;
            }

            // face 4
            t = (len - py)/dy;
            x = px + dx*t;
            z = pz + dz*t;

            if(t >= 0.0 && (x >= -len && x <= len) && (z >= -len && z <= len) && t <= mini){
                mini = t;
                r = 255;
                g = 255;
                b = 0;
            }
        }

        if(dz != 0){
            // face 5
            t = (-len - pz)/dz;
            x = px + dx*t;
            y = py + dy*t;

            if(t >= 0.0 && (x >= -len && x <= len) && (y >= -len && y <= len) && t <= mini){
                mini = t;
                r = 255;
                g = 0;
                b = 255;
            }

            // face 6
            t = (len - pz)/dz;
            x = px + dx*t;
            y = py + dy*t;

            if(t >= 0.0 && (x >= -len && x <= len) && (y >= -len && y <= len) && t <= mini){
                mini = t;
                r = 0;
                g = 255;
                b = 255;
            }
        }

        this.setColor(r, g, b);
    }

    private void setColor(int r, int g, int b){
        Color c = new Color(r, g, b);

        for(int i=this.x0; i<=this.xf;i++){
            for(int j=this.y0; j<=this.yf;j++){
                this.buffer[i*this.width + j] = (int)c.getRGB();
            }
        }
    }
}
