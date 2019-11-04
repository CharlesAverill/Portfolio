import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Polygon extends Canvas implements Sierpinski{
	ArrayList<Point> originalPoints = new ArrayList<Point>();
	//lastPoint also holds the list of all points to paint
	public ArrayList<Point> points = new ArrayList<Point>();
	Point lastChoice;
	public int iterations = 0;
	int n;
	
	public Polygon(int x) {
		Random rand = new Random();
		n = x;
		originalPoints.add(new Point(100, 100));
		for(int i = 1; i < x; i++) {
			originalPoints.add(new Point(rand.nextInt(800), rand.nextInt(600)));
		}
		points.addAll(originalPoints);
		points.add(originalPoints.get(0).getMidpoint(originalPoints.get(1)));
		lastChoice = originalPoints.get(1);
	}
	
	public void iterate() {
		iterations++;
		repaint();
		Random rand = new Random();
		int dice = rand.nextInt(n);
		Point p = points.get(points.size() - 1).getMidpoint(originalPoints.get(dice));
		points.add(p);
	}
	
	public void paint(Graphics g) {
		iterate();
		g.setColor(Color.WHITE);
		for(Point p : points) {
			g.drawOval(p.getX(), p.getY(), 1, 1);
		}
	}
}