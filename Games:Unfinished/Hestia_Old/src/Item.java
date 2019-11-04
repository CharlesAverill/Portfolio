public class Item implements Comparable<Item>{
	
	private String name;
	private boolean flammable;
	
	public Item(String n, Boolean b) {
		name = n;
		flammable = b;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean canBurn() {
		return flammable;
	}
	
	public String use(Location loc) {
		if(name.equals("Axe")) {
			if(loc.getBiome().equals("You are in a Forest.")) {
				return "You use your axe to chop down a tree for firewood.";
			}
			else {
				return "Try using this in a forest.";
			}
		}
		if(name.equals("Boat")) {
			if(loc.isOnWater()) {
				System.out.println("You're already in your boat.");
			}
			else System.out.println("You try rowing, but notice that the ground is harder to push against than water.");
		}
		if(name.equals("Drone")) {
			System.out.println(loc.droneMapToString());
			return "";
		}
		if(name.equals("Fishing Pole")) {
			if(!loc.isOnWater()) {
				System.out.println("You try to catch the legendary landfish, but to no avail.");
			}
			else System.out.println("You're now fishing.");
		}
		return "You can't use that item.";
	}
	
	
	
	public String getDescription() {
		if(name.toLowerCase().equals("wood")) {
			return "Used to help a fire's growth.";
		}
		if(name.toLowerCase().equals("axe")) {
			return "Used to chop down trees.";
		}
		if(name.toLowerCase().equals("water")) {
			return "Used to hinder a fire's growth.";
		}
		if(name.toLowerCase().equals("lighter fluid")) {
			return "A powerful tool to greatly increase your fire's size.";
		}
		if(name.toLowerCase().equals("fire extinguisher")) {
			return "A powerful tool to immediately put out most fires.";
		}
		if(name.toLowerCase().equals("boat")) {
			return "Used to travel over water.";
		}
		if(name.toLowerCase().equals("fishing Pole")) {
			return "Used to catch fish";
		}
		if(name.toLowerCase().equals("drone")) {
			return "Shows the location of all of your fires.";
		}
		return null;
	}
	
	public int compareTo(Item o) {
		if(o.getName().equals(getName()) && o.canBurn() == canBurn()) {
			return 1;
		}
		return 0;
	}
	
	public String toString() {
		return name;
	}
}