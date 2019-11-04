package barn1;
/*
ID: S642000
LANG: JAVA
TASK: barn1
 */

import java.util.*;
import java.io.*;

public class barn1 {
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("barn1.in"));
		PrintWriter pw = new PrintWriter(new File("barn1.out"));
		
		int maxBoards = file.nextInt();
		int stalls = file.nextInt();
		int cows = file.nextInt();
		file.nextLine();
		
		int[] stallNumbers = new int[cows];
		ArrayList<Spaces> spaces = new ArrayList<Spaces>();
		
		for(int i = 0; i < cows; i++) {
			stallNumbers[i] = file.nextInt();
		}
		Arrays.sort(stallNumbers);
		
		for(int i = 1; i < cows; i++) {
			if(stallNumbers[i] - stallNumbers[i-1] > 1) {
				spaces.add(new Spaces(stallNumbers[i-1], stallNumbers[i]));
			}
		}
		
		Collections.sort(spaces);
		
		int output = stallNumbers[cows - 1] - stallNumbers[0] + 1;
		for(int i = 0; i < spaces.size() && i < maxBoards - 1; i++) {
			output -= spaces.get(i).distance;
		}
		
		pw.println(output);
		
		pw.close();
		file.close();
	}
}

class Spaces implements Comparable<Spaces>{
	private int begin;
	private int end;
	public int distance;
	
	Spaces(int b, int e) {
		begin = b;
		end = e;
		distance = end - begin - 1;
	}
	
	public int compareTo(Spaces o) {
		return o.distance - distance;
	}
}
