import java.util.*;
import java.io.*;

public class ReturnLengthOfShortestPathThroughMazeWithStartAndEnd {
	
	private static int width;
	private static String[][] mat;
	private static int[][] shadow;
	private static boolean solved;
	private static int shortest;
	
	public static String path = ".";
	public static String wall = "#";
	public static String start = "C";
	public static String end = "E";
	
	
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("RLOSPTMWSAE.in"));
		
		int n = file.nextInt();
		file.nextLine();
		
		for(int i = 0; i < n; i++) {
			mat = new String[file.nextInt()][file.nextInt()];
			shadow = new int[mat.length][mat[0].length];
			file.nextLine();
			shortest = Integer.MAX_VALUE;
			solved = false;
			int rE = 0;
			int cE = 0;
			for(int r = 0; r < mat.length; r++) {
				String line = file.nextLine();
				for(int c = 0; c < mat[r].length; c++) {
					mat[r][c] = line.substring(c, c + 1);
					if(mat[r][c].equals(start)) {
						rE = r;
						cE = c;
					}
				}
			}
			recur(rE, cE, 0);
			if(solved) {
				System.out.println(shortest);
			}
			else System.out.println("Squish.");
		}
	}
	
	public static void recur(int r, int c, int steps) {
		if(r >= 0 && r < mat.length && c >= 0 && c < mat[r].length && shortest > steps) {
			if(mat[r][c].equals(end)) {
				solved = true;
				shortest = steps;
			}
			if(mat[r][c].equals(start) || mat[r][c].equals(path)) {
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
	}
}
