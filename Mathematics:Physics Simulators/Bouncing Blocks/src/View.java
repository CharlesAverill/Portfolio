import java.awt.*;

import javax.swing.*;

public class View extends JPanel{
	
	Block b1;
	Block b2;
	
	static int collisions;
	
	public View(Block a, Block b) {
		b1 = a;
		b2 = b;
		collisions = 0;
	}
	
	public void paint(Graphics g) {
		g.drawString("Collisions: " + collisions, 10, 500);
		g.setColor(Color.RED);
		g.drawLine(10, 50, 10, 300);
		g.drawLine(10, 300, 800, 300);
		
		b1.paint(g);
		b2.paint(g);
	}
	
	public void update() {
		b1.update(b2);
		if(b1.overlaps(b2) || b1.x < 10) {
			collisions++;
		}
		//System.out.println(collisions);
	}
}
