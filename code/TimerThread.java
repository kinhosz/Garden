import java.lang.Thread;

public class TimerThread extends Thread{

	Timer T;

	TimerThread(Timer t){
		this.T = t;
	}

	public void run(){
		T.run();
	}

	public static void main(String[] args){
		EventQueue Q = new EventQueue();
		Timer t = new Timer(1000,Q);
		TimerThread thread = new TimerThread(t);
		thread.start();
		try { Thread.sleep (5000); } catch (InterruptedException ex) {}
		t.kill();
		System.out.println(Q.size());
	}
}