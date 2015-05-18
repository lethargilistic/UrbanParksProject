package model;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
	 * This is a text area.
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
		setLayout(new BorderLayout());
		
		
		getContentPane().add(createViewJobsButton());
		getContentPane().add(createSignUpButton());
		getContentPane().add(createMyJobsButton());
		getContentPane().add(createLogOutButton());
		
		
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}

	/**
	 * Creates a button to view all available jobs.
	 */
	private JButton createViewJobsButton() {
		JButton b = new JButton("View Available Jobs");
		getContentPane().add(b);

		b.addMouseListener(new MouseAdapter() {
			public void mouseClicked(final MouseEvent theEvent) {
				showJobs();
			}
		});
		
		return b;
	}
	
	
	/**
	 * This shows all of the jobs that are available to sign up for.
	 */
	private void showJobs() {
		JFrame frame2 = new JFrame("View Available Jobs");
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame2.setVisible(true);
	    frame2.setSize(600,600);
	    
	   
	    JPanel panel = new JPanel();
	    frame2.setContentPane(panel);
	    panel.setLayout(new BorderLayout());
	    
	    JLabel label = new JLabel("Jobs");
	    panel.add(label, BorderLayout.NORTH);       
	    
	    JTextArea area = new JTextArea();
	    textWrite(area);
	    
	    JScrollPane scrollPane = new JScrollPane(area);
	    
	    panel.add(scrollPane, BorderLayout.CENTER);
	    
		
	}
	
	private void textWrite(JTextArea theArea) {
		theArea.setText("Here are the available jobs. \n");
		List<Job> jList = myVol.getTheJobs();
		for (Job j: jList) {
			theArea.append("Job ID: " + j.getJobID() + "\n");
			theArea.append("     Start Date: " + j.getStartDate() + "\n");
			theArea.append("     End Date: " + j.getEndDate() + "\n");
			theArea.append("     Light Jobs : " + j.getLightCurrent() + "/" + j.getLightMax() + "\n");
			theArea.append("     Medium Jobs : " + j.getMediumCurrent() + "/" + j.getMediumMax() + "\n");
			theArea.append("     Heavy Jobs : " + j.getHeavyCurrent() + "/" + j.getHeavyMax() + "\n");
			theArea.append("     Location: " + j.getPark() + "\n");
			theArea.append("     Manager: " + j.getManager() + "\n");
			theArea.append("\n");
		}
		
		
		
		
		//use JTextArea.append(String text) to add more text.
		
		
		//write all of the jobs into theArea (check to see if the job is not in the past).
	}
	
	
	
	
	/**
	 * Creates a button to sign up for a job.
	 * @return 
	 */
	private JButton createSignUpButton() {
		JButton b = new JButton("Sign up");
		
		//TODO
		
		
		return b;
	}
	
	/**
	 * Creates a button to view the jobs the volunteer have signed up for.
	 * @return 
	 */
	private JButton createMyJobsButton() {
		JButton b = new JButton("See my jobs");
		
		//TODO
		
		
		return b;
	}

	/**
	 * Creates a log out button.
	 */
	private JButton createLogOutButton() {
		JButton b = new JButton("Logout");
		b.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				closeOut();
			}
		});
		
		return b;
	}
	
	private void closeOut() {
		super.dispose();
	}
	
	
	
	//NOTE: USE WINDOW BUILDER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	
	

	//NOTE: create a volunteer class method which gets info from data pollster using getJobListCopy()
	//then list the jobs on the screen that are in the future.

	

	//NOTE: to merge guiMaster into guiMasterArsh: right click on guimasterArsh project,
	//then team > merge. Then click origin/guiMaster in remote tracking folder.






	
	
	
}
