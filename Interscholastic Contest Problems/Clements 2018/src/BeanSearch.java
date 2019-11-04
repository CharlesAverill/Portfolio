import java.util.*;
import java.io.*;
import java.text.*;
import java.math.*;
import static java.lang.System.*;
import static java.lang.Integer.*;
import static java.lang.Double.*;
import static java.lang.Math.*;

			//change the class name
public class BeanSearch
{
	public void run() throws Exception
	{
		Scanner file = new Scanner(new File("beansearch.dat"));
		
			//read in the number at the top of the data file
		int times = file.nextInt(); 
			//pick up the left over enter key
		file.nextLine();
		
			//read in each data set
		for(int x = 0; x<times; x++)
		{
			int r = file.nextInt();
			int c = file.nextInt();
			file.nextLine();
			char[][]mat=new char[r][c];
			for(int i = 0;i<mat.length;i++)
			{
				mat[i]=file.nextLine().toCharArray();
			}
			int find = file.nextInt();
			
			char[][]words=new char[r][c];
			for(int i = 0;i<mat.length;i++)
			{
				words[i]=file.nextLine().toCharArray();
			}
			for(int y = 0;y<find;y++)
			{
				boolean found = false;
				for(int i = 0;i<mat.length;i++)
				{
					for(int j = 0;j<mat.length;j++)
					{
						int r2 = i;
						int c2=j;
						if(found!=true) {
							for(int f = 0;f<words[y].length&&r2<mat.length&&c2<mat[0].length;f++)
							{
								if(mat[r2][c2]==words[y][f])
								{
									found=true;
								}
								else
								{
									found=false;
									break;
								}
								r2++;
								c2++;
							}
						}
						if(found!=true)
						{
							r2 = i;
							c2=j;
							for(int f = 0;f<words[y].length&&r2<mat.length&&c2<mat[0].length;f++)
							{
								if(mat[r2][c2]==words[y][f])
								{
									found=true;
								}
								else
								{
									found=false;
									break;
								}
								r2++;
								
							}
						}
						if(found!=true)
						{
							r2 = i;
							c2=j;
							for(int f = 0;f<words[y].length&&r2<mat.length&&c2<mat[0].length;f++)
							{
								if(mat[r2][c2]==words[y][f])
								{
									found=true;
								}
								else
								{
									found=false;
									break;
								}
								c2++;
								
							}
						}
					}
					if(found==true)
					{
						break;
					}
				}
				if(found==true)
				{
					System.out.println("FOUND");
				}
				else {
					System.out.println("NOT FOUND");
				}
			}
		}
	}

	public static void main(String[] args) throws Exception
	{
			//change this to whatever your class name is
		new BeanSearch().run();
	}	
	
}