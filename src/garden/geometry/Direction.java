package geometry;
import java.lang.Math;

public class Direction {
    
    private double x;
    private double y;
    private double z;
    private double alpha;
    private double beta;
    private final double eps = -1e9;

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

    public Direction(double alpha, double beta){
        this.setDirection(alpha, beta);
    }

    public synchronized void setDirection(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;

        this.normalize();
    }

    private synchronized void normalize(){
        double norma = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        if(norma <= eps){
            this.x = 1.0;
            this.y = 0.0;
            this.z = 0.0;
        }
        else{
            this.x = this.x/norma;
            this.y = this.y/norma;
            this.z = this.z/norma;
        }

        if(this.z > 1.0){
            this.z = 1.0;
        }

        this.beta = Math.toDegrees(Math.asin(this.z));
        double aux = 0.0;

        if(Math.abs(Math.cos(Math.toRadians(this.beta))) <= this.eps){
            aux = this.x/this.eps;
        }
        else{
            aux = this.x/Math.cos(Math.toRadians(this.beta));
        }
        aux = Math.min(aux, 1.0);
        
        this.alpha = Math.toDegrees(Math.acos(aux));

        this.normalizeAngles();
    }

    public synchronized double getX(){
        return this.x;
    }

    public synchronized double getY(){
        return this.y;
    }

    public synchronized double getZ(){
        return this.z;
    }

    public synchronized void rotationZ(double angle){
        double rad = Math.toRadians(angle);

        double x = this.x*Math.cos(rad) - this.y*Math.sin(rad);
        double y = this.x*Math.sin(rad) + this.y*Math.cos(rad);

        this.x = x;
        this.y = y;

        this.normalize();
    }

    public synchronized void rotationX(double angle){
        double rad = Math.toRadians(angle);

        double y = this.y*Math.cos(rad) - this.z*Math.sin(rad);
        double z = this.y*Math.sin(rad) + this.z*Math.cos(rad);

        this.y = y;
        this.z = z;

        this.normalize();
    }

    public synchronized void eulerRotation(double alpha, double beta, double gama){

        // the euler rotation: zxz
        this.rotationZ(alpha);
        this.rotationX(beta);
        this.rotationZ(gama);
    }

    private synchronized void normalizeAngles(){

        while(this.alpha > 360.0){
            this.alpha -= 360.0;
        }

        while(this.alpha < 0){
            this.alpha += 360.0;
        }

        while(this.beta > 360.0){
            this.beta -= 360.0;
        }

        while(this.beta < 0){
            this.beta += 360.0;
        }
    }

    public synchronized void setDirection(double alpha, double beta){
        this.alpha = alpha;
        this.beta = beta;

        this.normalizeAngles();

        this.x = Math.cos(Math.toRadians(this.alpha)) * Math.cos(Math.toRadians(this.beta));
        this.y = Math.sin(Math.toRadians(this.alpha)) * Math.cos(Math.toRadians(this.beta));
        this.z = Math.sin(Math.toRadians(this.beta));
    }

    public synchronized void addAlpha(double dalpha){
        this.alpha = this.alpha + dalpha;

        this.setDirection(this.alpha, this.beta);
    }

    public synchronized void addBeta(double dbeta){
        this.beta = this.beta + dbeta;

        this.setDirection(this.alpha, this.beta);
    }

    public synchronized double getAlpha(){
        return this.alpha;
    }

    public synchronized double getBeta(){
        return this.beta;
    }

    public Direction crossProduct(Direction B){

        double ax, ay, az;
        double bx, by, bz;

        ax = this.x;
        ay = this.y;
        az = this.z;

        bx = B.getX();
        by = B.getY();
        bz = B.getZ();

        double cx, cy, cz;

        cx = ay*bz - az*by;
        cy = az*bx - ax*bz;
        cz = ax*by - ay*bx;

        Direction C = new Direction(cx, cy, cz);

        return C;
    }

    public double dotProduct(Direction B){

        double ax, ay, az;
        double bx, by, bz;

        ax = this.x;
        ay = this.y;
        az = this.z;

        bx = B.getX();
        by = B.getY();
        bz = B.getZ();

        double dot = ax*bx + ay*by + az*bz;

        return dot;
    }
}
