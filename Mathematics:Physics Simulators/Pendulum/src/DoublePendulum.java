import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.awt.*;
import javax.swing.*;

public class DoublePendulum extends JPanel{
	
	public static ArrayList<Point> paintPoints;
	double g = .75;
	int length1 = 100;
	int length2 = 100;
	int mass1 = 10;
	int mass2 = 10;
	double angle1 = 0;
	double angle2 = 0;
	double v1 = 0;
	double v2 = 0;
	int radius = 10;
	int xOffset = 300;
	int yOffset = 250;

	int x1 = 0;
	int y1 = 0;
	int x2 = 0;
	int y2 = 0;
	int lastx2 = 0;
	int lasty2 = 0;
	
	public DoublePendulum() {
		
		Random rand = new Random();
		angle1 = rand.nextInt(length1) + yOffset;
		angle2 = rand.nextInt(length2) + yOffset;
		paintPoints = new ArrayList<Point>();
		
	}
	
	public void integrate(){
		double num1 = -g * (2 * mass1 + mass2) * Math.sin(angle1);
		double num2 = -mass2 * g * Math.sin(angle1 - 2 * angle2);
		double num3 = -2 * Math.sin(angle1 - angle2) * mass2;
		double num4 = v2 * v2 * length2 + v1 * v1 * length1 * Math.cos(angle1 - angle2);
		double den = length1 * (2 * mass1 + mass2 - mass2 * Math.cos(2 * angle1 - 2 * angle2));
		double a1_a = (num1 + num2 + num3 * num4) / den;

		num1 = 2 * Math.sin(angle1 - angle2);
		num2 = (v1 * v1 * length1 * (mass1 + mass2));
		num3 = g * (mass1 + mass2) * Math.cos(angle1);
		num4 = v1 * v2 * length2 * mass2 * Math.cos(angle1 - angle2);
		den = length2 * (2 * mass1 + mass2 - mass2 * Math.cos(2 * angle1 - 2 * angle2));
		double a2_a = (num1 * (num2 + num3 + num4)) / den;
		
		x1 = (int)(length1 * Math.sin(angle1));
		y1 = (int)(length1 * Math.cos(angle1));

		x2 = (int)(x1 + length2 * Math.sin(angle2));
		y2 = (int)(y1 + length2 * Math.cos(angle2));
		
		v1 += a1_a;
		v2 += a2_a;
		angle1 += v1;
		angle2 += v2;
	}
	
	public void paint(Graphics g){
		integrate();
		g.setColor(Color.RED);
		g.drawLine(xOffset, yOffset, x1 + xOffset - radius / 2, y1 + yOffset - radius / 2);
		g.fillOval(x1 + xOffset - radius, y1 + yOffset - radius, radius, radius);
		g.drawLine(x1 + xOffset - radius / 2, y1 + yOffset - radius / 2, x2 + xOffset - radius / 2, y2 + yOffset - radius / 2);
		g.fillOval(x2 + xOffset - radius, y2 + yOffset - radius, radius, radius);
		
		paintPoints.add(new Point(x2 + (xOffset - radius / 2), y2 + (yOffset - radius)));
		g.setColor(Color.BLUE);
		lastx2 = paintPoints.get(0).x;
		lasty2 = paintPoints.get(0).y;
		for(int i = 0; i < paintPoints.size(); i++) {
			Point p = paintPoints.get(i);
			if(p.age <= 1 || p.age > 1) {
				if(!isCovered(p) && !p.painted) {
					p.painted = true;
					g.drawLine(p.x, p.y, lastx2, lasty2);
				}
				else if(isCovered(p)) {
					p.painted = false;
				}
				if(i != paintPoints.size() - 1) {
					g.drawLine(p.x, p.y, lastx2, lasty2);
				}
			}
			lastx2 = p.x;
			lasty2 = p.y;
		}
	}
	
	public boolean isCovered(Point p) {
		p.age++;
		double distance1 = Math.sqrt((x1 - p.x) * (x1 - p.x) + (y1 - p.y) * (y1 - p.y));
		if(distance1 < radius) {
			return true;
		}
		double distance2 = Math.sqrt((x2 - p.x) * (x2 - p.x) + (y2 - p.y) * (y2 - p.y));
		if(distance2 < radius) {
			return true;
		}
		return false;
	}
}

class Point{
	
	public int x;
	public int y;
	public int age;
	public boolean painted;
	
	public Point(int a, int b) {
		x = a;
		y = b;
		age = 0;
		painted = false;
	}
}