package handle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import bits.Queue;
import constants.KeyboardConst;
import event.KeyboardEv;

public class Keyboard extends KeyAdapter implements KeyboardConst{

    private Queue buffer;

    public Keyboard(Queue b){
        super();
        this.buffer = b;
    }

    @Override
    public void keyPressed(KeyEvent e){
        KeyboardEv ev = new KeyboardEv(Keyboard.KEY_PRESSED, e);
        this.buffer.push(ev);
    }

    @Override
    public void keyReleased(KeyEvent e){
        KeyboardEv ev = new KeyboardEv(Keyboard.KEY_RELEASED, e);
        this.buffer.push(ev);
    }

    @Override
    public void keyTyped(KeyEvent e){
        KeyboardEv ev = new KeyboardEv(Keyboard.KEY_TYPED, e);
        this.buffer.push(ev);
    }
}
