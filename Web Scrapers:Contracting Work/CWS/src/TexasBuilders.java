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
public class TexasBuilders extends Scraper{
	
	int index = 1;
	
	public TexasBuilders() throws FileNotFoundException {
		
		System.out.println("Texas Builders");
		
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
			
		System.out.println("Retrieving HTML page...");
			
		try {
			
			String url = "http://builderfusion.texasbuilders.org/bf/website/directory/printMemberDirInfo.jsp";
				
			HtmlPage page = client.getPage(url);
				
			System.out.println("HTML page retrieved");
				
			items = (List<HtmlElement>)(Object)page.getByXPath("//td[@class='lineAbove']");
				
			if(items.isEmpty()) {
				System.out.println("No items found!");
			}
				
			else {
				System.out.println("Processing...");
					
				for(HtmlElement item : items) {
					Entry entry = new Entry();
					
					index++;
					
					String text = item.asText();
						
					FastReader file = new FastReader(text);
						
					if(!isPhoneNumber(text)) {
						String line = file.br.readLine();
							
						if(!line.contains(",")) {
							entry.companyName = line;
							entry.contactFirstName = "";
							entry.contactLastName = "";
						}
							
						else {
							entry.companyName = line.substring(0, line.lastIndexOf(","));
							line = line.substring(line.lastIndexOf(",") + 2);
							String[] split = line.split(" ");
							if(split.length == 2) {
								entry.contactFirstName = split[0];
								entry.contactLastName = split[1];
							}
							else {
								entry.contactFirstName = split[0];
							}
						}
							
						line = file.br.readLine();
							
						if(line.length() > 1) {
							entry.address1 = line.substring(0, line.indexOf(","));
							entry.address2 = line.substring(line.indexOf(",") + 2, line.lastIndexOf(",") + 10);
							entry.city = line.substring(line.indexOf(",") + 2, line.lastIndexOf(","));
							entry.zipCode = line.substring(line.lastIndexOf(",") + 5, line.lastIndexOf(",") + 10);
						}
							
						if(!printed.contains(text)) {
							printed.add(text);
						}
							
					}
						
					else {
						entry.businessPhoneNumber = text;
					}
						
					entry.source = "http://builderfusion.texasbuilders.org/bf/website/directory/printMemberDirInfo.jsp";
					if(index % 2 == 0) {					
						entries.add(entry);
					}
					else {
						entries.get(entries.size() - 1).businessPhoneNumber = entry.businessPhoneNumber;
					}
					//System.out.println(text);
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
