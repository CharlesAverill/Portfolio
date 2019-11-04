import java.util.*;
import java.io.*;

public class carmoji {
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("carmoji.in"));
		
		ArrayList<String> emoticons = new ArrayList<String>();
		ArrayList<String> meanings = new ArrayList<String>();
		
		emoticons.add(":D");
		emoticons.add(":)");
		emoticons.add(":{)");
		emoticons.add(":]");
		emoticons.add("O.o");
		
		meanings.add("laugh");
		meanings.add("smile");
		meanings.add("disguise");
		meanings.add("awkward");
		meanings.add("shock");
		
		int n = file.nextInt();
		file.nextLine();
		
		for(int i = 0; i < n; i++) {
			String line = file.nextLine();
			String output = "";
			
			Scanner s = new Scanner(line);
			
			while(s.hasNext()) {
				String temp = s.next();
				boolean added = true;
				for(int x = 0; x < 5; x++) {
					if(temp.contains(emoticons.get(x))) {
						output += meanings.get(x) + temp.substring(temp.indexOf(emoticons.get(x)) + emoticons.get(x).length()) + " ";
						added = false;
					}
					else if(temp.contains(meanings.get(x))) {
						output += emoticons.get(x) + temp.substring(temp.indexOf(meanings.get(x)) + meanings.get(x).length()) + " ";
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
}
