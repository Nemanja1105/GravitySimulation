package Gui;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import java.awt.Font;
import Vector.Vector;
import ParticleEngine.*;
import javax.swing.border.LineBorder;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;

public class Simulation extends JFrame {

	private JPanel contentPane;
	private JLabel lblParticles_1;
	private JLabel lblFps_1;
	private JLabel lblOff;
	private long lastTime = 0;
	private long fps;

	public Simulation() {
		setTitle("GravitySimulation");
		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		// setUndecorated(true);
		setBounds(100, 100, 1366, 720);
		contentPane = new JPanel();
		contentPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					ParticleEngine2.reset();
				else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					ParticleEngine2.friction = !ParticleEngine2.friction;
					if (ParticleEngine2.friction) {
						lblOff.setText("ON");
						lblOff.setForeground(Color.GREEN);
					} else {
						lblOff.setText("OFF");
						lblOff.setForeground(Color.RED);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE)
					ParticleEngine2.spawnRandomStaticParticle();
			}
		});
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)
					ParticleEngine2.createParticleOnMouseLocation(new Vector(e.getX(), e.getY()));
				else
					ParticleEngine2.spawnRefPoint();
			}
		});
		contentPane.setFocusable(true);
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Friction:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 11, 53, 17);
		contentPane.add(lblNewLabel);

		lblOff = new JLabel("OFF");
		lblOff.setForeground(Color.RED);
		lblOff.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblOff.setBounds(66, 7, 33, 24);
		contentPane.add(lblOff);

		JLabel lblParticles = new JLabel("Particles:");
		lblParticles.setForeground(Color.WHITE);
		lblParticles.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblParticles.setBounds(598, 11, 67, 17);
		contentPane.add(lblParticles);

		lblParticles_1 = new JLabel("0");
		lblParticles_1.setForeground(Color.WHITE);
		lblParticles_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblParticles_1.setBounds(659, 11, 56, 17);
		contentPane.add(lblParticles_1);

		JLabel lblFps = new JLabel("FPS:");
		lblFps.setForeground(Color.WHITE);
		lblFps.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblFps.setBounds(1244, 0, 29, 34);
		contentPane.add(lblFps);

		lblFps_1 = new JLabel("0");
		lblFps_1.setForeground(Color.ORANGE);
		lblFps_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblFps_1.setBounds(1274, 9, 33, 17);
		contentPane.add(lblFps_1);
		JPanel panel = new JPanel() {
			@Override
			public void paint(Graphics g) {
				var img = createImage(this.getWidth(), this.getHeight());
				var tmpGraph = img.getGraphics();
				ParticleEngine2.updateAll();
				ParticleEngine2.paintAll(tmpGraph);
				lblParticles_1.setText(Integer.toString(ParticleEngine2.numOfParticles()));
				g.drawImage(img, 0, 0, null);
				fpsCounter();
				repaint(1000 / 60);
			}
		};
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(0, 35, 1350, 646);
		ParticleEngine2.WIDTH = panel.getWidth();
		ParticleEngine2.HEIGHT = panel.getHeight();
		contentPane.add(panel);

	}

	private void fpsCounter() {
		fps++;
		long now = System.currentTimeMillis();
		if (now - lastTime >= 1000) {
			// System.out.println("fps:"+fps);
			lblFps_1.setText(Long.toString(fps));
			lastTime = now;
			fps = 0;
		}
	}
}
