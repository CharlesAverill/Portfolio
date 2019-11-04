/*
ID: S642000
LANG: JAVA
TASK: skidesign
 */

import java.util.*;
import java.io.*;

public class skidesign {
	public static void main(String[] args) throws IOException{
		
		Scanner file = new Scanner(new File("skidesign.in"));
		PrintWriter pw = new PrintWriter(new File("skidesign.out"));
		
		int n = file.nextInt();
		int cheap = Integer.MAX_VALUE;
		
		int[] hills = new int[n];
		for(int i = 0; i < n; i++) {
			hills[i] = file.nextInt();
		}
		
		for(int i = 0; i <= 83; i++) { //Hills only go up to 100, 100-17=83
			int cost = 0;
			int temp = 0;
			
			for(int j = 0; j < n; j++) {
				if(hills[j] < i) {
					temp = i - hills[j];
				}
				else if(i + 17 < hills[j]) {
					temp = hills[j] - i - 17;
				}
				else temp = 0;
				cost += Math.pow(temp, 2);
			}
			
			if(cheap > cost) {
				cheap = cost;
			}
		}
		
		pw.println(cheap);
		pw.close();
		file.close();
	}
}
