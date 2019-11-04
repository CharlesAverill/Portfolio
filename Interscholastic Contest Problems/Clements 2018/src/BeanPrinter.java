import java.util.*;
import java.io.*;
import java.text.*;
import java.math.*;
import static java.lang.System.*;
import static java.lang.Integer.*;
import static java.lang.Double.*;
import static java.lang.Math.*;

			//change the class name
public class BeanPrinter
{
	public void run() throws Exception
	{
		//Scanner file = new Scanner(new File("template.dat"));
		
			//read in the number at the top of the data file
		//int times = file.nextInt(); 
			//pick up the left over enter key
		//file.nextLine();
		
			//read in each data set
		System.out.println("BBBBB-EEEEE-------A-------N-----N\n" + 
				"B---B-E----------A-A------NN----N\n" + 
				"B---B-E---------A---A-----N-N---N\n" + 
				"BBBB--EEEEE----AAAAAAA----N--N--N\n" + 
				"B---B-E-------A-------A---N---N-N\n" + 
				"B---B-E------A---------A--N----NN\n" + 
				"BBBBB-EEEEE-A-----------A-N-----N");
	}

	public static void main(String[] args) throws Exception
	{
			//change this to whatever your class name is
		new BeanPrinter().run();
	}	
	
}