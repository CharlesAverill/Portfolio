import java.io.*;
import java.util.*;

public class Presidents {
	public void run() throws Exception {
		Scanner file = new Scanner(new File("Presidents.dat"));
		int times = file.nextInt(); file.nextLine();
		for (int ab = 0; ab < times; ab++) {
			
			String[] pres = file.nextLine().split(" ");
			int total = 0;
			for(String gg:pres) {
				switch(gg) {
					case("Franklin"):
						total+=100; break;
					case("Grant"):
						total+=50; break;
					case("Jackson"):
						total+=20; break;
					case("Hamilton"):
						total+=10; break;
					case("Lincoln"):
						total+=5; break;
					case("Washington"):
						total+=1; break;
				}
			}
			System.out.println("$" + total);
		}
	}

	public static void main(String[] args) throws Exception {
		new Presidents().run();
	}
}