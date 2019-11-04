import java.io.*;
import java.util.*;

public class CanGetFrom00ToEndWithoutObstacles {
	
	public static String[][] mat;
	public static int[][] shadow;
	public static boolean solved;
	public static int shortest;
	public static int j;
	public static int k;
	public static int time;
	
	public static String wall = "#";
	public static String path = ".";
	public static String end = "C";
	
	
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("CGF00TEWO.in"));
		
		int a = file.nextInt();
		file.nextLine();
		
		for(int i = 0; i < a; i++) {
			int n = file.nextInt();
			int m = file.nextInt();
			j = file.nextInt();
			k = file.nextInt();
			file.nextLine();
			time = 0;
			
			mat = new String[n][m];
			shadow = new int[n][m];
			shortest = Integer.MAX_VALUE;
			
			for(int r = 0; r < mat.length; r++) {
				String line = file.nextLine();
				mat[r] = line.split("");
			}
			recur(0, 0, 0);
			if(solved) {
				System.out.println("YES");
			}
			else System.out.println("NO");
		}
	}
	
	public static void recur(int r, int c, int steps) {
		if(r >= 0 && c >= 0 && r < mat.length && c < mat[r].length && shortest > steps) {
			if(isDirty()) {
				if(mat[r][c].equals(wall)) {
					time += j;
					if(time >= k) {
						solved = false;
					}
					mat[r][c] = "0";
					if((steps < shadow[r][c] || shadow[r][c] == 0) && time < k) {
						shadow[r][c] = steps;
						recur(r + 1, c, steps + 1);
						recur(r - 1, c, steps + 1);
						recur(r, c + 1, steps + 1);
						recur(r, c - 1, steps + 1);
					}
					mat[r][c] = path;
				}
				if(mat[r][c].equals(path)) {
					mat[r][c] = "0";
					if(steps < shadow[r][c] || shadow[r][c] == 0) {
						shadow[r][c] = steps;
						recur(r + 1, c, steps + 1);
						recur(r - 1, c, steps + 1);
						recur(r, c + 1, steps + 1);
						recur(r, c - 1, steps + 1);
					}
					mat[r][c] = path;
				}
			}
			else {
				if(mat[r][c].equals(end) && time < k) {
					solved = true;
				}
			}
		}
	}
	
	public static boolean isDirty() {
		for(int r = 0; r < mat.length; r++) {
			for(int c = 0; c < mat[r].length; c++) {
				if(mat[r][c].equals(wall)) {
					return true;
				}
			}
		}
		return false;
	}
}
