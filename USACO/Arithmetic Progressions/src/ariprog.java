/*
ID: 642000
LANG: JAVA
TASK: ariprog
 */

import java.util.*;
import java.io.*;

public class ariprog {
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("ariprog.in"));
		PrintWriter pw = new PrintWriter(new File("ariprog.out"));
		
		int n = file.nextInt();
		int m = file.nextInt();
		
		boolean[] isMatch = new boolean[125001];
		
		for(int p = 0; p <= m; p++) {
			for(int q = p; q <= m; q++) {
				isMatch[(p *p) + (q * q)] = true;
			}
		}
		
		ArrayList<int[]> pairs = new ArrayList<int[]>();
		int c = 0;
		
		for(int first = 0; first < m * m * 2; first++){
			if(!isMatch[first]) {
				continue;
			}
		    
		    for(int second = 1; second <= ((m * m * 2) - first) / (n-1); second++){
		    	boolean broken = false;
		    	for(int i = 1; i <= n-1; i++){
		    		if(!isMatch[first + (second * i)]) {
		    			broken = true;
		    			break;
		    		}
		    	}
		    	if(!broken) {
			    	pairs.add(new int[]{first,second});
		    	}
		    }
		}
		
		Collections.sort(pairs, new Comparator<int[]>() {
			public int compare(int[] first, int[] second) {
				if(first[1] < second[1]) {
					return -1;
				}
				if(first[1] > second[1]) {
					return 1;
				}
				if(first[0] < second[0]) {
					return -1;
				}
				if(first[0] < second[0]) {
					return 1;
				}
				return 0;
			}
		});
		
		for(int i = 0; i < pairs.size(); i++) {
			pw.println("" + pairs.get(i)[0] + " " + pairs.get(i)[1]);
		}
		
		if(pairs.size() == 0) {
			pw.println("NONE");
		}
		
		pw.close();
	}
}
