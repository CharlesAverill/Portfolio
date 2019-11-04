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
	
	static ArrayList<Entry> entries = new ArrayList<Entry>();
	
	static int lastLength = 0;
	
	public static void main(String[] args) throws IOException, RowsExceededException, WriteException, InterruptedException{
		String[] mailingList = new String[] {"charlesaverill20@gmail.com"};
		EmailSender es = new EmailSender("averill.bot@gmail.com", mailingList);
		String checkOut = "";
		Scanner memory = new Scanner(new File("output.out"));
		while(memory.hasNextLine()) {
			checkOut = memory.nextLine();
		}
		memory.close();
		while(true) {
			//Excel Stuff
			XSSFWorkbook workbook = new XSSFWorkbook();
			
			XSSFSheet sheet = workbook.createSheet("Sheet1");
			FileOutputStream out = new FileOutputStream(new File("output.xlsx"));

			String[] top = new String[] {"Company Name (required)", "Contact Name First", "Contact Name Last", "Business Phone (required if no email)", "Business Fax", "Cell Phone", "Website URL", "Email (required if no businnes phone)", "Address 1", "Address 2", "City", "Zip", "PWC (ID or Description)", "Source", "Comments"};
			XSSFRow first = sheet.createRow(0);
			for(int i = 0; i < top.length; i++) {
				first.createCell(i);
				first.getCell(i).setCellValue(top[i]);
			}
			//
			//Adding Scrapers
			ArrayList<Scraper> scrapers = new ArrayList<Scraper>();
			scrapers.add(new TexasBuilders());
			scrapers.add(new DallasCityHall());
			scrapers.add(new WVLabor());
			scrapers.add(new DeKalb());
			

			
			
			System.out.println("Concatenating...");
			
			for(Scraper scraper : scrapers) {
				System.out.println(scraper);
				update(scraper.entries);
			}
			//
			//flag tests if there is new data. tempOut has all collected data, not just new stuff
			boolean flag = false;
			String tempOut = "";
			//Adding to output file
			if(entries.size() > lastLength) {
				String output = "";
				int c = 0;
				for(int i = entries.size() - 1; i >= 0; i--) {
					Entry entry = entries.get(c++);
					if(i % 100 == 0) {
						System.out.println("" + i + " left");
					}
					if(!checkOut.contains(entry.companyName)) {
						flag = true;
						XSSFRow temp = sheet.createRow(i + 2);
						temp.createCell(0).setCellValue(entry.companyName);
						temp.createCell(1).setCellValue(entry.contactFirstName);
						temp.createCell(2).setCellValue(entry.contactLastName);
						temp.createCell(3).setCellValue(entry.businessPhoneNumber);
						temp.createCell(4).setCellValue(entry.faxNumber);
						temp.createCell(5).setCellValue(entry.cellPhoneNumber);
						temp.createCell(6).setCellValue(entry.URL);
						temp.createCell(7).setCellValue(entry.email);
						temp.createCell(8).setCellValue(entry.address1);
						temp.createCell(9).setCellValue(entry.address2);
						temp.createCell(10).setCellValue(entry.city);
						temp.createCell(11).setCellValue(entry.zipCode);
						temp.createCell(12).setCellValue(entry.PWC);
						temp.createCell(13).setCellValue(entry.source);
						temp.createCell(14).setCellValue(entry.comment);
						tempOut += entry + "\n\n\n\n";
						output += entry + "\n\n\n\n";
					}
				}
				
				PrintWriter pw = new PrintWriter(new File("output.out"));
				pw.println("New: \n" + output + "\nOld:\n" + checkOut);
				pw.close();
				
				PrintWriter allOut = new PrintWriter(new File("allOutput.out"));
				allOut.println(tempOut);

				if(flag) {				
					System.out.println("NEW ENTRIES - CHECK TOP OF SPREADSHEET");
				}
			}
			System.out.println("Composing spreadsheet...");
			
			workbook.write(out);
			out.close();
			
			System.out.println("Spreadsheet composed");
			if(flag) {
				System.out.println("Sending email to " + Arrays.asList(es.toEmail));
				es.setSubject("NEW ENTRIES - " + df.format(dateobj));
				List<Entry> subArray = entries.subList(lastLength, entries.size());
				String message = "";
				for(Entry e : subArray) {
					message += e.toString() + "\n\n";
				}
				es.setMessage("NEW ENTRIES - \n\n" + message);
				es.sendFile(new File("output.xlsx"));
			}
			
			System.out.print("Completed ");
			System.out.println(entries.size());
			System.out.println(df.format(dateobj));
			System.out.println();
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			lastLength = entries.size();
			workbook.close();
			
			Thread.sleep((long)(1000 * 60 * 5));
		}
	}
	
	public static void update(ArrayList<Entry> list) {
		for(Entry entry : list) {
			int t = longSearch(entry.companyName);
			if(t != -1) {
				if(!entry.companyName.equals(" ")) {
					entries.get(t).companyName = entry.companyName;
				}
				if(!entry.contactFirstName.equals(" ")) {
					entries.get(t).contactFirstName = entry.contactFirstName;
				}
				if(!entry.contactLastName.equals(" ")) {
					entries.get(t).contactLastName = entry.contactLastName;
				}
				if(!entry.businessPhoneNumber.equals(" ")) {
					entries.get(t).businessPhoneNumber = entry.businessPhoneNumber;
				}
				if(!entry.faxNumber.equals(" ")) {
					entries.get(t).faxNumber = entry.faxNumber;
				}
				if(!entry.cellPhoneNumber.equals(" ")) {
					entries.get(t).cellPhoneNumber = entry.cellPhoneNumber;
				}
				if(!entry.URL.equals(" ")) {
					entries.get(t).URL = entry.URL;
				}
				if(!entry.email.equals(" ")) {
					entries.get(t).email = entry.email;
				}
				if(!entry.address1.equals(" ")) {
					entries.get(t).address1 = entry.address1;
				}
				if(!entry.address2.equals(" ")) {
					entries.get(t).address2 = entry.address2;
				}
				if(!entry.city.equals(" ")) {
					entries.get(t).city = entry.city;
				}
				if(!entry.zipCode.equals(" ")) {
					entries.get(t).zipCode = entry.zipCode;
				}
				if(!entry.PWC.equals(" ")) {
					entries.get(t).PWC = entry.PWC;
				}
				if(!entry.source.equals(" ")) {
					entries.get(t).source = entry.source;
				}
				if(!entry.comment.equals(" ")) {
					entries.get(t).comment = entry.comment;
				}
			}
			else {
				entries.add(entry);
			}
		}
	}
	
	public static int longSearch(String str) {
		for(Entry entry : entries) {
			if(str.equals(entry.companyName)) {
				return entries.indexOf(entry);
			}
		}
		return -1;
	}
}
