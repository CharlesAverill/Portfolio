import java.io.*;
import java.util.*;

public class Clothes {
	public void run() throws Exception {
		Scanner file = new Scanner(new File("Clothes.dat"));
		int d = file.nextInt(); file.nextLine();
		for (int ab = 0; ab < d; ab++) {
			int c = file.nextInt(); file.nextLine();
			
			ArrayList<Article> articles = new ArrayList<Article>();
			for(int i = 0; i < c; i++) {
				String line = file.nextLine();
				articles.add(0, new Article(line.substring(0, line.indexOf("(") - 1), line.substring(line.indexOf("(") + 1, line.indexOf(")"))));
			}
			
			ArrayList<ArrayList<Article>> wearing = new ArrayList<ArrayList<Article>>();
			
			
		}
	}
	
	public boolean containsArticle(String type, ArrayList<Article> arr) {
		for(Article article : arr) {
			if(article.type.equals(type)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
		new Clothes().run();
	}
}

class Article{
	String type;
	String name;
	
	public Article(String n, String t) {
		name = n;
		type = t;
	}
	
	public String toString() {
		return name + " (" + type + ")";
	}
}