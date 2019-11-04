/*
ID: 642000
LANG: JAVA
TASK: milk3
 */

import java.util.*;
import java.io.*;

public class milk3 {
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("milk3.in"));
		PrintWriter pw = new PrintWriter(new File("milk3.out"));
		
		int AC = file.nextInt();
		int BC = file.nextInt();
		int CC = file.nextInt();
		int[] capacities = {AC, BC, CC};
		ArrayList<Integer> solutions = new ArrayList<Integer>();
		Stack<Bucket> buckets = new Stack<Bucket>();
		buckets.push(new Bucket(0, 0, CC));
		boolean[][][] pusher = new boolean[21][21][21];
		int [][] checker = {{0,1},{0,2},{1,0},{1,2},{2,0},{2,1}};
		while(!buckets.isEmpty()) {
			Bucket b = buckets.pop();
			if(b.bucket[0] == 0 && !solutions.contains(b.bucket[2])) {
				solutions.add(b.bucket[2]);
			}
			pusher[b.bucket[0]][b.bucket[1]][b.bucket[2]] = true;
			int[] x;
			for(int i = 0; i < checker.length; i++) {
				x = Bucket.pour(b.bucket, checker[i][0], checker[i][1], capacities[checker[i][1]]);
				if(!pusher[x[0]][x[1]][x[2]]) {
					buckets.push(new Bucket(x[0], x[1], x[2]));
				}
			}
		}
		Collections.sort(solutions);
		String output = "";
		for(int i = 0; i < solutions.size(); i++) {
			if(i != solutions.size() - 1) {
				output += solutions.get(i) + " ";
			}
			else output += solutions.get(i) + "";
		}
		pw.println(output);
		file.close();
		pw.close();
	}
}

class Bucket{
	public int[] bucket = new int[3];
	
	public Bucket(int a, int b, int c) {
		bucket[0] = a;
		bucket[1] = b;
		bucket[2] = c;
	}
	
	public static int[] pour(int[] abc, int f, int t, int cap){
		int p = cap - abc[t];
		int[] output = new int[3];
		output[0] = abc[0];
		output[1] = abc[1];
		output[2] = abc[2];
		if(p > output[f]) {
			p = output[f];
		}
		output[t] += p;
		output[f] -= p;
		return output;
	}
}