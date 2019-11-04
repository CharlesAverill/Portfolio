import java.io.*;
import java.util.*;

public class Organizing {
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("organizing.in"));
		
		int n = file.nextInt();
		
		ArrayList<Item> items = new ArrayList<Item>();
		
		for(int i = 0; i < n; i++) {
			file.nextLine();
			items.add(new Item(file.next(), file.nextInt(), file.nextInt(), file.nextInt()));
		}
		
		Collections.sort(items);
		
		for(Item i : items) {
			System.out.print(i.name + ", ");
		}
	}
}

class Item implements Comparable{
	public String name;
	public int utility;
	public int weight;
	public int cost;
	
	public Item(String a, int b, int c, int d) {
		name = a;
		utility = b;
		weight = c;
		cost = d;
	}
	
	public String getName() {
		return name;
	}
	
	public int getUtility() {
		return utility;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public int getCost() {
		return cost;
	}
	
	public int compareTo(Object o) {
		Item i = (Item)o;
		if(utility != i.getUtility()) {
			if(utility > i.getUtility()) {
				return -1;
			}
			return 1;
		}
		if(weight != i.getWeight()) {
			if(weight < i.getWeight()) {
				return -1;
			}
			return 1;
		}
		if(cost < i.getCost()) {
			return -1;
		}
		return 1;
	}
}
