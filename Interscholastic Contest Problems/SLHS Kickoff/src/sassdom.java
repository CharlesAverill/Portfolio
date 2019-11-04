import java.util.*;
import java.io.*;
import java.awt.Rectangle;

public class sassdom {
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("sassdom.in"));
		
		int n = file.nextInt();
		file.nextLine();
		
		for(int i = 0; i < n; i++) {
			int rectangles = file.nextInt();
			file.nextLine();
			ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
			for(int x = 0; x < rectangles; x++) {
				int a = file.nextInt();
				int b = file.nextInt();
				int c = file.nextInt();
				int d = file.nextInt();
				rects.add(new Rectangle(a, b, c - a, b - d));
			}
			if(rectangles < 2) {
				System.out.println((int)(rects.get(0).getWidth() + rects.get(0).getWidth() + rects.get(0).getHeight() + rects.get(0).getHeight()));
				continue;
			}
			for(int x = 0; x < rectangles - 1; x++) {
				if(rects.get(x).intersects(rects.get(x + 1))) {
					int P1 = (int)(rects.get(x).getWidth() * 2) + (int)(rects.get(x).getHeight() * 2);
					int stickingOut = (int)(rects.get(x + 1).getWidth() - (rects.get(x).getMaxX() - rects.get(x + 1).getMaxX()));
					System.out.println((int)(P1 + stickingOut - rects.get(x + 1).getHeight()));
				}
			}
		}
	}
}

class Point{
	public int x;
	public int y;
	
	public Point(int a, int b) {
		x = a;
		y = b;
	}
}
