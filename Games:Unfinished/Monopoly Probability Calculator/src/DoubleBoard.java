import java.io.IOException;
import java.util.*;
public class DoubleBoard {
	
	static Map<String, Double> map = new LinkedHashMap<String, Double>();
	static String[] places = {"Go", "Mediterranean", "Community Chest 1", "Baltic", "Income tax", "Reading Railroad", "Oriental", "Chance 1", "Vermont", "Connecticut", "Pass Jail", "Saint Charles", "Electric Company", "States", "Virginia", "Pennsylvania Railroad", "St. James", "Community Chest 2", "Tennessee", "New York", "Free Parking", "Kentucky", "Chance 2", "Indiana", "Illinois", "B&O Railroad", "Atlantic", "Ventnor", "Water works", "Marvin Gardens", "Go to jail", "Pacific", "North Carolina", "Community Chest 3", "Short Line Railroad", "Chance 3", "Park Place", "Luxury Tax", "Boardwalk", "Andy\'s Room", "Speeding Fine", "Sid\'s Room", "Piston Cup 1", "RC", "Ramone\'s house of body art", "Bon appetit 1", "Luigi\'s Casa Del Tires", "Flo\'s V8 Cafe", "Go To Jail 2", "A\'s toy barn", "laugh power", "al\'s apartment", "zurgs planet", "tow mater towing and salvage", "monsters inc", "harry housens", "piston cup 2", "sully and mikes apartment", "free parking 2", "eiffel tower", "sewers of paris", "bon appetit 2", "gusteaus restauraunt", "dinoco helicopter", "great barrier reef", "bubbles treasure chest", "scream power", "gills pirate school", "visiting jail 2", "syndromes base", "edna modes mansion", "piston cup 2", "parrs family home", "the incredible", "insurance premium", "ant island", "bon appetit 3", "pt fleas circus"};
	
	public static void main(String[] args) throws IOException{
		
		for(String str : places) {
			map.put(str, 0.0);
		}
		
		double numTurns = 0;
		int[] players = new int[6];
		Random rand = new Random();
		
		for(int passes = 0; passes < 100; passes++) {
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
			System.out.println(places[i] + " " + percentage + "%");
		}
	}
}