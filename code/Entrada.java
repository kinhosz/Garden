import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class Entrada extends KeyAdapter{

	EventQueue buffer;

	Entrada(EventQueue eq){

		buffer = eq;
	}

	public void keyPressed(KeyEvent e){
		char letra = e.getKeyChar();
		String s = "";
		s += letra;
		buffer.push(new Event("keyboard", s));
	}

	public void keyRelesead(KeyEvent e){
		char letra = e.getKeyChar();
	}

	public void keyTyped(KeyEvent e){
		char letra = e.getKeyChar();
	}
}