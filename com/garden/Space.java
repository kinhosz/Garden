package com.garden;

import java.awt.AWTException;
import javax.swing.JFrame;
import com.garden.handle.Keyboard;
import com.garden.handle.Mouse;
import com.garden.bits.Queue;
import com.garden.handle.Motion;
import com.garden.camera.Camera;
import com.garden.event.Event;
import com.garden.event.KeyboardEv;
import com.garden.event.MouseEv;
import com.garden.event.TimerEv;
import com.garden.handle.TimerAsync;

public class Space{

    private Camera camera;

    public Space(int height){
        this.camera = new Camera(height);
    }

    public Camera getCamera(){
        return this.camera;
    }

    public void bye(){
        this.camera.kill();
    }

    public static void main(String[] args) throws AWTException{
        JFrame frame = new JFrame("Teste");
        Space space = new Space(240);
        Queue buffer = new Queue();
        Keyboard keyboard = new Keyboard(buffer);
        Mouse mouse = new Mouse(buffer);
        Motion motion = new Motion(buffer, frame);
        TimerAsync timer = new TimerAsync(buffer, "display");
        
        Camera camera = space.getCamera();

        motion.lockCursor();
        motion.setCursorPosition(camera.getWidth()/2, camera.getHeight()/2);

        frame.addKeyListener(keyboard);
        frame.addMouseListener(mouse);
        frame.addMouseMotionListener(motion);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(camera.takePicture());
        System.out.println("hello");
        frame.pack();
        System.out.println("hello");
        frame.setVisible(true);

        System.out.println(frame.getLocationOnScreen().getX());
        System.out.println(frame.getLocationOnScreen().getY());

        timer.trigger();

        System.out.println("entrando no looping");

        while(true){

            Event ev;

            if(buffer.size() == 0) continue;
            ev = (Event)buffer.front();
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
            }
            else if(ev instanceof MouseEv){

                MouseEv e = (MouseEv) ev;
                camera.horizontalRotation(0.05 * (camera.getWidth()/2 - e.getX()));
                camera.verticalRotation(0.05 * (camera.getHeight()/2 - e.getY()));
            }
            else if(ev instanceof TimerEv){

                if(camera.updated()){
                    Event x = new Event();
                    frame.getContentPane().add(camera.takePicture());
                    frame.pack();
                    frame.setVisible(true);

                    System.out.println("FPS = " + (1000/x.elapsed()));
                }

                timer.trigger();
            }
        }

        space.bye();
    }
}
