import java.awt.*;
import java.awt.image.*;
import java.util.concurrent.TimeUnit;

public class Hestia implements Runnable{
	
	public Display display;
	
	public Thread thread;
	
	public String title;
	public int width, height;
	
	public boolean running = false;
	
	public BufferStrategy bs;
	public Graphics g;
	
	public BufferedImage currentScreen;
	public static int screenX = 4;
	public static int screenY = 2;
	
	public static Protagonist p;
	
	public Hestia(String t, int w, int h) {
		title = t;
		width = w;
		height = h;
	}

	public void run(){
		init();
	    long lastLoopTime = System.nanoTime();
	    final int TARGET_FPS = 60;
	    final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	    long lastFpsTime = 0;
	    while(true){
	        long now = System.nanoTime();
	        long updateLength = now - lastLoopTime;
	        lastLoopTime = now;
	        double delta = updateLength / ((double)OPTIMAL_TIME);

	        lastFpsTime += updateLength;
	        if(lastFpsTime >= 1000000000){
	            lastFpsTime = 0;
	        }

	        update();
	        render();

	        try{
	            long gameTime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
	            Thread.sleep(gameTime);
	        }catch(Exception e){
	        }
	    }
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
		//Render background, then hidden items, then top level items
		g.drawImage(currentScreen, 0, 0, null);
		g.drawImage(p.currentSprite, p.x, p.y, null);
		//
		bs.show();
		g.dispose();
	}
	
	private void init() {
		Assets.init();
		display = new Display(title, width, height);
		p = new Protagonist(10, 5, Assets.player.down);
		currentScreen = Assets.Overworld.crop(screenX * 16 * 15, screenY * 16 * 10, 240, 160);
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
