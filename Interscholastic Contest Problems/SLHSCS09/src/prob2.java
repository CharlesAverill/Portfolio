import java.util.*;
import java.io.*;
import java.awt.*;

public class prob2 {
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("prob2.in"));
		
		int n = file.nextInt();
		
		double[] prices = new double[] {2500.99, 15000.85, 1999.02, .50, 9988.69};
		for(int i = 0; i < n; i++) {
			file.nextLine();
			int a = file.nextInt();
			int b = file.nextInt();
			int c = file.nextInt();
			int d = file.nextInt();
			int e = file.nextInt();
			
			System.out.print("$");
			System.out.printf("%.2f", ((prices[0] * a) + (prices[1] * b) + (prices[2] * c) + (prices[3] * d) + prices[4] * e));
			System.out.println();
		}
	}
}
