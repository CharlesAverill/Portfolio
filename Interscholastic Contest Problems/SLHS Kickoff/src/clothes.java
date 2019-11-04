import java.io.*;
import java.util.*;

public class clothes {
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("clothes.in"));
		
		int n = file.nextInt();
		file.nextLine();
		
		for(int i = 0; i < n; i++) {
			int c = file.nextInt();
			file.nextLine();
			
			ArrayList<Item> items = new ArrayList<Item>();
			for(int x = 0; x < c; x++) {
				items.add(new Item(file.next(), file.next(), file.next(), file.next()));
				if(x != c - 1) {
					file.nextLine();
				}
			}
			
			Collections.sort(items, new Comparator<Item>() {
				public int compare(Item a, Item b) {
					if(!a.type.equals(b.type)) {
						if(!a.color.equals(b.color)) {
							if(!a.store.equals(b.store)) {
								if(a.type.compareTo(b.type) > 0) {
									return 1;
								}
								return -1;
							}
							if(a.color.compareTo(b.color) > 0) {
								return 1;
							}
							return -1;
						}
						if(a.store.compareTo(b.store) > 0) {
							return 1;
						}
						return -1;
					}
					return 0;
				}
			});
			
			for(int x = 0; x < items.size(); x++) {
				System.out.println(items.get(x).name);
			}
		}
	}
}

class Item{
	public String name;
	public String type;
	public String color;
	public String store;
	
	public Item(String a, String b, String c, String d) {
		name = a;
		type = b;
		color = c;
		store = d;
	}
}