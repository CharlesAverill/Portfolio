import java.io.*;
import java.util.*;

public class Friends {
	public void run() throws Exception {
		Scanner file = new Scanner(new File("Friends.dat"));
		int times = file.nextInt();
		file.nextLine();
		for (int ab = 0; ab < times; ab++) {
			int n = file.nextInt();
			file.nextLine();
			
			TreeMap<Integer, String> names = new TreeMap<Integer, String>();
			for(int i = 0; i < n; i++) {
				String name = file.next();
				
				names.put(file.nextInt(), name);
			}
			
			ArrayList<Integer> keys = new ArrayList<Integer>(names.keySet());
			
			int c = 0;
			for(Integer gg: names.descendingKeySet()) {
				if(c == names.descendingKeySet().size() - 1) {
					System.out.print(names.get(gg));
				}
				else {				
					System.out.print(names.get(gg) +", ");
				}
				c++;
			}
			
			System.out.println();
		}
	}

	public static void main(String[] args) throws Exception {
		new Friends().run();
	}
}