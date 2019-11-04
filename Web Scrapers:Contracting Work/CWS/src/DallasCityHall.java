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
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gargoylesoftware.htmlunit.WebClient;

public class DallasCityHall extends Scraper{
	
	
	public DallasCityHall() throws FileNotFoundException {
		System.out.println("Dallas City Hall");
		
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
			
		System.out.println("Retrieving Excel sheet...");
			
		try {	
			
			String url = "https://dallascityhall.com/departments/sustainabledevelopment/buildinginspection/DCH%20documents/excel/Registered_Contractors.xlsx";
				
			FileUtils.copyURLToFile(new URL(url), new File("dallascityhall.xlsx"), 0, 0);
			XSSFWorkbook workbook = new XSSFWorkbook(new File("dallascityhall.xlsx"));
			XSSFSheet sheet = workbook.getSheetAt(0);
			DataFormatter formatter = new DataFormatter();
				
			System.out.println("Excel sheet retrieved");
			
			for(Row row : sheet) {
				if(row.getRowNum() < 1) {
					continue;
				}
				Entry entry = new Entry();
				entry.source = url;
				for(Cell cell : row) {
					if(cell.getColumnIndex() < 1) {
						continue;
					}
					String text = formatter.formatCellValue(cell);
					//System.out.println(text);
					switch(cell.getColumnIndex()) {
						case 1: if(text.contains(",") && !text.contains(", LLC") && !text.contains(", INC")){
									entry.contactLastName = text.substring(0, text.indexOf(","));
									entry.contactFirstName = text.substring(text.indexOf(",") + 1);
								}
								else entry.companyName = text;
								break;
						case 2: entry.address1 = text;
								break;
						case 3: entry.address2 = text;
								entry.city = entry.address2.substring(0, entry.address2.indexOf(","));
								entry.zipCode = entry.address2.substring(entry.address2.length() - 5);
								break;
						case 4: entry.businessPhoneNumber = text;
								break;
						case 5: entry.email = text;
								break;
						default: break;
					}
				}
				entries.add(entry);
			}
			
			workbook.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		client.close();
	}
}
