package com.garden.event;

public class TimerEv extends Event{

    private String name;
    private int type;

    public TimerEv(int type, String name){
        super();
        this.type = type;
        this.name = name;
    }

    public int getType(){
        return this.type;
    }

    public String getName(){
        return this.name;
    }
}
