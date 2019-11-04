import java.awt.*;

public class Particle{
	
	public double x;
	public double y;
	public double vx;
	public double vy;
	public double ax;
	public double ay;
	public double mass;
	public int color;
	public boolean collided = false;
	public double radius = 0;
	public String text = "";
	final double G = 6.67 * Math.pow(10, -11);
	
	public Particle(double m, int posx, int posy) {
		mass = m;
		x = posx;
		y = posy;
		vx = 0;
		vy = 0;
		ax = 0;
		ay = 0;
		
		double scale_green = ((0x00FF00 >> 8) / (int)(1 + Math.pow(mass / 100000, 1))) << 8;
		double scale_blue = 0x0000FF / (1 + Math.pow(mass / 10000, 1));
		color = (int)(0xFF0000 + scale_green + scale_blue);
		
		radius = Math.log(Math.E + mass / 10);
	}
	
	public Particle(double m, double velx, double vely, double posx, double posy) {
		mass = m;
		vx = velx;
		vy = vely;
		x = posx;
		y = posy;
		ax = 0;
		ay = 0;
		
		double scale_green = ((0x00FF00 >> 8) / (int)(1 + Math.pow(mass / 100000, 1))) << 8;
		double scale_blue = 0x0000FF / (1 + Math.pow(mass / 10000, 1));
		color = (int)(0xFF0000 + scale_green + scale_blue);
		
		radius = Math.log(Math.E + mass / 10);
	}
	
	public boolean equals(Particle p) {
		if(x == p.x && y == p.y && vx == p.vx && vy == p.vy && ax == p.ax && ay == p.ay && mass == p.mass && color == p.color && collided == p.collided && radius == p.radius && text.equals(p.text)) {
			return true;
		}
		return false;
	}
	
	public double distX(Particle other) {
		return Math.abs(x - other.x);
	}
	public double distY(Particle other) {
		return Math.abs(y - other.y);
	}
	public static double calculateDistance(double distX, double distY) {
		return Math.sqrt(distX * distX + distY * distY);
	}
	/*
	public void interact(Particle other) {
		double dx = other.x - x;
		double dy = other.y - y;
		double r = calculateDistance(dx, dy);
		double r3 = r * r * r;
		
		ax += other.mass * dx / r3;
		ay += other.mass * dy / r3;
		other.ax += mass * -dx / r3;
		other.ay += mass * -dy / r3;
	}
	*/
	public void interact(Particle other) {
		double dx = other.x - x;
		double dy = other.y - y;
		double r = calculateDistance(dx, dy);
		
		double magnitude = G * (mass * other.mass) / r;
		
		ax = magnitude * dx;
		ay = magnitude * dy;
		other.ax = magnitude * -dx;
		other.ax = magnitude * -dy;
	}
	
	public void simulate() {
		vx += ax;
		vy += ay;
		
		ax = 0;
		ay = 0;
		
		x += vx;
		y += vy;
	}
}
