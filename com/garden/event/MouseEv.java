package com.garden.event;

import java.awt.event.MouseEvent;

public class MouseEv extends Event{
    
    private int button;
    private int clicks;
    private int x; // source component
    private int y;
    private int xOnScreen; // absolute horizontal x
    private int yOnScreen; // absolute vertical y
    private int type;

    public MouseEv(int type, MouseEvent e){
        super();
        this.clicks = e.getClickCount();
        this.x = e.getX();
        this.y = e.getY();
        this.xOnScreen = e.getXOnScreen();
        this.yOnScreen = e.getYOnScreen();
        this.type = type;

        switch(e.getButton()){
            case MouseEvent.NOBUTTON:
                this.button = Event.NOBUTTON;
            break;
            case MouseEvent.BUTTON1:
                this.button = Event.BUTTON1;
            break;
            case MouseEvent.BUTTON2:
                this.button = Event.BUTTON2;
            break;
            case MouseEvent.BUTTON3:
                this.button = Event.BUTTON3;
            break;
        }
    }

    public int getButton(){
        return this.button;
    }

    public int clicks(){
        return this.clicks;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getXOnScreen(){
        return this.xOnScreen;
    }

    public int getYOnScreen(){
        return this.yOnScreen;
    }

    public int type(){
        return this.type;
    }
}
