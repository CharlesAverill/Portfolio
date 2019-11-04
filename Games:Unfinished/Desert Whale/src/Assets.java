import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;

public class Assets {
	
	SpriteSheet mainCharacter = new SpriteSheet(ImageLoader.loadImage("/MainCharacter_16x23/sheet.png"));
	SpriteSheet horseCharacter = new SpriteSheet(ImageLoader.loadImage("/MainCharacter_16x23/Horse_16x39/sheet.png"));
	SpriteSheet bullets = new SpriteSheet(ImageLoader.loadImage("/MainCharacter_16x23/Bullets_20x5/sheet.png"));
	
	public Assets() {
		init();
	}
	
	public static void init() {
		
	}
	
	public static BufferedImage flipImage(BufferedImage in) {
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
	    tx.translate(-in.getWidth(null), 0);
	    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	    in = op.filter(in, null);
	    return in;
	}
}
