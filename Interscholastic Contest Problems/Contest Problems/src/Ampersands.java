import java.util.*;
import java.io.*;

public class Ampersands {
	public static void main(String[] args) throws IOException{
		for(int i = 20; i > 0; i--) {
			for(int j = 0; j < i; j++) {
				System.out.print("&");
			}
			System.out.println();
		}
	}
}
