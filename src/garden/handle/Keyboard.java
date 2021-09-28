package handle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import struct.Buffer;
import constants.KeyboardConst;
import event.KeyboardEv;

public class Keyboard extends KeyAdapter implements KeyboardConst{

    private Buffer buffer;

    public Keyboard(Buffer b){
        super();
        this.buffer = b;
    }

    @Override
    public void keyPressed(KeyEvent e){
        KeyboardEv ev = new KeyboardEv(Keyboard.KEY_PRESSED, e);
        this.buffer.put(ev);
    }

    @Override
    public void keyReleased(KeyEvent e){
        KeyboardEv ev = new KeyboardEv(Keyboard.KEY_RELEASED, e);
        this.buffer.put(ev);
    }

    @Override
    public void keyTyped(KeyEvent e){
        KeyboardEv ev = new KeyboardEv(Keyboard.KEY_TYPED, e);
        this.buffer.put(ev);
    }
}
