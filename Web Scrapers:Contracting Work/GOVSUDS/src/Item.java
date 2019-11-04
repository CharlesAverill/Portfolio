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


public class Item{
	
	String site;
	String url;
	String title;
	String quantity;
	String condition;
	String category;
	String sellerName;
	String sellerAddress;
	String currentBid;
	String numBids;
	String endingDate;
	String remainingTime;
	
	public Item() {
		site = " ";
		url = " ";
		title = " ";
		quantity = " ";
		condition = " ";
		category = " ";
		sellerName = " ";
		sellerAddress = " ";
		currentBid = " ";
		numBids = " ";
		endingDate = " ";
		remainingTime = " ";
	}
	
	public Item(String a, String b, String c, String d, String e, String f, String g, String h, String i, String j, String k, String l, String m) {
		site = a;
		url = b;
		title = c;
		quantity = d;
		condition = e;
		category = f;
		sellerName = g;
		sellerAddress = h;
		currentBid = i;
		numBids = j;
		endingDate = l;
		remainingTime = m;
	}
	
	public String toString() {
		return "Site: " + site + "\nURL: " + url + 
				"\nTitle: " + title + "\nQuantity: " + quantity + 
				"\nCondition: " + condition + "\nCategory: " + category + 
				"\nSeller Name: " + sellerName + "\nSeller Address: " + sellerAddress + "\nCurrent Bid: " + currentBid + "\nNumber of Bids: " + numBids + 
				"\nEnding Date: " + endingDate + "\nRemaining Time: " + remainingTime;
	}
}
