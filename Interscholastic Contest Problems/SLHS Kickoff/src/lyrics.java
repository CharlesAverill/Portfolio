import java.io.*;
import java.util.*;
public class lyrics {
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("lyrics.in"));
		
		int n = file.nextInt();
		file.nextLine();
		
		for(int i = 0; i < n; i++) {
			int operations = 0;
			
			int m = file.nextInt();
			file.nextLine();
			
			String correct = "";
			for(int x = 0; x < m; x++) {
				correct += file.nextLine();
			}
			
			String stroud = "";
			for(int x = 0; x < m; x++) {
				stroud += file.nextLine();
			}
		}
	}
	
	public static int fix(String good, String bad) {
		int output = 0;
		String[] goodWords = good.split(" ");
		String[] badWords = bad.split(" ");
		int shorter;
		if(goodWords.length < badWords.length) {
			shorter = goodWords.length;
		}
		else shorter = badWords.length;
		for(int i = 0; i < shorter; i++) {
			if(!goodWords[i].equals(badWords[i])) {
				
				
				
			}
		}
		return output;
	}
}
