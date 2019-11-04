package wormhole;
/*
ID: S642000
LANG: JAVA
TASK: wormhole
 */

import java.util.*;
import java.io.*;
import java.awt.*;

public class wormhole {
	
	private static int[] x = new int[13];
	private static int[] y = new int[13];
	private static int n;
	private static int[] pairs = new int[13];
	private static int[] nR = new int[13];
	
	public static void main(String[] args) throws IOException{
		
		Scanner file = new Scanner(new File("wormhole.in"));
		PrintWriter pw = new PrintWriter(new File("wormhole.out"));
		
		n = file.nextInt();
		file.nextLine();
		
		for(int i = 1; i <= n; i++) {
			int x1 = file.nextInt();
			int y1 = file.nextInt();
			x[i] = x1;
			y[i] = y1;
		}
		
		for(int i = 1; i <= n; i++) {
			for(int j = 1; j <= n; j++) {
				if(x[j] > x[i] && y[i] == y[j]) {
					if(nR[i] == 0 || x[j] - x[i] < x[nR[i]] - x[i]) {
						nR[i] = j;
					}
				}
			}
		}
		pw.println(pair());
		file.close();
		pw.close();
	}
	
	public static int pair() {
		int pairings = 0;
		
		int i = 0;
		for(i = 1; i <= n; i++) {
			if(pairs[i] == 0) {
				break;
			}
		}
		
		if(i > n) {
			for(int a = 1; a <= n; a++) {
				int c = a;
				for(int b = 1; b < n; b++) {
					c = nR[pairs[c]];
				}
				if(c != 0) {
					return 1;
				}
			}
			return 0;
		}
		
		for(int j = i + 1; j <= n; j++) {
			if(pairs[j] == 0) {
				pairs[i] = j;
				pairs[j] = i;
				pairings += pair();
				pairs[i] = 0;
				pairs[j] = 0;
			}
		}
		return pairings;
	}
}
