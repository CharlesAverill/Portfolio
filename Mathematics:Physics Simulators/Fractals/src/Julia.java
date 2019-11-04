import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

public class Julia extends Canvas{
	
	private int x;
	private int y;
	public double ZOOM;
	public int XOFFSET;
	public int YOFFSET;
	
	public Julia(double z, int a, int b) {
		ZOOM = z;
		XOFFSET = a;
		YOFFSET = b;
		setBackground(Color.WHITE);
	}
	
	public void paint(Graphics window) {
		x = 0;
		y = 0;
		int w = 800;
		int h = 600;
		double cX = -0.7;
        double cY = 0.27015;
        int colorOffset = (int)(15);
		//window.setColor(Color.BLACK);
		//window.drawString("Outside", 50, 50);
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                double zx = 1.5 * ((x + XOFFSET) - w / 2) / (0.5 * ZOOM * w);
                double zy = ((y + YOFFSET) - h / 2) / (0.5 * ZOOM * h);
                float i = 400;
                while (zx * zx + zy * zy < 4 && i > 0) {
                    double tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    i--;
                }
                if(zy < 2) {
					window.setColor(Color.BLACK);
                }
                else {
                	int c = Color.HSBtoRGB((300 / i) % 1, 1, i > 0 ? 1 : 0);
                	window.setColor(new Color(c));
                }
                window.drawLine(x , y, x + 1, y);
            }
        }
	}
}
