import java.util.*;
import java.io.*;
import java.math.RoundingMode;
import java.text.*;
public class tedious {
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("tedious.in"));
		
		int n = file.nextInt();
		file.nextLine();
		
		for(int i = 0; i < n; i++) {
			int a = file.nextInt();
			int b = file.nextInt();
			int c = file.nextInt();
			int d = file.nextInt();
			int x = file.nextInt();
			
			double numerator = 2 * x * Math.pow(a + b, 3) * (b - (3 * c * d) + (a * b) - 23);
			double denominator = Math.pow(c, 3) * Math.pow(d, 4) * Math.pow(x, 2);
			
			System.out.printf("%.3f", (numerator / denominator));
		}
	}
}
