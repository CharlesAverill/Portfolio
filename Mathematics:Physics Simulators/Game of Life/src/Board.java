import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Arrays;

public class Board extends JPanel{
	public static Square[][] board;
	public static Square[][] oldBoard;
	
	public int generation = 0;
	
	public int width;
	public int height;
	
	public int tileWidth;
	public int tileHeight;
	
	public Board(boolean[][] input) {
		board = new Square[input.length][input[0].length];
		oldBoard = new Square[input.length][input[0].length];
		width = board.length;
		height = board[0].length;
		
		tileWidth = Runner.frame.getWidth() / width;
		tileHeight = Runner.frame.getHeight() / height;
		
		for(int i = 0; i < input.length; i++) {
			for(int j = 0; j < input[i].length; j++) {
				board[i][j] = new Square(input[i][j], i * tileWidth, j * tileHeight - 4 * tileHeight, tileWidth, tileHeight);
				oldBoard[i][j] = board[i][j];
			}
		}
	}
	
	public void cloverLeaf(int x, int y) {
		int[][] input = new int[][] {
			{0, 0, 0, 1, 0, 1, 0, 0, 0},
			{0, 1, 1, 1, 0, 1, 1, 1, 0},
			{1, 0, 0, 0, 1, 0, 0, 0, 1},
			{1, 0, 1, 0, 0, 0, 1, 0, 1},
			{0, 1, 1, 0, 1, 0, 1, 1, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 1, 1, 0, 1, 0, 1, 1, 0},
			{1, 0, 1, 0, 0, 0, 1, 0, 1},
			{1, 0, 0, 0, 1, 0, 0, 0, 1},
			{0, 1, 1, 1, 0, 1, 1, 1, 0},
			{0, 0, 0, 1, 0, 1, 0, 0, 0}
		};
		x = clickToTileWidth(x);
		y = clickToTileHeight(y);
		int w = input[0].length;
		int h = input.length;
		for(int i = x - w; i < x + w; i++) {
			for(int j = y - h; j < y + h; j++) {
				if(withinBounds(i, j)) {
					System.out.println("" + (i - (x - w)) + ", " + (j - (y - h)));
					if(input[i - (x - w)][j - (y - h)] == 0) {
						board[i][j].alive = false;
					}
					else board[i][j].alive = true;
				}
			}
		}
	}
	
	public void createLife(int x, int y) {
		x = clickToTileWidth(x);
		y = clickToTileHeight(y);
		board[x][y].alive = true;
	}
	
	public void clickBomb(int x, int y) {
		x = clickToTileWidth(x);
		y = clickToTileHeight(y);
		int radius = 15;
		for(int i = x - radius; i < x + radius; i++) {
			for(int j = y - radius; j < y + radius; j++) {
				if(withinBounds(i, j)) {
					board[i][j].alive = false;
				}
			}
		}
	}
	
	public int clickToTileHeight(int n) {
		for(int i = 0; i < Runner.frame.getHeight(); i += tileHeight) {
			if(n >= i && n <= i + tileHeight) {
				return (i / tileHeight) + 1;
			}
		}
		return -1;
	}
	
	public int clickToTileWidth(int n) {
		for(int i = 0; i < Runner.frame.getWidth(); i += tileWidth) {
			if(n >= i && n <= i + tileWidth) {
				return i / tileWidth;
			}
		}
		return -1;
	}
	
	private boolean withinBounds(int x, int y) {
		if(x >= 0 && y >= 0) {
			if(x < board.length && y < board[0].length) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean allDead() {
		for(Square[] a : board) {
			for(Square sqr : a) {
				if(sqr.alive) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void paint(Graphics g) {
		for(Square[] a : board) {
			for(Square s : a) {
				if(s.alive) {
					g.setColor(Color.BLACK);
					g.fillRect(s.x, s.y, s.width, s.height);
				}
				else {
					g.setColor(Color.BLACK);
					g.drawRect(s.x, s.y, s.width, s.height);
				}
			}
		}
	}
	
	public void update() {
		generation++;
		Square[][] oldBoard = board;
		for(int r = 0; r < board.length; r++) {
			for(int c = 0; c < board[r].length; c++) {
				Square[] neighbors = getNeighbors(r, c);
				
				int liveNeighbors = getLiveNeighbors(neighbors);
				
				if(board[r][c].alive) {
					//rule 1
					if(liveNeighbors < 2) {
						board[r][c].alive = false;
					}
					//rule 2
					if(liveNeighbors == 2 || liveNeighbors == 3) {
						board[r][c].alive = true;
					}
					//rule 3
					if(liveNeighbors > 3) {
						board[r][c].alive = false;
					}
				}
				else if(liveNeighbors == 3) {
					board[r][c].alive = true;
				}
			}
		}
		oldBoard = board;
	}
	
	public int getLiveNeighbors(Square[] neighbors) {
		int output = 0;
		for(Square s : neighbors) {
			if(s != null) {
				if(s.alive) {
					output++;
				}
			}
		}
		return output;
	}
	
	public Square[] getNeighbors(int i, int j) {
		Square[] output = new Square[8];
		int count = 0;
		int rowLimit = board.length-1;
		int columnLimit = board[0].length-1;

		for(int x = Math.max(0, i-1); x <= Math.min(i+1, rowLimit); x++) {
		    for(int y = Math.max(0, j-1); y <= Math.min(j+1, columnLimit); y++) {
		        if(x != i || y != j) {
		        	output[count++] = oldBoard[x][y];
		        }
		    }
		}
		return output;
	}
	
	public String toString() {
		String output = "";
		for(Square[] a : board) {
			for(Square s : a) {
				output += s;
			}
			output += "\n";
		}
		return output;
	}
}
