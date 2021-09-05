package bits;
public class Event{

    private String type;
    private String name;
    private String data;

    public Event(String name, String type, String data){
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public String getType(){
        return this.type;
    }

    public String getName(){
        return this.name;
    }

    public String getData(){
        return this.data;
    }
}