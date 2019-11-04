import java.awt.*;
import java.util.*;

public class Point{
	private int x;
	private int y;
	
	public Point(int a, int b) {
		x = a;
		y = b;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Point getMidpoint(Point p) {
		return new Point((int)((x + p.getX()) / 2), (int)((y + p.getY()) / 2));
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
