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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WVLabor extends Scraper{
	
	int index = 1;
	static ArrayList<Thread> threads = new ArrayList<Thread>();
	
	public WVLabor() throws FileNotFoundException {
		
		System.out.println("WVLabor");
		
		
		WebClient client = new WebClient();
		client.getOptions().setTimeout(0);
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
			
		System.out.println("Multithreading...");
			
		try {
			
			String url1 = "http://www.wvlabor.com/new_searches/contractor_RESULTS.cfm?PageNum_WVNUMBER=";
			String url2 = "&wvnumber=&contractor_name=&dba=&city_name=&County=&Submit3=Search+Contractors";
			
			int urlNum = 1;
			
			String url = url1 + urlNum + url2;
			System.out.println("Retrieving HTML page");
			HtmlPage page = client.getPage(url);
			System.out.println("HTML page retrieved");
			
			String text = page.asText();
			text = text.substring(text.lastIndexOf("Records") - 6, text.lastIndexOf("Records") - 1);
			double records = Integer.parseInt(text);
			double times = Math.floorDiv((int)records, 24);
			System.out.println("Number of pages: " + (int)times);
			
			double individualDurationInSeconds = 1.0 / times;
			
			for(int i = 1; i < times + 1; i++) {
				//times + 1
				threads.add(new Thread(new WVLMultithread(i, this)));
				threads.get(i - 1).start();

				Thread.sleep(350);
			}
			
			for(Thread t : threads) {
				t.join();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		//System.out.println(entries);
	}
	
	public static boolean allThreadsDone() {
		for(Thread t : threads) {
			if(t.isAlive()) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) throws IOException{
		WVLabor wvl = new WVLabor();
	}
}

class WVLMultithread extends Scraper implements Runnable{
	
	WVLabor work;
	int n;
	String url1 = "http://www.wvlabor.com/new_searches/contractor_RESULTS.cfm?PageNum_WVNUMBER=";
	String url2 = "&wvnumber=&contractor_name=&dba=&city_name=&County=&Submit3=Search+Contractors";
	
	public WVLMultithread(int num, WVLabor w) {
		work = w;
		n = num;
	}

	@Override
	public void run() {
		System.out.println("Start " + n);
		WebClient client = new WebClient();
		client.getOptions().setTimeout(0);
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		
		String url = url1 + n + url2;
		try {
			HtmlPage page = client.getPage(url);
			
			items = (List<HtmlElement>)(Object)page.getByXPath("//tr");
			
			if(items.isEmpty()) {
				System.out.println("No items found!");
			}
				
			else {
					
				for(HtmlElement item : items) {
					Entry entry = new Entry();
					
					String t = item.asText();
					String[] text = t.split("\t");
					if(!text[0].contains("WV Number") && text[0].length() != 0) {
						
						entry.PWC = text[0];
						entry.companyName = text[1];
						entry.address1 = text[3];
						entry.city = text[4];
						String state = text[5];
						entry.zipCode = text[6];
						entry.address2 = entry.city + ", " + state + " " + entry.zipCode;
						entry.businessPhoneNumber = text[8];
						entry.source = url;
						
						work.entries.add(entry);
					}
				}
			}
			
			
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Done " + n);
	}
}
