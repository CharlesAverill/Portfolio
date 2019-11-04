import java.util.*;
import java.io.*;

public class Dryrun {
	public static void main(String[] args) throws IOException{
		Scanner sc = new Scanner(new File("dryrun.in"));
		for(int i = 0; i < 4; i++) {
			int n = sc.nextInt();
			System.out.println("The PACER Test score is " + n + ".");
		}
	}
}
