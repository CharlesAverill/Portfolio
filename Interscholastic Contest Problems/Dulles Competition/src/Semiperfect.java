import java.io.*;
import java.util.*;

public class Semiperfect {
	public void run() throws Exception {
		Scanner file = new Scanner(new File("Semiperfect.dat"));
		int times = file.nextInt();
		file.nextLine();
		
		for (int ab = 0; ab < times; ab++) {
			int n = file.nextInt();
			if(ab != times - 1) {
				file.nextLine();
			}
			
			ArrayList<Integer> factors = new ArrayList<Integer>();
			for(int x = 1; x < n; x++) {
				if(n % x == 0) {
					factors.add(x);
				}
			}
			
			
			
			if(permutations(factors, 0, 0, n)) {
				System.out.println("Semiperfect");
			}
			else {
				System.out.println("NOT Semiperfect");
			}
		}
	}
	
	public boolean permutations(ArrayList<Integer> factors, int index, int count, int goal) {
		if(count == goal) {
			return true;
		}
		if(index > factors.size() - 1 || index < 0) {
			return false;
		}
		count += factors.get(index);
		return permutations(factors, index - 1, count, goal) || permutations(factors, index + 1, count, goal);
	}

	public static void main(String[] args) throws Exception {
		new Semiperfect().run();
	}
}