public class Time{
	
	//total does the real work, minutes and hours are just for output
	public int total;
	private int minutes;
	private int hours;
	
	public Time() {
		total = 0;
		minutes = 0;
		hours = 0;
	}
	
	public void add(int mins) {
		total += mins;
	}
	
	//mod bless up
	public static String minutesToTime(int mins) {
		return String.format("%02d:%02d", mins / 60, mins % 60);
	}
	
	public int getMins() {
		return total;
	}
	
	//returns in (00:00) format
	public String toString() {
		hours = total / 60;
		minutes = total % 60;
		return("" + hours + ":" + minutes);
	}
}