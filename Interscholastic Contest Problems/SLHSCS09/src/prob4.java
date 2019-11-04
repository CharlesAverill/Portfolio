import java.util.*;
import java.io.*;

public class prob4 {
	
	static int money;
	static int exp;
	
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("prob4.in"));
		
		int n = file.nextInt();

		file.nextLine();
		
		for(int i = 0; i < n; i++) {
			int a = file.nextInt();
			int b = file.nextInt();
			if(i != n - 1) {
				file.nextLine();
			}
			
			
			ArrayList<Monster> monsters = new ArrayList<Monster>();
			ArrayList<Equipment> equipment = new ArrayList<Equipment>();
			
			for(int j = 0; j < a; j++) {
				monsters.add(new Monster(file.next(), file.nextInt(), file.nextInt(), file.nextInt()));
				//System.out.println(monsters.get(j));
				if(j != a - 1) {
					file.nextLine();
				}
			}
			
			for(int j = 0; j < b; j++) {
				equipment.add(new Equipment(file.next(), file.nextInt(), file.nextInt()));
				//System.out.println(equipment.get(j));
				if(j != b - 1) {
					file.nextLine();
				}
			}
			Game(monsters, equipment);
			System.out.println();
		}
	}
	
	public static void Game(ArrayList<Monster> monsters, ArrayList<Equipment> equipment) {
		money = 0;
		exp = 0;
		
		while(exp < 10) {
			int nowEXP = exp;
			if(monsters.size() > 0 && bestCanBeat(exp, monsters, equipment) != null) {
				exp += bestCanBeat(nowEXP, monsters, equipment).expBonus;
				System.out.println("Defeated a " + bestCanBeat(nowEXP, monsters, equipment));
				money += bestCanBeat(nowEXP, monsters, equipment).moneyBonus;
			}
			
			Equipment temp = bestCanBuy(money, equipment);
			if(equipment.size() > 0 && temp != null) {
				exp += temp.expBonus;
				System.out.println("Bought a " + temp);
				money -= temp.cost;
			}
		}
		
		System.out.println("Gained 10 experience");
	}
	
	public static Monster bestCanBeat(int exp, ArrayList<Monster> monsters, ArrayList<Equipment> equipment) {
		int bestXP = 0;
		int bestMoney = 0;
		for(int i = 0; i < monsters.size(); i++) {
			if(money > getCheapest(equipment).cost || money == 0) {
				if(monsters.get(i).exp <= exp && monsters.get(i).expBonus >= bestXP) {
					return monsters.get(i);
				}
			}
			if(monsters.get(i).exp <= exp && monsters.get(i).expBonus >= bestXP && monsters.get(i).moneyBonus >= bestMoney) {
				return monsters.get(i);
			}
		}
		return null;
	}
	
	public static Equipment getCheapest(ArrayList<Equipment> equipment) {
		int c = Integer.MAX_VALUE;
		int index = 0;
		for(int i = 0; i < equipment.size(); i++) {
			Equipment o = equipment.get(i);
			if(c > o.cost && o.expBonus > 0) {
				c = o.cost;
				index = i;
			}
		}
		return equipment.get(index);
	}
	
	public static Equipment bestCanBuy(int money, ArrayList<Equipment> equipment) {
		int bestBonus = 0;
		for(int i = 0; i < equipment.size(); i++) {
			if(equipment.get(i).cost <= money && equipment.get(i).expBonus > 0) {
				Equipment a = equipment.get(i);
				equipment.remove(equipment.get(i));
				return a;
			}
		}
		return null;
	}
}

class Monster{
	public String name;
	public int exp;
	public int moneyBonus;
	public int expBonus;
	
	public Monster(String n, int a, int b, int c) {
		name = n;
		exp = a;
		moneyBonus = b;
		expBonus = c;
	}
	
	public String toString() {
		return name + " " + exp + " " + moneyBonus + " " + expBonus;
	}
}

class Equipment{
	public String name;
	public int cost;
	public int expBonus;
	
	public Equipment(String n, int a, int b) {
		name = n;
		cost = a;
		expBonus = b;
	}
	
	public String toString() {
		return (name + " " + cost + " " + expBonus);
	}
}