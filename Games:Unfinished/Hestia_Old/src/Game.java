import java.util.Random;
import java.util.Scanner;

public class Game {
	
	private static Scanner input;
	private static boolean on;
	public static Fire fire;
	public static Bag inventory;
	public static Time masterTime;
	public static Location location;
	public static Wind wind;
	
	public Game() {
		masterTime = new Time();
		input = new Scanner(System.in);
		on = true;
		fire = new Fire();
		wind = new Wind();
	}
	
	public static void showMainMenu() {
		String output = "\n\n\n\n\n\n";
		output += "Check";
		output += "\n";
		output += "Inventory";
		output += "\n";
		output += "Feed";
		output += "\n";
		output += "Action (Limited)";
		output += "\n";
		output += "Explore";
		output += "\n";
		output += "Map";
		System.out.println(output);
	}
	
	public static void showCheckMenu(Fire f) {
		System.out.println("\n\n\n\n\n\n" + f.toString());
	}
	
	public static void showInventoryMenu(Bag bag) {
		System.out.println(bag.toString());
	}
	
	public static void showActionMenu() {
		String output = "\n\n\n\n\n\n";
		output += "Look";
		output += "\n";
		output += "Use";
		output += "\n";
		System.out.println(output);
	}
	
	public static void run() {
		Bag inventory = new Bag();
		Location location = new Location();
		input.nextLine();
		
		Item axe = new Item("Axe", false);
		Item boat = new Item("Boat", false);
		Item wood = new Item("Wood", true);
		Item water = new Item("Water", true);
		Item fireExtinguisher = new Item("Fire Extinguisher", true);
		Item lighterFluid = new Item("Lighter Fluid", true);
		inventory.add(wood,  5);
		inventory.add(water, 5);
		inventory.add(axe,  1);
		inventory.add(boat,  1);
		inventory.add(fireExtinguisher,  1);
		inventory.add(lighterFluid,  1);
		
		while(on) {
			
			boolean finished = false;
			
			int timePassed = 0;
			
			showMainMenu();
			
			//The menu choice
			String tempInput = input.nextLine().toLowerCase();
			
			if(tempInput.equals("quit")) {
				//2 methods just for backup
				on = false;
				finished = true;
				break;
			}
			
			//Check Menu
			if(tempInput.equals("check")) {
				if(location.getFire() != null) {
					//If there's a fire here, its stats are shown
					showCheckMenu(location.getFire());
				}
				else {
					System.out.println("There's no fire here.");
				}
			}
			
			//Inventory
			if(tempInput.equals("inventory")) {
				showInventoryMenu(inventory);
				String nextInput = input.nextLine();

				if(!nextInput.isEmpty()) {
					if(inventory.containsName(nextInput)) {
						System.out.println(inventory.get(nextInput).getDescription());
						finished = false;
					}
					else {
						System.out.println("You don't have that.");
						finished = false;
					}
				}
				else finished = true;
			}
			
			//Feed
			if(tempInput.equals("feed")) {
				
				boolean done = false;
				
				if(location.getFire() == null) {
					done = true;
					System.out.println("There's no fire to feed here.");
				}
				
				while(!done) {
					System.out.println("What will you put into the fire?");
					
					Item choice = inventory.get(input.nextLine());
					//System.out.println(choice);
					if(inventory.contains(choice) && inventory.useItem(choice)) {
						location.getFire().feed(choice);
						//All feeding takes 1 minute to complete
						timePassed += 1;
						finished = true;
						break;
					}
					
					else {
						System.out.println("You don't have that.");
						continue;
					}
				}
			}
			
			//Action
			if(tempInput.equals("action")) {
				
				String nextInput = "";
				while(!nextInput.toLowerCase().equals("back")) {

					showActionMenu();
					
					nextInput = input.nextLine();
					
					if(nextInput.toLowerCase().equals("quit")) {
						on = false;
						finished = true;
						break;
					}
					
					if(nextInput.toLowerCase().equals("look")) {
						System.out.println(location.getDescription(location.row, location.column));
						input.nextLine();
						timePassed += 15;
						break;
					}
					
					if(nextInput.toLowerCase().equals("use")) {
						boolean done = false;
						while(!done) {
							showInventoryMenu(inventory);
							System.out.println("Which item?");
							String choice = input.next();
							if(inventory.containsName(choice)) {
								System.out.println(inventory.get(choice).use(location));
								if(inventory.get(choice).use(location).equals("You use your axe to chop down a tree for firewood.")) {
									Random rand = new Random();
									int chopped = rand.nextInt(5) + 1;
									System.out.println(("You got " + chopped + " wood."));
									inventory.add(wood,  chopped);
									timePassed += 30;
								}
								//I really don't know why I need two of these but it skips one for some reason (So don't delete them)
								input.nextLine();
								input.nextLine();
								break;
							}
						}
					}
				}
				finished = true;
			}
			
			
			//Explore
			if(tempInput.equals("explore")) {
				
				String direction = "";
				
				//Keeps asking for direction until you want to stop moving. Maybe make a toggle later
				while(!direction.toLowerCase().equals("back")) {
					
					if(direction.toLowerCase().equals("quit")) {
						on = false;
						finished = true;
						break;
					}
					
					System.out.println("What direction?");
					
					direction = input.nextLine();
					
					//If you can move, you do, and it tells you what kind of area you're in
					if(location.move(direction, inventory)) {
						
						System.out.println(location.getBiome());
						//All moving takes 5 minutes
						timePassed += 5;
						//Updates the player's map
						location.playerMapAdd();
					}
					//If you can't move
					else {
						if(location.hitsBoundary(direction)) {
							System.out.println("If you go too far, your fire could grow out of control");
						}
						else if(location.hitsWater(direction)) {
								System.out.println("The water looks deep. Maybe you should find a boat.");
						}
						else if(direction.toLowerCase().equals("back")) {
							break;
						}
						else if(direction.toLowerCase().equals("quit")) {
							on = false;
						}
						else System.out.println("That's not a direction.");
					}
				}
				finished = true;
			}
			
			//Map
			if(tempInput.equals("map")) {
				System.out.println("\n\n\n\n" + location.playerMapToString());
				finished = false;
			}
			
			//PASSIVE ACTIONS
			
			//Fire spreading
			double d = Math.random();
			if(wind.getDuration() > 0) {
				if(wind.getStrength() * d > 25 && wind.getStrength() * d < 50) {
					Fire.spreadMap(wind, location);
				}
				wind.passTime(timePassed);
			}
			else {
				wind = new Wind();
				if(wind.getStrength() * d > 25 && wind.getStrength() * d < 50) {
					Fire.spreadMap(wind, location);
				}
			}
			
			if(location.dangerAreas() != null && location.dangerAreas().equals("Ranger")) {
				System.out.println("Your fire has spread to the Ranger station! Quickly put it out before you are tracked.");
			}
			if(location.dangerAreas() != null && location.dangerAreas().equals("City")) {
				System.out.println("Your fire has spread to the City! The firemen there will put it out, but you need to"
						+ "\nput out the forest fires before you're tracked!");
			}
			
			//Obvious
			if(fire.getSize() <= 0) {
				System.out.println("Your fire has died.");
				on = false;
			}
			//A buffer to give the player time to read what just happened
			else if(!finished){
				input.nextLine();
			}
		}
	}
}
