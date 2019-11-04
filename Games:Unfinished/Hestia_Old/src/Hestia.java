import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.awt.*;
import javax.swing.*;
//import it all because why not

public class Hestia{
	
	/*
	 * WHAT NEEDS WORK:
	 * 
	 * 
	 * Action menu will be vague but needs to be implemented to chop trees, fish, etc.
	 * 
	 * 
	 * A big enough fire will spread its sparks on the wind
	 * 
	 * Make a patrolling ranger that moves around with every bit of time
	 * 
	 * Maybe the player has to worry about their health and they can fish for food. 
	 * (Maybe like the fishing minigame from Link's Awakening but not as dumb)
	 * 
	 * Hide more items that have more effects than just fire size
	 * 
	 * Add story element if possible
	 * 
	 * Add GUI if JFrame ever cooperates (Going for an Oregon Trail look)
	 */
	
	
	
	
	public static void main(String[] args) throws InterruptedException{
		
		String intro = "This is Hestia. You have been given a fire. Your objective is to make it grow as large as possible. However, there are risks.\n"
					 + "If your fire gets too large, the Ranger will notice, and you will be arrested for arson. If your fire spreads to the city, \n"
					 + "you will be arrested for manslaughter. If your fire dies, you go along with it. Explore the area to improve your map and find\n"
					 + "resources. Use \"Quit\" on the main screen to stop the game. Saving is not supported yet. Press enter to start. -Matthew\n\n\n\n";
		System.out.println(intro);
		
		Game game = new Game();
		game.run();
	}
}












//This is a copy