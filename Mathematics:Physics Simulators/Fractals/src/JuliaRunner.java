import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JuliaRunner extends JFrame
{
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	public JuliaRunner()
	{
		super("Julia Fractal");
		
		setSize(WIDTH,HEIGHT);

		getContentPane().add(new Julia(1, 0, 0) );
		
		setVisible(true);
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main( String args[] )
	{
		JuliaRunner run = new JuliaRunner();
	}
}