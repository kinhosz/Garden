package handle;

import java.lang.Thread;

import struct.Buffer;
import event.TimerEv;

public class Timer extends Thread{
    
    private Buffer buffer;
    private String name;
    private boolean alive;
    private long delay;

    public Timer(Buffer b, String name, long ms){
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
            this.buffer.put(t);

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
