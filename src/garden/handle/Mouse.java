package handle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import struct.Buffer;
import event.MouseEv;
import constants.MouseConst;

public class Mouse extends MouseAdapter implements MouseConst{

    private Buffer buffer;

    public Mouse(Buffer b){
        super();
        this.buffer = b;
    }

    @Override
    public void mouseClicked(MouseEvent e){
        MouseEv ev = new MouseEv(Mouse.MOUSE_CLICKED, e);
        this.buffer.put(ev);
    }

    @Override
    public void mouseEntered(MouseEvent e){
        MouseEv ev = new MouseEv(Mouse.MOUSE_ENTERED, e);
        this.buffer.put(ev);
    }

    @Override
    public void mouseExited(MouseEvent e){
        MouseEv ev = new MouseEv(Mouse.MOUSE_EXITED, e);
        this.buffer.put(ev);
    }

    @Override
    public void mousePressed(MouseEvent e){
        MouseEv ev = new MouseEv(Mouse.MOUSE_PRESSED, e);
        this.buffer.put(ev);
    }

    @Override
    public void mouseReleased(MouseEvent e){
        MouseEv ev = new MouseEv(Mouse.MOUSE_RELEASED, e);
        this.buffer.put(ev);
    }
}
