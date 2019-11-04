package dualpal;
/*
ID: S642000
LANG: JAVA
TASK: dualpal
 */

import java.io.*;
import java.util.*;

public class dualpal {
	
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("dualpal.in"));
		PrintWriter pw = new PrintWriter(new File("dualpal.out"));
		
		int n = file.nextInt();
		int s = file.nextInt();
		
		int num = s + 1;
		int found = 0;
		while(found < n) {
			int count = 0;
			for(int b = 2; b < 11; b++) {
				if(isPalindrome("" + toBase("" + num, b))) {
					count++;
				}
				if(count >= 2) {
					pw.println(num);
					found++;
					break;
				}
			}
			num++;
		}
		
		file.close();
		pw.close();
	}
	
	public static boolean isPalindrome(String str) {
		String reverse = "";
		for(int i = str.length()-1; i >= 0; i--) {
			reverse += str.substring(i,  i+1);
		}
		return(reverse.equals(str));
	}
	
	
	public static long toBase(String n, int b) {
		return Long.parseLong(Integer.toString(Integer.parseInt(n, 10), b));
	}

}
