import java.awt.*;
import java.awt.image.*;

public class DesertWhale implements Runnable{
	
	private Display display;
	
	private Thread thread;
	
	public String title;
	public int WIDTH, HEIGHT;
	
	private boolean running = false;
	
	private BufferStrategy bs;
	private Graphics g;
	
	public DesertWhale(String t, int w, int h) {
		title = t;
		WIDTH = w;
		HEIGHT = h;
	}

	public void run() {
		init();
		while(running) {
			update();
			render();
		}
		stop();
	}
	
	private void update() {
		
	}
	
	private void render() {
		bs = display.canvas.getBufferStrategy();
		if(bs == null) {
			display.canvas.createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);
		//
		
		//
		bs.show();
		g.dispose();
	}
	
	private void init() {
		display = new Display(title, WIDTH, HEIGHT);
	}
	
	public synchronized void start() {
		if(running) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if(!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
