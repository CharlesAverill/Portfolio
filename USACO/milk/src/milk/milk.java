package milk;
/*
ID: S642000
LANG: JAVA
TASK: milk
 */

import java.io.*;
import java.util.*;

public class milk {
	
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("milk.in"));
		PrintWriter pw = new PrintWriter(new File("milk.out"));
		
		int bought = 0;
		int cost = 0;
		int n = file.nextInt();
		int m = file.nextInt();
		ArrayList<Integer> p = new ArrayList<Integer>();
		ArrayList<Integer> a = new ArrayList<Integer>();
		
		
		for(int i = 0; i < m; i++) {
			file.nextLine();
			p.add(file.nextInt());
			a.add(file.nextInt());
		}
		
		while(bought < n) {
			bought += a.get(findLowest(p));
			cost += (a.get(findLowest(p)) * p.get(findLowest(p)));
			
			if(bought > n) {
				cost -= (bought - n) * p.get(findLowest(p));
			}
			a.remove(findLowest(p));
			p.remove(findLowest(p));
			
		}
		
		pw.println(cost);
		
		pw.close();
		file.close();
	}
	
	public static int findLowest(ArrayList<Integer> a) {
		int lowest = a.get(0);
		int place = 0;
		for(int i = 0; i < a.size(); i++) {
			if(a.get(i) < lowest) {
				place = i;
				lowest = a.get(i);
			}
		}
		
		return place;
	}

}
