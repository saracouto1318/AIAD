package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

public class mainPage extends JFrame implements ActionListener {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new mainPage().setVisible(true);
			}
		});
	}


	/**
	 * Create the application.
	 */
	public mainPage() {
		super("Elevators Manager");
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 475, 316);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblWelcomeToThe = new JLabel("Welcome to the Elevators Manager");
		lblWelcomeToThe.setBounds(55, 34, 354, 36);
		lblWelcomeToThe.setFont(new Font("Tahoma", Font.PLAIN, 22));
		getContentPane().add(lblWelcomeToThe);
		
		JButton btnNormal = new JButton("Normal");
		btnNormal.setBounds(183, 121, 89, 23);
		btnNormal.addActionListener(this);
		btnNormal.setActionCommand("OpenNormal");
		getContentPane().add(btnNormal);
		
		JButton btnSpecial = new JButton("Special");
		btnSpecial.setBounds(183, 170, 89, 23);
		btnSpecial.addActionListener(this);
		btnSpecial.setActionCommand("OpenSpecial");
		getContentPane().add(btnSpecial);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

        if(cmd.equals("OpenNormal") || cmd.equals("OpenSpecial"))
        {
        	dispose();
            new form();
        }
		
	}
}