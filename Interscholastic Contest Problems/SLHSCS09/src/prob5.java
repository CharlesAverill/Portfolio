import java.io.*;
import java.util.*;

public class prob5 {
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("prob5.in"));
		
		int a = file.nextInt();
		int b = file.nextInt();
		file.nextLine();
		
		String heirarchy = "";
		
		for(int i = 0; i < a; i++) {
			heirarchy += file.nextLine() + "\n";
		}
		for(int i = 0; i < b; i++) {
			System.out.println(find(heirarchy, file.nextLine()));
		}
		
		
	}
	
	public static String find(String s, String f) {
		return "";
	}
}
