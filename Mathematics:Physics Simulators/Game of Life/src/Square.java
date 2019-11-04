import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class Square{
	public boolean alive;
	public int width;
	public int height;
	public int x;
	public int y;
	
	public Square() {
		alive = false;
		width = 20;
		height = 20;
		x = 0;
		y = 0;
	}
	
	public Square(boolean b, int q, int r, int w, int h) {
		alive = b;
		x = q;
		y = r;
		width = w;
		height = h;
	}
	
	public String toString() {
		if(alive) {
			return "1";
		}
		return "0";
	}
}
