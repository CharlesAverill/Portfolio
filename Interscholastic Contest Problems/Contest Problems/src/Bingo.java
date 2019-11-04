import java.io.*;
import java.util.*;

public class Bingo {
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("bingo.dat"));
		
		int n  = file.nextInt();
		file.nextLine();
		
		for(int i = 0; i < n; i++) {
			int[][] card = new int[5][5];
			for(int x = 0; x < 5; x++) {
				for(int y = 0; y < 5; y++) {
					String token = file.next();
					if(!token.equals("*")) {
						card[i][x] = Integer.parseInt(token);
					}
					else {
						card[i][x] = -1;
					}
				}
				if(file.hasNextLine()) {
					file.nextLine();
				}
			}
			boolean valid = true;
			for(int x = 0; x < 5; x++) {
				for(int y = 0; y < 5; y++) {
					switch(x) {
						case 0:
							if(!(card[x][y] >= 1 && card[x][y] <= 15)) {
								valid = false;
							}
						case 1:
							if(!(card[x][y] >= 16 && card[x][y] <= 30)) {
								valid = false;
							}
						case 2:
							if(!((card[x][y] >= 31 && card[x][y] <= 45) || card[x][y] == -1)) {
								valid = false;
							}
						case 3:
							if(!(card[x][y] >= 46 && card[x][y] <= 60)) {
								valid = false;
							}
						case 4:
							if(!(card[x][y] >= 61 && card[x][y] <= 75)) {
								valid = false;
							}
					}	
				}
			}
			if(valid) {
				System.out.println("VALID");
			}
			else {
				System.out.println("INVALID");
			}
		}
	}
}
