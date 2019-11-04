import java.io.*;
import java.util.*;

public class Castle {
	public void run() throws Exception {
		Scanner file = new Scanner(new File("castle.in"));
		PrintWriter pw = new PrintWriter(new File("castle.out"));
		
		int width = file.nextInt();
		int height = file.nextInt(); file.nextLine();
		
		Module[][] rooms = new Module[height][width];
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int room = file.nextInt();
				
				rooms[y][x] = new Module(room);
			}
			
			if(file.hasNextLine()) {
				file.nextLine();
			}
		}
		
		
		
		/*
		for (int i = 0; i < height; i++)
		    for (int j = 0; j < width; j++) {
			System.out.println("" + i + ", " + j);
			if (rooms[i][j].n)
			    System.out.println("###");
			else
			    System.out.println("# #");
			if (rooms[i][j].w)
			    System.out.print("#");
			else
			    System.out.print(' ');
			System.out.print(' ');
			if (rooms[i][j].e)
			    System.out.println("#");
			else
			    System.out.println(' ');
			if (rooms[i][j].s)
			    System.out.println("###");
			else
			    System.out.println("# #");
			System.out.println();
		}
		*/
		
		file.close();
		pw.close();
	}

	public static void main(String[] args) throws Exception {
		new Castle().run();
	}
}

class Module{
	boolean n;
	boolean s;
	boolean e;
	boolean w;
	
	public Module(int x) {
		// North
		if (x >= 8) {
		    s = true;
		    x -= 8;
		} else
		    s = false;

		// East;
		if (x >= 4) {
		    e = true;
		    x -= 4;
		} else
		    e = false;

		// North;
		if (x >= 2) {
		    n = true;
		    x -= 2;
		} else
		    n = false;

		// West;
		if (x >= 1) {
		    w = true;
		    x -= 1;
		} else
		    w = false;
	}
}