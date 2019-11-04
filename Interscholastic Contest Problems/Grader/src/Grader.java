import java.util.*;
import java.io.*;

public class Grader {
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("Grader.dat"));
		
		int x = file.nextInt();
		file.nextLine();
		
		for(int i = 0; i < x; i++) {
			System.out.println("Test #" + (i + 1) + ":");
			String[] key = file.nextLine().split("");
			
			int n = file.nextInt();
			int r = file.nextInt();
			int w = file.nextInt();
			int s = file.nextInt();
			int l = file.nextInt();
			file.nextLine();

			ArrayList<Student> students = new ArrayList<Student>();
			for(int j = 0; j < n; j++) {
				String name = file.next();
				String[] answers = file.nextLine().substring(1).split("");
				//System.out.println(Arrays.toString(key));
				
				int score = (r * right(key, answers)) + (w * wrong(key, answers)) + (s * skip(answers));
				//System.out.println(right(key, answers));
				students.add(new Student(score, name));
			}
			
			Collections.sort(students, new Comparator<Student>() {
				public int compare(Student a, Student b) {
					if(a.score > b.score) {
						return -1;
					}
					else return 1;
				}
			});
			boolean didLine = false;
			for(int j = 0; j < students.size(); j++) {
				String condition;
				if(students.get(j).score >= l) {
					condition = " lives to see another day";
				}
				else {
					condition = " gets the meter stick";
					if(!didLine) {
						System.out.println();
						didLine = true;
					}
				}
				System.out.println("" + students.get(j).score + " : " + students.get(j).name + condition);
				if(j == students.size() - 1) {
					System.out.println();
				}
			}
		}
	}
	
	public static int right(String[] a, String[] b) {
		int output = 0;
		for(int i = 0; i < a.length; i++) {
			if(b[i].equals(a[i])) {
				output++;
			}
		}
		return output;
	}
	
	public static int wrong(String[] a, String[] b) {
		int output = 0;
		for(int i = 0; i < a.length; i++) {
			if(!b[i].equals(a[i]) && !b[i].equals(" ")) {
				output++;
			}
		}
		return output;
	}
	
	public static int skip(String[] b) {
		int output = 0;
		for(int i = 0; i < b.length; i++) {
			if(b[i].equals(" ")) {
				output++;
			}
		}
		return output;
	}
}
class Student{
	public int score;
	public String name;
	public Student(int s, String n) {
		score = s;
		name = n;
	}
}