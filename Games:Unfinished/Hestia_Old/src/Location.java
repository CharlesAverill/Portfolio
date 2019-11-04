public class Location{
	
	//fullmap lists all biomes, ranger stations, lakes, city, etc. Only for internal reference, never accessed by player
	private static String[][] fullmap;
	//playerMap is what the player has been to so far. 
	private static String[][] playerMap;
	//topography defines where the player can and cannot move.
	private static String[][] topography;
	//items lists where hidden items are. Still trying to think of a better way to do this without instantiating 49 Areas
	private static Item[][] items;
	//fires lists where all fires are. Not accessible by player.
	private static Fire[][] fires;
	//Drone map, shows the ranger and your fires
	private static String[][] droneMap;
	//For "look" descriptions
	private static Area[][] lookMap;
	//player's y coord
	public static int row;
	//player's x coord
	public static int column;
	//For text fluidity
	public static boolean movedOnWaterLastTurn;
	
	public Location() {
		//Starts off at Home
		row = 3;
		column = 3;
		//F is forest
		//R is a Ranger Station
		//W is Water
		//H is your Home/Fire
		//C is the City
		fullmap = new String[][] {
			{"F", "F", "F", "F", "F", "F", "F"}, 
			{"R", "F", "F", "F", "F", "F", "F"},
			{"W", "F", "F", "F", "F", "F", "F"},
			{"W", "W", "F", "H", "F", "F", "F"},
			{"W", "W", "F", "F", "F", "F", "F"},
			{"W", "W", "F", "F", "F", "C", "C"},
			{"W", "W", "F", "F", "F", "C", "C"},
			{"W", "W", "F", "F", "F", "C", "C"}
		};
		//A is normal, traversable ground
		//Z is water, or rough, non-traversable ground (except by boat, which might happen later on)
		topography = new String[][] {
			{"A", "A", "A", "A", "A", "A", "A"}, 
			{"A", "A", "A", "A", "A", "A", "A"},
			{"Z", "A", "A", "A", "A", "A", "A"},
			{"Z", "Z", "A", "A", "A", "A", "A"},
			{"Z", "Z", "A", "A", "A", "A", "A"},
			{"Z", "Z", "A", "A", "A", "A", "A"},
			{"Z", "Z", "A", "A", "A", "A", "A"}
		};
		
		//Empty until you walk around on the squares. With GUI comes a color map
		playerMap = new String[][] {
			{"O", "O", "O", "O", "O", "O", "O"},
			{"O", "O", "O", "O", "O", "O", "O"},
			{"O", "O", "O", "O", "O", "O", "O"},
			{"O", "O", "O", "H", "O", "O", "O"},
			{"O", "O", "O", "O", "O", "O", "O"},
			{"O", "O", "O", "O", "O", "O", "O"},
			{"O", "O", "O", "O", "O", "O", "O"}
		};
		
		items = new Item[][] {
			{null, null, null, null, null, null, new Item("Lighter Fluid", true)},
			{null, null, null, null, null, null, null},
			{null, new Item("Boat", false), null, null, null, null},
			{null, null, null, new Item("Axe", false), null, null, null},
			{null, null, null, null, null, null, null},
			{null, null, null, null, null, null, new Item("Drone", false)},
			{null, null, new Item("Fishing Pole", false), null, null, new Item("Fire Extinguisher", true), null}
		};
		
		droneMap = new String[7][7];
		for(String[] r : droneMap) {
			for(String c : r) {
				c = "";
			}
		}
		
		//Fire class comes in handy
		fires = new Fire[][] {
			{null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null},
			{null, null, null, Game.fire, null, null, null},
			{null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null}
		};
		
		lookMap = new Area[][] {
			{new Area(1), new Area(2), new Area(3), new Area(4), new Area(5), new Area(6), new Area(7)},
			{new Area(8), new Area(9), new Area(10), new Area(11), new Area(12), new Area(13), new Area(14)},
			{new Area(15), new Area(16), new Area(17), new Area(18), new Area(19), new Area(20), new Area(21)},
			{new Area(22), new Area(23), new Area(24), new Area(25), new Area(26), new Area(27), new Area(28)},
			{new Area(29), new Area(30), new Area(31), new Area(32), new Area(33), new Area(34), new Area(35)},
			{new Area(36), new Area(37), new Area(38), new Area(39), new Area(40), new Area(41), new Area(42)},
			{new Area(43), new Area(44), new Area(45), new Area(46), new Area(47), new Area(48), new Area(49)}
		};
		
		movedOnWaterLastTurn = false;
	}
	
	public static int getX() {
		return row;
	}
	public static int getY() {
		return column;
	}
	
	public String dangerAreas() {
		for(int r = 0; r < fires.length; r++) {
			for(int c = 0; c < fires[r].length; c++) {
				if(fires[r][c] != null) {
					if(fullmap[r][c].equals("R")) {
						return ("Ranger");
					}
					if(fullmap[r][c].equals("C")) {
						return ("City");
					}
				}
			}
		}
		return null;
	}
	
	//Allows player to check any specific fire that they're in the same area as
	public Fire getFire() {
		return fires[row][column];
	}
	//Deals with adding/spreading fires
	public void addFire(int x, int y, Fire f) {
		fires[x][y] = f;
	}
	
	public Fire[][] getFires(){
		return fires;
	}
	
	//Updates playerMap
	public void playerMapAdd() {
		playerMap[row][column] = fullmap[row][column];
	}
	
	//updates droneMap
	private void updateDroneMap() {
		for(int r = 0; r < droneMap.length; r++) {
			for(int c = 0; c < droneMap[r].length; c++) {
				if(fires[r][c] != null) {
					//Adds instead of resets so that the Ranger can be in the same tile as a fire
					//Ranger not implemented yet
					droneMap[r][c] += "F";
				}
			}
		}
	}
	
	//Checks if you hit the boundary so that the game gives you the right "turn back" message
	public static boolean hitsBoundary(String direction) {
		direction = direction.toLowerCase();
		if(direction.equals("north") || direction.equals("up")) {
			return (row == 0);
		}
		if(direction.equals("south") || direction.equals("down")) {
			return (row == fullmap.length - 1);
		}
		if(direction.equals("east") || direction.equals("right")) {
			return (column == fullmap[row].length - 1);
		}
		if(direction.equals("west") || direction.equals("left")) {
			return (column == 0);
		}
		
		return false;
	}
	
	//Same as above with water
	public static boolean hitsWater(String direction) {
		direction = direction.toLowerCase();
		if(direction.equals("north") || direction.equals("up")) {
			return (fullmap[row - 1][column] == "W");
		}
		if(direction.equals("south") || direction.equals("down")) {
			return (fullmap[row + 1][column] == "W");
		}
		if(direction.equals("east") || direction.equals("right")) {
			return (fullmap[row][column + 1] == "W");
		}
		if(direction.equals("west") || direction.equals("left")) {
			return (fullmap[row][column - 1] == "W");
		}
		return false;
	}
	
	//Returns true if you moved, false if you didn't
	public boolean move(String direction, Bag b) {
		direction = direction.toLowerCase();
		if(b.containsName("Boat")){
			if(direction.equals("north") || direction.equals("up")) {
				if(!hitsBoundary(direction)) {
					row--;
					if(fullmap[row][column].equals("W")) {
						if(!movedOnWaterLastTurn) {
							System.out.println("You used your boat to travel on the water.");
						}
						movedOnWaterLastTurn = true;
						return true;
					}
					else {
						movedOnWaterLastTurn = false;
						return true;
					}
				}
			}
			if(direction.equals("south") || direction.equals("down")) {
				if(!hitsBoundary(direction)) {
					row++;
					if(fullmap[row][column].equals("W")) {
						if(!movedOnWaterLastTurn) {
							System.out.println("You used your boat to travel on the water.");
						}
						movedOnWaterLastTurn = true;
						return true;
					}
					else return true;
				}
			}
			if(direction.equals("east") || direction.equals("right")) {
				if(!hitsBoundary(direction)) {
					column++;
					if(fullmap[row][column].equals("W")) {
						if(!movedOnWaterLastTurn) {
							System.out.println("You used your boat to travel on the water.");
						}
						movedOnWaterLastTurn = true;
						return true;
					}
					else {
						movedOnWaterLastTurn = false;
						return true;
					}
				}
			}
			if(direction.equals("west") || direction.equals("left")) {
				if(!hitsBoundary(direction)) {
					column--;
					if(fullmap[row][column].equals("W")) {
						if(!movedOnWaterLastTurn) {
							System.out.println("You used your boat to travel on the water.");
						}
						movedOnWaterLastTurn = true;
						return true;
					}
					else {
						movedOnWaterLastTurn = false;
						return true;
					}
				}
			}
		}
		else {
			if(direction.equals("north") || direction.equals("up")) {
				if(row > 0 && topography[row - 1][column].equals("A")) {
					row--;
					movedOnWaterLastTurn = false;
					return true;
				}
			}
			if(direction.equals("south") || direction.equals("down")) {
				if(row < fullmap[row].length - 1 && topography[row + 1][column].equals("A")) {
					row++;
					movedOnWaterLastTurn = false;
					return true;
				}
			}
			if(direction.equals("east") || direction.equals("right")) {
				if(column < fullmap.length - 1 && topography[row][column + 1].equals("A")) {
					column++;
					movedOnWaterLastTurn = false;
					return true;
				}
			}
			if(direction.equals("west") || direction.equals("left")) {
				if(column > 0 && topography[row][column - 1].equals("A")) {
					column--;
					movedOnWaterLastTurn = false;
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isOnWater() {
		return fullmap[row][column].equals("W");
	}
	
	//Used for flavor text
	public String getBiome() {
		if(fullmap[row][column].equals("F")) {
			return "You are in a Forest.";
		}
		if(fullmap[row][column].equals("W")) {
			return "You are in Water.";
		}
		if(fullmap[row][column].equals("R")) {
			return "You are close to the Ranger Station.";
		}
		if(fullmap[row][column].equals("H")) {
			return "You are with your fire.";
		}
		if(fullmap[row][column].equals("C")) {
			return "You are in the city.";
		}
		return "You managed to escape existence. Congrats.";
	}
	
	public String getDescription(int a, int b) {
		return lookMap[a][b].getDescription();
	}
	
	public String fullMapToString() {
		String output = "";
		for(int r = 0; r < fullmap.length; r++) {
			for(int c = 0; c < fullmap[r].length; c++) {
				output += fullmap[r][c] + " ";
			}
			output += "\n";
		}
		return output;
	}
	
	public String droneMapToString() {
		String output = "";
		for(int r = 0; r < droneMap.length; r++) {
			for(int c = 0; c < droneMap[r].length; c++) {
				output += droneMap[r][c] + " ";
			}
			output += "\n";
		}
		return output;
	}
	
	public String playerMapToString() {
		String output = "\n";
		for(int r = 0; r < playerMap.length; r++) {
			for(int c = 0; c < playerMap[r].length; c++) {
				output += playerMap[r][c] + " ";
			}
			output += "\n";
		}
		output += "(" + (row + 1) + ", " + (column + 1) + ")";
		return output;
	}
}