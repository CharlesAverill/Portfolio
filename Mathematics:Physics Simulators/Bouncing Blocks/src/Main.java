import javax.swing.*;
import java.awt.*;

public class Main {
	
	JFrame frame;
	View view = new View(new Block(1, -10, 100, 40), new Block(100, -3, 350, 75));
	
	public Main() throws InterruptedException {
		frame = new JFrame("Block Collision");
		frame.setSize(1600, 1200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setDoubleBuffered(true);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(new JButton("test"));
		
		view.add(bottomPanel, BorderLayout.NORTH);

		frame.setVisible(true);
		frame.add(view);
		frame.setVisible(true);
		Thread.sleep(500);
		
		
		
		run();
	}
	
	public void run() {
		long lastLoopTime = System.nanoTime();
	    final int TARGET_FPS = 120;
	    final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	    long lastFpsTime = 0;
		while(true) {
			long now = System.nanoTime();
	        long updateLength = now - lastLoopTime;
	        lastLoopTime = now;
	        double delta = updateLength / ((double)OPTIMAL_TIME);

	        lastFpsTime += updateLength;
	        if(lastFpsTime >= 1000000000){
	            lastFpsTime = 0;
	        }
	        
			view.update();
			view.repaint();

	        try{
	            long time = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
	            Thread.sleep(time);
	        }catch(Exception e){
	        	
	        }
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Main m = new Main();
	}
}
