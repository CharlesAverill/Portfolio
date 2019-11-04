public class Wind{
	
	private String direction;
	private int duration;
	private int strength;
	
	public Wind() {
		direction = randomDirection();
		duration = (int)(100 * Math.random());
		strength = (int)(10 * Math.random());
	}
	
	public String getDirection() {
		return direction;
	}
	
	public String randomDirection() {
		double temp = Math.random();
		if(temp < 0.25) {
			return ("North");
		}
		if(temp >= 0.25 && temp < 0.5) {
			return ("South");
		}
		if(temp >= 0.5 && temp < 0.75) {
			return ("East");
		}
		else {
			return ("West");
		}
	}
	
	public void passTime(int minutes) {
		duration -= minutes;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public String toString() {
		return "Direction: " + direction + "\nTime Left: " + Time.minutesToTime(duration);
	}
}