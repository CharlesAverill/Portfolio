import java.util.*;
import java.io.*;

public class prob03 {
	
	public static final long[] c = new long[64];
	
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(new File("prob03.in"));
		
		while(file.hasNext()) {
			int n = file.nextInt();
			if(n == 0) {
				break;
			}
			
			int b = 1;
			boolean isMagnanimous = true;
			while(b <= ("" + n).length() - 1) {
				String n1 = "" + n;
				String first = n1.substring(0, b);
				String second = n1.substring(b);
				isMagnanimous = isMagnanimous && isPrime(Integer.parseInt(first) + Integer.parseInt(second));
				b++;
			}
			if(isMagnanimous) {
				System.out.println("" + n + " MAGNANIMOUS");
			}
			else {
				System.out.println("" + n + " PETTY");
			}
		}
	}
	
	public static boolean isPrime(int num) 
	{
	        if (num == 2) 
	            return true;
	        if (num < 2 || num % 2 == 0) 
	            return false;
	        for (int i = 3; i * i <= num; i += 2)
	            if (num % i == 0) 
	                return false;
	        return true;
	}
}
