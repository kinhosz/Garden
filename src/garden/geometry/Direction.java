package geometry;
import java.lang.Math;

public class Direction {
    
    private double x;
    private double y;
    private double z;
    private double alpha;
    private double beta;
    private final double MAX_BETA = 44;
    private final double MIN_BETA = -44;

    public Direction(){
        this.x = 1.0;
        this.y = 0.0;
        this.z = 0.0;
        this.alpha = 0.0;
        this.beta = 0.0;
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

        this.beta = Math.toDegrees(Math.asin(this.z));
        this.beta = Math.max(this.MIN_BETA, Math.min(this.MAX_BETA, this.beta));
        this.alpha = Math.toDegrees(Math.acos(this.x/Math.cos(Math.toRadians(this.beta))));
        this.normalizeAngles();
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
        double rad = Math.toRadians(angle);

        double x = this.x*Math.cos(rad) - this.y*Math.sin(rad);
        double y = this.x*Math.sin(rad) + this.y*Math.cos(rad);

        this.x = x;
        this.y = y;

        this.normalize();
    }

    private void normalizeAngles(){

        while(this.alpha > 360.0){
            this.alpha -= 360.0;
        }

        while(this.alpha < 0){
            this.alpha += 360.0;
        }

        this.beta = Math.max(this.MIN_BETA, Math.min(this.MAX_BETA, this.beta));
    }

    public void setDirection(double alpha, double beta){
        this.alpha = alpha;
        this.beta = beta;

        this.normalizeAngles();

        this.x = Math.cos(Math.toRadians(this.alpha)) * Math.cos(Math.toRadians(this.beta));
        this.y = Math.sin(Math.toRadians(this.alpha)) * Math.cos(Math.toRadians(this.beta));
        this.z = Math.sin(Math.toRadians(this.beta));
    }

    public void addAlpha(double dalpha){
        this.alpha = this.alpha + dalpha;
        
        this.normalizeAngles();

        this.setDirection(this.alpha, this.beta);
    }

    public void addBeta(double dbeta){
        this.beta = this.beta + dbeta;

        this.normalizeAngles();

        this.setDirection(this.alpha, this.beta);
    }

    public double getAlpha(){
        return this.alpha;
    }

    public double getBeta(){
        return this.beta;
    }
}
