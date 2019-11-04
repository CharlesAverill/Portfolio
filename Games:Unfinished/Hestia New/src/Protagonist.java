import java.util.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Protagonist {
	public BufferedImage currentSprite;
	public int x;
	public int y;
	
	public Protagonist(int x1, int y1, BufferedImage s) {
		x = x1;
		y = y1;
		currentSprite = s;
	}
	
	public Protagonist(int x1, int y1, int x2, int y2, BufferedImage s) {
		x = x1;
		y = y1;
		currentSprite = s;
	}
	
	public void move(int direction, int distance) {
		/*
		 * n = 0
		 * s = 1
		 * e = 2
		 * w = 3
		 */
		if(direction == 0) {
			for(int i = 0; i < distance; i++) {
				y -= 1;
			}
			//y -= distance;
		}
		if(direction == 1) {
			for(int i = 0; i < distance; i++) {
				y += 1;
			}
			y += distance;
		}
		if(direction == 2) {
			x += distance;
		}
		if(direction == 3) {
			x -= distance;
		}
		
		if(x < 0 * 16) {
			x = 14 * 16;
			Hestia.screenX--;
		}
		else if(x > 14 * 16) {
			x = 0 * 16;
			Hestia.screenX++;
		}
		if(y < 0 * 16) {
			y = 9 * 16;
			Hestia.screenY--;
		}
		else if(y > 9 * 16) {
			y = 0 * 16;
			Hestia.screenY++;
		}
	}
}
