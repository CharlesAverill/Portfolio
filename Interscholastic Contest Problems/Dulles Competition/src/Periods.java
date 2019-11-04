import java.io.*;
import java.util.*;

public class Periods {
	public void run() throws Exception {
		Scanner file = new Scanner(new File("periods.dat"));
		int times = file.nextInt();
		file.nextLine();
		for (int ab = 0; ab < times; ab++) {
			String line = file.nextLine();
			
			if(line.charAt(line.length() - 1) == '.') {
				System.out.println(line);
			}
			else {
				System.out.println(line + ".");
			}
		}
	}

	public static void main(String[] args) throws Exception {
		new Periods().run();
	}
}