import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.image.*;
public class Sprite {
	
	public BufferedImage up;
	public BufferedImage down;
	public BufferedImage left;
	public BufferedImage right;
	public String collision;
	
	public Sprite(BufferedImage a, BufferedImage b, BufferedImage c, BufferedImage d, String co) {
		up = a;
		down = b;
		left = c;
		right = d;
		collision = co;
	}
	
	public Sprite(BufferedImage only, String co) {
		up = only;
		collision = co;
	}
}
