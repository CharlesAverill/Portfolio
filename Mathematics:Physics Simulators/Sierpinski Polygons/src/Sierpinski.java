import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.peer.CanvasPeer;

public interface Sierpinski{
	public int iterations = 0;
	public void paint(Graphics g);
	public void iterate();
}
