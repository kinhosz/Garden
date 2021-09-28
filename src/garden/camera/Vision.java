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
    private double verticalAngleRange = 60.0;
    private double horizontalAngleRange = 90.0;
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
        return this.height;
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

        this.splitImage(4, p, d);
        
        JLabel label = this.createImage();
        
        return label;
    }

    private void splitImage(int threads, Point p, Direction d) throws Exception{

        if(threads > 300){
            throw new Exception("How many threads!");
        }

        this.locked = true;

        int block = (int)Math.sqrt(threads);

        Point myPoint = new Point(p.getX(), p.getY(), p.getZ());
        Direction myDirection = new Direction(d.getAlpha(), d.getBeta());

        int[] pixels = ((DataBufferInt) this.getImage().getRaster().getDataBuffer()).getData();

        int dx = (int)(this.height + block - 1)/block;
        int dy = (int)(this.width + block - 1)/block;

        Queue party = new Queue();

        for(int x=0;x<this.height;x+=dx){

            int xf = Math.min(x + dx, this.height) - 1;
            if(xf < x) continue;

            for(int y=0;y<this.width;y+=dy){

                int yf = Math.min(y + dy, this.width) - 1;

                if(yf < y) continue;

                Pool pool = new Pool(pixels, myPoint, myDirection, this.height, this.width);
                pool.setAngleRange(this.verticalAngleRange, this.horizontalAngleRange);
                pool.setShape(x, xf, y, yf);
                pool.start();

                party.push(pool);
            }
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
