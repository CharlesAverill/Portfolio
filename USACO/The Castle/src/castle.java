import java.io.*;
import java.util.*;

public class castle {
	
	public static Module[][] castle;
	public static boolean[][] visited;
	
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("castle.in"));
		PrintWriter pw = new PrintWriter(new File("castle.out"));
		
		int m = file.nextInt();
		int n = file.nextInt();
		file.nextLine();
		
		castle = new Module[n][m];
		
		for(int i = 0; i < n; i++) {
			String[] temp = file.nextLine().split(" ");
			for(int x = 0; x < m; x++) {
				castle[i][x] = new Module(getWalls(Integer.parseInt(temp[x])), n);
			}
		}
		file.close();
		
		pw.println(m * n);
	}
	
	public static ArrayList<Integer> getWalls(int n){
		ArrayList<Integer> output = new ArrayList<Integer>();
		if(n >= 8) {
			n -= 8;
			output.add(8);
		}
		if(n >= 4) {
			n -= 4;
			output.add(4);
		}
		if(n >= 2) {
			n -= 2;
			output.add(2);
		}
		if(n >= 1) {
			n -= 1;
			output.add(1);
		}
		return output;
	}
}

class Module{
	public boolean north = false;
	public boolean south = false;
	public boolean east = false;
	public boolean west = false;
	public int n;
	
	public Module(ArrayList<Integer> walls, int x) {
		if(walls.contains(1)) {
			west = true;
		}
		if(walls.contains(2)) {
			north = true;
		}
		if(walls.contains(4)) {
			east = true;
		}
		if(walls.contains(8)) {
			south = true;
		}
		n = x;
	}
	
	public String toString() {
		String output = "";
		if(north) {
			output += " _";
		}
		else output += "  ";
		output += "\n";
		if(west) {
			output += "|";
		}
		else output += " ";
		if(south) {
			output += "_";
		}
		else output += " ";
		if(east) {
			output += "|";
		}
		else output += " ";
		return output;
	}
}