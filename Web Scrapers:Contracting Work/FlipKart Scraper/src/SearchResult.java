import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import com.gargoylesoftware.htmlunit.WebClient;
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

public class SearchResult extends Scraper implements Runnable{
	
	String url;
	
	Product product;
	
	SearchPage sp;
	
	HtmlPage page;
	
	int n;
	
	public SearchResult(String u, SearchPage s) {
		url = u;
		sp = s;
		
		n = sp.count;
		sp.count += 1;
		
		product = new Product();
	}
	
	
	
	public String getXML() {
		return page.asXml();
	}
	
	public String getText() {
		return page.asText();
	}
	
	@Override
	public void run() {
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		try {
			page = client.getPage(url);
			
			addAttributes(page.asXml(), page.asText());
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		client.close();
	}
	
	public void addAttributes(String xml, String text) throws Exception {
		product.addField("N", "" + n);
		Scanner file = new Scanner(xml);
		
		String line = "";
		
		
		//NAME
		while(!line.contains("<title>")) {
			line = file.nextLine().trim();
		}
		line = file.nextLine();
		if(!line.contains("Buy")) {
			if(line.contains(": Flipkart.com")) {
				product.addField("Name", line.substring(0, line.indexOf(": Flipkart.com") - 1).trim());
			}
			else {
				if(line.contains("Price in India")) {
					product.addField("Name", line.substring(0, line.indexOf("Price in India") - 1).trim());
				}
				else {
					if(line.contains(("-"))){
						product.addField("Name", line.substring(0, line.indexOf("-") - 1).trim());
					}
					else {
						product.addField("Name", line.substring(firstNonSpace(line), line.indexOf("Online") - 1).trim());
					}
				}
			}
		}
		else{
			if(line.contains("Price in India")) {
				product.addField("Name", line.substring(0, line.indexOf("Price in India") - 1).trim());
			}
			else {
				if(!line.trim().subSequence(0, 3).equals("Buy")) {
					product.addField("Name", line.substring(0, line.indexOf("Buy")).trim());
				}
				else if(line.contains("Products") && line.substring(firstNonSpace(line), line.indexOf("-") - 1).equals("Products")) {
					file.close();
					return;
				}
				else {	
					if(line.contains("Buy Products Online at Best Price in India")) {
						file.close();
						return;
					}
					else if(line.contains("online")) {
						product.addField("Name", line.substring(line.indexOf("Buy") + 4, line.indexOf("online") - 1).trim());
					}
					else {	
						if(line.contains("by")) {
							product.addField("Name", line.substring(line.indexOf("Buy") + 4, line.lastIndexOf("by") - 1).trim());
						}
						else {
							product.addField("Name", line.substring(line.indexOf("Buy") + 4, line.indexOf("Online") - 1).trim());
						}
					}
					
				}
				
			}
			
		}
		
		file.close();
		
		file = new Scanner(text);
		
		//PRICE
		while(!line.contains("₹") && file.hasNextLine()) {
			line = file.nextLine().trim();
		}
		if(line.contains("Extra") && line.contains("discount")) {
			Scanner temp = new Scanner(line);
			temp.next();
			product.addField("Extra Discount", temp.next());
			temp.close();
			
			product.addField("Discounted Price", file.nextLine());
			product.addField("Normal Price", file.nextLine());
			line = file.nextLine().trim();
			product.addField("Discount", line.substring(0, line.indexOf("off") - 1));
		}
		else {
			if(file.hasNextLine()) {
				product.addField("Discounted Price", line);
				product.addField("Normal Price", file.nextLine());
				product.addField("Discount", file.nextLine());
				product.addField("Extra Discount", "N/A");
			}
		}
		
		//DELIVERY TIME
		while(!line.contains("Usually delivered") && file.hasNextLine()) {
			line = file.nextLine().trim();
		}
		if(file.hasNextLine()) {
			Scanner temp = new Scanner(line);
			temp.next();
			temp.next();
			String t = temp.next();
			product.addField("Delivery Time", t.substring(t.indexOf("n") + 1) + " " + temp.next());
			temp.close();
		}
		
		file.close();
		
		file = new Scanner(text);
		
		//SELLER
		while(!line.equals("Seller") && file.hasNextLine()) {
			line = file.nextLine().trim();
		}
		if(file.hasNextLine()) {
			product.addField("Seller", file.nextLine());
			product.addField("Seller Rating", file.nextLine());
		}
		else {
			product.addField("Seller", "N/A");
			product.addField("Seller Rating", "N/A");
		}
		
		file.close();
		
		file = new Scanner(text);
		
		//DESCRIPTION
		if(text.contains("Description")) {
			while(!line.equals("Description") && file.hasNextLine()) {
				line = file.nextLine().trim();
			}
			if(!file.hasNextLine()) {
				product.addField("Description", "N/A");
			}
			else {
				line = file.nextLine().trim();
				product.addField("Description", line);
			}
		}
		else {
			product.addField("Description", "N/A");
		}
		
		file.close();
		
		
		//PRODUCT DETAILS
		file = new Scanner(xml);
		
		line = " a";
		while(file.hasNextLine()) {
			line = file.nextLine().trim();
			if(line.equals("Product Details") || line.equals("Specifications")) {
				break;
			}
		}
		
		int c = 2;
		String key = "";
		String value = "";

		while(file.hasNextLine()) {
			line = file.nextLine().trim();
			
			if(line.equals("Disclaimer")) {
				String disclaimer = "";
				while(!line.contains("</li>")) {
					System.out.println(line);
					line = file.nextLine().trim();
					if(!line.contains("<")) {
						disclaimer += line + " | ";
					}
				}
				product.addField("Disclaimer", disclaimer);
				key = "";
				value = "";
				continue;
			}
			
			if(line.contains("Read More") || line.contains("<button")) {
				break;
			}
			if(line.contains("In The Box")) {
				String x = file.nextLine();
				if(x.contains("</div>") || x.contains("<table")) {
					continue;
				}
			}
			if(line.equals("Dimensions") || line.equals("Warranty Summary")) {
				continue;
			}
			
			else if(line.contains("Features")) {
				if(file.nextLine().trim().contains("</div>")) {
					continue;
				}
			}
			
			else if(line.length() < 500 && !line.contains("<") && !line.contains("Frequently Bought Together") && !line.equals("Other Details") && !line.equals("Product Details") && !line.equals("General") && !line.equals("More Details")) {
				c++;
				if(line.contains(":")) {
					if(!key.isEmpty()) {
						product.addField(key, line);
					}
					else {
						product.addField(line.substring(0, line.indexOf(":")), line.substring(line.indexOf(":")));
					}
					c = 2;
					key = "";
					value = "";
					continue;
				}
				if(c % 2 != 0) {
					key = line.replace("&amp;", "&");
				}
				else {
					value = line.replace("&amp;", "&");
				}
				if(!value.equals("") && !key.equals("")) {
					product.addField(key, value);
					c = 2;
					key = "";
					value = "";
				}
			}
		}
		
		file.close();
		
		//REVIEWS
		file = new Scanner(xml);
		
		line = "";
		while(file.hasNextLine() && !line.equals("Ratings &amp; Reviews")) {
			line = file.nextLine().trim();
		}
		if(!file.hasNextLine()) {
			product.addField("Reviews", "None Found");
		}
		else {
			while(file.hasNextLine() && !isNumber(line)) {
				line = file.nextLine().trim();
			}
			product.addField("Overall Rating", line);
			
			while(file.hasNextLine() && !line.equals("5")) {
				line = file.nextLine().trim();
			}
			if(file.hasNextLine()) {
				line = file.nextLine().trim();
				while(file.hasNextLine() && !isNumber(line.substring(1))) {
					line = file.nextLine().trim();
				}
				line=file.nextLine().trim();
				while(file.hasNextLine() && !isNumber(line.substring(1))) {
					line = file.nextLine().trim();
				}
				product.addField("Five Star Ratings", line.substring(1));
				
				while(file.hasNextLine() && !line.equals("4")) {
					line = file.nextLine().trim();
				}
				if(file.hasNextLine()) {
					line = file.nextLine().trim();
					while(file.hasNextLine() && !isNumber(line.substring(1))) {
						line = file.nextLine().trim();
					}
					line=file.nextLine().trim();
					while(file.hasNextLine() && !isNumber(line.substring(1))) {
						line = file.nextLine().trim();
					}
					product.addField("Four Star Ratings", line.substring(1));
					
					while(file.hasNextLine() && !line.equals("3")) {
						line = file.nextLine().trim();
					}
					if(file.hasNextLine()) {
						line = file.nextLine().trim();
					}
					while(file.hasNextLine() && !isNumber(line.substring(1))) {
						line = file.nextLine().trim();
					}
					if(file.hasNextLine()) {
						line = file.nextLine().trim();
					}
					while(file.hasNextLine() && !isNumber(line.substring(1))) {
						line = file.nextLine().trim();
					}
					product.addField("Three Star Ratings", line.substring(1));
					
					while(file.hasNextLine() && !line.equals("2")) {
						line = file.nextLine().trim();
					}
					if(file.hasNextLine()) {
						line = file.nextLine().trim();
					}
					while(file.hasNextLine() && !isNumber(line.substring(1))) {
						line = file.nextLine().trim();
					}
					if(file.hasNextLine()) {
						line=file.nextLine().trim();
					}
					while(file.hasNextLine() && !isNumber(line.substring(1))) {
						line = file.nextLine().trim();
					}
					product.addField("Two Star Ratings", line.substring(1));
					
					while(file.hasNextLine() && !line.equals("1")) {
						line = file.nextLine().trim();
					}
					if(file.hasNextLine()) {
						line = file.nextLine().trim();
					}
					while(file.hasNextLine() && !isNumber(line.substring(1))) {
						line = file.nextLine().trim();
					}
					if(file.hasNextLine()) {
						line=file.nextLine().trim();
					}
					while(file.hasNextLine() && !isNumber(line.substring(1))) {
						line = file.nextLine().trim();
					}
					product.addField("One Star Ratings", line.substring(1));
				}
			}
		}
		
		
		file.close();
		
		//IMAGES
		file = new Scanner(xml);
		line = "";
		while(file.hasNextLine()) {
			line = file.nextLine();
			if(line.contains("<div") && line.contains("style=\"background-image:url")){
				downloadImage(line.substring(line.indexOf("https"), line.indexOf(")")));
			}
			else {
				if(line.contains("imageUrl\":\"http:")) {
					line = line.substring(line.indexOf("imageUrl\":\"http:") + 11);
					line = line.substring(0, line.indexOf("?"));
					if(line.contains("/{@width}/{@height}/")) {
						line = line.substring(0, line.indexOf("/{@width}/{@height}/")) + line.substring(line.indexOf("/{@width}/{@height}/") + 19);
					}
					downloadImage(line);
				}
			}
		}
		product.replace("N", "" + n);
		
		double totalCount = sp.count - 1;
		
		if((double)n / totalCount > .25 && !sp.print25) {
			System.out.println("25%");
			sp.print25 = true;
		}
		else if((double)n / totalCount > .5 && !sp.print50) {
			System.out.println("50%");
			sp.print50 = true;
		}
		else if((double)n / totalCount > .75 && !sp.print75) {
			System.out.println("75%");
			sp.print75 = true;
		}
		
		file.close();
	}
	
	public File downloadImage(String url) throws InterruptedException{
		 URL urlObject;
		try {
			urlObject = new URL(url);
			InputStream in = new BufferedInputStream(urlObject.openStream());
			 ByteArrayOutputStream out = new ByteArrayOutputStream();
			 byte[] buf = new byte[1024];
			 int n = 0;
			 while (-1!=(n=in.read(buf)))
			 {
			    out.write(buf, 0, n);
			 }
			 out.close();
			 in.close();
			 byte[] response = out.toByteArray();
			 
			 int indexname = url.lastIndexOf("/");
			 
		     if (indexname == url.length()) {
		    	 url = url.substring(1, indexname);
		     }
		 
		     indexname = url.lastIndexOf("/");
		     String name = url.substring(indexname, url.length());
			 
		     File imageFolder = new File(sp.folder.getPath() + "/images");
		     imageFolder.mkdirs();
		     imageFolder.createNewFile();
		     imageFolder.setWritable(true);
		     imageFolder.setReadable(true);
		     
		     File tempImageFolder = new File(imageFolder.getPath() + "/" + product.getValue("Name").replaceAll("\\s", ""));
		     /*
		     if(tempImageFolder.exists()) {
		    	 int c = 1;
		    	 while(tempImageFolder.exists()) {
		    		 tempImageFolder = new File(sp.folder.getName() + "/" + imageFolder.getName() + "/" + product.getValue("Name").replaceAll("\\s", "") + "(" + c++ + ")");
		    	 }
		     }
		     */
		     tempImageFolder.mkdirs();
		     tempImageFolder.createNewFile();
		     tempImageFolder.setWritable(true);
		     tempImageFolder.setReadable(true);
		     if(!name.contains("?")) {
		    	 name = name + "?";
		     }
		     
		     File image = new File(tempImageFolder.getCanonicalFile() + name.substring(0, name.lastIndexOf("?")));
		     
			 FileOutputStream fos = new FileOutputStream(image);
			 fos.write(response);
			 fos.close();
			 

			return tempImageFolder;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Thread.sleep(1000);
			downloadImage(url);
		}
		
		return null;
	}
	
	public boolean isRating(String str) {
		return (str.length() == 3 && str.contains(".") && isNumber(str));
	}
	
	public boolean isImage(String line) {
		if(line.contains("<img")) {
			return true;
		}
		return false;
	}
	
	public int firstNonSpace(String str) {
		for(int i = 0; i < str.length(); i++) {
			if(str.charAt(i) != ' ') {
				return i;
			}
		}
		return -1;
	}
	
	public boolean equals(SearchResult sr) {
		return (sr.product.keys.equals(product.keys) && sr.product.values.equals(product.values));
	}
	
	public boolean isNumber(String str) {
		String nums = "1234567890";
		for(char c : str.toCharArray()) {
			if(!nums.contains("" + c)) {
				if(c == '.' || c == ',' || c == ' ') {
					continue;
				}
				else{
					return false;
				}
			}
		}
		return true;
	}
}
