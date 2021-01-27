import javax.swing.JFrame;
import java.awt.event.KeyListener;

public class Garden{

	public static final int WIDTH = 300;
	public static final int HEIGHT = 300;

	public static JFrame FrameConfig(){

		JFrame window = new JFrame("Garden");
		window.setSize(WIDTH,HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationByPlatform(true);
		window.setVisible(true);

		return window;
	}

	public static void main(String[] args){

		JFrame window = FrameConfig();
		EventQueue fila = new EventQueue();
		Entrada teclado = new Entrada(fila);
		window.addKeyListener(teclado);
		CampoVisao cp = new CampoVisao(WITDH,HEIGHT);
	}
}