package com.garden.event;

import java.awt.event.KeyEvent;

public class KeyboardEv extends Event{

    private char keyChar;
    private int keyCode;
    private int type;

    public KeyboardEv(int type, KeyEvent e){
        super();
        this.keyChar = e.getKeyChar();
        this.keyCode = e.getKeyCode();
        this.type = type;
    }

    public int getType(){
        return this.type;
    }

    public int getKeyCode(){
        return this.keyCode;
    }

    public char getKeyChar(){
        return this.keyChar;
    }
}
