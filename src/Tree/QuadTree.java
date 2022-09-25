package Tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.swing.plaf.basic.BasicTreeUI.TreeCancelEditingAction;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import ParticleEngine.Particle;
import ParticleEngine.ParticleEngine2;
import Vector.Vector;

public class QuadTree {
	private Node head;

	public QuadTree(double quadrantWidth) {
		this.head = new Node(0.0, 0.0, quadrantWidth);
	}

	public QuadTree(double x, double y, double quadrantWidth) {
		this.head = new Node(x, y, quadrantWidth);
	}

	public void insert(Particle p) {
		this.insertMechanism(this.head, p);
	}

	private void insertMechanism(Node root, Particle p) {
		if (root.nodes == null)// list
		{
			// list je prazan
			if (root.particle == null) {
				root.particle = p;
				root.centerOfMass = new Vector(p.getCenter());
				root.totalMass = p.getMass();
				return;
			}
			// vec imamo neki particle u cvoru znaci moramo da dijelimo cvor na 4 kvadratna

			Particle temp = root.particle;

			// root.centerOfMass.add(p.getCenter());
			addCenterOfMass(root, p);
			root.totalMass += p.getMass();
			root.particle = null;

			Node currentNode = root;// trazimo slobidni cvor za nove partikle
			int pQ = root.getQuadrant(p.getCenter());
			int pT = root.getQuadrant(temp.getCenter());
			// System.out.println(p.getCenter()+":"+temp.getCenter());
			while (pQ == pT)// nalaze se u istom kvadrantu
			{
				currentNode.split();
				currentNode = currentNode.nodes[pQ];
				// currentNode.centerOfMass.add(p.getCenter());
				// currentNode.centerOfMass.add(temp.getCenter());
				addCenterOfMass(currentNode, p);
				addCenterOfMass(currentNode, temp);
				currentNode.totalMass += p.getMass();
				currentNode.totalMass += temp.getMass();
				pQ = currentNode.getQuadrant(p.getCenter());
				pT = currentNode.getQuadrant(temp.getCenter());
			}
			currentNode.split();
			currentNode.nodes[pQ].particle = p;
			currentNode.nodes[pQ].centerOfMass = new Vector(p.getCenter());
			currentNode.nodes[pQ].totalMass = p.getMass();

			currentNode.nodes[pT].particle = temp;
			currentNode.nodes[pT].centerOfMass = new Vector(temp.getCenter());
			currentNode.nodes[pT].totalMass = temp.getMass();
			return;
		}
		// unutrasnji cvor
		// root.centerOfMass.add(p.getCenter());
		addCenterOfMass(root, p);
		root.totalMass += p.getMass();
		int pos = root.getQuadrant(p.getCenter());
		insertMechanism(root.nodes[pos], p);
	}

	private void addCenterOfMass(Node root, Particle p) {
		double m = root.totalMass;
		root.centerOfMass.scale(m);
		root.centerOfMass.add(Vector.scale(p.getCenter(), p.getMass()));
		root.centerOfMass.scale(1.0 / (m + p.getMass()));
	}

	public void addAll(List<Particle> list) {
		list.forEach(p -> this.insert(p));
	}

	public void forEach(Consumer<Particle> consumer) {
		visit(this.head, consumer);
	}

	private void visit(Node root, Consumer<Particle> consumer) {
		if (root != null) {
			if (root.particle != null)
				consumer.accept(root.particle);
			if (root.nodes != null) {
				for (var node : root.nodes)
					visit(node, consumer);
			}
		}
	}

	public Vector evaluateGravity(Particle p) {
		return evaluateForce(this.head, p);
	}

	private Vector evaluateForce(Node root, Particle p) {
		if (root == null || root.particle == p)
			return new Vector();
		if (root.nodes == null && root.particle == null)
			return new Vector();
		if (root.nodes == null && root.particle != null)// svaki list nije popunjen
			return ParticleEngine2.evaluateGravity(p, root.particle);
		double dist = Vector.distance(root.centerOfMass, p.getCenter());
		if (root.quadrantWidth / dist < 0.5) {
			Particle particle = new Particle();
			particle.setCenter(root.centerOfMass);
			particle.setMass(root.totalMass);
			return ParticleEngine2.evaluateGravity(p, particle);
		} else {
			Vector rez = new Vector();
			for (var node : root.nodes)
				rez.add(evaluateForce(node, p));
			return rez;
		}
	}

}
