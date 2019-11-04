package crypt1;
/*
ID: S642000
LANG: JAVA
TASK: crypt1
 */

import java.util.*;
import java.io.*;

public class crypt1 {
	private static ArrayList<Integer> digits;
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("crypt1.in"));
		PrintWriter pw = new PrintWriter(new File("crypt1.out"));
		
		int n = file.nextInt();
		file.nextLine();
		digits = new ArrayList<Integer>();
		
		for(int i = 0; i < n; i++) {
			digits.add(file.nextInt());
		}
		
		int solutions = 0;
		Collections.sort(digits);
		for(int a : digits) {
			a *= 100;
			if(a != 0) {
				for(int b: digits) {
					b *= 10;
					for(int c : digits) {
						int abc = a + b + c;
						for(int d : digits) {
							if(d != 0) {
								int p1 = abc * d;
								if(confirm(p1, 3)) {
									for(int e : digits) {
										int p2 = abc * e;
										if(confirm(p2, 3)) {
											int result = (p1 * 10) + p2;
											if(confirm(result, 4)) {
												solutions++;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		pw.println(solutions);
		
		pw.close();
		file.close();
	}
	
	public static boolean isValid(int d) {
		return digits.contains(d);
	}
	
	public static boolean confirm(int n, int len) {
		for(int k = 0; k < len; k++) {
			if(!isValid(n %  10)) {
				return false;
			}
			n /= 10;
		}
		return(n == 0);
	}
}
