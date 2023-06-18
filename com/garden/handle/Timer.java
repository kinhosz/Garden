package com.garden.handle;

import java.lang.Thread;

import com.garden.bits.Queue;
import com.garden.event.TimerEv;

public class Timer extends Thread{
    
    private Queue buffer;
    private String name;
    private boolean alive;
    private long delay;

    public Timer(Queue b, String name, long ms){
        super();

        this.buffer = b;
        this.name = name;
        this.alive = true;
        this.delay = ms;
    }

    @Override
    public void run(){

        while(this.alive){
            TimerEv t = new TimerEv(TimerEv.TIMER, this.name);
            this.buffer.push(t);

            try {
                Thread.sleep(this.delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void kill(){
        this.alive = false;
    }
}
