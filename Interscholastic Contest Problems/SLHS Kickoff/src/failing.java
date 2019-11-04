import java.io.*;
import java.util.*;

public class failing {
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("failing.in"));
		
		int n = file.nextInt();
		
		for(int i = 0; i < n; i++) {
			String name = file.next() + " " + file.next();
			int grade = file.nextInt();
			if(i != n - 1) {
				file.nextLine();
			}
			
			if(grade < 70) {
				System.out.println(name);
			}
		}
	}
}
