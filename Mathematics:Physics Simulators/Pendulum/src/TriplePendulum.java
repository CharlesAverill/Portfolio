import java.awt.*;
import java.util.*;
import java.io.*;

public class TriplePendulum extends Canvas{
	
	public static ArrayList<Point> paintPoints;
	private int px1;
	private int px2;
	private int px3;
	private int py1;
	private int py2;
	private int py3;
	int length1 = 100;
	int length2 = 100;
	int length3 = 100;
	private double phi1;
	private double omega1;
	private double phi2;
	private double omega2;
	private double phi3;
	private double omega3;
	private int reib;
	private static final double grav = 9.81D; private static final double dt = 5.0E-4D; private double[] k1 = new double[4];
	private double[] k2 = new double[4];
	private double[] k3 = new double[4];
	private double[] l1 = new double[4];
	private double[] l2 = new double[4];
	private double[] l3 = new double[4];
	private double[] startwert = new double[3];
	private int startwert_reib;
	int xOffset = 300;
	int yOffset = 250;
	int radius = 20;
    int lastx3;
    int lasty3;
    int step;
	
	public TriplePendulum() {
		
		paintPoints = new ArrayList<Point>();
		
		startwert[0] = 2.2689280275926285D;
	    startwert[1] = 2.2689280275926285D;
	    startwert[2] = 2.2689280275926285D;
	    startwert_reib = 0;
	    
		phi1 = startwert[0];
	    phi2 = startwert[1];
	    phi3 = startwert[2];
	    reib = startwert_reib;
	    omega1 = 0.0D;
	    omega2 = 0.0D;
	    omega3 = 0.0D;
		
	    step = 0;
	}
	
	public void integrate(){
	        try { Thread.sleep(10L);
	        } catch (InterruptedException localInterruptedException2) {}
	      }{ runge_step_phi1();
	        runge_step_phi2();
	        runge_step_phi3();
	        phi1 += (k1[0] + 2.0D * k1[1] + 2.0D * k1[2] + k1[3]) / 6.0D;
	        omega1 += (l1[0] + 2.0D * l1[1] + 2.0D * l1[2] + l1[3]) / 6.0D;
	        phi2 += (k2[0] + 2.0D * k2[1] + 2.0D * k2[2] + k2[3]) / 6.0D;
	        omega2 += (l2[0] + 2.0D * l2[1] + 2.0D * l2[2] + l2[3]) / 6.0D;
	        phi3 += (k3[0] + 2.0D * k3[1] + 2.0D * k3[2] + k3[3]) / 6.0D;
	        omega3 += (l3[0] + 2.0D * l3[1] + 2.0D * l3[2] + l3[3]) / 6.0D;
	        
	        if (step % 20 == 0) {
	          try {
	            Thread.sleep(8L); } catch (InterruptedException localInterruptedException3) {}
	          	px1 = ((int)Math.round(245.0D + 55.0D * Math.sin(phi1)));
	          	py1 = ((int)Math.round(165.0D + 55.0D * Math.cos(phi1)));
	          	px2 = ((int)Math.round(px1 + 55.0D * Math.sin(phi2)));
	          	py2 = ((int)Math.round(py1 + 55.0D * Math.cos(phi2)));
	          	px3 = ((int)Math.round(px2 + 55.0D * Math.sin(phi3)));
	          	py3 = ((int)Math.round(py2 + 55.0D * Math.cos(phi3)));
	          	repaint();
	        }
	        step += 1;
	}
	
	public void paint(Graphics g){
		step++;
		if(step % 2 == 0) {
			integrate();
		}
		g.setColor(Color.RED);
		g.drawLine(xOffset, yOffset, px1 + xOffset - radius / 2, py1 + yOffset - radius / 2);
		g.drawLine(px1 + xOffset - radius / 2, py1 + yOffset - radius / 2, px2 + xOffset - radius / 2, py2 + yOffset - radius / 2);
		g.drawLine(px2 + xOffset - radius / 2, py2 + yOffset - radius / 2, px3 + xOffset - radius / 2, px3 + yOffset - radius / 2);
		g.fillOval(px1 + xOffset - radius, py1 + yOffset - radius, radius, radius);
		g.fillOval(px2 + xOffset - radius, py2 + yOffset - radius, radius, radius);
		g.fillOval(px3 + xOffset - radius, px3 + yOffset - radius, radius, radius);
		
		paintPoints.add(new Point(px3 + xOffset - radius / 2, py3 + yOffset - radius));
		g.setColor(Color.BLUE);
		lastx3 = paintPoints.get(0).x;
		lasty3 = paintPoints.get(0).y;
		for(int i = 0; i < paintPoints.size(); i++) {
			Point p = paintPoints.get(i);
			if(p.age <= 1 || p.age > 1) {
				if(!isCovered(p) && !p.painted) {
					p.painted = true;
					g.drawLine(p.x, p.y, lastx3, lasty3);
				}
				else if(isCovered(p)) {
					p.painted = false;
				}
				if(i != paintPoints.size() - 1) {
					//g.drawLine(p.x, p.y, lastx3, lasty3);
				}
			}
			lastx3 = p.x;
			lasty3 = p.y;
		}
	}
	
	public boolean isCovered(Point p) {
		p.age++;
		double distance1 = Math.sqrt((px1 - p.x) * (px1 - p.x) + (py1 - p.y) * (py1 - p.y));
		if(distance1 < radius) {
			return true;
		}
		double distance2 = Math.sqrt((px2 - p.x) * (px2 - p.x) + (py2 - p.y) * (py2 - p.y));
		if(distance2 < radius) {
			return true;
		}
		return false;
	}
	
	public void runge_step_phi1()
	  {
	    k1[0] = (5.0E-4D * omega1);
	    l1[0] = (5.0E-4D * forcephi1(phi1, omega1, phi2, omega2, phi3, omega3));
	    k1[1] = (5.0E-4D * (omega1 + l1[0] / 2.0D));
	    l1[1] = (5.0E-4D * forcephi1(phi1 + k1[0] / 2.0D, omega1 + l1[0] / 2.0D, phi2, omega2, phi3, omega3));
	    k1[2] = (5.0E-4D * (omega1 + l1[1] / 2.0D));
	    l1[2] = (5.0E-4D * forcephi1(phi1 + k1[1] / 2.0D, omega1 + l1[1] / 2.0D, phi2, omega2, phi3, omega3));
	    k1[3] = (5.0E-4D * (omega1 + l1[2]));
	    l1[3] = (5.0E-4D * forcephi1(phi1 + k1[2], omega1 + l1[2], phi2, omega2, phi3, omega3));
	  }
	  
	  public void runge_step_phi2()
	  {
	    k2[0] = (5.0E-4D * omega2);
	    l2[0] = (5.0E-4D * forcephi2(phi1, omega1, phi2, omega2, phi3, omega3));
	    k2[1] = (5.0E-4D * (omega2 + l2[0] / 2.0D));
	    l2[1] = (5.0E-4D * forcephi2(phi1, omega1, phi2 + k2[0] / 2.0D, omega2 + l2[0] / 2.0D, phi3, omega3));
	    k2[2] = (5.0E-4D * (omega2 + l2[1] / 2.0D));
	    l2[2] = (5.0E-4D * forcephi2(phi1, omega1, phi2 + k2[1] / 2.0D, omega2 + l2[1] / 2.0D, phi3, omega3));
	    k2[3] = (5.0E-4D * (omega2 + l2[2]));
	    l2[3] = (5.0E-4D * forcephi2(phi1, omega1, phi2 + k2[2], omega2 + l2[2], phi3, omega3));
	  }
	  
	  public void runge_step_phi3()
	  {
	    k3[0] = (5.0E-4D * omega3);
	    l3[0] = (5.0E-4D * forcephi3(phi1, omega1, phi2, omega2, phi3, omega3));
	    k3[1] = (5.0E-4D * (omega3 + l3[0] / 2.0D));
	    l3[1] = (5.0E-4D * forcephi3(phi1, omega1, phi2, omega2, phi3 + k3[0] / 2.0D, omega3 + l3[0] / 2.0D));
	    k3[2] = (5.0E-4D * (omega3 + l3[1] / 2.0D));
	    l3[2] = (5.0E-4D * forcephi3(phi1, omega1, phi2, omega2, phi3 + k3[1] / 2.0D, omega3 + l3[1] / 2.0D));
	    k3[3] = (5.0E-4D * (omega3 + l3[2]));
	    l3[3] = (5.0E-4D * forcephi3(phi1, omega1, phi2, omega2, phi3 + k3[2], omega3 + l3[2]));
	  }
	  
	  public double forcephi1(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
		  return (98.10000000000001D * Math.sin(paramDouble1) + 39.24D * Math.sin(paramDouble1 - 2.0D * paramDouble3) - 9.81D * Math.sin(paramDouble1 + 2.0D * paramDouble3 - 2.0D * paramDouble5) - 9.81D * Math.sin(paramDouble1 - 2.0D * paramDouble3 + 2.0D * paramDouble5) + 4.0D * paramDouble2 * paramDouble2 * Math.sin(2.0D * paramDouble1 - 2.0D * paramDouble3) + 8.0D * paramDouble4 * paramDouble4 * Math.sin(paramDouble1 - paramDouble3) + 2.0D * paramDouble6 * paramDouble6 * Math.sin(paramDouble1 - paramDouble5) + 2.0D * paramDouble6 * paramDouble6 * Math.sin(paramDouble1 - 2.0D * paramDouble3 + paramDouble5)) / (-10.0D + 4.0D * Math.cos(2.0D * paramDouble1 - 2.0D * paramDouble3) + 2.0D * Math.cos(2.0D * paramDouble3 - 2.0D * paramDouble5)) - 0.1D * reib * paramDouble2;
	  }
	  
	  public double forcephi2(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) { 
		  return (-68.67D * Math.sin(2.0D * paramDouble1 - paramDouble3) + 68.67D * Math.sin(paramDouble3) + 9.81D * Math.sin(paramDouble3 - 2.0D * paramDouble5) + 9.81D * Math.sin(2.0D * paramDouble1 + paramDouble3 - 2.0D * paramDouble5) + 2.0D * paramDouble2 * paramDouble2 * Math.sin(paramDouble1 + paramDouble3 - 2.0D * paramDouble5) - 14.0D * paramDouble2 * paramDouble2 * Math.sin(paramDouble1 - paramDouble3) + 2.0D * paramDouble4 * paramDouble4 * Math.sin(2.0D * paramDouble3 - 2.0D * paramDouble5) - 4.0D * paramDouble4 * paramDouble4 * Math.sin(2.0D * paramDouble1 - 2.0D * paramDouble3) + 6.0D * paramDouble6 * paramDouble6 * Math.sin(paramDouble3 - paramDouble5) - 2.0D * paramDouble6 * paramDouble6 * Math.sin(2.0D * paramDouble1 - paramDouble3 - paramDouble5)) / (-10.0D + 4.0D * Math.cos(2.0D * paramDouble1 - 2.0D * paramDouble3) + 2.0D * Math.cos(2.0D * paramDouble3 - 2.0D * paramDouble5)) - 0.1D * reib * paramDouble4;
	  }
	  
	  public double forcephi3(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6){
		    return -2.0D * Math.sin(paramDouble3 - paramDouble5) * (9.81D * Math.cos(2.0D * paramDouble1 - paramDouble3) + 9.81D * Math.cos(paramDouble3) + 2.0D * paramDouble2 * paramDouble2 * Math.cos(paramDouble1 - paramDouble3) + 2.0D * paramDouble4 * paramDouble4 + paramDouble6 * paramDouble6 * Math.cos(paramDouble3 - paramDouble5)) / (-5.0D + 2.0D * Math.cos(2.0D * paramDouble1 - 2.0D * paramDouble3) + Math.cos(2.0D * paramDouble3 - 2.0D * paramDouble5)) - 0.1D * reib * paramDouble6;
	  }
}
