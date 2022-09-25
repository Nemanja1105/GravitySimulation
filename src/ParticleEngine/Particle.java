package ParticleEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import Vector.Vector;

public class Particle {
	public static final int SIZE_OF_PARTICLE = 3;
	private static Random random = new Random();
	private Vector center;
	private double radius;
	private Color color;
	private Vector speedVector;
	private Vector accelerationVector;
	private double mass = 1;

	public Particle() {
		super();
	}

	public Particle(Vector center, double radius, Color color, Vector speedVector, Vector accelerationVector,
			double mass) {
		this.center = center;
		this.radius = radius;
		this.color = color;
		this.speedVector = speedVector;
		this.mass = mass;
		this.accelerationVector = accelerationVector;
	}

	public Particle(Vector center) {
		this(center, Vector.generateRandomSpeed());
	}

	public Particle(Vector center, Vector speed) {
		this.mass = random.nextDouble() * 10 + 4;
		this.center = center;
		this.speedVector = speed;
		this.radius = SIZE_OF_PARTICLE + (mass / 2.5);
		this.color = this.generateRandomColor();
		// this.color=Color.WHITE;
		this.accelerationVector = new Vector();
	}

	private Color generateRandomColor() {
		int r = random.nextInt(256);
		int g = random.nextInt(256);
		int b = random.nextInt(256);
		return new Color(r, g, b);
	}

	public Vector getCenter() {
		return this.center;
	}

	public double getRadius() {
		return this.radius;
	}

	public Color getColor() {
		return this.color;
	}

	public double getMass() {
		return this.mass;
	}

	public Vector getSpeedVector() {
		return this.speedVector;
	}

	public void setCenter(Vector value) {
		this.center = value;
	}

	public void setRadius(double value) {
		this.radius = value;
	}

	public void setColor(Color value) {
		this.color = value;
	}

	public void setMass(double value) {
		this.mass = value;
	}

	public void setSpeedVector(Vector value) {
		this.speedVector = value;
	}

	public void setAcceleration(Vector value) {
		this.accelerationVector = value;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Particle))
			return false;
		Particle tmp = (Particle) other;
		return this.center.equals(tmp.center);
	}

	@Override
	public int hashCode() {
		double x = this.center.getX();
		double y = this.center.getY();
		int hash = 3;
		hash = hash * 7 + Double.hashCode(x);
		hash = hash * 7 + Double.hashCode(y);
		return hash;
	}

	public void paint(Graphics g) {
		g.setColor(this.color);
		g.fillOval((int) (this.center.getX() - this.radius), (int) (this.center.getY() - this.radius),
				(int) this.radius * 2, (int) this.radius * 2);
	}

	public void update() {
		this.speedVector.add(this.accelerationVector);
		this.center.add(this.speedVector);

	}

}
