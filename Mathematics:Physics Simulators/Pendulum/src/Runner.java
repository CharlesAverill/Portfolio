import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.*;

public class Runner extends JFrame{
	
	public Runner() throws InterruptedException {
		super("Double Pendulum");
		setVisible(false);
		Scanner input = new Scanner(System.in);
		System.out.println("1) Double Pendulum\n2) Triple Pendulum");
		String choice = "1";
		if(choice.equals("1")) {
			DoublePendulum dp = new DoublePendulum();
			dp.setDoubleBuffered(true);
			setVisible(false);
			setSize(600, 500);
			getContentPane().add(dp);
			setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			while(true) {
				dp.repaint();
				Thread.sleep(15);
			}
		}
		else if(choice.equals("2")) {
			TriplePendulum tp = new TriplePendulum();
			setVisible(false);
			setSize(600, 500);
			getContentPane().add(tp);
			setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			while(true) {
				tp.repaint();
				//Thread.sleep(15);
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Runner r = new Runner();
	}
}
