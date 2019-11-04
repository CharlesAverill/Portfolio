package namenum;
/*
ID: S642000
LANG: JAVA
TASK: namenum
*/

import java.util.*;
import java.io.*;

class namenum {
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("namenum.in"));
		Scanner d = new Scanner(new File("dict.txt"));
		PrintWriter pw = new PrintWriter(new File("namenum.out"));
		
		String input = file.nextLine();
		ArrayList<String> output = new ArrayList<String>();
		ArrayList<String> dictionary = new ArrayList<String>();
		while(d.hasNextLine()) {
			dictionary.add(d.nextLine());
		}
		
		for(int i = 0; i < dictionary.size(); i++) {
			String name = dictionary.get(i);
			String nameConverted = "";
			for(int k = 0; k < name.length(); k++) {
				nameConverted += find(name.substring(k,  k+1));
			}
			
			if(nameConverted.equals(input)) {
				output.add(name);
			}
		}
		
		Collections.sort(output,  new Comparator<String>() {
			@Override
			public int compare(String one, String two) {
				return one.compareToIgnoreCase(two);
			}
		});
		
		for(int i = 0; i < output.size(); i++) {
			pw.println(output.get(i));
		}
		
		if(output.size() == 0) {
			pw.println("NONE");
		}
		pw.close();
		d.close();
		file.close();
	}
	public static String find(String s) {
		String[][] chars = {{"A", "B", "C"}, {"D", "E", "F"}, {"G", "H", "I"}, {"J", "K", "L"}, {"M", "N", "O"}, {"P", "R", "S"}, {"T", "U", "V"}, {"W", "X", "Y"}};
		for(int r = 0; r < 8; r++) {
			for(int c = 0; c < 3; c++) {
				if(chars[r][c].equals(s)) {
					return ("" + (r + 2));
				}
			}
		}
		return "";
		
	}
	
}
