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
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import com.gargoylesoftware.htmlunit.WebClient;

public class FortLauderdale extends Scraper{
	
	int index = 1;
	
	public FortLauderdale() throws InvalidPasswordException, IOException {
		
		System.out.println("Fort Lauderdale");
		
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
			
		System.out.println("Retrieving PDF...");
			
		try {
			
			//This scraper doesn't work, just manually convert the newest pdf with the link below
			String url = "https://www.fortlauderdale.gov/home/showdocument?id=34436";
			//https://www.pdftoexcel.com
				
			System.out.println("PDF retrieved");
			
			FileUtils.copyURLToFile(
					  new URL(url), 
					  new File("fortlauderdale.pdf"));
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		try (PDDocument document = PDDocument.load(new File("fortlauderdale.pdf"))) {

            document.getClass();

            if (!document.isEncrypted()) {
			
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);

                PDFTextStripper tStripper = new PDFTextStripper();

                String pdfFileInText = tStripper.getText(document);
                //System.out.println("Text:" + st);

				// split by whitespace
                String lines[] = pdfFileInText.split("\\r?\\n");
                for (String line : lines) {
                	String companyName = "";
                	String firstName = "";
                	String lastName = "";
                	
                	if(line.equals("Business Name Est. DateAddress Phone# Owner Name Business DescriptionLicense # Zip Co") || line.equals("Page")) {
                		continue;
                	}
                	if(!line.contains("2018")) {
                		continue;
                	}
                    
                    String allName = line.substring(0, indexOfDate(line) - 1);
                    
                    if(allName.contains(",")) {
                        firstName = allName.substring(0, allName.indexOf(","));
                        lastName = allName.substring(allName.indexOf(",") + 1);
                        System.out.println(firstName + " " + lastName);
                    }
                    else {
                    	companyName = allName;
                    	System.out.println(companyName);
                    }
                    
                    
                }
            }

        }
		client.close();
	}
	
	public static int getIndexNthNumber(String str, int n) {
		int count = 0;
		for(int i = 0; i < str.length(); i++) {
			if(Character.isDigit(str.charAt(i))) {
				count++;
				if(count == n) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public static int indexOfDate(String str) {
		return str.indexOf("2018") - 5;
	}
	
	public static int countChars(String str, char c) {
		int count = 0;
		char[] arr = str.toCharArray();
		
		for(char ch : arr) {
			if(ch == c) {
				count++;
			}
		}
		
		return count;
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
	
	public static void main(String[] args) throws InvalidPasswordException, IOException {
		new FortLauderdale();
	}
}
