import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {
	
	static ArrayList<Email> emailOccur = new ArrayList<Email>();
	
	public static void main(String[] args) throws IOException{
		Scanner input = new Scanner(System.in);
	    
		System.out.println("How many spreadsheets?");
		int numSheets = input.nextInt();
		input.nextLine();
		
		for(int i = 0; i < numSheets; i++) {
			System.out.println("Sheet " + (i + 1) + " name: ");
			String tempFileName = input.nextLine();
			
			Workbook workbook = WorkbookFactory.create(new File(tempFileName + ".xlsx"));
		    Sheet sheet = workbook.getSheetAt(0);
		    
		    updateEmails(sheet);
		    
		    Collections.sort(emailOccur);
		}
		
		for(Email email : emailOccur) {
			System.out.println(email.email + " occurs " + email.count + " time(s).");
		}
	}
	
	public static void updateEmails(Sheet sheet) {
		Iterator<Row> rowIterator = sheet.rowIterator();
		while(rowIterator.hasNext()) {
			Row r = rowIterator.next();
			String email = r.getCell(0).toString();
			if(email.isEmpty()) {
				continue;
			}
			if(!containsEmail(email)) {
				emailOccur.add(new Email(email, 1));
			}
			else {
				emailOccur.get(indexOfEmail(email)).count++;
			}
		}
	}
	
	public static int indexOfEmail(String email) {
		for(int i = 0; i < emailOccur.size(); i++) {
			if(emailOccur.get(i).email.equals(email)) {
				return i;
			}
		}
		return -1;
	}
	
	public static boolean containsEmail(String email) {
		for(Email e : emailOccur) {
			if(e.email.equals(email)) {
				return true;
			}
		}
		return false;
	}
}

class Email implements Comparable{
	String email;
	int count;
	
	public Email() {
		email = "";
		count = 1;
	}
	
	public Email(String a, int b) {
		email = a;
		count = b;
	}

	@Override
	public int compareTo(Object a) {
		Email o = (Email)a;
		if(count > o.count) {
			return -1;
		}
		else if(count < o.count) {
			return 1;
		}
		if(email.compareTo(o.email) > 1) {
			return 1;
		}
		else if(email.compareTo(o.email) < 1) {
			return -1;
		}
		return 0;
	}
}
