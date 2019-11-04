import java.awt.*;
import java.awt.image.*;

public class Caelum implements Runnable{
	
	private Display display;
	
	private Thread thread;
	
	public String title;
	public int width, height;
	
	private boolean running = false;
	
	private BufferStrategy bs;
	private Graphics g;
	
	public Caelum(String t, int w, int h) {
		title = t;
		width = w;
		height = h;
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
		g.clearRect(0, 0, width, height);
		//
		g.setColor(Color.RED);
		g.drawRect(0, 0, 50, 50);
		//
		bs.show();
		g.dispose();
	}
	
	private void init() {
		display = new Display(title, width, height);
		//sheet = new SpriteSheet(ImageLoader.loadImage("/textures/Adventurer-1/adventurer-v1.5-Sheet.png"));
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
