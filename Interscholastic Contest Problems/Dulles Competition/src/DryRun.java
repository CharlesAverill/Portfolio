import java.io.*;
import java.util.*;

public class DryRun {
	public static void run() throws Exception {
		Scanner file = new Scanner(new File("DryRun.dat"));
		int times = file.nextInt(); file.nextLine(); file.nextLine();
		for (int ab = 0; ab < times; ab++) {
			System.out.println("I like "+ file.nextLine() + ".");
	
		}
	}

	public static void main(String[] args) throws Exception {
		new DryRun().run();
	}
}
