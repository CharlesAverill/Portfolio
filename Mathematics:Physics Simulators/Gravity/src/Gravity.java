import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import java.awt.*;

public class Gravity extends JPanel{
	
	public static ArrayList<Particle> particles;
	public static double h;
	public static int elapsedTime = 0;
	
	public Gravity() {
		setBackground(Color.BLACK);
		particles = new ArrayList<Particle>();
		h = 1.0 / 200.0;
	}
	
	public void clear() {
		particles.clear();
	}
	
	public void paint(Graphics window){
		integrate();
		for(Particle p : particles) {
			if(p.mass > 0) {
				window.setColor(new Color(p.color));
				window.fillOval((int)p.x, (int)p.y, (int)p.radius, (int)p.radius);
			}
			else if(p.mass < 0){
				p.text = "You shouldn't have done that";
				window.setColor(new Color(p.color));
				window.fillOval((int)p.x, (int)p.y, 10, 10);
			}
		}
	}
	
	public static void addParticle(Particle p) {
		particles.add(p);
	}
	
	public static void integrate() {
		ArrayList<Particle> newParticles = new ArrayList<Particle>();
		for(int i = 0; i < particles.size(); i++) {
			Particle particle = particles.get(i);
			double axsum = 0;
			double aysum = 0;
			
			for(int j = 0; j < particles.size(); j++) {
				Particle otherParticle = particles.get(j);
				if(!particle.equals(otherParticle) && !particle.collided && !otherParticle.collided) {
					int xD = (int)(otherParticle.x - particle.x);
					int yD = (int)(otherParticle.y - particle.y);
					double displacementMagnitude = Math.sqrt(xD * xD + yD * yD);
					
					if(displacementMagnitude < ((double)particle.radius / 1.5) + ((double)otherParticle.radius / 1.5)) {
						particle.collided = true;
						otherParticle.collided = true;
						double sumMass = particle.mass + otherParticle.mass;
						int newvx = (int)((particle.vx * particle.mass + otherParticle.vx * otherParticle.mass) / sumMass);
						int newvy = (int)((particle.vy * particle.mass + otherParticle.vy * otherParticle.mass) / sumMass);
						int newx = (int)((particle.x * particle.mass + otherParticle.x * otherParticle.mass) / sumMass);
						int newy = (int)((particle.y * particle.mass + otherParticle.y * otherParticle.mass) / sumMass);
						
						Particle newParticle = new Particle(sumMass, newvx, newvy, newx, newy);
						
						newParticles.add(newParticle);
					}
					
					double acceleration = (double)otherParticle.mass / (Math.pow(displacementMagnitude, 2));
					axsum += acceleration * (xD / displacementMagnitude);
					aysum += acceleration * (yD / displacementMagnitude);
				}
				
				particle.ax = axsum;
				particle.ay = aysum;
			}
			
			for(int x = 0; x < particles.size(); x++) {
				particles.get(x).vx += particles.get(x).ax * h;
				particles.get(x).vy += particles.get(x).ay * h;
				particles.get(x).x += particles.get(x).vx * h;
				particles.get(x).y += particles.get(x).vy * h;
				if(particles.get(x).collided) {
					particles.remove(x);
					x--;
				}
			}
			
			for(Particle newParticle : newParticles) {
				particles.add(newParticle);
			}
		}
		/*for(Particle particle : particles) {
			for(Particle other : particles) {
				particle.interact(other);
			}
		}
		for(Particle particle : particles) {
			particle.simulate();
		}*/
	}
}
