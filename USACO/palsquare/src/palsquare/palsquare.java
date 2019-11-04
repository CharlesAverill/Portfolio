package palsquare;
/*
ID: S642000
LANG: JAVA
TASK: palsquare
 */

import java.io.*;
import java.util.*;

public class palsquare {
	private static final String symbols = "0123456789ABCDEFGHIJ";
	private static ArrayList<String> counts;
	
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("palsquare.in"));
		PrintWriter pw = new PrintWriter(new File("palsquare.out"));
		
		int base = file.nextInt();
		
		for(int i = 1; i <= 300; i++) {
			String num = Integer.toString(i * i, base);
			if(isPalindrome(num)) {
				pw.println(Integer.toString(i, base).toUpperCase() + " " + num.toUpperCase());
			}
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
}
