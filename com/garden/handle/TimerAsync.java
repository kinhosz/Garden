package com.garden.handle;

import com.garden.bits.Queue;
import com.garden.event.TimerEv;

public class TimerAsync{
    
    private Queue buffer;
    private String name;

    public TimerAsync(Queue b, String name){

        this.buffer = b;
        this.name = name;
    }

    public void trigger(){
        TimerEv t = new TimerEv(TimerEv.TIMER, this.name);
        this.buffer.push(t);
    }
}
