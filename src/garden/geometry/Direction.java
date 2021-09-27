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

    public Direction getUpVector(){

        double alpha = Math.toRadians(this.alpha);
        double beta = Math.toRadians(this.beta);

        double x = -Math.cos(alpha)*Math.sin(beta);
        double y = -Math.sin(alpha)*Math.sin(beta);
        double z = Math.cos(beta);

        Direction d = new Direction(x, y, z);

        return d;
    }

    public Direction getLeftVector(){

        double alpha = Math.toRadians(this.alpha);

        double x = Math.sin(alpha);
        double y = -Math.cos(alpha);
        double z = 0.0;

        Direction d = new Direction(x, y, z);

        return d;
    }

    public Direction getAnotherVectorZ(double thetaDegree) throws Exception{

        Direction U = this.getUpVector();
        Direction L = this.getLeftVector();

        double theta = Math.toRadians(thetaDegree);
        double sin_theta = Math.sin(theta);
        double cos_theta = Math.cos(theta);

        double nx, ny, nz;
        double lx, ly, lz;
        double ux, uy, uz;
        double px, py, pz;

        double c1, c2, c3;
        double c4, c5, c6;
        double c7, c8, c9;

        nx = this.x;
        ny = this.y;
        nz = this.z;

        lx = L.getX();
        ly = L.getY();
        lz = L.getZ();

        ux = U.getX();
        uy = U.getY();
        uz = U.getZ();

        if(nx <= this.eps){
            throw new Exception("nx is zero");
        }

        c1 = (cos_theta * lx)/nx;

        c2 = -(ny  * lx)/nx;

        c3 = -(nz * lx)/nx;

        c4 = (cos_theta * ux)/nx;

        c5 = - (ny * ux)/nx;

        c6 = -(nz * ux)/nx;

        if((c2 + ly) <= this.eps){
            throw new Exception("(c2 + ly) is zero");
        }

        c7 = c4 - sin_theta*(c5 + uy)/(c2 + ly) - c1*(c5 + uy)/(c2 + ly);

        c8 = -(c3 + lz)*(c5 + uy)/(c2 + ly);

        c9 = c6 + uz + c8;

        if(c9 <= this.eps){
            throw new Exception("c9 is zero.");
        }

        pz = -c7/c9;

        py = (-sin_theta - c1 - pz*(c3 + lz))/(c2 + ly);

        px = (cos_theta - py*ny - pz*nz)/nx;

        Direction d = new Direction(px, py, pz);

        return d;
    }
}
