package geometry;
import java.lang.Math;

public class Direction {
    
    private double x;
    private double y;
    private double z;

    public Direction(){
        this.x = 1.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    public Direction(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;

        this.normalize();
    }

    public void setDirection(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;

        this.normalize();
    }

    private void normalize(){
        double norma = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        if(norma == 0.0){
            this.x = 1.0;
            this.y = 0.0;
            this.z = 0.0;
        }
        else{
            this.x = this.x/norma;
            this.y = this.y/norma;
            this.z = this.z/norma;
        }
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    public double getZ(){
        return this.z;
    }

    public void rotation2D(double angle){
        double rad = (angle*Math.PI)/180.0;

        double x = this.x*Math.cos(rad) - this.y*Math.sin(rad);
        double y = this.x*Math.sin(rad) + this.y*Math.cos(rad);

        this.x = x;
        this.y = y;

        this.normalize();
    }
}
