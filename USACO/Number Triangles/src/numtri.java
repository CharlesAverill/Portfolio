/*
ID: 642000
LANG: JAVA
TASK: numtri
 */
import java.util.*;
import java.io.*;

public class numtri {
	
	private static int sum = -1;
	private static ArrayList<ArrayList<Integer>> triangle = new ArrayList<ArrayList<Integer>>();
	
	public static void main(String[] args) throws IOException{
		BufferedReader file = new BufferedReader(new FileReader("numtri.in"));
		PrintWriter pw = new PrintWriter(new File("numtri.out"));
		
		int n = Integer.parseInt(file.readLine());
		
		for(int i = 0; i < n; i++) {
			triangle.add(new ArrayList<Integer>());
			String line = file.readLine();
			StringTokenizer temp = new StringTokenizer(line);
			while(temp.hasMoreTokens()) {
				triangle.get(i).add(Integer.parseInt(temp.nextToken()));
			}
		}
		
		sum = solve(0, 0, n);
		
		pw.println(sum);
		file.close();
		pw.close();
	}
	
	public static int solve(int r, int c, int n) {
		for (int i = n - 2; i >= 0; i--) {
			for (int j = 0; j <= i; j++) {
				int temp = triangle.get(i).get(j);
				triangle.get(i).set(j, temp + Math.max(triangle.get(i + 1).get(j), triangle.get(i + 1).get(j + 1)));
			}
		}
		return triangle.get(0).get(0);
	}
}