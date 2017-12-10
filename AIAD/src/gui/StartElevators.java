package gui;

import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.*;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

import elevator.Elevator;
import stats.Statistics;
import stats.StatisticsElevator;

import javax.swing.border.Border;

/**
 * 
 * This class starts the elevators behaviours
 *
 */
public class StartElevators extends JFrame implements ActionListener {
	
	/**
	 * Heuristic to be used
	 */
	private int heuristic;
	/**
	 * Building's number of elevators
	 */
	private int nElevators;
	/**
	 * Building's floors
	 */
	private int nFloors;
	/**
	 * Array with the capacity of each elevator
	 */
	private Integer[] capacities;
	/**
	 * Array with all the floors of each elevator
	 */
	private volatile JLabel[] labels;
	
	/**
	 * Thread that updates the GUI
	 */
	private Thread updateGUI;
	
	private JadeBoot boot;
	
	/**
	 * Creates the application
	 * @param nElevators Number of elevators
	 * @param nFloors Number of floors
	 * @param capacities Array with the capacity of each elevator
	 */
	public StartElevators(int nElevators, int nFloors, Integer[] capacities, int heuristic) {
		super("Elevators");
		this.nElevators = nElevators;
		this.nFloors = nFloors;
		this.heuristic = heuristic;
		this.capacities = capacities;
		this.labels = new JLabel[this.nElevators * this.nFloors];
		getContentPane().setForeground(Color.BLACK);
		initialize();
	}

	/**
	 * Initialize the contents of the frame
	 */
	private void initialize() {
		try {
			boot = new JadeBoot(this.nFloors, this.nElevators, this.capacities);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(ABORT);
		}
		
		setBounds(100, 100, 550, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JButton btnNext = new JButton("End");
		
		btnNext.setBounds(238, 379, 89, 23);
		btnNext.addActionListener(this);
		btnNext.setActionCommand("End");
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
				
				labels[j + i*nFloors] = label;
				
				getContentPane().add(label);
			}
		}	
        setVisible(true);
        
        updateGUI = new UpdateGUI(this, boot);
        updateGUI.start();
	}

	/**
	 * Changes the GUI when we click in the option 'Next'
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

        if(cmd.equals("End"))
        {
        	finishProgram();
        }
    }
	
	private void finishProgram() {
		updateGUI.interrupt();
		boot.end();
		if(boot.hasAllInstancesOfElevator()) {
			for(int i = 0; i < boot.getElevatorAgents().length; i++) {
				Elevator elev = boot.getElevatorAgents()[i];
				elev.getStatistics().setId(i);
				try {
					Statistics.instance.elevatorInfo(elev.getStatistics());
				} catch (IOException e1) {}
			}
		}
		Statistics.instance.finish();
		try {
			Statistics.instance.interrupt();
			System.out.println("Joining");
			Statistics.instance.join();
		} catch (InterruptedException ignore) {
		}
		dispose();
		System.exit(0);		
	}
	
	/**
	 * Gets the label index by floor and elevator
	 * @param floor Elevator's floor
	 * @param elevator Elevator's identifier
	 * @return The respective label index
	 */
	private int labelIndex(int floor, int elevator) {
		return (nFloors - floor - 1) + (nFloors * elevator);
	}
	
	/**
	 * Changes the floor color when the elevator is moving
	 * @param floor Elevator's floor
	 * @param elevator Elevator's identifier
	 */
	public synchronized void eraseFloor(int floor, int elevator) {
		int index;
		if(floor > 0) {
			index = labelIndex(floor - 1, elevator);
			labels[index].setBackground(Color.WHITE);
		}
		if(floor < nFloors - 1) {
			index = labelIndex(floor + 1, elevator);
			labels[index].setBackground(Color.WHITE);
		}
	}
	
	/**
	 * This function paints the floor of an elevator
	 * @param floor Elevator's floor
	 * @param elevator Elevator's identifier
	 */
	public synchronized void paintFloor(int floor, int elevator){
		int index = labelIndex(floor, elevator);
		labels[index].setBackground(Color.GREEN);
	}
}
