package handle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import bits.Queue;
import constants.KeyboardConst;
import event.KeyboardEv;

public class Keyboard extends KeyAdapter implements KeyboardConst{

    private Queue buffer;

    public Keyboard(Queue q){
        super();
        this.buffer = q;
    }

    @Override
    public void keyPressed(KeyEvent e){
		KeyboardEv ev = new KeyboardEv(KEY_PRESSED, e);
        this.buffer.push(ev);
	}

    @Override
	public void keyReleased(KeyEvent e){
		KeyboardEv ev = new KeyboardEv(KEY_RELEASED, e);
        this.buffer.push(ev);
	}

    @Override
	public void keyTyped(KeyEvent e){
		KeyboardEv ev = new KeyboardEv(KEY_TYPED, e);
        this.buffer.push(ev);
	}
}
