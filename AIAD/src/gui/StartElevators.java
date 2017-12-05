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

import java.util.*;


public class StartElevators extends JFrame implements ActionListener {
	private int nElevators;
	private int nFloors;
	private Integer[] capacities;
	private JLabel[] labels;
	
	/**
	 * Create the application.
	 */
	public StartElevators(int nElevators, int nFloors, Integer[] capacities) {
		super("Elevators");
		this.nElevators = nElevators;
		this.nFloors = nFloors;
		this.capacities = capacities;
		this.labels = new JLabel[this.nElevators * this.nFloors];
		getContentPane().setForeground(Color.BLACK);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			JadeBoot boot = new JadeBoot(this.nFloors, this.nElevators, this.capacities);
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
		int index = 0;
		//ciclo em que a cada iteração vai apagando as labels????
		for(int i=0; i<nElevators; i++){
			for(int j=0; j<nFloors; j++){
				label = new JLabel("");
				label.setBounds(34+(i*50), 22+(j*10), 25, 10);
				label.setForeground(Color.black);
				label.setBorder(border);
				label.setBackground(Color.white);
				label.setOpaque(true);
				
				labels[index] = label;
				index++;
				
				getContentPane().add(label);
			}
		}
		setVisible(true);
		paintingElevators(3, 0);
		paintingElevators(1, 2);
		paintingElevators(29, 4);
		paintingElevators(29, 0);
		paintingElevators(29, 1);
		paintingElevators(29, 2);
		paintingElevators(29, 3);
		paintingElevators(27, 3);
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
	
	public void paintingElevators(int floor, int elevator){
		int index = (nFloors - floor - 1) + (nFloors * elevator);
		labels[index].setBackground(Color.GREEN);
	}
}
