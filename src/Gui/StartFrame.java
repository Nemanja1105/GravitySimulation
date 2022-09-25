package Gui;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StartFrame extends JFrame {

	private JPanel contentPane;

	public StartFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		//setBounds(100, 100, 450, 300);
		setBounds(100, 100, 337, 294);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Gravity Simulation");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 21));
		lblNewLabel.setForeground(Color.ORANGE);
		lblNewLabel.setBounds(64, 11, 168, 30);
		contentPane.add(lblNewLabel);
		
		JLabel lblCommans = new JLabel("Commands:");
		lblCommans.setForeground(new Color(51, 153, 51));
		lblCommans.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblCommans.setBounds(10, 43, 91, 30);
		contentPane.add(lblCommans);
		
		JLabel lblEscreset = new JLabel("ESC - RESET");
		lblEscreset.setForeground(new Color(51, 153, 51));
		lblEscreset.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblEscreset.setBounds(30, 160, 291, 21);
		contentPane.add(lblEscreset);
		
		JLabel lblSpacespawnStaticParticles = new JLabel("SPACE - SPAWN STATIC PARTICLES");
		lblSpacespawnStaticParticles.setForeground(new Color(51, 153, 51));
		lblSpacespawnStaticParticles.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblSpacespawnStaticParticles.setBounds(30, 71, 291, 21);
		contentPane.add(lblSpacespawnStaticParticles);
		
		JLabel lblLmbspawnParticlesIn = new JLabel("LMB - SPAWN PARTICLES IN RADIUS");
		lblLmbspawnParticlesIn.setForeground(new Color(51, 153, 51));
		lblLmbspawnParticlesIn.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblLmbspawnParticlesIn.setBounds(30, 94, 291, 21);
		contentPane.add(lblLmbspawnParticlesIn);
		
		JLabel lblRmbspawnParticlesIn = new JLabel("RMB - SPAWN REF POINT");
		lblRmbspawnParticlesIn.setForeground(new Color(51, 153, 51));
		lblRmbspawnParticlesIn.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblRmbspawnParticlesIn.setBounds(30, 118, 291, 21);
		contentPane.add(lblRmbspawnParticlesIn);
		
		JLabel lblEnterfrictionOnoff = new JLabel("ENTER - FRICTION ON/OFF");
		lblEnterfrictionOnoff.setForeground(new Color(51, 153, 51));
		lblEnterfrictionOnoff.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblEnterfrictionOnoff.setBounds(30, 139, 291, 21);
		contentPane.add(lblEnterfrictionOnoff);
		
		JButton btnNewButton = new JButton("START");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mouseStart(e);	
			}
		});
		btnNewButton.setBackground(Color.DARK_GRAY);
		btnNewButton.setForeground(Color.ORANGE);
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnNewButton.setBounds(103, 192, 108, 53);
		contentPane.add(btnNewButton);
	}
	
	public void mouseStart(MouseEvent e) {
		Simulation simulation=new Simulation();
		this.setVisible(false);
		simulation.setVisible(true);
		
	}

}
