package camera;
import javax.swing.JLabel;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.image.DataBufferInt;

import geometry.Point;
import geometry.Direction;
import bits.Queue;

public class Vision {

    private int width;
    private int height;
    private BufferedImage image;
    private double verticalAngleRange = 120.0;
    private double horizontalAngleRange = 120.0;
    private boolean locked;

    public Vision(int width, int height){
        this.width = width;
        this.height = height;
        this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        this.image.setAccelerationPriority(0); // idk
        this.createBaseImage();
        this.locked = false;
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

        return this.locked;
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

        if(this.locked()){
            throw new Exception("The buffered Image is locked");
        }

        JLabel label = this.createImage();

        this.splitImage(1, p, d);

        return label;
    }

    private void splitImage(int threads, Point p, Direction d) throws Exception{

        if(threads > 100){
            throw new Exception("How many threads!");
        }

        this.locked = true;

        int block = (int)Math.sqrt(threads);

        Point myPoint = new Point(p.getX(), p.getY(), p.getZ());
        Direction myDirection = new Direction(d.getX(), d.getY(), d.getZ());

        double v0 = -this.verticalAngleRange/2;
        double vf = this.verticalAngleRange/2;
        double h0 = -this.horizontalAngleRange/2;
        double hf = this.horizontalAngleRange/2;

        int[] pixels = ((DataBufferInt) this.getImage().getRaster().getDataBuffer()).getData();

        int dx = (int)(this.height + block - 1)/block;
        int dy = (int)(this.width + block - 1)/block;

        double dv = (vf - v0)/this.height;
        double dh = (hf - h0)/this.width;

        Queue party = new Queue();

        double vf_copy = vf;

        for(int x=0;x<this.height;x+=dx){
            double h0_copy = h0;
            int xf = Math.min(x + dx, this.height) - 1;
            if(xf < x) continue;
            
            for(int y=0;y<this.width;y+=dy){

                int yf = Math.min(y + dy, this.width) - 1;

                if(yf < y) continue;

                double hf_copy = h0_copy + dh*(yf - y);
                double v0_copy = vf_copy - dv*(xf - x);

                Pool pool = new Pool(pixels, myPoint, myDirection, this.height, this.width);
                pool.setAngleRange(v0_copy, vf_copy, h0_copy, hf_copy);
                pool.setShape(x, xf, y, yf);
                pool.start();

                party.push(pool);

                h0_copy = hf_copy + dh*(yf - y);
            }
            vf_copy = vf_copy - dv*(xf - x);
        }

        while(!party.empty()){
            Pool pool = (Pool)party.front();
            party.pop();

            pool.join();
        }

        this.locked = false;
    }

    public BufferedImage getImage(){
        return this.image;
    }
}
