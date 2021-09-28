package struct;

import bits.Queue;
import event.TimerEv;

public class Buffer extends Queue{

    private final int MAX_TIMER_EVENTS = 5;
    private int timerCount;

    public Buffer(){
        super();
        this.timerCount = 0;
    }

    public void put(Object o){

        if(this.timerCount < this.MAX_TIMER_EVENTS && o instanceof TimerEv){
            this.push(o);
            this.timerCount++;
        }
        else if(!(o instanceof TimerEv)){
            this.push(o);
        }
    }

    public Object get(){

        Object o = this.front();

        if(o instanceof TimerEv){
            this.timerCount--;
        }

        return o;
    }
}
