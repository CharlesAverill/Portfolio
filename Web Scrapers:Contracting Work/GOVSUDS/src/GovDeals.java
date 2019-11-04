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
public class GovDeals extends Scraper{
	
	int index = 1;
	
	static String searchterm;
	
	static ArrayList<Thread> threads = new ArrayList<Thread>();
	static ArrayList<String> urls = new ArrayList<String>();
	
	public GovDeals(String st){
		searchterm = st;
		searchterm = searchterm.replaceAll(" ", "%20");
	}
	
	public void run(){
		System.out.println("GovDeals.com");
		
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
			
		System.out.println("Retrieving HTML page...");
			
		try {
			
			String url = "https://www.govdeals.com/index.cfm?fa=Main.AdvSearchResultsNew&sortoption=aa&kWord=" + searchterm + "&whichForm=vehicle&searchPg=Main&rowCount=200&StartRow=1";
				
			HtmlPage page = client.getPage(url);
				
			System.out.println("HTML page retrieved");
				
			items = (List<HtmlElement>)(Object)page.getByXPath("//td[@valign='top']");
				
			if(items.isEmpty()) {
				System.out.println("No items found!");
			}
				
			else {
				System.out.println("Processing...");
					
				for(HtmlElement item : items) {
					
					index++;
					
					String xml = item.asXml();
					
					FastReader file = new FastReader(xml);
					if(xml.contains("<a href=") && !xml.contains("Help Desk Hours: Monday - Friday, 8 am - 7 pm ET.") && !xml.contains("https://www.liquidityservices.com")) {
						
						//System.out.println("\n-----------------------------\n" + xml);
						
						file.nextLine();
						String line = file.nextLine();
						
						//System.out.println(line);
						
						String itemurl = "https://govdeals.com/" + line.substring(line.indexOf("<a href=") + 9, line.lastIndexOf("\""));
						itemurl = itemurl.replaceAll(";", "&");
						//System.out.println(itemurl);
						
						urls.add(itemurl);
					}
						
				}
				
				System.out.println("Multithreading...");
				
				int n = 0;
				for(String u : urls) {
					n += 1;
					Thread t = new Thread(new GovDealsItemScraper(n, this, u));
					threads.add(t);
					t.start();
					Thread.sleep(350);
				}
				
				for(Thread t : threads) {
					t.join();
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
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
}
