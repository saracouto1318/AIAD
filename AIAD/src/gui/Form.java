package gui;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;

import javax.swing.JButton;
import java.awt.event.ActionEvent;


public class Form extends JFrame implements ActionListener {
	private int nFloors;
	private int nElevators;
	private JSlider slider, slider_1;
	
	/**
	 * Create the application.
	 */
	public Form() {
		super("Configuration");
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		slider = new JSlider(JSlider.HORIZONTAL, 0, 10, 4);
		slider.setBounds(124, 72, 200, 50);
		slider.setMajorTickSpacing(5);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		getContentPane().add(slider);
		
		slider_1 = new JSlider(JSlider.HORIZONTAL, 0, 30, 5);
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
		
		JButton btnNext = new JButton("Random Capacity");
		btnNext.setBounds(84, 227, 140, 23);
		btnNext.addActionListener(this);
		btnNext.setActionCommand("Random");
		getContentPane().add(btnNext);
		
		JButton btnNext1 = new JButton("Manual Capacity");
		btnNext1.setBounds(236, 227, 140, 23);
		btnNext1.addActionListener(this);
		btnNext1.setActionCommand("Manual");
		getContentPane().add(btnNext1);
		
        setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		nElevators = slider.getValue();
		nFloors = slider_1.getValue();

        if(cmd.equals("Manual"))
        {
        	dispose();
            new ElevatorConf(nElevators, nFloors);
        }
        else if(cmd.equals("Random")){
        	dispose();
        	//chamar a pagina dos elevadores já com capacidade pré definida
        }
		
		
	}
}
