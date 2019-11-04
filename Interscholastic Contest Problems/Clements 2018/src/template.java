import java.util.*;
import java.io.*;
import java.text.*;
import java.math.*;
import static java.lang.System.*;
import static java.lang.Integer.*;
import static java.lang.Double.*;
import static java.lang.Math.*;

			//change the class name
public class template
{
	public void run() throws Exception
	{
		Scanner file = new Scanner(new File("template.dat"));
		
			//read in the number at the top of the data file
		int times = file.nextInt(); 
			//pick up the left over enter key
		file.nextLine();
		
			//read in each data set
		for(int i = 0; i<times; i++)
		{
			
		}
	}

	public static void main(String[] args) throws Exception
	{
			//change this to whatever your class name is
		new template().run();
	}	
	
}