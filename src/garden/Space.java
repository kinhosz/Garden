import java.awt.Color;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import handle.Keyboard;
import handle.Mouse;
import bits.Queue;
import handle.Motion;
import camera.Camera;
import event.Event;
import event.KeyboardEv;

public class Space{

    private Camera camera;

    public Space(int width, int height){
        this.camera = new Camera(width, height);
    }

    public Camera getCamera(){
        return this.camera;
    }

    public double getXCameraLocation(){
        return this.camera.getXLocation();
    }

    public double getYCameraLocation(){
        return this.camera.getYLocation();
    }

    public double getZCameraLocation(){
        return this.camera.getZLocation();
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Teste");
        Space space = new Space(300,300);
        Queue buffer = new Queue();
        Keyboard keyboard = new Keyboard(buffer);
        Mouse mouse = new Mouse(buffer);
        Motion motion = new Motion(buffer);

        frame.addKeyListener(keyboard);
        frame.addMouseListener(mouse);
        frame.addMouseMotionListener(motion);

        Camera camera = space.getCamera();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(camera.takePicture());
        frame.pack();
        frame.setVisible(true);

        while(true){

            if(buffer.size() == 0) continue;
            Event ev = buffer.front();
            buffer.pop();

            if(ev instanceof KeyboardEv){
                KeyboardEv e = (KeyboardEv) ev;
                if(e.getKeyChar() == 'f' || e.getKeyChar() == 'F'){
                    break;
                }
                else if(e.getKeyChar() == 'w'){
                    camera.moveFront();
                }
                else if(e.getKeyChar() == 'd'){
                    camera.moveRight();
                }
                else if(e.getKeyChar() == 's'){
                    camera.moveBack();
                }
                else if(e.getKeyChar() == 'a'){
                    camera.moveLeft();
                }
                System.out.println("----------------------");
                System.out.println(space.getXCameraLocation());
                System.out.println(space.getYCameraLocation());
                System.out.println(space.getZCameraLocation());
            }
        }
    }
}
