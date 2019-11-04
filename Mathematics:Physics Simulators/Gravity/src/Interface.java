import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Interface {
	
	static ArrayList<Integer> keyEvents = new ArrayList<Integer>();
	public static boolean firstClick = true;
	static Gravity g = new Gravity();
	static int numParticles = g.particles.size();
	
	public static int WIDTH = 800;
	public static int HEIGHT = 700;
	public static JFrame frame;
	private static JPanel panel = new JPanel(new BorderLayout());
	private static JPanel bottomPanel = new JPanel(new BorderLayout());
	private JTextField massField = new JTextField("" + inputMass);
	private static JLabel partic = new JLabel("Particles: " + numParticles);
	private JButton clear = new JButton(new AbstractAction("Clear") {
		@Override
		public void actionPerformed(ActionEvent e) {
			g.clear();
		}
	});
	private JButton protoDiskButton = new JButton(new AbstractAction("Generate Proto Disk") {
		@Override
		public void actionPerformed(ActionEvent e) {
			generateProtoDisk();
		}
	});
	private JButton tinyButton = new JButton(new AbstractAction("Tiny") {
		@Override
		public void actionPerformed(ActionEvent e) {
			input = tiny;
			inputMass = (int)tiny.mass;
			massField.setText("" + inputMass);
		}
	});
	private JButton smallButton = new JButton(new AbstractAction("Small") {
		@Override
		public void actionPerformed(ActionEvent e) {
			input = small;
			inputMass = (int)small.mass;
			massField.setText("" + inputMass);
		}
	});
	private JButton mediumButton = new JButton(new AbstractAction("Medium") {
		@Override
		public void actionPerformed(ActionEvent e) {
			input = medium;
			inputMass = (int)medium.mass;
			massField.setText("" + inputMass);
		}
	});
	private JButton largeButton = new JButton(new AbstractAction("Large") {
		@Override
		public void actionPerformed(ActionEvent e) {
			input = large;
			inputMass = (int)large.mass;
			massField.setText("" + inputMass);
		}
	});
	private JButton hugeButton = new JButton(new AbstractAction("Huge") {
		@Override
		public void actionPerformed(ActionEvent e) {
			input = huge;
			inputMass = (int)huge.mass;
			massField.setText("" + inputMass);
		}
	});
	private JButton OMGButton = new JButton(new AbstractAction("OMG") {
		@Override
		public void actionPerformed(ActionEvent e) {
			input = OMG;
			inputMass = (int)OMG.mass;
			massField.setText("" + inputMass);
		}
	});
	
	public static int mouseX;
	public static int mouseY;
	public static int mouseX2;
	public static int mouseY2;
	public static int vx;
	public static int vy;
	public static int inputMass = 10000;
	
	public static Particle input = new Particle(inputMass, mouseX, mouseY);
	public static final Particle tiny = new Particle(100, mouseX, mouseY);
	public static final Particle small = new Particle(1000, mouseX, mouseY);
	public static final Particle medium = new Particle(10000, mouseX, mouseY);
	public static final Particle large = new Particle(100000, mouseX, mouseY);
	public static final Particle huge = new Particle(1000000, mouseX, mouseY);
	public static final Particle OMG = new Particle(10000000, mouseX, mouseY);
	
	public Interface(){
		
		frame = new JFrame("Gravity");
		frame.setVisible(false);
		frame.setSize(WIDTH, HEIGHT);
		   
		frame.addWindowListener(new WindowAdapter() {
		   public void windowClosing(WindowEvent windowEvent){
		      System.exit(0);
		   }        
		});
		
		g.clear();
		g.setDoubleBuffered(true);
		g.addMouseListener(new CustomMouseListener());
		g.addKeyListener(new CustomKeyListener());
		g.requestFocus();
		
		JLabel massLabel = new JLabel("Mass:");
		massField.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        inputMass = Integer.parseInt(massField.getText());
		      }
		});
		bottomPanel.add(clear, BorderLayout.EAST);
		bottomPanel.add(massLabel, BorderLayout.WEST);
		bottomPanel.add(massField);
		JPanel extraPanel = new JPanel();
		bottomPanel.add(extraPanel, BorderLayout.AFTER_LAST_LINE);
		extraPanel.add(partic);
		extraPanel.add(protoDiskButton);
		extraPanel.add(tinyButton);
		extraPanel.add(smallButton);
		extraPanel.add(mediumButton);
		extraPanel.add(largeButton);
		extraPanel.add(hugeButton);
		extraPanel.add(OMGButton);
		panel.add(g);
		panel.add(bottomPanel, BorderLayout.AFTER_LAST_LINE);
		
		frame.getContentPane().add(panel);
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public static void main(String[] args){
		Interface  i = new Interface();
		while(true) {
			g.elapsedTime++;
			numParticles = g.particles.size();
			partic.setText("Particles: " + numParticles);
			g.repaint();
		}
		
	}
	
	public static void generateProtoDisk() {
		double centerX = WIDTH / 2;
		double centerY = HEIGHT / 2;
		
		for(int i = 0; i < 50; i++) {
			Random rand = new Random();
			double rand1 = rand.nextDouble() * 2 * Math.PI;
			double rand2 = rand.nextDouble();
			double x = (100 * rand2 * Math.cos(rand1));
			double y = (100 * rand2 * Math.sin(rand1));
			double magic = Math.sqrt(x * x + y * y);
			
			Particle p = new Particle(10000, y * magic / 70, -x * magic / 70, centerX + x, centerY + y);
			g.particles.add(p);
		}
		
		g.repaint();
	}
}

class CustomMouseListener implements MouseListener{
	public void mouseClicked(MouseEvent e) {
		if(Interface.firstClick) {
			Interface.g.particles.clear();
			Interface.firstClick = false;
		}
		Interface.frame.requestFocus();
		Interface.mouseX = e.getX();
		Interface.mouseY = e.getY();
		if(Interface.keyEvents.contains(KeyEvent.VK_SHIFT)) {
			Particle target = new Particle(0, Interface.mouseX, Interface.mouseY);
			for(Particle p : Interface.g.particles) {
				if(p.x == Interface.mouseX && p.y == Interface.mouseY) {
					Interface.g.particles.remove(p);
					break;
				}
			}
		}
		else {
			Interface.g.addParticle(Interface.input);
		}
		Interface.g.repaint();
		Interface.frame.requestFocus();
	}

	public void mousePressed(MouseEvent e) {
		if(Interface.firstClick) {
			Interface.g.particles.clear();
			Interface.firstClick = false;
		}
		Interface.mouseX = e.getX();
		Interface.mouseY = e.getY();
		if(Interface.keyEvents.contains(KeyEvent.VK_SHIFT)) {
			Particle target = new Particle(0, Interface.mouseX, Interface.mouseY);
			for(Particle p : Interface.g.particles) {
				if(p.x == Interface.mouseX && p.y == Interface.mouseY) {
					Interface.g.particles.remove(p);
					break;
				}
			}
		}
		if(Interface.keyEvents.contains((KeyEvent.VK_P))){
			Interface.generateProtoDisk();
		}
		Interface.g.repaint();
		Interface.frame.requestFocus();
	}

	public void mouseReleased(MouseEvent e) {
		Interface.mouseX2 = e.getX();
		Interface.mouseY2 = e.getY();
		Interface.vx = Interface.mouseX2 - Interface.mouseX;
		Interface.vy = Interface.mouseY2 - Interface.mouseY;
		Particle newParticle = new Particle(Interface.inputMass * 10, Interface.vx, Interface.vy, Interface.mouseX, Interface.mouseY);
		Interface.g.addParticle(newParticle);;
		Interface.g.repaint();
		Interface.frame.requestFocus();
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}
}

class CustomKeyListener implements KeyListener{
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		Interface.keyEvents.add(e.getKeyCode());
		Interface.frame.requestFocus();
	}

	public void keyReleased(KeyEvent e) {
		Interface.keyEvents.remove(e.getKeyCode());
		Interface.frame.requestFocus();
	}

}
