import java.awt.*;
import javax.swing.*;

public class Display {
	
	public JFrame frame;
	private int WIDTH, HEIGHT;
	private String title;
	
	public Canvas canvas;
	
	public Display(String t, int w, int h) {
		title = t;
		WIDTH = w;
		HEIGHT = h;
		
		init();
	}
	
	private void init() {
		frame = new JFrame(title);
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		canvas.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		canvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		frame.add(canvas);
		
		frame.pack();
		
		frame.setVisible(true);
	}
}
