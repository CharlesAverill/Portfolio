public class Fire{
	
	private double size;
	private int birthtime;
	private double fuel;
	private int x;
	private int y;
	
	public Fire() {
		size = 1.0;
		birthtime = Game.masterTime.getMins();
		fuel = 1.0;
		x = 3;
		y = 3;
	}
	
	public Fire(double s, double f, int a, int b) {
		size = s;
		birthtime = Game.masterTime.getMins();
		fuel = f;
		x = a;
		y = b;
	}
	
	public double getSize() {
		return size;
	}
	public double getFuel() {
		return fuel;
	}
	
	public int getAge() {
		//Current time minus the birthtime gets age duh
		return (Game.masterTime.getMins() - birthtime);
	}
	
	//Will eventually add interaction for all obtainable items to give more player freedom. Better fuel could make the growing trait last longer, etc.
	public void feed(Item o) {
		if(o.getName().equals("Wood")) {
			size += .3;
			fuel += 1;
		}
		
		if(o.getName().equals("Water")) {
			size -= .7;
			fuel -= .7;
		}
		
		if(o.getName().equals("Lighter Fluid")) {
			size += 3;
			fuel += .1;
		}
		
		if(o.getName().equals("Fire Extinguisher")) {
			size = 0.0;
		}
	}
	
	public void spread(Wind w, Location l) {
		if(!l.hitsBoundary(w.getDirection()) && !l.hitsWater(w.getDirection())) {
			if(w.getDirection().equals("North")) {
				l.addFire(l.getX(), l.getY() - 1, new Fire(size / 2, fuel / 2, l.getX(), l.getY() - 1));
			}
			if(w.getDirection().equals("South")) {
				l.addFire(l.getX(), l.getY() + 1, new Fire(size / 2, fuel / 2, l.getX(), l.getY() + 1));
			}
			if(w.getDirection().equals("East")) {
				l.addFire(l.getX() + 1, l.getY(), new Fire(size / 2, fuel / 2, l.getX() + 1, l.getY()));
			}
			if(w.getDirection().equals("West")) {
				l.addFire(l.getX() - 1, l.getY(), new Fire(size / 2, fuel / 2, l.getX() - 1, l.getY()));
			}
		}
	}
	
	public static void spreadMap(Wind w, Location l) {
		Fire[][] fires = l.getFires();
		for(Fire[] n : fires) {
			for(Fire f : n) {
				if(f != null) {
					f.spread(w,  l);
				}
			}
		}
	}
	
	public String toString() {
		return "Size: " + size + 
				"\nAge: " + "(" + Time.minutesToTime(getAge()) + ")" + 
				"\nFuel: " + fuel;
	}
}