import java.util.*;
import java.io.*;

public class prob02 {
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("prob02.in"));
		
		while(file.hasNext()) {
			double v = file.nextDouble();
			double a = file.nextDouble();
			double t = file.nextDouble();
			if(v == 0 && a == 0 && t == 0) {
				break;
			}
			System.out.printf("%.3f", ((v * t) + (.5 * a * t * t)));
			System.out.println();
		}
	}
}
