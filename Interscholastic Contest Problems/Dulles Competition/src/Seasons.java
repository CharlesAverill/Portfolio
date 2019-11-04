import java.io.*;
import java.util.*;

public class Seasons {
	public void run() throws Exception {
		Scanner file = new Scanner(new File("Seasons.dat"));
		int times = file.nextInt(); file.nextLine();
		ArrayList<String> months = new ArrayList<String>();
		months.add("January");
		months.add("February");
		months.add("March");
		months.add("April");
		months.add("May");
		months.add("June");
		months.add("July");
		months.add("August");
		months.add("September");
		months.add("October");
		months.add("November");
		months.add("December");
		GregorianCalendar gc = new GregorianCalendar();
		for (int ab = 0; ab < times; ab++) {
			int distance = file.nextInt(); file.nextLine();
			
			String month = file.next();
			String temp = file.next();
			int day = Integer.parseInt(temp.substring(0, temp.length() - 1));
			int year = file.nextInt();
			
			int speed;
			if(months.indexOf(month) < 4 && day <= 5) {
				speed = 3;
			}
			else if(months.indexOf(month) >= 4 && months.indexOf(month) <= 8) {
				speed = 5;
			}
			else speed = 1;
			int days = distance / speed;
			//System.out.println(days);
			
			
			
			gc.set(year, months.indexOf(month), day);
			gc.add(gc.DAY_OF_YEAR, days);
			System.out.println(gc.DAY_OF_YEAR);
			System.out.println(gc.get(gc.MONTH) + " " + gc.get(gc.DATE) + ", " + gc.get(gc.YEAR));
		}
	}

	public static void main(String[] args) throws Exception {
		new Seasons().run();
	}
}