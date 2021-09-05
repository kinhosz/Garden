package bits;
import bits.Event;
import bits.Item;

public class Queue{

    private int size;
    private Item head;
    private Item tail;

    public Queue(){
        size = 0;
        head = null;
        tail = null;
    }

    synchronized public void pop(){

        if(this.size > 0){
            this.size--;
            this.head = this.head.getNext();
        }
    }

    synchronized public Event front(){

        if(this.size == 0) return null;

        return this.head.getData();
    }

    synchronized public void push(Event e){
        
        this.size++;
        Item it = new Item(e);

        if(this.size == 1){
            this.head = it;
            this.tail = it;
        }
        else{
            this.tail.setNext(it);
            this.tail = this.tail.getNext();
        }
    }

    synchronized public boolean empty(){
        return this.size == 0;
    }

    synchronized public int size(){
        return this.size;
    }
}