import java.io.IOException;
import java.util.*;
public class SingleBoard {
	
	static Map<String, Double> map = new LinkedHashMap<String, Double>();
	static String[] places = {"Go", "Mediterranean", "Community Chest 1", "Baltic", "Income tax", "Reading Railroad", "Oriental", "Chance 1", "Vermont", "Connecticut", "Pass Jail", "Saint Charles", "Electric Company", "States", "Virginia", "Pennsylvania Railroad", "St. James", "Community Chest 2", "Tennessee", "New York", "Free Parking", "Kentucky", "Chance 2", "Indiana", "Illinois", "B&O Railroad", "Atlantic", "Ventnor", "Water works", "Marvin Gardens", "Go to jail", "Pacific", "North Carolina", "Community Chest 3", "Short Line Railroad", "Chance 3", "Park Place", "Luxury Tax", "Boardwalk"};
	
	public static void main(String[] args) throws IOException{
		
		for(String str : places) {
			map.put(str, 0.0);
		}
		
		double numTurns = 0;
		int[] players = new int[6];
		Random rand = new Random();
		
		for(int passes = 0; passes < 1000; passes++) {
			for(int i = 0; i < players.length; i++) {
				
				int p = players[i];
				
				int move = rand.nextInt(12) + 1;
				for(int x = 0; x < move; x++) {
					numTurns++;
				}
				p += move;
				if(p >= places.length) {
					p = p - places.length;
				}
				map.put(places[p], map.get(places[p]) + 1.0);
				players[i] = p;
			}
		}
		
		ArrayList<Double> data = new ArrayList<Double>();
		for(Double d : map.values()) {
			data.add(d);
		}
		
		for(int i = 0; i < map.size(); i++) {
			double fl = (data.get(i) / numTurns);
			double percentage = fl * 100;
			System.out.print(places[i] + " ");
			System.out.printf("%.3f", percentage);
			System.out.println("%");
		}
	}
}