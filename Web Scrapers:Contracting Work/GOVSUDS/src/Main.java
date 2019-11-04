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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
public class Main {
	
	public static DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	public static Date dateobj = new Date();
	
	static ArrayList<Item> entries = new ArrayList<Item>();
	
	public static void main(String[] args) throws IOException, RowsExceededException, WriteException, InterruptedException{
		String supported = "GovDeals.com \n";
		
		System.out.println("Hi there, and welcome to GOVSUDS, or, GOVernment SUrplus Data Scraper.\n\nThis data scraper was built with the intent of providing a method to\nsearch many government and military auction sites, so that finding a \nspecific item doesn't turn into a wild goose chase across multiple sites.\n\nHere are the current supported sites:\n\n" + supported);
		System.out.print("Please enter your search term: ");
		
		Scanner input = new Scanner(System.in);
		String searchterm = input.nextLine();
		
		System.out.println("Searching for \"" + searchterm + "\"...");
		
		//Excel Stuff
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		XSSFSheet sheet = workbook.createSheet("Search Results");
		FileOutputStream out = new FileOutputStream(new File(searchterm.replaceAll(" ", "_") + ".xlsx"));

		String[] top = new String[] {"N", "Title", "Site", "URL", "Quantity", "Condition", "Category", "Seller Name", "Seller Address", "Current Bid", "Number of Bids", "Ending Date", "Remaining Time"};
		XSSFRow first = sheet.createRow(0);
		for(int i = 0; i < top.length; i++) {
			first.createCell(i);
			first.getCell(i).setCellValue(top[i]);
		}
		
		
		//Adding Scrapers
		ArrayList<Scraper> scrapers = new ArrayList<Scraper>();
		scrapers.add(new GovDeals(searchterm));
		
		for(Scraper scr : scrapers) {
			scr.run();
		}
		
		
		System.out.println("Concatenating...");
		
		for(Scraper scraper : scrapers) {
			update(scraper.entries);
		}
		String tempOut = "";
		//Adding to output file
		int count = entries.size();
		if(entries.size() > 0) {
			String output = "";
			int c = 0;
			for(int i = entries.size() - 1; i >= 0; i--) {
				Item entry = entries.get(c++);
				
				XSSFRow temp = sheet.createRow(i + 2);
				temp.createCell(0).setCellValue("" + count);
				temp.createCell(1).setCellValue(entry.title);
				temp.createCell(2).setCellValue(entry.site);
				temp.createCell(3).setCellValue(entry.url);
				temp.createCell(4).setCellValue(entry.quantity);
				temp.createCell(5).setCellValue(entry.condition);
				temp.createCell(6).setCellValue(entry.category);
				temp.createCell(7).setCellValue(entry.sellerName);
				temp.createCell(8).setCellValue(entry.sellerAddress);
				temp.createCell(9).setCellValue(entry.currentBid);
				temp.createCell(10).setCellValue(entry.numBids);
				temp.createCell(11).setCellValue(entry.endingDate);
				temp.createCell(12).setCellValue(entry.remainingTime);
				count--;
			}
		}
		
		for(int i = 0; i < top.length; i++) {
			sheet.autoSizeColumn(i);
		}
		
		System.out.println("Composing spreadsheet...");
		
		workbook.write(out);
		out.close();
		
		System.out.println("Spreadsheet composed");
		
		
		workbook.close();
		
	}
	
	public static void update(ArrayList<Item> list) {
		for(Item entry : list) {
			int t = longSearch(entry.title);
			if(t != -1) {
				if(!entry.site.equals(" ")) {
					entries.get(t).site = entry.site;
				}
				if(!entry.title.equals(" ")) {
					entries.get(t).title = entry.title;
				}
				if(!entry.quantity.equals(" ")) {
					entries.get(t).quantity = entry.quantity;
				}
				if(!entry.condition.equals(" ")) {
					entries.get(t).condition = entry.condition;
				}
				if(!entry.category.equals(" ")) {
					entries.get(t).category = entry.category;
				}
				if(!entry.sellerName.equals(" ")) {
					entries.get(t).sellerName = entry.sellerName;
				}
				if(!entry.sellerAddress.equals(" ")) {
					entries.get(t).sellerAddress = entry.sellerAddress;
				}
				if(!entry.currentBid.equals(" ")) {
					entries.get(t).currentBid = entry.currentBid;
				}
				if(!entry.numBids.equals(" ")) {
					entries.get(t).numBids = entry.numBids;
				}
				if(!entry.endingDate.equals(" ")) {
					entries.get(t).endingDate = entry.endingDate;
				}
				if(!entry.remainingTime.equals(" ")) {
					entries.get(t).remainingTime = entry.remainingTime;
				}
			}
			else {
				entries.add(entry);
			}
		}
	}
	
	public static int longSearch(String str) {
		for(Item entry : entries) {
			if(str.equals(entry.title)) {
				return entries.indexOf(entry);
			}
		}
		return -1;
	}
}
