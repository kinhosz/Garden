package camera;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import java.awt.Color;

import geometry.Point;
import geometry.Direction;

import bits.Queue;

public class Vision {

    private int width;
    private int height;
    private BufferedImage image;
    private double verticalAngleRange = 120.0;
    private double horizontalAngleRange = 120.0;
    private int[][][] matrix;

    public Vision(int width, int height){
        this.width = width;
        this.height = height;
        this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        this.matrix = new int[this.height][this.width][3];
    }

    private JLabel createImage(){

        for(int i=0;i<this.height;i++){
            for(int j=0;j<this.width;j++){
                Color color = new Color(this.matrix[i][j][0], this.matrix[i][j][1], this.matrix[i][j][2]);
                this.image.setRGB(j, i, color.getRGB());
            }
        }

        JLabel context = new JLabel(new ImageIcon(this.image));
        return context;
    }

    public JLabel takePicture(Point p, Direction d) throws InterruptedException{

        double verticalInitial = this.verticalAngleRange/2;
        double horizontalInitial = -this.horizontalAngleRange/2;

        double dVert = this.verticalAngleRange/this.height;
        double dHoriz = this.horizontalAngleRange/this.width;

        double v0 = verticalInitial;

        Queue consumers = new Queue();

        for(int i=0;i<this.height;i++){
            double h0 = horizontalInitial;
            for(int j=0;j<this.width;j++){
                Point point = new Point(p.getX(), p.getY(), p.getZ());
                Direction direction = new Direction(0.0, 1.0, 0.0);

                direction.eulerRotation(d.getAlpha() - 90.0, d.getBeta() + v0, h0);

                RayTracing rt = new RayTracing(point, direction, this.matrix, i,j);
                rt.run();

                consumers.push(rt);

                h0 = h0 + dHoriz;
            }

            v0 = v0 - dVert;
        }

        while(!consumers.empty()){
            RayTracing rt = (RayTracing)consumers.front();
            consumers.pop();

            rt.join();
        }

        return this.createImage();
    }
}
