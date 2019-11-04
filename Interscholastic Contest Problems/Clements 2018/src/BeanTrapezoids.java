import java.util.*;
import java.io.*;
public class BeanTrapezoids {
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("beantrapezoids.dat"));
		
		int n = file.nextInt();
		file.nextLine();
		
		for(int i = 0; i < n; i++) {
			double b1 = file.nextDouble();
			double b2 = file.nextDouble();
			double h = file.nextDouble();
			file.nextLine();
			double output = (h * (b1 + b2)) / 2;
			System.out.printf("%.2f\n", output);
		}
		
		file.close();
	}
}
