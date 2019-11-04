import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;

public class Assets {
	
	public static Sprite player;
	
	public static SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/Tilesets/Sprites/sprites.png"));
	
	public static SpriteSheet Overworld = new SpriteSheet(ImageLoader.loadImage("/Maps/Overworld.png"));
	
	public Assets() {
		init();
	}
	
	public static void init() {
		player = new Sprite(sheet.crop(16, 0, 16, 16), sheet.crop(0, 0, 16, 16), flipImage(sheet.crop(32, 0, 16, 16)), sheet.crop(32, 0, 16, 16), "NONE");
	}
	
	public static BufferedImage flipImage(BufferedImage in) {
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
	    tx.translate(-in.getWidth(null), 0);
	    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	    in = op.filter(in, null);
	    return in;
	}
}
