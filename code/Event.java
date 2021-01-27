public class Event{

	String type;
	String data;
	Event next;

	Event(String type, String data){
		this.type = type;
		this.data = data;
	}
}