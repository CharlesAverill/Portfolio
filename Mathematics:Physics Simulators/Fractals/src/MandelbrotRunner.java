import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MandelbrotRunner extends JFrame
{
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	public MandelbrotRunner()
	{
		super("Mandelbrot Fractal");
		
		setSize(WIDTH,HEIGHT);
		
		long co = 10;
		
		getContentPane().add(new Mandelbrot(250, 500, 300, co) );
		
		setVisible(true);
		
		//while(true) {
			
			//co += 10;
			//getContentPane().add(new Mandelbrot(250, 500, 300, co));
			//setVisible(true);
			//System.out.println(co);
			//setBackground(Color.WHITE);
		//}
		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}

	public static void main( String args[] )
	{
		MandelbrotRunner run = new MandelbrotRunner();
	}
}