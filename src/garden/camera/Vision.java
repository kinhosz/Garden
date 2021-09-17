package camera;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class Vision {

    private int width;
    private int height;
    private BufferedImage image;

    public Vision(int width, int height){
        this.width = width;
        this.height = height;
        this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
    }

    synchronized public JLabel takePicture(){
        JLabel context = new JLabel(new ImageIcon(this.image));
        return context;
    }
}
