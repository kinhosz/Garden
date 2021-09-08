package handle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import bits.Queue;
import event.MouseEv;
import constants.MouseConst;

public class Mouse extends MouseAdapter implements MouseConst{

    private Queue buffer;

    public Mouse(Queue q){
        this.buffer = q;
    }

    @Override
    public void mouseClicked(MouseEvent e){
        MouseEv ev = new MouseEv(MOUSE_CLICKED, e);
        this.buffer.push(ev);
    }

    @Override
    public void mouseEntered(MouseEvent e){
        MouseEv ev = new MouseEv(MOUSE_ENTERED, e);
        this.buffer.push(ev);
    }

    @Override
    public void mouseExited(MouseEvent e){
        MouseEv ev = new MouseEv(MOUSE_EXITED, e);
        this.buffer.push(ev);
    }

    @Override
    public void mousePressed(MouseEvent e){
        MouseEv ev = new MouseEv(MOUSE_PRESSED, e);
        this.buffer.push(ev);
    }

    @Override
    public void mouseReleased(MouseEvent e){
        MouseEv ev = new MouseEv(MOUSE_RELEASED, e);
        this.buffer.push(ev);
    }
}
