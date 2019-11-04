import java.util.HashMap;
import java.util.Set;

public class Bag{
	
	//Leaving this as Object and not String because eventually I might make an item class? Still not sure.
	//The item and how many of it there are
	private HashMap<Item, Integer> stuff;
	
	public Bag() {
		stuff = new HashMap<Item, Integer>();
	}
	
	public void add(Item s, Integer i) {
		//If it already exists, just add the quantity
		if(stuff.containsKey(s)) {
			int temp = stuff.get(s);
			stuff.put(s,  temp + i);
		}
		else {
			//System.out.println(s);
			stuff.put(s,  i);
			//System.out.println(stuff.get(s));
		}
	}
	
	public int getNum(Item o) {
		Set<Item> keySet = stuff.keySet();
		Item[] keys = keySet.toArray(new Item[keySet.size()]);
		for(int i = 0; i < keys.length; i++) {
			if(keys[i].compareTo(o) == 1) {
				return stuff.get(keys[i]);
			}
		}
		return 0;
	}
	
	public void remove(Item o) {
		//System.out.println(stuff.get(o));
		//System.out.println(stuff);
		//System.out.println(o);
		if(getNum(o) == 1) {
			stuff.remove(o);
		}
		if(getNum(o) > 1) {
			int temp = getNum(o) - 1;
			Item temp2 = o;
			stuff.remove(o);
			//System.out.println(stuff);
			//System.out.println(temp);
			//System.out.println(temp2);
			//System.out.println(stuff.get(o));
			stuff.put(temp2,  temp);
		}
	}
	
	//Simpler way of checking for items than going through Map in my main
	public boolean contains(Item o) {
		Set<Item> keySet = stuff.keySet();
		if(keySet.contains(o)){
			return true;
		}
		return false;
	}
	//Only checking names
	public boolean containsName(String str) {
		str = str.toLowerCase();
		Set<Item> keySet = stuff.keySet();
		Item[] keys = keySet.toArray(new Item[keySet.size()]);
		for(int i = 0; i < keys.length; i++) {
			if(keys[i].getName().toLowerCase().equals(str)) {
				return true;
			}
		}
		return false;
	}
	
	//returns an item from a name
	public Item get(String name) {
		name = name.toLowerCase();
		Set<Item> keySet = stuff.keySet();
		Item[] keys = keySet.toArray(new Item[keySet.size()]);
		for(int i = 0; i < keys.length; i++) {
			if(keys[i].getName().toLowerCase().equals(name)) {
				return keys[i];
			}
		}
		return null;
	}
	
	//true if you can use it (and then you use it), false if you can't (and then you don't).
	public boolean useItem(Item usedItem) {
		if(contains(usedItem)) {
			remove(usedItem);
			return true;
		}
		return false;
	}
	
	//Copied from StackOverflow because why would I ever write this on my own?
	public static String toTitleCase(String input) {
	    StringBuilder titleCase = new StringBuilder();
	    boolean nextTitleCase = true;

	    for (char c : input.toCharArray()) {
	        if (Character.isSpaceChar(c)) {
	            nextTitleCase = true;
	        } else if (nextTitleCase) {
	            c = Character.toTitleCase(c);
	            nextTitleCase = false;
	        }

	        titleCase.append(c);
	    }

	    return titleCase.toString();
	}
	
	//Object x3
	public String toString() {
		String output = "";
		for(int i = 0; i < stuff.size(); i++) {
			String thisKey = stuff.keySet().toArray()[i].toString();
			output += thisKey + " x" + stuff.get(get(thisKey));
			output += "\n";
		}
		return output;
	}
}