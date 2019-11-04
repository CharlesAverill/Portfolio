package ride;

/*
ID: S642000
LANG: JAVA
TASK: ride
 */

import java.io.*;
import java.util.*;

public class ride{
	
	public static void main(String[] args) throws IOException{
		
		Scanner file = new Scanner(new File("ride.in"));
		PrintWriter pw = new PrintWriter(new File("ride.out"));
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		String abcs = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for(int i = 0; i < abcs.length(); i++) {
			map.put(abcs.substring(i, i+1), i+1);
		}
		
		String UFO = file.nextLine();
		String name = file.nextLine();
		int UFOCount = 1;
		int nameCount = 1;
		
		for(int i = 0; i < UFO.length(); i++) {
			UFOCount *= (int)map.get(UFO.substring(i,  i+1));
		}
		
		for(int i = 0; i < name.length(); i++) {
			nameCount *= (int)map.get(name.substring(i,  i+1));
		}
		
		//System.out.println(UFOCount);
		//System.out.println(nameCount);
		
		if(UFOCount % 47 == nameCount % 47) {
			pw.println("GO");
		}
		else {
			pw.println("STAY");
		}
		file.close();
		pw.close();
	}
	
	
}
