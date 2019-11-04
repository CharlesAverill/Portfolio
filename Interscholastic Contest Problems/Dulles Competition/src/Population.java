import java.io.*;
import java.util.*;

public class Population {
	public void run() throws Exception {
		Scanner file = new Scanner(new File("Population.dat"));
		int times = file.nextInt(); file.nextLine();
		for (int ab = 0; ab < times; ab++) {
			long p = file.nextLong();
			long t = file.nextLong();
			if(file.hasNextLine()) {
				file.nextLine();
			}
			
			for(int i = 0; i <= t; i++) {
				if(i % 7 == 0) {
					p--;
				}
				if(i % 4 == 0) {
					p++;
				}
			}
			System.out.println(p);
		}
	}

	public static void main(String[] args) throws Exception {
		new Population().run();
	}
}