package Vector;

import java.util.Random;

public class Vector {
	private double x;
	private double y;
	private static Random random = new Random();

	public Vector() {
		this.x = 0;
		this.y = 0;
	}

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector(Vector other) {
		this(other.x, other.y);
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public void setX(double value) {
		this.x = value;
	}

	public void setY(double value) {
		this.y = value;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Vector))
			return false;
		Vector tmp = (Vector) other;
		return this.x == tmp.x && this.y == tmp.y;
	}

	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}

	public void add(Vector other) {
		this.x += other.x;
		this.y += other.y;
	}

	public void scale(double value) {
		this.x *= value;
		this.y *= value;
	}

	public void sub(Vector other) {
		this.x -= other.x;
		this.y -= other.y;
	}

	public static Vector add(Vector v1, Vector v2) {
		return new Vector(v1.x + v2.x, v1.y + v2.y);
	}

	public static Vector scale(Vector v, double value) {
		return new Vector(v.x * value, v.y * value);
	}

	public static double distance(Vector v1, Vector v2) {
		return Math.sqrt(Math.pow(v2.getX() - v1.getX(), 2) + Math.pow(v2.getY() - v1.getY(), 2));
	}

	public static Vector sub(Vector v1, Vector v2) {
		return new Vector(v1.x - v2.x, v1.y - v2.y);
	}

	public double ampl() {
		return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
	}

	public Vector getOrt() {
		return scale(this, 1.0 / this.ampl());
	}

	public static Vector generateRandomSpeed() {
		double angle = Math.toRadians(random.nextDouble() * 360.0);
		double x = Math.cos(angle);
		double y = Math.sin(angle);
		return new Vector(x, y);
	}

}
