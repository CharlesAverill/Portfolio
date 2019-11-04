import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Runner extends JFrame{
	
	boolean playing = true;
	Sierpinski now;
	
	public Runner() throws IOException, InterruptedException{
		super("Sierpinski");
		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				/*
				if(key == e.VK_SPACE) {
					playing = !playing;
					try {
						play(now);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				*/
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.BLACK);
		Triangle t = new Triangle();
		Polygon p = new Polygon(4);
		String input = "t";
		if(input.equals("t")) {
			getContentPane().add(t);
			setVisible(true);
			now = t;
			play(now);
		}
		if(input.equals("p")) {
			getContentPane().add(p);
			setVisible(true);
			now = p;
			play(now);
		}
	}
	
	public void play(Sierpinski o) throws InterruptedException {
		while(playing) {
			System.out.println(playing);
			o.iterate();
			Thread.sleep(15);
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException{
		Runner r = new Runner();
	}
}
