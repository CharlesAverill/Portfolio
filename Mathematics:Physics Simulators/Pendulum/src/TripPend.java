import java.awt.Graphics;

public class TripPend extends java.applet.Applet implements Runnable, java.awt.event.ActionListener { private static final int Lx = 490;
  private static final int Ly = 330;
  private int px1;
  private int px2;
  private int px3;
  private int py1;
  private int py2;
  private int py3;
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
  
  private int step;
  
  java.awt.Canvas can;
  java.awt.Canvas header;
  java.awt.Button but_new;
  java.awt.Button but_phi1_plus;
  java.awt.Button but_phi1_min;
  java.awt.Button but_phi2_plus;
  java.awt.Button but_phi2_min;
  
  public void init()
  {
    setLayout(null);
    header = new java.awt.Canvas();
    header.setBounds(1, 1, 490, 40);
    header.setBackground(java.awt.Color.white);
    can = new java.awt.Canvas();
    can.setBounds(1, 41, 490, 380);
    can.setBackground(java.awt.Color.white);
    java.awt.Panel localPanel = new java.awt.Panel();
    localPanel.setLayout(null);
    localPanel.setBounds(491, 1, 100, 420);
    localPanel.setBackground(new java.awt.Color(0, 200, 100));
    localPanel.setFont(new java.awt.Font("Verdana", 0, 10));
    
    but_new = new java.awt.Button("New");
    but_phi1_plus = new java.awt.Button("Phi1 +");
    but_phi1_min = new java.awt.Button("Phi1 -");
    but_phi2_plus = new java.awt.Button("Phi2 +");
    but_phi2_min = new java.awt.Button("Phi2 -");
    but_phi3_plus = new java.awt.Button("Phi3 +");
    but_phi3_min = new java.awt.Button("Phi3 -");
    but_reib_plus = new java.awt.Button("Friction ++");
    but_reib_min = new java.awt.Button("Friction --");
    cb_stop = new java.awt.Checkbox("Stop/Go", false);
    
    but_new.setBounds(10, 20, 80, 27);
    but_phi1_plus.setBounds(10, 70, 80, 27);
    but_phi1_min.setBounds(10, 105, 80, 27);
    but_phi2_plus.setBounds(10, 145, 80, 27);
    but_phi2_min.setBounds(10, 180, 80, 27);
    but_phi3_plus.setBounds(10, 220, 80, 27);
    but_phi3_min.setBounds(10, 255, 80, 27);
    but_reib_plus.setBounds(10, 300, 80, 27);
    but_reib_min.setBounds(10, 335, 80, 27);
    cb_stop.setBounds(10, 380, 70, 30);
    cb_stop.setBackground(new java.awt.Color(0, 200, 100));
    
    localPanel.add(but_new);localPanel.add(cb_stop);
    localPanel.add(but_phi1_plus);localPanel.add(but_phi1_min);
    localPanel.add(but_phi2_plus);localPanel.add(but_phi2_min);
    localPanel.add(but_phi3_plus);localPanel.add(but_phi3_min);
    localPanel.add(but_reib_plus);localPanel.add(but_reib_min);
    add(localPanel);add(can);add(header);
    
    but_new.addActionListener(this);
    but_phi1_plus.addActionListener(this);
    but_phi1_min.addActionListener(this);
    but_phi2_plus.addActionListener(this);
    but_phi2_min.addActionListener(this);
    but_phi3_plus.addActionListener(this);
    but_phi3_min.addActionListener(this);
    but_reib_plus.addActionListener(this);
    but_reib_min.addActionListener(this);
    
    ghead = header.getGraphics();
    ghead.setColor(java.awt.Color.blue);
    ghead.setFont(new java.awt.Font("Verdana", 1, 15));
    g = can.getGraphics();
    g.setFont(new java.awt.Font("Verdana", 1, 10));
    
    startwert[0] = 2.2689280275926285D;
    startwert[1] = 2.2689280275926285D;
    startwert[2] = 2.2689280275926285D;
    startwert_reib = 0;
  }
  
  public void startwerte()
  {
    phi1 = startwert[0];
    phi2 = startwert[1];
    phi3 = startwert[2];
    reib = startwert_reib;
    omega1 = 0.0D;
    omega2 = 0.0D;
    omega3 = 0.0D;
    step = 0;
  }
  
  public void run() {
    try {
      Thread.sleep(500L); } catch (InterruptedException localInterruptedException1) {}
    ghead.drawString("Triple-Pendulum", 180, 25);
    startwerte();
    
    while (Thread.currentThread() == animator)
    {
      if (cb_stop.getState()) {
        try { Thread.sleep(10L);
        } catch (InterruptedException localInterruptedException2) {}
      } else { runge_step_phi1();
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
          pixels();
          repaint();
        }
        step += 1;
      }
    }
  }
  
  public void paint(Graphics paramGraphics) {
    if (offImage != null)
    {
      paramGraphics = can.getGraphics();
      paramGraphics.drawImage(offImage, 0, 0, null);
    }
  }
  
  public void update(Graphics paramGraphics)
  {
    paramGraphics = can.getGraphics();
    if (offGraphics == null)
    {
      offImage = createImage(490, 380);
      offGraphics = offImage.getGraphics();
    }
    offGraphics.setFont(new java.awt.Font("Verdana", 1, 10));
    offGraphics.setColor(can.getBackground());
    offGraphics.fillRect(0, 0, 490, 380);
    paintFrame(offGraphics);
    paramGraphics.drawImage(offImage, 0, 0, null);
  }
  
  public void paintFrame(Graphics paramGraphics)
  {
    print(paramGraphics);
    paramGraphics.setColor(java.awt.Color.black);
    paramGraphics.drawLine(0, 165, 490, 165);
    paramGraphics.drawLine(245, 0, 245, 330);
    paramGraphics.drawString("Step " + step, 20, 10);
    paramGraphics.setColor(java.awt.Color.red);
    paramGraphics.drawLine(245, 165, px1, py1);
    paramGraphics.drawLine(px1, py1, px2, py2);
    paramGraphics.drawLine(px2, py2, px3, py3);
    paramGraphics.setColor(java.awt.Color.black);
    paramGraphics.fillOval(px1 - 5, py1 - 5, 10, 10);
    paramGraphics.fillOval(px2 - 5, py2 - 5, 10, 10);
    paramGraphics.fillOval(px3 - 5, py3 - 5, 10, 10);
  }
  
  public void print(Graphics paramGraphics)
  {
    paramGraphics.setColor(java.awt.Color.blue);
    paramGraphics.drawString("Energy:", 30, 350);
    paramGraphics.drawString(String.valueOf(Math.round(10.0D * energy()) / 10.0D), 30, 370);
    paramGraphics.drawString("Friction:", 120, 350);
    paramGraphics.drawString(String.valueOf(reib), 120, 370);
    paramGraphics.drawString("Phi1:", 230, 350);
    paramGraphics.drawString(Math.round(10.0D * phi1 * 180.0D / 3.141592653589793D) / 10.0D + "°", 230, 370);
    paramGraphics.drawString("Phi2:", 320, 350);
    paramGraphics.drawString(Math.round(10.0D * phi2 * 180.0D / 3.141592653589793D) / 10.0D + "°", 320, 370);
    paramGraphics.drawString("Phi3:", 410, 350);
    paramGraphics.drawString(Math.round(10.0D * phi3 * 180.0D / 3.141592653589793D) / 10.0D + "°", 410, 370);
  }
  
  public void pixels()
  {
    px1 = ((int)Math.round(245.0D + 55.0D * Math.sin(phi1)));
    py1 = ((int)Math.round(165.0D + 55.0D * Math.cos(phi1)));
    px2 = ((int)Math.round(px1 + 55.0D * Math.sin(phi2)));
    py2 = ((int)Math.round(py1 + 55.0D * Math.cos(phi2)));
    px3 = ((int)Math.round(px2 + 55.0D * Math.sin(phi3)));
    py3 = ((int)Math.round(py2 + 55.0D * Math.cos(phi3))); }
  
  java.awt.Button but_phi3_plus;
  java.awt.Button but_phi3_min;
  java.awt.Button but_reib_plus;
  java.awt.Button but_reib_min;
  java.awt.Checkbox cb_stop;
  
  public double forcephi1(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) { return 
    








      (98.10000000000001D * Math.sin(paramDouble1) + 39.24D * Math.sin(paramDouble1 - 2.0D * paramDouble3) - 9.81D * Math.sin(paramDouble1 + 2.0D * paramDouble3 - 2.0D * paramDouble5) - 9.81D * Math.sin(paramDouble1 - 2.0D * paramDouble3 + 2.0D * paramDouble5) + 4.0D * paramDouble2 * paramDouble2 * Math.sin(2.0D * paramDouble1 - 2.0D * paramDouble3) + 8.0D * paramDouble4 * paramDouble4 * Math.sin(paramDouble1 - paramDouble3) + 2.0D * paramDouble6 * paramDouble6 * Math.sin(paramDouble1 - paramDouble5) + 2.0D * paramDouble6 * paramDouble6 * Math.sin(paramDouble1 - 2.0D * paramDouble3 + paramDouble5)) / (-10.0D + 4.0D * Math.cos(2.0D * paramDouble1 - 2.0D * paramDouble3) + 2.0D * Math.cos(2.0D * paramDouble3 - 2.0D * paramDouble5)) - 0.1D * reib * paramDouble2;
  }
  
  Thread animator;
  Graphics g;
  Graphics offGraphics;
  Graphics ghead;
  java.awt.Image offImage;
  public double forcephi2(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) { return 
    










      (-68.67D * Math.sin(2.0D * paramDouble1 - paramDouble3) + 68.67D * Math.sin(paramDouble3) + 9.81D * Math.sin(paramDouble3 - 2.0D * paramDouble5) + 9.81D * Math.sin(2.0D * paramDouble1 + paramDouble3 - 2.0D * paramDouble5) + 2.0D * paramDouble2 * paramDouble2 * Math.sin(paramDouble1 + paramDouble3 - 2.0D * paramDouble5) - 14.0D * paramDouble2 * paramDouble2 * Math.sin(paramDouble1 - paramDouble3) + 2.0D * paramDouble4 * paramDouble4 * Math.sin(2.0D * paramDouble3 - 2.0D * paramDouble5) - 4.0D * paramDouble4 * paramDouble4 * Math.sin(2.0D * paramDouble1 - 2.0D * paramDouble3) + 6.0D * paramDouble6 * paramDouble6 * Math.sin(paramDouble3 - paramDouble5) - 2.0D * paramDouble6 * paramDouble6 * Math.sin(2.0D * paramDouble1 - paramDouble3 - paramDouble5)) / (-10.0D + 4.0D * Math.cos(2.0D * paramDouble1 - 2.0D * paramDouble3) + 2.0D * Math.cos(2.0D * paramDouble3 - 2.0D * paramDouble5)) - 0.1D * reib * paramDouble4;
  }
  



  public double forcephi3(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
  {
    return 
    






      -2.0D * Math.sin(paramDouble3 - paramDouble5) * (9.81D * Math.cos(2.0D * paramDouble1 - paramDouble3) + 9.81D * Math.cos(paramDouble3) + 2.0D * paramDouble2 * paramDouble2 * Math.cos(paramDouble1 - paramDouble3) + 2.0D * paramDouble4 * paramDouble4 + paramDouble6 * paramDouble6 * Math.cos(paramDouble3 - paramDouble5)) / (-5.0D + 2.0D * Math.cos(2.0D * paramDouble1 - 2.0D * paramDouble3) + Math.cos(2.0D * paramDouble3 - 2.0D * paramDouble5)) - 0.1D * reib * paramDouble6;
  }
  
  public double energy()
  {
    return 
    



      0.5D * (3.0D * omega1 * omega1 + 2.0D * omega2 * omega2 + omega3 * omega3 + 4.0D * omega1 * omega2 * Math.cos(phi1 - phi2) + 2.0D * omega1 * omega3 * Math.cos(phi1 - phi3) + 2.0D * omega2 * omega3 * Math.cos(phi2 - phi3)) - 9.81D * (3.0D * Math.cos(phi1) + 2.0D * Math.cos(phi2) + Math.cos(phi3));
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
  
  public void actionPerformed(java.awt.event.ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getActionCommand() == but_new.getActionCommand())
    {
      stop();
      startwerte();
      pixels();
      update(g);
      start();
    }
    if (paramActionEvent.getActionCommand() == but_phi1_plus.getActionCommand())
    {
      stop();
      startwert[0] += 0.08726646259971647D;
      startwerte();
      pixels();
      update(g);
      start();
    }
    if (paramActionEvent.getActionCommand() == but_phi1_min.getActionCommand())
    {
      stop();
      startwert[0] -= 0.08726646259971647D;
      startwerte();
      pixels();
      update(g);
      start();
    }
    if (paramActionEvent.getActionCommand() == but_phi2_plus.getActionCommand())
    {
      stop();
      startwert[1] += 0.08726646259971647D;
      startwerte();
      pixels();
      update(g);
      start();
    }
    if (paramActionEvent.getActionCommand() == but_phi2_min.getActionCommand())
    {
      stop();
      startwert[1] -= 0.08726646259971647D;
      startwerte();
      pixels();
      update(g);
      start();
    }
    if (paramActionEvent.getActionCommand() == but_phi3_plus.getActionCommand())
    {
      stop();
      startwert[2] += 0.08726646259971647D;
      startwerte();
      pixels();
      update(g);
      start();
    }
    if (paramActionEvent.getActionCommand() == but_phi3_min.getActionCommand())
    {
      stop();
      startwert[2] -= 0.08726646259971647D;
      startwerte();
      pixels();
      update(g);
      start();
    }
    if (paramActionEvent.getActionCommand() == but_reib_plus.getActionCommand())
    {
      startwert_reib += 1;reib = startwert_reib;
      if (cb_stop.getState()) update(g);
    }
    if (paramActionEvent.getActionCommand() == but_reib_min.getActionCommand())
    {
      if (reib > 0) {
        startwert_reib -= 1;reib = startwert_reib;
        if (cb_stop.getState()) update(g);
      }
    }
  }
  
  public void start()
  {
    animator = new Thread(this);
    animator.start();
  }
  
  public void stop()
  {
    animator = null;
    offImage = null;
    offGraphics = null;
  }
  
  public TripPend() {}
  
  public void main(String[] args) {
	  start();
  }
}
