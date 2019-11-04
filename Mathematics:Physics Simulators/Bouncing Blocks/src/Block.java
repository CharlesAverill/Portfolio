import java.awt.*;

import javax.swing.*;

public class Block extends Canvas{
	
	double mass;
	double velocity;
	double x;
	int width;
	
	public Block(double m, double v, int x1, int w) {
		mass = m;
		velocity = v;
		x = x1;
		width = w;
	}
	
	public void bounce(Block b) {
		double m1 = mass;
		double v1 = velocity;
		double m2 = b.mass;
		double v2 = b.velocity;
		if(x < 10) {
			velocity *= -1;
		}
		else if(overlaps(b)) {
			velocity = (v1 * ((m1 - m2) / (m1 + m2))) + (v2 * ((2 * m2)) / (m1 + m2));
			b.velocity = (v2 * ((m2 - m1) / (m1 + m2))) + (v1 * ((2 * m1)) / (m1 + m2));
		}
	}
	
	public void update(Block b) {
		x += velocity;
		b.x += b.velocity;
		bounce(b);
	}
	
	public boolean overlaps (Block b) {
	    return (x <= b.x + b.width && x + width >= b.x);
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect((int)x, 300 - width, width, width);
		g.setColor(Color.white);
		g.drawString("" + mass, (int)x + (width / 4), 300 - (width / 3));
	}
}
