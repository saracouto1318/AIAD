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

/**
 * 
 * This class allows to configurate the different elevators
 *
 */
public class ElevatorConf extends JFrame implements ActionListener {
	/**
	 * Heuristic ti be used
	 */
	private int heuristic;
	/**
	 * Building's number of elevators
	 */
	private int nElevators;
	/**
	 * Building's number of floors
	 */
	private int nFloors;
	/**
	 * Array with the capacity of each elevator
	 */
	private Integer[] capacities;
	
	/**
	 * Create the GUI to configurate the elevators
	 */
	public ElevatorConf(int nElevators, int nFloors, int heuristic) {
		super("Elevators Configuration");
		this.nElevators = nElevators;
		this.nFloors = nFloors;
		this.heuristic = heuristic;
		this.capacities = new Integer[this.nElevators];
		getContentPane().setForeground(Color.BLACK);
		initialize();
	}

	/**
	 * Initialize the contents of the frame
	 */
	private void initialize() {
		setBounds(100, 100, 600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JButton btnNext = new JButton("Next");
		
		btnNext.setBounds(240, 382, 89, 23);
		btnNext.addActionListener(this);
		btnNext.setActionCommand("Next");
		getContentPane().add(btnNext);
		
		Border border = LineBorder.createGrayLineBorder();
		
		JLabel label1 = new JLabel("Capacity");
		label1.setBounds(10, 22+((nFloors+1)*10)+20, 50, 15);
		label1.setOpaque(true);
		getContentPane().add(label1);	
		
		for(int i=0; i<nElevators; i++){
			for(int j=0; j<nFloors; j++){
				JLabel label = new JLabel("");
				label.setBounds(80+(i*50), 22+(j*10), 25, 10);
				label.setForeground(Color.black);
				label.setBorder(border);
				label.setBackground(Color.white);
				label.setOpaque(true);
				getContentPane().add(label);	
			}
			
			JTextField maxCapacity = new JTextField("");
			maxCapacity.setFont(new Font("Tahoma", Font.PLAIN, 14));
			maxCapacity.setForeground(Color.black);
			maxCapacity.setBorder(border);
			maxCapacity.setBackground(Color.cyan);
			maxCapacity.setBounds(80+(i*50), 22+((nFloors+1)*10)+20, 27, 14);
			getContentPane().add(maxCapacity);
			
			this.getCapacities(maxCapacity, i);
		}	
        setVisible(true);
		
	}

	/**
	 * Changes the GUI when we click in the option 'Next'
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

        if(cmd.equals("Next"))
        {
        	dispose();
            new StartElevators(nElevators, nFloors, capacities, heuristic);
        }
    }
	
	/**
	 * Gets the elevators' capacities
	 * @param textField TextField where the capacities will be placed
	 * @param i Elevator's identifier
	 */
	public void getCapacities(JTextField textField, final int i){
		textField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				onChange();
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				onChange();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				onChange();
				
			}
			
			public void onChange(){
				int capacity = Integer.parseInt(textField.getText());
				capacities[i] = capacity;
			}
		});
	}
}
