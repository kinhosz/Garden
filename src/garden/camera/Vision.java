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
    private boolean locked;

    public Vision(int width, int height){
        this.width = width;
        this.height = height;
        this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
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

    private synchronized boolean getStatus(){
        return this.locked;
    }

    private synchronized void lock(){
        this.locked = true;
    }

    private synchronized void unlock(){
        this.locked = false;
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

    public synchronized void changePicture(int[][][] matrix){
        
        for(int i=0;i<this.height;i++){
            for(int j=0;j<this.width;j++){
                Color c = new Color(matrix[i][j][0], matrix[i][j][1], matrix[i][j][2]);
                this.image.setRGB(j, i, c.getRGB());
            }
        }

        this.unlock();
    }

    public JLabel takePicture(Point p, Direction d){

        if(!this.getStatus()){
            this.lock();
            Picture picture = new Picture(p, d, this);
            picture.start();
        }

        return this.createImage();
    }
}
