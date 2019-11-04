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
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import com.gargoylesoftware.htmlunit.WebClient;
public class DeKalb extends Scraper{
	public DeKalb() throws InvalidPasswordException, IOException {
		System.out.println("DeKalb");
		
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		
		String urlGeneral = "https://www.cityofdekalb.com/DocumentCenter/View/6559/Registered-General-Contractors";
		String urlMechanical = "https://www.cityofdekalb.com/DocumentCenter/View/2444/Registered-Mechanical-Contractors";
			
		System.out.println("Retrieving PDFs...");
		
		try {
			
				
			System.out.println("PDF retrieved");
			
			FileUtils.copyURLToFile(
					  new URL(urlGeneral), 
					  new File("dekalbGeneral.pdf"));
			
			
			System.out.println("PDF retrieved");
			
			FileUtils.copyURLToFile(
					  new URL(urlMechanical), 
					  new File("dekalbMechanical.pdf"));
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		try (PDDocument document = PDDocument.load(new File("dekalbGeneral.pdf"))) {

            document.getClass();

            if (!document.isEncrypted()) {
			
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);

                PDFTextStripper tStripper = new PDFTextStripper();

                String pdfFileInText = tStripper.getText(document);
                //System.out.println("General:\n" + pdfFileInText);

				// split by whitespace
                String lines[] = pdfFileInText.split("\\r?\\n");
                for (String line : lines) {
                	if(line.contains("Registered Mechanical Contractors") || line.contains("Registered General Contractors") || line.contains("Contractor lists are updated monthly") || line.contains("Phone:") || line.contains("CITY OF DEKALB - REGISTERED") || line.contains("Building & Code Enforcement Division")) {
                		continue;
                	}
                	Entry e = new Entry();
                	e.source = urlGeneral;
                	e.contactFirstName = " ";
                	e.contactLastName = " ";
                	
                	e.companyName = line.substring(0, firstIndexOfAddress(line));
                	String temp = line.substring(firstIndexOfAddress(line));
                	e.address1 = line.substring(firstIndexOfAddress(line), temp.indexOf(",") + (line.length() - temp.length()));
                	e.address2 = line.substring(line.indexOf(","), lastIndexOfZip(line) + 5);
                	e.zipCode = line.substring(e.address2.length() - 5);
                	e.city = e.address2.substring(0, e.address2.lastIndexOf(","));
                	e.businessPhoneNumber = line.substring(line.length() - 12);
                	
                	entries.add(e);
                }
            }

        }
		
		try (PDDocument document = PDDocument.load(new File("dekalbMechanical.pdf"))) {

            document.getClass();

            if (!document.isEncrypted()) {
			
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);

                PDFTextStripper tStripper = new PDFTextStripper();

                String pdfFileInText = tStripper.getText(document);
                //System.out.println("Mechanical:\n" + pdfFileInText);

				// split by whitespace
                String lines[] = pdfFileInText.split("\\r?\\n");
                for (String line : lines) {
                	if(line.contains("Registered Mechanical Contractors") || line.contains("Registered General Contractors") || line.contains("Contractor lists are updated monthly") || line.contains("Phone:") || line.contains("CITY OF DEKALB - REGISTERED") || line.contains("Building & Code Enforcement Division")) {
                		continue;
                	}
                	Entry e = new Entry();
                	e.source = urlMechanical;
                	e.contactFirstName = " ";
                	e.contactLastName = " ";
                	
                	e.companyName = line.substring(0, firstIndexOfAddress(line));
                	String temp = line.substring(firstIndexOfAddress(line));
                	e.address1 = line.substring(firstIndexOfAddress(line), temp.indexOf(",") + (line.length() - temp.length()));
                	e.address2 = line.substring(line.indexOf(","), lastIndexOfZip(line) + 5);
                	e.zipCode = line.substring(e.address2.length() - 5);
                	e.city = e.address2.substring(0, e.address2.lastIndexOf(","));
                	e.businessPhoneNumber = line.substring(line.length() - 12);
                	
                	entries.add(e);
                }
            }

        }
		
		client.close();
	}
	
	public int firstIndexOfAddress(String str) {
		if(str.contains("P.O. Box")) {
			return str.indexOf("P.O. Box");
		}
		else {
			if(countChar(',', str) > 3) {
				return str.indexOf(",") + 1;
			}
			for(int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);
				if(Character.isDigit(c)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public int countChar(char c, String str) {
		int count = 0;
		for(char ch : str.toCharArray()) {
			if(ch == c) {
				count++;
			}
		}
		return count;
	}
	
	public int lastIndexOfZip(String str) {
		Scanner file = new Scanner(str);
		ArrayList<String> parts = new ArrayList<String>();
		while(file.hasNext()) {
			parts.add(0, file.next());
		}
		for(String t : parts) {
			if(t.length() == 5 && isNumeric(t)) {
				return str.indexOf(t);
			}
		}
		return -1;
	}
	
	public boolean isNumeric(String str) {
		try {
	        double d = Double.parseDouble(str);
	    } catch (NumberFormatException | NullPointerException nfe) {
	        return false;
	    }
		return true;
	}
	
	public static void main(String[] args) throws InvalidPasswordException, IOException {
		new DeKalb();
	}
}
