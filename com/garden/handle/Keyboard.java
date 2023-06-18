package com.garden.handle;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import com.garden.bits.Queue;
import com.garden.constants.KeyboardConst;
import com.garden.event.KeyboardEv;

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
