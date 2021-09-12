package handle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import bits.Queue;
import constants.MouseConst;
import event.MouseEv;

public class Motion extends MouseMotionAdapter implements MouseConst{
   
    private Queue buffer;

    public Motion(Queue q){
        super();
        this.buffer = q;
    }

    @Override
    public void mouseDragged(MouseEvent e){
        MouseEv ev = new MouseEv(Motion.MOUSE_DRAGGED, e);
        this.buffer.push(ev);
    }

    @Override
    public void mouseMoved(MouseEvent e){
        MouseEv ev = new MouseEv(Motion.MOUSE_MOVED, e);
        this.buffer.push(ev);
    }
}
