import java.util.*;
import java.io.*;

public class prob6 {
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("prob6.in"));
		
		ArrayList<String> emoticons = new ArrayList<String>();
		ArrayList<String> meanings = new ArrayList<String>();
		
		emoticons.add(":)");
		emoticons.add(":(");
		emoticons.add("D:<");
		emoticons.add("XD");
		emoticons.add("ugh");
		emoticons.add("lol");
		emoticons.add("ttyl");
		emoticons.add("idk");
		emoticons.add("$)");
		
		meanings.add("happy".toUpperCase());
		meanings.add("sad".toUpperCase());
		meanings.add("angry".toUpperCase());
		meanings.add("laughing".toUpperCase());
		meanings.add("discontented".toUpperCase());
		meanings.add("laugh out loud".toUpperCase());
		meanings.add("talk to you later".toUpperCase());
		meanings.add("I don't know".toUpperCase());
		meanings.add("cool".toUpperCase());
		
		int n = file.nextInt();
		file.nextLine();
		
		for(int i = 0; i < n; i++) {
			String line = file.nextLine();
			String output = "";
			line = removePunct(line);
			System.out.println(line);
			
			Scanner s = new Scanner(line);
			
			while(s.hasNext()) {
				String temp = s.next();
				String temp2 = temp.toUpperCase();
				boolean added = true;
				for(int x = 0; x < 9; x++) {
					if(temp2.equals(emoticons.get(x))) {
						output += meanings.get(x) + " ";
						added = false;
					}
					else if(temp2.equals(meanings.get(x))) {
						output += emoticons.get(x) + " ";
						added = false;
					}
				}
				if(added) {
					output += temp + " ";
				}
			}
			
			System.out.println(output);
		}
	}
	
	public static String removePunct(String str) {
		String output = str;
		String punct = ".,!";
		for(int i = 0; i < str.length(); i++) {
			if(i == str.length() - 1 && punct.contains(str.substring(i, i+1))) {
				str = str.substring(0, str.length() - 2);
			}
			else if(punct.contains(str.substring(i, i + 1))) {
				str = str.substring(0, i) + str.substring(i + 1);
			}
		}
		return output;
	}
}
