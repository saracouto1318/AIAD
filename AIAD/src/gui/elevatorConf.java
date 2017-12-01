package gui;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;

import java.awt.*;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import javax.swing.border.Border;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.*;

import java.util.*;


public class elevatorConf extends JFrame implements ActionListener {
	int nElevators;
	int nFloors;
	
	ArrayList<Integer> capacities = new ArrayList<Integer>();
	/**
	 * Create the application.
	 */
	public elevatorConf(int nElevators, int nFloors) {
		super("Elevators Configuration");
		this.nElevators = nElevators;
		this.nFloors = nFloors;
		getContentPane().setForeground(Color.BLACK);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 550, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JButton btnNext = new JButton("Next");
		if(nFloors < 20)
			btnNext.setBounds(228, 227, 89, 23);
		else{
			btnNext.setBounds(228, 380, 89, 23);
			setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
			setResizable(true);
		}
		btnNext.addActionListener(this);
		btnNext.setActionCommand("Next");
		getContentPane().add(btnNext);
		
		Border border = LineBorder.createGrayLineBorder();
		
		for(int i=0; i<nElevators; i++){
			int aux = 0;
			for(int j=0; j<nFloors; j++){
				aux = j;
				JLabel label = new JLabel("");
				label.setBounds(34+(i*50), 22+(j*10), 25, 10);
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
			maxCapacity.setBounds(34+(i*50), 22+((aux+1)*10)+20, 27, 14);
			getContentPane().add(maxCapacity);
			
			maxCapacity.getDocument().addDocumentListener(new DocumentListener() {

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
					int capacity = Integer.parseInt(maxCapacity.getText());
					
					capacities.add(capacity);
					System.out.println(capacities);
					if(capacities.get(capacities.size()-1) >= 10)
						capacities.remove(capacities.size()-2);
					
					System.out.println(capacities);
				}
			});
		}

        setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

        if(cmd.equals("Next"))
        {
        	dispose();
            new form();
        }
		
		
	}
}
