package model;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * This class is the gui for the volunteer.
 * Note: it does no logic work.
 * @author Arsh
 *
 */
public class VolunteerGUI extends JFrame{

	//=================================
	//FIELDS
	//=================================
		
	
	/**
	 * This is an instance of a volunteer. It is used when a volunteer logs in.
	 */
	private Volunteer myVol;

	
	
	
	
	//=================================
	//METHODS
	//=================================
	

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

	/**
	 * This adds text to an area.
	 * The text it adds is all of the jobs that are not in the past.
	 * @param theArea is a JTextArea.
	 */
	private void textWrite(JTextArea theArea) {
		theArea.setText("Here are the available jobs. \n");
		List<Job> jList = myVol.getTheJobs();
		for (Job j: jList) {

			if (!j.isInPast()) {
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
		}

	}



	/**
	 * Creates a button to sign up for a job.
	 * @return 
	 */
	private JButton createSignUpButton() {
		JButton b = new JButton("Sign up");

		getContentPane().add(b);
		
		b.addMouseListener(new MouseAdapter() {
			public void mouseClicked(final MouseEvent theEvent) {
				joinJob();
			}
		});


		return b;
	}
	
	
	/** This method creates a new panel which will ask the user for
	 * what job they want to join.
	 */
	private void joinJob() {
		JFrame frame2 = new JFrame("Sign up for job");
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame2.setVisible(true);
		frame2.setSize(600, 400);
		
		JPanel panel = new JPanel();
		frame2.setContentPane(panel);
		panel.setLayout(new BorderLayout());

		JLabel label = new JLabel("Sign up for a job by typing in the Job ID below.");
		panel.add(label, BorderLayout.NORTH);     
		
		JTextField field = new JTextField();
		panel.add(field, BorderLayout.CENTER);
		
		JButton b = new JButton("Sign Up");
		panel.add(b, BorderLayout.SOUTH);
		b.addMouseListener(new MouseAdapter() {
			public void mouseClicked(final MouseEvent theEvent) {
				String ID = field.getText();
				
				//TODO change this ID string to an ID int.
				//NOTE: dont let a volunteer sign up for the same job twice
				//NOTE: dont let a volunteer sign up for a past job.
			}
		});
		
		
	}
	
	
	

	/**
	 * Creates a button to view the jobs the volunteer have signed up for.
	 * @return 
	 */
	private JButton createMyJobsButton() {
		JButton b = new JButton("See my jobs");
		getContentPane().add(b);

		b.addMouseListener(new MouseAdapter() {
			public void mouseClicked(final MouseEvent theEvent) {
				seeJobs();
			}
		});
		
		return b;
	}

	/**
	 * This method creates a new frame that shows all the available jobs.
	 */
	private void seeJobs() {
		JFrame frame2 = new JFrame("View Available Jobs");
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame2.setVisible(true);
		frame2.setSize(600,600);


		JPanel panel = new JPanel();
		frame2.setContentPane(panel);
		panel.setLayout(new BorderLayout());

		JLabel label = new JLabel("My Jobs");
		panel.add(label, BorderLayout.NORTH);       

		JTextArea area = new JTextArea();
		textWrite2(area);

		JScrollPane scrollPane = new JScrollPane(area);

		panel.add(scrollPane, BorderLayout.CENTER);
	}


	/**
	 * This writes the jobs that this volunteer has signed up for to a JTextArea.
	 * @param theArea is the JtextArea that needs to be filled out.
	 */
	private void textWrite2(JTextArea theArea){
		theArea.setText("Here are the jobs that you have signed up for. \n");

		List<Job> allJobs = myVol.getTheJobs();

		for (Job j: allJobs){
			ArrayList<ArrayList<String>> allVols = j.getVolunteerList();

			for (ArrayList<String> list: allVols) {
				if (list.get(0).equals(myVol.getEmail())) {
					if (!j.isInPast()) {
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
				}
			}
		}
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



	//NOTE: to merge guiMaster into guiMasterArsh: right click on guimasterArsh project,
	//then team > merge. Then click origin/guiMaster in remote tracking folder.

}
