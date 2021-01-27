import java.lang.Thread;

public class Timer{

	private int taxa;
	private boolean stop;
	EventQueue q;

	Timer(int taxa,EventQueue queue){

		this.taxa = taxa;
		stop = false;
		this.q = queue;
	}

	public void run(){

		while(!stop){
			try { Thread.sleep (taxa); } catch (InterruptedException ex) {}
			q.push(new Event("timer","none"));
		}
	}

	public void kill(){
		stop = true;
	}
}