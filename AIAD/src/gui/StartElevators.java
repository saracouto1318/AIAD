package gui;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.*;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

import javax.swing.border.Border;

import javax.swing.JTextField;
import javax.swing.event.*;

import elevator.Elevator;

import java.util.*;


public class StartElevators extends JFrame implements ActionListener {
	private int nElevators;
	private int nFloors;
	private Integer[] capacities;
	
	/**
	 * Create the application.
	 */
	public StartElevators(int nElevators, int nFloors, Integer[] capacities) {
		super("Elevators");
		this.nElevators = nElevators;
		this.nFloors = nFloors;
		this.capacities = capacities;
		getContentPane().setForeground(Color.BLACK);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JadeBoot boot = null;
		try {
			boot = new JadeBoot(this.nFloors, this.nElevators, this.capacities);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(ABORT);
		}
		
		setBounds(100, 100, 550, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JButton btnNext = new JButton("Next");
		
		btnNext.setBounds(238, 379, 89, 23);
		btnNext.addActionListener(this);
		btnNext.setActionCommand("Next");
		getContentPane().add(btnNext);
		
		Border border = LineBorder.createGrayLineBorder();
		JLabel label = new JLabel("");

		for(int i=0; i<nElevators; i++){
			for(int j=0; j<nFloors; j++){
				label = new JLabel("");
				label.setBounds(34+(i*50), 22+(j*10), 25, 10);
				label.setForeground(Color.black);
				label.setBorder(border);
				label.setBackground(Color.white);
				label.setOpaque(true);
				getContentPane().add(label);	
			}
		}	
        setVisible(true);
        
        Thread updateGUI = new UpdateGUI(this, boot);
        updateGUI.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

        if(cmd.equals("Next"))
        {
        	dispose();
            new Form();
        }
    }
}
