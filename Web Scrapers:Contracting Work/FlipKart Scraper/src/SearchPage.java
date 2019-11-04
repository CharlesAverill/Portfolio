import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

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

public class SearchPage extends Scraper{
	
	String search;
	int pageNumber = 1;
	boolean success;
	PrintWriter pw;
	
	File folder;
	File f;
	
	String allPages;
	
	boolean print25 = false;
	boolean print50 = false;
	boolean print75 = false;
	
	int count = 1;
	
	ArrayList<URL> imageURLs = new ArrayList<URL>();
	
	
	public SearchPage(String s, String a) {
		search = s;
		allPages = a;
	}
	
	public void run() throws IOException {
		System.out.println("Searching...");
		
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		
		System.out.println("Retrieving search results for " + search + "...");
		
		if(allPages.equals("y")) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss");
			Date date = new Date();
			
			File masterFolder = new File(search + dateFormat.format(date));
			masterFolder.mkdir();
			masterFolder.createNewFile();
			masterFolder.setWritable(true);
			masterFolder.setReadable(true);
			
			success = true;
			
			while(success) {
				scrape(client, masterFolder, dateFormat);
				print25 = false;
				print50 = false;
				print75 = false;
				System.out.println("\n--------------------\n");
			}
		}
		else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss");
			scrape(client, null, dateFormat);
		}
		
		System.out.println("Scraping completed. Output will be in the same folder.");
		client.close();
	}
	
	public String findLink(String full) {
		Scanner file = new Scanner(full);
		
		String line = "";
		while(!line.contains("<a") && file.hasNextLine()) {
			line = file.nextLine().trim();
		}
		
		if(!file.hasNextLine() || full.contains("pr?sid=")) {
			file.close();
			return null;
		}
		
		String link = line.substring(line.indexOf("href") + 6, line.lastIndexOf("\""));
		file.close();
		return link;
	}
	
	public boolean allThreadsDone(ArrayList<Thread> threads) {
		for(Thread t : threads) {
			if(t.isAlive()) {
				return false;
			}
		}
		return true;
	}
	
	public String findImageUrl(String xml) {
		String temp = xml.substring(xml.indexOf("//"));
		System.out.println(temp);
		return "https:" + temp.substring(0, xml.indexOf(" ") - 1);
	}
	
	public void scrape(WebClient client, File masterFolder, DateFormat dateFormat) {
		Date date = new Date();
		try {
			if(masterFolder != null) {
				System.out.println("Scraping page " + pageNumber);
			}
			String urlSearch = search.replaceAll(" ", "+");
			String url;
			if(pageNumber > 1) {
				url = "https://www.flipkart.com/search?q=" + urlSearch + "&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=offpage%3D2&page=" + pageNumber++;
			}
			else url = "https://www.flipkart.com/search?q=" + urlSearch + "&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=offpage=" + pageNumber++;
			HtmlPage page = client.getPage(url);
			
			System.out.println("Search results retrieved.");
			
			items = (List<HtmlElement>)(Object)page.getByXPath("//a[@rel='noopener noreferrer']");
			ArrayList<String> usedLinks = new ArrayList<String>();
			
			ArrayList<Thread> threads = new ArrayList<Thread>();
			
			if(masterFolder == null) {
				folder = new File(search + dateFormat.format(date));
				folder.mkdir();
				folder.createNewFile();
				folder.setWritable(true);
				folder.setReadable(true);
			}
			else {
				folder = new File(masterFolder.getPath() + "/" + search  + "_Page" + (pageNumber - 1));
				folder.mkdir();
				folder.createNewFile();
				folder.setWritable(true);
				folder.setReadable(true);
			}
			
			f = new File(folder.getPath() + "/" + search + ".txt");
			new File(folder.getPath() + "/" + "html").mkdirs();
			f.createNewFile();
			f.setWritable(true);
			
			new File(folder.getPath() + "/" + "text").mkdirs();
			
			pw = new PrintWriter(f);
			
			for(HtmlElement item : items) {
				int indexOfHref = item.asXml().indexOf("href") + 6;
				
				if(findLink(item.asXml()) == null) {
					continue;
				}
				
				if(usedLinks.contains(findLink(item.asXml())) || findLink(item.asXml()).contains("login") || indexOfHref < 6) {
					continue;
				}
				else {
					usedLinks.add(findLink(item.asXml()));
				}
				SearchResult sr = new SearchResult("https://flipkart.com" + findLink(item.asXml()), this);
				results.add(sr);
				
				Thread t = new Thread(sr);
				threads.add(t);
				t.start();
			}
			if(count - 1 != 0) {
				System.out.println("" + (count - 1) + " items found.");
				System.out.println("Beginning data organization.");
				
				while(!allThreadsDone(threads)) {
					
				}
				
				int replaceCount = 0;
				for(int a = 0; a < results.size(); a++) {
					SearchResult A = results.get(a);
					for(int b = a + 1; b < results.size(); b++) {
						SearchResult B = results.get(b);
						if(A.equals(B)) {
							replaceCount++;
							results.remove(B);
						}
					}
				}
				if(replaceCount > 0) {
					System.out.println("" + replaceCount + " duplicates found.");
				}
				
				System.out.println("Writing to files...");
				
				count = 1;
				
				for(SearchResult sr : results) {
					Printer p = new Printer(this, sr, "normal", count++, null);
					p.run();
				}
				
				count = 1;
				
				int q = 1;
				for(SearchResult sr : results) {
					Printer p = new Printer(this, sr, "html", q++, null);
					Thread t = new Thread(p);
					p.t = t;
					t.start();
					threads.add(t);
					Thread.sleep(300);
				}
				
				while(!allThreadsDone(threads)) {
					
				}
				
				System.out.println("Writing to files completed.");
			}
			else {
				System.out.println("No items found with that name.");
				success = false;
			}
		} 
		catch(Exception e) {
			success = false;
		}
		
		pw.close();
	}
	
}
