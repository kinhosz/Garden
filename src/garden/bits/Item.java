package bits;

public class Item{

    private Object data;

    private Item next;

    public Item(Object data){
        this.data = data;
        this.next = null;
    }

    public Item getNext(){
        return this.next;
    }

    public void setNext(Item next){
        this.next = next;
    }

    public Object getData(){
        return this.data;
    }
}