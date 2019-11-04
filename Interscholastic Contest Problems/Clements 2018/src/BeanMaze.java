import java.util.*;
import java.io.*;

public class BeanMaze {
	
	public static String[][] mat;
	public static int[][] shadow;
	public static boolean solved = false;
	public static int directions = 0;
	public static int[] s = new int[2];
	
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("beanmaze.dat"));
		int n = file.nextInt();
		file.nextLine();
		
		for(int x = 0; x < n; x++) {
			mat = new String[file.nextInt()][file.nextInt()];
			file.nextLine();
			shadow = new int[mat.length][mat[0].length];
			for(int r = 0; r < mat.length; r++) {
				String line = file.nextLine();
				mat[r] = line.split("");
				for(int i = 0; i < mat[r].length; i++) {
					if(mat[r].equals("S")) {
						s[0] = r;
						s[1] = i;
					}
				}
			}
			recur(0, s[0], s[1]);
			if(solved) {
				System.out.println("POSSIBLE");
			}
			else System.out.println("NOT POSSIBLE");
			directions = 0;
			solved = false;
		}
	}
	
	public static void recur(int r, int c, int steps) {
		if(r >= 0 && c >= 0 && r < mat.length && c < mat[r].length && directions < 4) {
				if(mat[r][c].equals("#")) {
					directions--;
					mat[r][c] = "0";
					if((steps < shadow[r][c] || shadow[r][c] == 0)) {
						shadow[r][c] = steps;
						recur(r + 1, c, steps + 1);
						recur(r - 1, c, steps + 1);
						recur(r, c + 1, steps + 1);
						recur(r, c - 1, steps + 1);
					}
					mat[r][c] = ".";
				}
				if(mat[r][c].equals(".")) {
					directions++;
					mat[r][c] = "0";
					if(steps < shadow[r][c] || shadow[r][c] == 0) {
						shadow[r][c] = steps;
						recur(r + 1, c, steps + 1);
						recur(r - 1, c, steps + 1);
						recur(r, c + 1, steps + 1);
						recur(r, c - 1, steps + 1);
					}
					mat[r][c] = ".";
				}
				else {
					if(mat[r][c].equals("B")) {
						solved = true;
					}
				}
			}
		}
	}
