public class EventQueue{

	private int size;
	private Event head;
	private Event tail;


	EventQueue(){

		size = 0;
		head = null;
		tail = null;
	}

	private synchronized Event operator(String type, Event e){

		if(type == "get"){
			if(size == 0) return new Event("none","none");
			else return head;
		}
		else if(type == "pop"){
			if(size == 0) return null;
			else if(size == 1){
				head = null;
				tail = null;
			}
			else{
				head = head.next;
			}
			size--;
		}
		else if(type ==  "push"){
			if(size == 0){
				head = e;
				tail = e;
			}
			else{
				tail.next = e;
				tail = e;
			}
			size++;
		}

		return null;
	}

	public synchronized boolean isEmpty(){
		return size == 0;
	}

	public synchronized Event front(){
		Event e = operator("get",null);
		return e;
	}

	public synchronized void pop(){
		operator("pop",null);
	}

	public synchronized void push(Event e){
		operator("push",e);
	}

	public int size(){
		return size;
	}
}