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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
public class GovDealsItemScraper implements Runnable{
	
	int index = 1;
	
	GovDeals work;
	int n;
	
	static String url;
	static boolean hasBeenBidOn = false;
	static boolean freeItem = false;
	static boolean reserveNotMet = false;
	
	public GovDealsItemScraper(int num, GovDeals gd, String u) {
		url = u;
		work = gd;
		n = num;
	}
	@Override
	public void run() {
		System.out.println("Start " + n);
		work.entries.add(getItemFromURL());
		System.out.println("Done " + n);
	}
	
	public Item getItemFromURL() {
		Item output = new Item();
		
		output.site = "GovDeals.com";
		output.url = url;
		
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
			
		try {
			HtmlPage page = client.getPage(url);
			
			String xml = page.asXml();
			
			FastReader file = new FastReader(xml);
			
			String line = "";
			
			while(!line.contains("<h1>")) {
				line = file.nextLine();
			}
			line = file.nextLine();
			output.title = line.trim();
			
			while(!line.contains("Auction Ends:")) {
				line = file.nextLine();
			}
			
			while(!line.contains("<b>")) {
				line = file.nextLine();
			}
			line = file.nextLine();
			output.endingDate = line.trim();
			
			while(!line.contains("Remaining:")) {
				line = file.nextLine();
			}
			
			while(!line.contains("<td align=\"right\" style=\"font-weight:bold\">")) {
				line = file.nextLine();
			}
			line = file.nextLine();
			line = file.nextLine();
			output.remainingTime = line.trim();
			
			boolean hasNumBids = false;
			
			if(xml.contains("Current Bid:")) {
				hasBeenBidOn = true;
			}
			else {
				hasBeenBidOn = false;
				if(xml.contains("This is a free item.")) {
					freeItem = true;
				}
				else {
					freeItem = false;
					if(xml.contains("Reserve Not Met")) {
						reserveNotMet = true;
					}
					else reserveNotMet = false;
				}
			}
			
			if(hasBeenBidOn) {
				while(!line.contains("Current Bid:")) {
					line = file.nextLine();
					if(line.contains("Bids:")) {
						while(!line.contains("<a href=")) {
							line = file.nextLine();
						}
						line = file.nextLine();
						output.numBids = line.trim();
						hasNumBids = true;
					}
				}
				if(!hasNumBids) {
					output.numBids = "N/A";
				}
			}
			
			else if(freeItem) {
				//System.out.println(xml.contains("This is a free item."));
				while(!line.contains("This is a free item.")) {
					line = file.nextLine();
				}
				output.numBids = "N/A";
				output.currentBid = "FREE ITEM";
			}
			
			else if(reserveNotMet) {
				while(!line.contains("Reserve Not Met")) {
					line = file.nextLine();
				}
				output.numBids = "0";
				while(!line.contains("Starting Bid:")) {
					line = file.nextLine();
				}
				file.nextLine();
				file.nextLine();
				file.nextLine();
				line = file.nextLine();
				output.currentBid = line.trim();
			}
			else {
				while(!line.contains("Starting Bid:")) {
					line = file.nextLine();
				}
				while(!line.contains("<b>")) {
					line = file.nextLine();
				}
				line = file.nextLine();
				output.currentBid = line.trim();
				output.numBids = "0";
			}
			
			if(!freeItem && !reserveNotMet) {
				while(!line.contains("<td align=\"right\" style=\"font-weight:bold\">")) {
					line = file.nextLine();
				}
				line = file.nextLine();
				output.currentBid = line.trim();
			}
			boolean hasQuantity = false;
			
			while(!line.contains("Category")) {
				line = file.nextLine();
				if(line.contains("Quantity")) {
					hasQuantity = true;
				}
			}
			
			while(!line.contains("</tr>")) {
				line = file.nextLine();
			}
			
			if(hasQuantity) {
				int count = 0;
				while(!line.contains("</tbody>")) {
					line = file.nextLine();
					if(line.contains("<td align=\"center\">")) {
						line = file.nextLine();
						count++;
						switch(count) {
							case(1):
								output.quantity = line.trim();
								break;
							case(2):
								output.condition = line.trim();
								break;
							case(3):
								line = file.nextLine();
								output.category = line.trim();
								break;
						}
					}
				}
			}
			
			else {
				output.quantity = "N/A";
				
				int count = 0;
				while(!line.contains("</tbody>")) {
					line = file.nextLine();
					
					if(line.contains("<td align=\"center\">")) {
						line = file.nextLine();
						count++;
						switch(count) {
							case(1):
								output.condition = line.trim();
								break;
							case(2):
								line = file.nextLine();
								output.category = line.trim();
								break;
						}
					}
				}
			}
			
			while(!line.contains("Seller Name:")) {
				line = file.nextLine();
			}
			
			while(!line.contains("<td")) {
				line = file.nextLine();
			}
			file.nextLine();
			line = file.nextLine().replaceAll("\"", "");
			
			output.sellerName = line.trim();
			
			while(!line.contains("Asset Location:")) {
				line = file.nextLine();
			}
			
			String address = "";
			
			while(!line.contains("<a href=")) {
				line = file.nextLine();
				if(!line.contains("<") && !line.contains(">")) {
					line = line.replaceAll("\" ", "");
					line = line.replaceAll("\"", "");
					
					address += line.trim() + " ";
				}
			}
			
			output.sellerAddress = address;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return output;
	}
	
	public static String getPhoneNumber(String str) {
		if(!str.contains("(") && !str.contains(")") && !str.contains("-")) {
			return "N/A";
		}
		return str;
	}
	
	public static boolean isPhoneNumber(String str) {
		if(str.length() < 3) {
			return true;
		}
		return str.substring(0, 2).equals(" (");
	}
	
	public boolean containsNumber(String str) {
		for(char c : str.toCharArray()) {
			if(isNumeric("" + c)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isNumeric(String strNum) {
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException | NullPointerException nfe) {
	        return false;
	    }
	    return true;
	}
}
