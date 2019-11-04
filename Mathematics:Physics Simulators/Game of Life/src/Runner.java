import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;

public class Runner {
	
	public static JFrame frame;
	public static String mode = "BOMB";
	public static Board board;
	
	public static boolean playing = true;
	
	public static void main(String[] args) throws IOException, InterruptedException{
		frame = new JFrame("Conway\'s Game of Life");
		frame.setSize(800, 800);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(mode.equals("BOMB")) {
					board.clickBomb(e.getX(), e.getY());
				}
				if(mode.equals("BIRTH")) {
					board.createLife(e.getX(), e.getY());
				}
				if(mode.equals("CLOVERLEAF")) {
					board.cloverLeaf(e.getX(),e.getY());
				}
				board.repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == 'B') {
					mode = "BOMB";
					System.out.println("Bomb mode");
				}
				if(e.getKeyChar() == 'N') {
					mode = "BIRTH";
					System.out.println("Birth mode");
				}
				if(e.getKeyChar() == 'P') {
					playing = !playing;
					if(playing) {
						System.out.println("Resuming");
					}
					else System.out.println("Paused");
				}
				
				if(e.getKeyChar() == 'c') {
					mode = "CLOVERLEAF";
					System.out.println(mode);
				}
				board.repaint();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		

		board = generateRandomBoard(100, 100);
		board.setDoubleBuffered(true);
		frame.add(board);

		frame.setVisible(true);
		
		while(true) {
			System.out.println(board.generation);
			if(playing) {
				board.repaint();
				board.update();
				Thread.sleep(50);
			}
		}
	}
	
	public static Board generateBlankBoard(int l, int w) {
		return new Board(new boolean[l][w]);
	}
	
	public static Board generateRandomBoard(int l, int w) {
		boolean[][] tempBoard = new boolean[l][w];
		Random rand = new Random();
		
		for(int i = 0; i < l; i++) {
			for(int j = 0; j < w; j++) {
				if(rand.nextInt(10000) % 13 == 0) {
					tempBoard[i][j] = true;
				}
			}
		}
		
		return new Board(tempBoard);
	}
}
