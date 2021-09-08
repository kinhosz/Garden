import java.awt.Color;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import handle.Keyboard;
import handle.Mouse;
import bits.Queue;
import handle.Motion;

public class Space{

    private BufferedImage image;
    private JLabel context;

    public Space(int width, int height){
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public void setPixel(int x, int y, int r, int g, int b){
        Color temp = new Color(r,g,b);
        image.setRGB(x, y, temp.getRGB());
    }

    public JLabel getContext(){
        context = new JLabel(new ImageIcon(this.image));
        return context;
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("teste");
        Space space = new Space(300,300);
        Queue buffer = new Queue();
        Mouse mouse = new Mouse(buffer);
        Keyboard keyboard = new Keyboard(buffer);
        Motion motion = new Motion(buffer);

        frame.addKeyListener(keyboard);
        frame.addMouseListener(mouse);
        frame.addMouseMotionListener(motion);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(space.getContext());
        frame.pack();
        frame.setVisible(true);
        for(int i=0;i<255;i++){
            for(int j=0;j<255;j++){
                for(int k=0;k<255;k++){
                    
                    //frame.remove(space.getContext());
                    for(int x=0;x<300;x++){
                        for(int y=0;y<300;y++){
                            space.setPixel(x, y, i, j, k);
                        }
                    }
                    frame.getContentPane().add(space.getContext());
                    frame.pack();
                    frame.setVisible(true);
                }
            }
        }
    }
}
