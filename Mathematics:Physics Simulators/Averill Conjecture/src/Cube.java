import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import javax.swing.*;

public class Cube extends JPanel{
	
	static double[] data;
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int PAD = 40;
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        // Draw ordinate.
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD));
        // Draw abcissa.
        g2.draw(new Line2D.Double(PAD, h-PAD, w-PAD, h-PAD));
        // Draw labels.
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics("0", frc);
        float sh = lm.getAscent() + lm.getDescent();
        // Ordinate label.
        int SPAD = 2;
        String s = "Surface Area";
        float sy = PAD + ((h - 2*PAD) - s.length()*sh)/2 + lm.getAscent();
        for(int i = 0; i < s.length(); i++) {
            String letter = String.valueOf(s.charAt(i));
            float sw = (float)font.getStringBounds(letter, frc).getWidth();
            float sx = (PAD - sw)/2;
            g2.drawString(letter, sx, sy);
            sy += sh;
        }
        // Abcissa label.
        s = "Cuts";
        sy = h - PAD - 20 + (PAD - sh)/2 + lm.getAscent() + 30;
        float sw = (float)font.getStringBounds(s, frc).getWidth();
        float sx = (w - sw)/2;
        g2.drawString(s, sx, sy);
        // Draw lines.
        double xInc = (double)(w - 2*PAD)/(data.length-1);
        double scale = (double)(h - 2*PAD)/getMax();
        g2.setPaint(Color.green.darker());
        for(int i = 0; i < data.length; i++) {
            if(i < data.length - 1) {
            	double x1 = PAD + i*xInc;
                double y1 = h - PAD - scale*data[i];
                double x2 = PAD + (i+1)*xInc;
                double y2 = h - PAD - scale*data[i+1];
                g2.draw(new Line2D.Double(x1, y1, x2, y2));
            }
	        // For the x location you start with PAD to move
	        // to the graph origin and count "xInc" intervals
	        // to the desired value location:
	        float x = (float)(PAD + i * xInc);
	        // The vertical positioning is the same as used to
	        // draw the abcissa label:
	        sy = h - PAD + (PAD - sh)/2 + lm.getAscent();
	        g2.drawString("" + i, x, sy);
        }
        // Mark data points.
        g2.setPaint(Color.red);
        for(int i = 0; i < data.length; i++) {
            double x = PAD + i*xInc;
            double y = h - PAD - scale*data[i];
            g2.fill(new Ellipse2D.Double(x-2, y-2, 4, 4));
            String coordinates = "(" + i + ", " + (round(data[i], 3)) + ")";
            g2.drawString(coordinates,  (int)(x - 20), (int)(y - 5));
        }
    }
	
	private double getMax() {
        double max = -Double.MAX_VALUE;
        for(int i = 0; i < data.length; i++) {
            if(data[i] > max)
                max = data[i];
        }
        return max;
    }
	
	
	public static void main(String[] args) throws IOException{
		Scanner file = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(new File("Cube.out"));
		//pw.println("test");
		
		double collectiveSurfaceArea = 0.0;
		
		System.out.println("Side Length:");
		double length = file.nextDouble();
		System.out.println("Cuts:");
		int cuts = file.nextInt();
		data = new double[cuts + 1];
		
		ArrayList<ArrayList<RectangularPrism>> prisms = new ArrayList<ArrayList<RectangularPrism>>();
		RectangularPrism cube = new RectangularPrism(length, length, length);
		prisms.add(new ArrayList<RectangularPrism>());
		prisms.get(0).add(cube);
		
		int numPrisms = 2;
		int index = 1;
		for(int i = 0; i < cuts + 1; i++) {
			prisms.add(new ArrayList<RectangularPrism>());
			for(int k = 0; k < numPrisms; k++) {
				RectangularPrism temp = new RectangularPrism(length, length, (prisms.get(index - 1).get(0).w / 2.0));
				//System.out.println(temp);
				prisms.get(index).add(temp);
			}
			index++;
			numPrisms *= 2;
			//System.out.println(prisms.size());
			data[i] = getCollectiveSurfaceArea(prisms.get(i));
			if(i == cuts) {
				prisms.remove(i);
			}
		}
		//System.out.println(prisms);
		
		System.out.println("Total Surface Area:");
		System.out.println("" + data[cuts]);
		pw.println("Total Surface Area:");
		pw.print(data[cuts]);
		
		file.close();
		pw.close();
		
		
		
		
		JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new Cube());
        f.setSize(500,500);
        f.setLocation(200,200);
        f.setVisible(true);
	}
	
	public static double getCollectiveSurfaceArea(ArrayList<RectangularPrism> arr) {
		double output = 0.0;
		for(RectangularPrism rP : arr) {
			output += rP.getSurfaceArea();
		}
		return output;
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
}

class RectangularPrism{
	public double l;
	public double h;
	public double w;
	
	public RectangularPrism(double x, double y, double z) {
		l = x;
		h = y;
		w = z;
	}
	
	public double getSurfaceArea() {
		return (2.0 * ((w * l) + (h * l) + (h * w)));
	}
	
	public String toString() {
		return ("(" + l + ", " + h + ", " + w + ", " + getSurfaceArea() + ")");
	}
}