package handle;
import constants.MouseConst;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import bits.Queue;
import event.MouseEv;

public class Motion extends MouseMotionAdapter implements MouseConst{
   
    private Queue buffer;

    public Motion(Queue q){
        super();
        this.buffer = q;
    }

    @Override
    public void mouseDragged(MouseEvent e){
        MouseEv ev = new MouseEv(MOUSE_DRAGGED, e);
        this.buffer.push(ev);
    }

    @Override
    public void mouseMoved(MouseEvent e){
        MouseEv ev = new MouseEv(MOUSE_MOVED, e);
        this.buffer.push(ev);
    }
}
