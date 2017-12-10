package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

/**
 * 
 * Creates the program's main page
 *
 */
public class MainPage extends JFrame implements ActionListener {

	/**
	 * Launch the application
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainPage().setVisible(true);
			}
		});
	}


	/**
	 * Create the application's main page
	 */
	public MainPage() {
		super("Elevators Manager");
		initialize();
	}

	/**
	 * Initialize the contents of the frame
	 */
	private void initialize() {
		setBounds(100, 100, 475, 316);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblWelcomeToThe = new JLabel("Welcome to the Elevators Manager");
		lblWelcomeToThe.setBounds(55, 34, 354, 36);
		lblWelcomeToThe.setFont(new Font("Tahoma", Font.PLAIN, 22));
		getContentPane().add(lblWelcomeToThe);
		
		JButton btnNormal = new JButton("Good Heuristic");
		btnNormal.setBounds(170, 122, 120, 23);
		btnNormal.addActionListener(this);
		btnNormal.setActionCommand("Heur1");
		getContentPane().add(btnNormal);
		
		JButton btnSpecial = new JButton("Bad Heuristic");
		btnSpecial.setBounds(170, 169, 120, 23);
		btnSpecial.addActionListener(this);
		btnSpecial.setActionCommand("Heur2");
		getContentPane().add(btnSpecial);
	}

	/**
	 * Changes the GUI when we click in the different options
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

        if(cmd.equals("Heur1"))
        {
        	dispose();
            new Form(true);
        }
        else if(cmd.equals("Heur2")){
        	dispose();
            new Form(false);
        }
		
	}
}