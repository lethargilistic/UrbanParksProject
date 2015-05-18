package model;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This class is the gui for the volunteer.
 * Note: it does no logic work.
 * @author Arsh
 *
 */
public class VolunteerGUI extends JFrame{

	/**
	 * This is an instance of a volunteer. It is used when a volunteer logs in.
	 */
	private Volunteer myVol;
	
	/**
	 * This is a text area .
	 */
	private JTextArea myTextArea;// similar to JScrollPane but with less functionality.
	
	
	
	/**
	 * This is the constructor.
	 */
	public VolunteerGUI(Volunteer theVol) {
		super("Volunteer");
		
		myVol = theVol;
		startUp();
		
	}


	/**
	 * This makes the good stuff appear.
	 */
	private void startUp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);


	}

	/**
	 * Creates a button to view all available jobs.
	 */
	private void createViewJobsButton() {
		JButton b = new JButton("View Available Jobs");
		getContentPane().add(b);
		
		b.addMouseListener(new MouseAdapter() {
            public void mouseClicked(final MouseEvent theEvent) {
            	
            }
            });
		
            
	}
	
	/**
	 * Creates a button to sign up for a job.
	 */
	private void createSignUpButton() {
		
	}
	
	/**
	 * Creates a button to view the jobs the volunteer have signed up for.
	 */
	private void createMyJobsButton() {
		
	}

	/**
	 * Creates a back button.
	 */
	private void createBackButton() {
		
	}
	
	
	
	
	
	//NOTE: USE WINDOW BUILDER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	
	

	//NOTE: create a volunteer class method which gets info from data pollster using getJobListCopy()
	//then list the jobs on the screen that are in the future.

	

	//NOTE: to merge guiMaster into guiMasterArsh: right click on guimasterArsh project,
	//then team > merge. Then click origin/guiMaster in remote tracking folder.






	
	
	
}
