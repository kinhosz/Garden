package camera;
import geometry.Point;
import geometry.Direction;
import javax.swing.JLabel;

public class Camera {
    
    private double sensitivity = 1.0;
    private Point location;
    private Direction direction;
    private int width;
    private int height;
    private Vision vision;

    public Camera(int width, int height){
        this.location = new Point();
        this.resize(width, height);
        this.vision = new Vision(width, height);
        this.direction = new Direction();
    }

    public void setLocation(double x, double y, double z){
        this.location.setX(x);
        this.location.setY(y);
        this.location.setZ(z);
    }

    public void setLocation(Point p){
        this.location.setX(p.getX());
        this.location.setY(p.getY());
        this.location.setZ(p.getZ());
    }

    public double getXLocation(){
        return this.location.getX();
    }

    public double getYLocation(){
        return this.location.getY();
    }

    public double getZLocation(){
        return this.location.getZ();
    }

    public void setDirection(double x, double y, double z){
        this.direction.setDirection(x, y, z);
    }

    public void setDirection(Point p){
        this.direction.setDirection(p.getX(), p.getY(), p.getZ());
    }

    public void setDirection(double alpha, double beta){
        this.direction.setDirection(alpha, beta);
    }

    public void resize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public double getSensitivy(){
        return this.sensitivity;
    }

    public void changeSensitivy(double percent){
        this.sensitivity = (percent/100.0);
    }

    public void moveFront(){
        Direction dir2d = new Direction(this.direction.getX(), this.direction.getY(), 0.0);
        double x = this.location.getX();
        double y = this.location.getY();
        x = x + (this.sensitivity * dir2d.getX());
        y = y + (this.sensitivity * dir2d.getY());

        this.location.setX(x);
        this.location.setY(y);
    }

    public void moveBack(){
        Direction dir2d = new Direction(this.direction.getX(), this.direction.getY(), 0.0);
        double x = this.location.getX();
        double y = this.location.getY();

        x = x - (this.sensitivity * dir2d.getX());
        y = y - (this.sensitivity * dir2d.getY());

        this.location.setX(x);
        this.location.setY(y);
    }

    public void moveLeft(){
        Direction dir2d = new Direction(this.direction.getX(), this.direction.getY(), 0.0);
        double x = this.location.getX();
        double y = this.location.getY();

        dir2d.rotationZ(90.0);

        x = x + (this.sensitivity * dir2d.getX());
        y = y + (this.sensitivity * dir2d.getY());

        this.location.setX(x);
        this.location.setY(y);
    }

    public void moveRight(){
        Direction dir2d = new Direction(this.direction.getX(), this.direction.getY(), 0.0);
        double x = this.location.getX();
        double y = this.location.getY();

        dir2d.rotationZ(270.0);

        x = x + (this.sensitivity * dir2d.getX());
        y = y + (this.sensitivity * dir2d.getY());

        this.location.setX(x);
        this.location.setY(y);
    }

    public void moveUp(){
        double z = this.location.getZ();
        z = z + (1.0 * this.sensitivity);
        this.location.setZ(z);
    }

    public void moveDown(){
        double z = this.location.getZ();
        z = z - (1.0 * this.sensitivity);
        this.location.setZ(z);
    }

    public JLabel takePicture(){
        return this.vision.takePicture();
    }

    public void horizontalRotation(double dAlpha){
        this.direction.addAlpha(dAlpha);
    }

    public void verticalRotation(double dBeta){
        this.direction.addBeta(dBeta);
    }

    public double getXDirection(){
        return this.direction.getX();
    }

    public double getYDirection(){
        return this.direction.getX();
    }

    public double getZDirection(){
        return this.direction.getZ();
    }

    public double getAlphaDirection(){
        return this.direction.getAlpha();
    }

    public double getBetaDirection(){
        return this.direction.getBeta();
    }
}