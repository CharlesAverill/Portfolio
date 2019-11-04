import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class Display {
	
	public JFrame frame;
	private int WIDTH, HEIGHT;
	private String title;
	
	private boolean uppress = false;
	private boolean leftpress = false;
	private boolean rightpress = false;
	private boolean downpress = false;
	
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
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_W) {
					uppress = false;
				}
				if(e.getKeyCode() == KeyEvent.VK_A) {
					leftpress = false;
				}
				if(e.getKeyCode() == KeyEvent.VK_S) {
					downpress = false;
				}
				if(e.getKeyCode() == KeyEvent.VK_D) {
					rightpress = false;
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println(e);
				if(e.getKeyCode() == KeyEvent.VK_W) {
					uppress = true;
					Hestia.p.currentSprite = Assets.player.up;
					while(uppress) {
						Hestia.p.move(0, 5);
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_A) {
					leftpress = true;
					Hestia.p.currentSprite = Assets.player.left;
					while(leftpress) {
						Hestia.p.move(3, 5);
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_S) {
					downpress = true;
					Hestia.p.currentSprite = Assets.player.down;
					while(downpress) {
						Hestia.p.move(1, 5);
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_D) {
					rightpress = true;
					Hestia.p.currentSprite = Assets.player.right;
					while(rightpress) {
						Hestia.p.move(2, 5);
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_W) {
					uppress = false;
				}
				if(e.getKeyCode() == KeyEvent.VK_A) {
					leftpress = false;
				}
				if(e.getKeyCode() == KeyEvent.VK_S) {
					downpress = false;
				}
				if(e.getKeyCode() == KeyEvent.VK_D) {
					rightpress = false;
				}
				System.out.println(e);
			}
			
		});
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		canvas.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		canvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		frame.add(canvas);
		
		frame.pack();
		
		frame.setVisible(true);
	}
	
	public static BufferedImage getScaledImage(Image srcImg, double scale){
		int width = srcImg.getWidth(null) * (int)scale;
		int height = srcImg.getHeight(null) * (int)scale;
	    BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, width, height, null);
	    g2.dispose();
	    return resizedImg;
	}
}
