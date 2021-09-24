import java.awt.AWTException;
import javax.swing.JFrame;
import handle.Keyboard;
import handle.Mouse;
import bits.Queue;
import handle.Motion;
import camera.Camera;
import event.Event;
import event.KeyboardEv;
import event.MouseEv;
import event.TimerEv;
import handle.Timer;

import event.Event;

public class Space{

    private Camera camera;

    public Space(int width, int height){
        this.camera = new Camera(width, height);
    }

    public Camera getCamera(){
        return this.camera;
    }

    public static void main(String[] args) throws AWTException{
        JFrame frame = new JFrame("Teste");
        Space space = new Space(600,480);
        Queue buffer = new Queue();
        Keyboard keyboard = new Keyboard(buffer);
        Mouse mouse = new Mouse(buffer);
        Motion motion = new Motion(buffer, frame);
        Timer timer = new Timer(buffer, "display", 100);
        
        motion.lockCursor();
        motion.setCursorPosition(300, 240);

        frame.addKeyListener(keyboard);
        frame.addMouseListener(mouse);
        frame.addMouseMotionListener(motion);

        Camera camera = space.getCamera();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(camera.takePicture());
        frame.pack();
        frame.setVisible(true);

        System.out.println(frame.getLocationOnScreen().getX());
        System.out.println(frame.getLocationOnScreen().getY());

        timer.start();

        Event event = new Event();

        while(true){

            Event ev;

            if(buffer.size() == 0) continue;
            ev = (Event)buffer.front();
            buffer.pop();

            if(ev instanceof KeyboardEv){

                //System.out.println("keyboard = " + ev.elapsed());

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
            }
            else if(ev instanceof MouseEv){

                MouseEv e = (MouseEv) ev;
                camera.horizontalRotation(0.5 * (150 - e.getX()));
                camera.verticalRotation(0.5 * (150 - e.getY()));
            }
            else if(ev instanceof TimerEv){

                if(camera.updated()){
                    frame.getContentPane().add(camera.takePicture());
                    frame.pack();
                    frame.setVisible(true);

                    System.out.println("flip = " + event.elapsed());
                    event = new Event();
                }
            }
        }

        timer.kill();
    }
}
