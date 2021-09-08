package bits;
import event.Event;
public class Item{

    private Event data;

    private Item next;

    public Item(Event data){
        this.data = data;
        this.next = null;
    }

    public Item getNext(){
        return this.next;
    }

    public void setNext(Item next){
        this.next = next;
    }

    public Event getData(){
        return this.data;
    }
}