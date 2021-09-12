package geometry;

public class Point {
    
    private double x;
    private double y;
    private double z;

    public Point(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(){
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public void setZ(double z){
        this.z = z;
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
        this.y=  y;
    }
}
