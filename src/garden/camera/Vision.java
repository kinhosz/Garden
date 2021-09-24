package camera;
import javax.swing.JLabel;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import java.awt.Color;

import geometry.Point;
import geometry.Direction;

public class Vision {

    private int width;
    private int height;
    private BufferedImage image;
    private double verticalAngleRange = 120.0;
    private double horizontalAngleRange = 120.0;
    private Pool myPool;

    public Vision(int width, int height){
        this.width = width;
        this.height = height;
        this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        this.createBaseImage();
        this.myPool = null;
    }

    private void createBaseImage(){

        for(int i=0;i<this.height;i++){
            for(int j=0;j<this.width;j++){
                Color c = new Color(0,0,0);
                this.image.setRGB(j, i, c.getRGB());
            }
        }
    }

    public synchronized boolean locked(){

        boolean locked;

        if(this.myPool == null){
            locked = false;
        }
        else if(this.myPool.closed()){
            locked = false;
        }
        else{
            locked = true;
        }

        return locked;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.width;
    }

    public double getVerticalAngleRange(){
        return this.verticalAngleRange;
    }

    public double getHorizontalAngleRange(){
        return this.horizontalAngleRange;
    }

    private synchronized JLabel createImage(){

        JLabel context = new JLabel(new ImageIcon(this.image));
        return context;
    }

    public synchronized JLabel takePicture(Point p, Direction d) throws Exception{

        Point myPoint = new Point(p.getX(), p.getY(), p.getZ());
        Direction myDirection = new Direction(d.getX(), d.getY(), d.getZ());

        if(this.locked()){
            throw new Exception("The buffered Image is locked");
        }
        
        JLabel label = this.createImage();

        double v0 = -this.verticalAngleRange/2;
        double vf = this.verticalAngleRange/2;
        double h0 = -this.horizontalAngleRange/2;
        double hf = this.horizontalAngleRange/2;

        Pool pool = new Pool(this.getImage(), myPoint, myDirection);
        this.myPool = pool;

        pool.setAngleRange(v0, vf, h0, hf);
        pool.setShape(0, this.height-1, 0, this.width-1);
        pool.start();

        return label;
    }

    public BufferedImage getImage(){
        return this.image;
    }
}
