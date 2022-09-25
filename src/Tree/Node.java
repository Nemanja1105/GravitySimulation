package Tree;

import ParticleEngine.Particle;
import Vector.Vector;

public class Node {
	// pozicija gornjeg desno ugla
	double startX, startY;
	double quadrantWidth;
	Vector centerOfMass;
	double totalMass;

	Node[] nodes;
	// kvadranti su rasporedjeni na sljedeci nacin
	// 0-sz 1-si 2-jz 3-ji
	Particle particle;

	static int globalid = 1;
	int id;

	public Node(double startX, double startY, double quadrantWidth) {
		this.startX = startX;
		this.startY = startY;
		this.quadrantWidth = quadrantWidth;
		this.centerOfMass = new Vector();
		this.totalMass = 0.0;
		this.nodes = null;
		this.particle = null;
		this.id = globalid++;
	}

	public void split() {
		double half = quadrantWidth / 2.0;
		this.nodes = new Node[4];
		nodes[0] = new Node(this.startX, startY, half);
		nodes[1] = new Node(this.startX + half, this.startY, half);
		nodes[2] = new Node(this.startX, this.startY + half, half);
		nodes[3] = new Node(this.startX + half, this.startY + half, half);
	}

	protected int getQuadrant(Vector v) {
		double half = this.quadrantWidth / 2.0;
		if (v.getY() < this.startY + half)// prva polovina
		{
			if (v.getX() < this.startX + half)
				return 0;
			else
				return 1;
		} else {
			if (v.getX() < this.startX + half)
				return 2;
			else
				return 3;
		}
	}
}
