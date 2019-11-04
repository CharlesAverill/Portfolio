import java.util.*;
import java.io.*;
	
public class BeanManager {
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("beanmanager.dat"));
		int n = file.nextInt();
		file.nextLine();
		for(int j = 0; j < n; j++) {
			int x = file.nextInt();
			ArrayList<Bean> beans = new ArrayList<Bean>();
			for(int i = 0; i < x; i++) {
				String name = file.next();
				String color = file.next();
				String c1 = file.next();
				String c = c1.substring(c1.indexOf("$") + 1);
				double cost = Double.parseDouble(c);
				String w1 = file.next();
				String w = w1.substring(0, w1.indexOf("g"));
				int weight = Integer.parseInt(w);
				
				beans.add(new Bean(name, color, cost, weight));
			}
			Collections.sort(beans, new Comparator<Bean>() {
				public int compare(Bean a, Bean b) {
					if(a.cost != b.cost) {
						if(a.weight != b.weight) {
							if(!a.color.equals(b.color)) {
								if(!a.name.equals(b.name)) {
									if(a.name.compareTo(b.name) > 0) {
										return 1;
									}
									return -1;
								}
								if(a.color.compareTo(b.color) > 0) {
									return 1;
								}
								return -1;
							}
							if(a.weight < b.weight) {
								return 1;
							}
							return -1;
						}
						if(a.cost > b.cost) {
							return 1;
						}
						return -1;
					}
					return 0;
				}
			});
			
			for(int i = 0; i < beans.size(); i++) {
				Bean bean = beans.get(i);
				System.out.print(bean.name + " " + bean.color + " " + bean.weight + "g" + " $");
				System.out.printf("%.2f\n", bean.cost);
			}
			System.out.println();
		}
	}
}

class Bean{
	public String name, color;
	public double cost;
	public int weight;
	
	public Bean(String n, String c, double co, int w) {
		name = n;
		color = c;
		cost = co;
		weight = w;
	}
}