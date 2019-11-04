import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Triangle extends Canvas implements Sierpinski{
	//ot = one two
	private Point ot;
	//tf = three four
	private Point tf;
	//fs = five six
	private Point fs;
	//lastPoint also holds the list of all points to paint
	Point lastPoint;
	public ArrayList<Point> points = new ArrayList<Point>();
	public int iterations = 0;
	
	public Triangle() {
		Random rand = new Random();
		ot = new Point(rand.nextInt(400), rand.nextInt(300) + 300);
		tf = new Point(400, 150);
		fs = new Point(rand.nextInt(400) + 400, rand.nextInt(300) + 300);
		lastPoint = ot.getMidpoint(tf);
		
		points.add(ot);
		points.add(tf);
		points.add(fs);
		points.add(lastPoint);
	}
	
	public Triangle(int EQUILATERAL) {
		ot = new Point(200, 533);
		tf = new Point(700, 533);
		fs = new Point(450, 100);
		lastPoint = ot.getMidpoint(tf);
		
		points.add(ot);
		points.add(tf);
		points.add(fs);
		points.add(lastPoint);
	}
	
	public void iterate() {
		iterations++;
		repaint();
		Random rand = new Random();
		int dice = rand.nextInt(5) + 1;
		if(dice == 1 || dice == 2) {
			Point p = lastPoint.getMidpoint(ot);
			points.add(p);
			lastPoint = p;
		}
		if(dice == 3 || dice == 4) {
			Point p = lastPoint.getMidpoint(tf);
			points.add(p);
			lastPoint = p;
		}
		if(dice == 5 || dice == 6) {
			Point p = lastPoint.getMidpoint(fs);
			points.add(p);
			lastPoint = p;
		}
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		for(Point p : points) {
			g.drawOval(p.getX(), p.getY(), 1, 1);
		}
	}
}