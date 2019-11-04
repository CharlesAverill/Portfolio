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
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;

public class Scraper {
	
	public Scraper() {
		Logger.getRootLogger().setLevel(Level.OFF);
	}
	
	public static List<HtmlElement> items = new ArrayList<HtmlElement>();
	public static List<String> printed = new ArrayList<String>();
	
	public ArrayList<SearchResult> results = new ArrayList<SearchResult>();
	
	public static int status(String url) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient client = new WebClient();
		client.getOptions().setThrowExceptionOnFailingStatusCode(false);
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		
		int output = client.getPage(url).getWebResponse().getStatusCode();
		client.close();
		
		return output;
	}
}