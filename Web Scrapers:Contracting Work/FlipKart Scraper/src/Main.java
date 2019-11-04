/*
Author: Matthew Averill

DISCLAIMER:
This SOFTWARE PRODUCT is provided by THE PROVIDER "as is" and "with all faults." 
THE PROVIDER makes no representations or warranties of any kind concerning the safety, 
suitability, lack of viruses, inaccuracies, typographical errors, or other harmful components 
of this SOFTWARE PRODUCT. There are inherent dangers in the use of any software, and you are 
solely responsible for determining whether this SOFTWARE PRODUCT is compatible with your 
equipment and other software installed on your equipment. You are also solely responsible 
for the protection of your equipment and backup of your data, and THE PROVIDER will not be 
liable for any damages you may suffer in connection with using, modifying, 
or distributing this SOFTWARE PRODUCT.

NOT FOR REDISTRIBUTION
*/

import java.io.*;
import java.net.URL;
import java.util.*;

public class Main implements Runnable{
	
	static String search;
	
	static SearchPage sp;
	
	public static void main(String[] args) throws IOException, InterruptedException{
		new Main().run();
	}
	
	public void run() {
		System.out.println("DISCLAIMER:\n" + 
				"This SOFTWARE PRODUCT is provided by THE PROVIDER \"as is\" and \"with all faults.\" \n" + 
				"THE PROVIDER makes no representations or warranties of any kind concerning the safety, \n" + 
				"suitability, lack of viruses, inaccuracies, typographical errors, or other harmful components \n" + 
				"of this SOFTWARE PRODUCT. There are inherent dangers in the use of any software, and you are \n" + 
				"solely responsible for determining whether this SOFTWARE PRODUCT is compatible with your \n" + 
				"equipment and other software installed on your equipment. You are also solely responsible \n" + 
				"for the protection of your equipment and backup of your data, and THE PROVIDER will not be \n" + 
				"liable for any damages you may suffer in connection with using, modifying, \n" + 
				"or distributing this SOFTWARE PRODUCT.\n\nThis software was developed by Matthew Charles Averill of Weyland-Averill Industries. \nFor more information, contact me at charlesaverill20@gmail.com\n\n");
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter search term:");
		
		search = input.nextLine();
		
		if(isUrl(search)) {
			while(isUrl(search)) {
				System.out.println("Please do not enter a link as your search.");
				search = input.nextLine();
			}
		}
		
		String allPages = "";
		
		System.out.println("Would you like to scrape all pages instead of only the first? (Y/N) This will significantly increase runtime.");

		allPages = input.nextLine().toLowerCase();
		
		if(!allPages.equals("y") || !allPages.equals("n")) {
			while(!allPages.equals("y") && !allPages.equals("n")) {
				System.out.println("Please enter \"y\" or \"n\".");
				allPages = input.nextLine().toLowerCase();
			}
		}
		
		while(!allPages.equals("y") && !allPages.equals("n")) {
			allPages = input.nextLine().toLowerCase();
		}
		
		sp = new SearchPage(search, allPages);
		
		try {
			sp.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		input.close();
	}
	
	public static boolean isUrl(String url) 
    { 
        /* Try creating a valid URL */
        try { 
            new URL(url).toURI(); 
            return true; 
        } 
          
        // If there was an Exception 
        // while creating URL object 
        catch (Exception e) { 
            return false; 
        } 
    } 
}
