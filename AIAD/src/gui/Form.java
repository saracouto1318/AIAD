package gui;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;

import javax.swing.JButton;
import java.awt.event.ActionEvent;

/**
 * 
 * Class that allows to choose the number of elevators and floors of a building
 *
 */
public class Form extends JFrame implements ActionListener {
	
	/**
	 * Choosing the heuristic to use
	 */
	private boolean typeHeuristic;
	/**
	 * Building's floors
	 */
	private int nFloors;
	/**
	 * Building's number of elevators
	 */
	private int nElevators;
	/**
	 * Sliders that allows to choose the floors and elevators
	 */
	private JSlider slider, slider_1;
	
	/**
	 * Creates the GUI that allows to choose the number of floors and elevators of a building
	 */
	public Form(boolean heuristic) {
		super("Configuration");
		this.typeHeuristic = heuristic;
		initialize();
	}

	/**
	 * Initialize the contents of the frame
	 */
	private void initialize() {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		slider = new JSlider(JSlider.HORIZONTAL, 2, 10, 5);
		slider.setBounds(124, 72, 200, 50);
		slider.setMajorTickSpacing(5);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		getContentPane().add(slider);
		
		slider_1 = new JSlider(JSlider.HORIZONTAL, 4, 30, 5);
		slider_1.setBounds(124, 155, 200, 45);
		slider_1.setMajorTickSpacing(5);
		slider_1.setMinorTickSpacing(1);
		slider_1.setPaintTicks(true);
		slider_1.setPaintLabels(true);
		getContentPane().add(slider_1);
		
		JLabel lblNumberOfElevators = new JLabel("Number of Elevators");
		lblNumberOfElevators.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumberOfElevators.setBounds(98, 47, 149, 14);
		getContentPane().add(lblNumberOfElevators);
		
		JLabel lblNumberOf = new JLabel("Number of Floors");
		lblNumberOf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumberOf.setBounds(98, 131, 130, 14);
		getContentPane().add(lblNumberOf);
		
		JButton btnNext1 = new JButton("Elevators Capacity");
		btnNext1.setBounds(147, 227, 140, 23);
		btnNext1.addActionListener(this);
		btnNext1.setActionCommand("Capacity");
		getContentPane().add(btnNext1);
		
        setVisible(true);
		
	}

	/**
	 * Changes the GUI when we click in the option 'Capacity'
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		nElevators = slider.getValue();
		nFloors = slider_1.getValue();

        if(cmd.equals("Capacity"))
        {
        	dispose();
            new ElevatorConf(nElevators, nFloors, typeHeuristic);
        }
		
		
	}
}
