public class Area {
	private String description;
	private Item item1;
	private Item item2;
	private Item item3;
	private int ID;
	
	public Area(int i) {
		ID = i;
		description = "\n\n\n\n\n\n";
		setup();
	}
	
	public void setup() {
		if(ID == 1) {
			
		}
		if(ID == 2) {
			
		}
		if(ID == 3) {
			
		}
		if(ID == 4) {
			
		}
		if(ID == 5) {
			
		}
		if(ID == 6) {
			
		}
		if(ID == 7) {
			
		}
		if(ID == 8) {
			description += "Hiding behind a tree, you notice the Ranger's station.\n You can almost see his watchful eyes through the tinted window.";
			item1 = new Item("Fire Extinguisher", true);
		}
		if(ID == 9) {
			
		}
		if(ID == 10) {
			
		}
		if(ID == 11) {
			
		}
		if(ID == 12) {
			
		}
		if(ID == 13) {
			
		}
		if(ID == 14) {
			
		}
		if(ID == 15) {
			
		}
		if(ID == 16) {
			
		}
		if(ID == 17) {
			
		}
		if(ID == 18) {
			
		}
		if(ID == 19) {
			
		}
		if(ID == 20) {
			
		}
		if(ID == 21) {
			
		}
		if(ID == 22) {
			
		}
		if(ID == 23) {
			
		}
		if(ID == 24) {
			
		}
		if(ID == 25) {
			description += "Around your fire lay your pack, tools and extra firewood.";
			item1 = new Item("Axe", false);
			item2 = new Item("Wood", true);
			item3 = new Item("Wood", true);
		}
		if(ID == 26) {
			
		}
		if(ID == 27) {
			
		}
		if(ID == 28) {
			
		}
		if(ID == 29) {
			
		}
		if(ID == 30) {
			
		}
		if(ID == 31) {
			
		}
		if(ID == 32) {
			
		}
		if(ID == 33) {
			
		}
		if(ID == 34) {
			
		}
		if(ID == 35) {
			
		}
		if(ID == 36) {
			
		}
		if(ID == 37) {
			
		}
		if(ID == 38) {
			
		}
		if(ID == 39) {
			
		}
		if(ID == 40) {
			
		}
		if(ID == 41) {
			description += "Your senses are overloaded from the lights and noise, making\n you long for the serenity of your campfire. You also notice\nan unguarded drone kiosk nearby.";
			item1 = new Item("Drone", false);
		}
		if(ID == 42) {
			
		}
		if(ID == 43) {
			
		}
		if(ID == 44) {
			
		}
		if(ID == 45) {
			
		}
		if(ID == 46) {
			
		}
		if(ID == 47) {
			
		}
		if(ID == 48) {
			
		}
		if(ID == 49) {
			
		}
	}
	
	public String getDescription() {
		return description;
	}
	
	public Item[] getItems() {
		int num = 0;
		if(item1 != null) {
			num++;
		}
		if(item2 != null) {
			num++;
		}
		if(item3 != null) {
			num++;
		}
		Item[] output = new Item[num];
		num = 0;
		if(item1 != null) {
			output[num++] = item1;
		}
		if(item2 != null) {
			output[num++] = item2;
		}
		if(item3 != null) {
			output[num++] = item3;
		}
		return output;
	}
}
