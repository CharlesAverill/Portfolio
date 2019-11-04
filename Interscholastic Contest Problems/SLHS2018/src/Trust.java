import java.util.*;
import java.io.*;

public class Trust {
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("trust.in"));
		
		int n = file.nextInt();
		file.nextLine();
		for(int i = 0; i < n; i++) {
			String line = file.nextLine().toLowerCase();
			//System.out.println(line);
			if(isTrustworthy(line)) {
				System.out.println("TWO-TWELVE!");
			}
			else System.out.println("Glory to Stroudonia...");
		}
	}
	
	public static boolean isTrustworthy(String str) {
		String[] arr = str.split(" ");
		str = "";
		for(int i = 0; i < arr.length; i++) {
			str += arr[i];
		}
		return str.contains("forest");
	}
}
