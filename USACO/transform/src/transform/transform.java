package transform;
/*
ID: S642000
LANG: JAVA
TASK: transform
*/

import java.util.*;
import java.io.*;

public class transform{
	public static void print(String[][] mat) {
		for(String[] r : mat) {
			for(String i : r) {
				System.out.print(i);
			}
			System.out.println();
		}
		System.out.println();
	}
	public static String[][] rotate90(String[][] m){
		String[][] mat = new String[m.length][m.length];
		for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                mat[j][m.length - i - 1] = m[i][j];
            }
        }
		return mat;
	}
	public static String[][] rotate180(String[][] m){
		return rotate90(rotate90(m));
	}
	public static String[][] rotate270(String[][] m){
		return rotate90(rotate90(rotate90(m)));
	}
	public static String[][] reflect(String[][] m){
		String[][] mat = new String[m.length][m.length];
		for(int r = 0; r < m.length; r++) {
			for(int c = 0; c < m.length; c++) {
				mat[r][c] = m[r][c];
			}
		}
		String temp;
		for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length / 2; j++) {
                temp = mat[i][j];
                mat[i][j] = mat[i][mat[i].length - j - 1];
                mat[i][m[i].length - j - 1] = temp;
            }
        }
		return mat;
	}
	public static boolean equals(String[][] mat, String[][] other){
		for(int i = 0; i < mat.length; i++){
			for(int k = 0; k < mat.length; k++){
				if(!mat[i][k].equals(other[i][k])){
					return false;
				}
			}
		}
		return true;		
	}
    public static void main(String[] args) throws IOException {
    	
    	Scanner file = new Scanner(new File("transform.in"));
        PrintWriter pw = new PrintWriter(new File("transform.out"));
        
        int n = file.nextInt();
        final String[][] before = new String[n][n];
        file.nextLine();
        for(int i = 0; i < n; i++){
        	String temp = file.nextLine();
        	for(int k = 0; k < n; k++){
        		before[i][k] = temp.substring(0, 1);
        		temp = temp.substring(1);
        	}
        }
        final String[][] after = new String[n][n];
        for(int i = 0; i < n; i++){
        	String temp = file.nextLine();
        	for(int k = 0; k < n; k++){
        		after[i][k] = temp.substring(0, 1);
        		temp = temp.substring(1);
        	}
        }
        String[][] tempReflect = reflect(before);
        String[][] temp90 = rotate90(before);
        String[][] temp180 = rotate180(before);
        String[][] temp270 = rotate270(before);
        
        if(equals(after, temp90)) {
        	pw.println(1);
        }
        else if(equals(after, temp180)) {
        	pw.println(2);
        }
        else if(equals(after, temp270)) {
        	pw.println(3);
        }
        else if(equals(after, tempReflect)) {
        	pw.println(4);
        }
        else if(equals(after, rotate90(reflect(before))) || equals(after, rotate180(reflect(before))) || equals(after, rotate180(reflect(before)))) {
        	pw.println(5);
        }
        else if(equals(before, after)) {
        	pw.println(6);
        }
        else pw.println(7);
        pw.close();
    }
}
