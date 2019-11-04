import java.io.*;
import java.util.*;

public class ReWordWrap {
	public void run() throws Exception {
		Scanner file = new Scanner(new File("ReWordWrap.dat"));
		int times = file.nextInt();
		for (int ab = 0; ab < times; ab++) {
			
			ArrayList<String> totalWords = new ArrayList<String>();
			int wordWrap = file.nextInt(); file.nextLine();
			int lineCount = file.nextInt(); file.nextLine();
			for(int g=0;g<lineCount;g++) {
				String[] words = file.nextLine().split(" ");
				for(String q:words) {
					totalWords.add(q + " ");
				}
			}
		
			int tempCharCount =0;
			
			for(int x=0;x<totalWords.size()-1;x++) {
				String gg = totalWords.get(x);
				String ggg = totalWords.get(x+1);
				if((tempCharCount)<=wordWrap){
					System.out.print(gg);
					tempCharCount+=gg.length();
					if((tempCharCount +ggg.length())>wordWrap) {
						System.out.println();
						continue;
					}
				}
				else {
					tempCharCount=0;
				}
			}
			
			
		}
	}

	public static void main(String[] args) throws Exception {
		new ReWordWrap().run();
	}
}