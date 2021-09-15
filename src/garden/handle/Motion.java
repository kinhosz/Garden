package handle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;

import java.awt.AWTException;
import java.awt.Robot;

import bits.Queue;
import constants.MouseConst;
import event.MouseEv;

public class Motion extends MouseMotionAdapter implements MouseConst{
   
    private Queue buffer;
    private JFrame frame;
    private boolean locked;
    private Robot robot;
    private int posX;
    private int posY;

    public Motion(Queue q, JFrame f) throws AWTException{
        super();
        this.buffer = q;
        this.frame = f;
        this.locked = false;
        this.robot = new Robot();
    }

    @Override
    public void mouseDragged(MouseEvent e){
        MouseEv ev = new MouseEv(Motion.MOUSE_DRAGGED, e);
        this.buffer.push(ev);
    }

    @Override
    public void mouseMoved(MouseEvent e){
        MouseEv ev = new MouseEv(Motion.MOUSE_MOVED, e);

        if(this.locked && e.getX() == this.posX && e.getY() == this.posY) return ;

        this.buffer.push(ev);

        if(this.locked) this.restartCursorPosition();
    }

    public void setCursorPosition(int x,int y){
        this.posX = x;
        this.posY = y;
    }

    private void restartCursorPosition(){
        int x = (int)this.frame.getLocationOnScreen().getX();
        int y = (int)this.frame.getLocationOnScreen().getY();

        x = x + this.posX;
        y = y + this.posY;

        this.robot.mouseMove(x, y);
    }

    public void lockCursor(){
        this.locked = true;
    }

    public void unlockCursor(){
        this.locked = false;
    }
}
