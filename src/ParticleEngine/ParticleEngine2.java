package ParticleEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import Tree.QuadTree;
import Vector.Vector;

public class ParticleEngine2 {
	private static Random random = new Random();
	private static ArrayList<Particle> particles = new ArrayList<>();
	private static ArrayList<Particle> refPoints = new ArrayList<>();
	public static int WIDTH = 1366;
	public static int HEIGHT = 670;
	public static boolean friction = false;

	private static final double G = Math.pow(6.67, -6);

	public static final int REF_POINT_RADIUS = 30;
	public static final int REF_POINT_MASS = 1000;
	public static final Color REF_POINT_COLOR = Color.GREEN;

	static {
		// getRefParticle(new Vector(WIDTH / 2, HEIGHT / 2));
	}

	public static void spawnRefPoint() {
		while (true) {
			int x = random.nextInt(WIDTH);
			int y = random.nextInt(HEIGHT);
			Vector loc = new Vector(x, y);
			boolean status = refPoints.stream().anyMatch((p) -> {
				if (Vector.distance(loc, p.getCenter()) <= 2 * REF_POINT_RADIUS)
					return true;
				return false;
			});
			if (status)
				continue;
			getRefParticle(loc);
			break;
		}
	}

	public static void spawnRandomStaticParticle() {
		for (int i = 0; i < 20; i++) {
			while (true) {
				int x = random.nextInt(WIDTH);
				int y = random.nextInt(HEIGHT);
				Vector loc = new Vector(x, y);
				boolean status = particles.stream().anyMatch((p) -> {
					if (Vector.distance(loc, p.getCenter()) <= 2 * REF_POINT_RADIUS)
						return true;
					return false;
				});
				if (status)
					continue;
				particles.add(new Particle(loc, new Vector()));
				break;
			}
		}
	}

	private static void getRefParticle(Vector location) {
		var tmp = new Particle(location);
		tmp.setRadius(REF_POINT_RADIUS);
		tmp.setMass(REF_POINT_MASS);
		tmp.setColor(REF_POINT_COLOR);
		particles.add(tmp);
		refPoints.add(tmp);
	}

	public static void addParticle(Particle p) {
		particles.add(p);
	}

	public static int numOfParticles() {
		return particles.size();
	}

	public static void removeParticle(Particle p) {
		particles.remove(p);
	}

	public static void paintAll(Graphics g) {
		for (var particle : particles)
			particle.paint(g);
	}

	public static void reset() {
		particles.clear();
		refPoints.clear();
		// getRefParticle(new Vector(WIDTH / 2, HEIGHT / 2));
	}

	public static void createParticleOnMouseLocation(Vector mouseLoc) {
		final int spawnRadius = 10;
		final int numOfGen = 20;
		for (int i = 1; i <= numOfGen; i++) {
			Vector speed = Vector.generateRandomSpeed();
			Vector orient = Vector.scale(speed, spawnRadius);
			Particle particle = new Particle(Vector.add(mouseLoc, orient), speed);
			ParticleEngine2.addParticle(particle);
		}
	}

	public static Vector evaluateGravity(Particle p, Particle otherParticle) {
		var ort = Vector.sub(otherParticle.getCenter(), p.getCenter()).getOrt();
		double distance = ort.ampl();
		double force = G * p.getMass() * otherParticle.getMass() / (Math.pow(distance, 2));
		ort = ort.getOrt();
		ort.scale(force / (p.getMass()));
		return ort;
	}

	private static double[] getSimulationParams() {
		var minX = particles.stream().mapToDouble((p) -> p.getCenter().getX()).min();
		var maxX = particles.stream().mapToDouble((p) -> p.getCenter().getX()).max();
		var minY = particles.stream().mapToDouble((p) -> p.getCenter().getY()).min();
		var maxY = particles.stream().mapToDouble((p) -> p.getCenter().getY()).max();
		if (!minX.isPresent())
			return new double[] { 0, 0, HEIGHT };
		double size = Math.max(maxX.getAsDouble() - minX.getAsDouble(), maxY.getAsDouble() - minY.getAsDouble());
		return new double[] { minX.getAsDouble(), minY.getAsDouble(), size };
	}

	public static void updateAll() {
		double[] params = getSimulationParams();
		QuadTree tree = new QuadTree(params[0], params[1], params[2]);
		tree.addAll(particles);
		var iterator = particles.iterator();
		while (iterator.hasNext()) {
			var p = iterator.next();
			if (refPoints.contains(p))
				continue;
			if (p.getCenter().getX() <= 0 || p.getCenter().getX() >= ParticleEngine2.WIDTH || p.getCenter().getY() <= 0
					|| p.getCenter().getY() >= ParticleEngine2.HEIGHT) {
				iterator.remove();
			} else
				p.setAcceleration(tree.evaluateGravity(p));
		}
		particles.stream().filter((p) -> !refPoints.contains(p)).forEach((p) -> {
			p.update();
			if (friction)
				p.setSpeedVector(Vector.scale(p.getSpeedVector(), 0.998));
		});
	}

	public static void updateAllBasic() {
		var iterator = particles.iterator();
		while (iterator.hasNext()) {
			var p = iterator.next();
			if (refPoints.contains(p))
				continue;
			if (p.getCenter().getX() <= 0 || p.getCenter().getX() >= ParticleEngine2.WIDTH || p.getCenter().getY() <= 0
					|| p.getCenter().getY() >= ParticleEngine2.HEIGHT) {
				iterator.remove();
			} else {
				Vector accVec = new Vector();
				for (var otherParticle : particles) {
					if (p == otherParticle)
						continue;
					accVec.add(evaluateGravity(p, otherParticle));
				}
				p.setAcceleration(accVec);
			}
		}
		particles.stream().filter((p) -> !refPoints.contains(p)).forEach((p) -> {
			p.update();
			if (friction)
				p.setSpeedVector(Vector.scale(p.getSpeedVector(), 0.998));
		});
	}

	public static void updateWithColision() {
		for (int i = 0; i < particles.size(); i++) {
			Particle p1 = particles.get(i);
			for (int j = i + 1; j < particles.size(); j++) {
				Particle p2 = particles.get(j);
				if (Vector.distance(p1.getCenter(), p2.getCenter()) < (p1.getRadius() + p2.getRadius())) {
					Vector v1 = (Vector.scale(p1.getSpeedVector(), (p1.getMass() - p2.getMass())));
					v1.add(Vector.scale(p2.getSpeedVector(), 2 * p2.getMass()));
					v1.scale(1.0 / (p1.getMass() + p2.getMass()));

					Vector v2 = Vector.scale(p1.getSpeedVector(), 2 * p1.getMass());
					v2.add(Vector.scale(p2.getSpeedVector(), p2.getMass() - p1.getMass()));
					v2.scale(1.0 / (p1.getMass() + p2.getMass()));
					p1.setSpeedVector(v1);
					p2.setSpeedVector(v2);
				}
			}
		}
		particles.forEach((p) -> p.update());
	}

}
