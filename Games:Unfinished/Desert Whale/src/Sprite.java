import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.*;

public class Sprite {
	
	public BufferedImage up;
	public BufferedImage down;
	public BufferedImage left;
	public BufferedImage right;
	public ArrayList<BufferedImage> animation;
	
	public String collision;
	
	public Sprite(BufferedImage u, BufferedImage d, BufferedImage lr, String c) {
		up = u;
		down = d;
		left = lr;
		right = flipImage(lr);
		collision = c;
	}
	
	public static BufferedImage flipImage(BufferedImage in) {
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
	    tx.translate(-in.getWidth(null), 0);
	    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	    in = op.filter(in, null);
	    return in;
	}
	
}
