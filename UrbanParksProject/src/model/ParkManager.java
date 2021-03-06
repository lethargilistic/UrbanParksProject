package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * ParkManager can add new jobs to the schedule, view all jobs for the parks they manage,
 * and view all volunteers for a given job.
 * @author Taylor Gorman
 * @version 9 May 2015
 *
 */
public class ParkManager {
	
	//Class Variables
	private ParkManagerUI myUI;	
	private Schedule mySchedule;
	private DataPollster myPollster;
	private List<Park> myManagedParks;	
	
	private String myFirstName;
	private String myLastName;
	
	private String myEmail;
	
	public ParkManager(String theEmail) {
		myEmail = theEmail;
	}
	
	public ParkManager(String theEmail, String theFirstName, String theLastName) {
		this.myEmail = theEmail;
		this.myFirstName = theFirstName;
		this.myLastName = theLastName;
	}
	
	public ParkManager(String theEmail, String theFirstName, String theLastName, List<Park> theParks) {
		this(theEmail, theFirstName, theLastName);
		this.myManagedParks = theParks;
	}
	
	/**
	 * Constructor for ParkManager, which requires a Schedule and DataPollster to be passed to it.
	 */
	public ParkManager(Schedule theSchedule, DataPollster thePollster,
			List<Park> theManagedParks, String theEmail) {
		this.mySchedule = theSchedule;
		this.myPollster = thePollster;
		this.myManagedParks = new ArrayList<>(theManagedParks);
		this.myUI = new ParkManagerUI();
		this.myEmail = theEmail;
	}

	//TODO: Should be removed in favor of having the commands be processed in the UI.
	public void initialize() {
		commandLoop();
	}

	//TODO: Should be removed in favor of having the commands be processed in the UI.
	// Reid: you mean having the commands processed in the UI class?
	/**
	 * The main loop for Park Manager.<br>
	 * Lists possible commands, prompts the user for one, and then acts on it.<br>
	 * If the user chooses to quit, or inputs an invalid command, then the loop terminates.
	 */
	public void commandLoop() {
		myUI.listCommands();
		int choice = myUI.getUserInt();
		
		if(parseCommand(choice)) {
			commandLoop();
		}
	}
	
	//TODO: Should be removed in favor of having the commands be processed in the UI.
	/**
	 * Parse a command, and call other methods to execute the command.
	 * @return Return true if we should continue the command loop, false if the user wants to logout.
	 */
	public boolean parseCommand(int choice) {
		boolean status = true;
		switch(choice) { 
			case 1:
				createJob(); 
				break;
				
			case 2:
				viewUpcomingJobs();
				break;
			
			case 3:
				viewJobVolunteers();
				break;
			
			case 4:
				status = false;
				
			default: 
				myUI.displayInvalidChoice();
				break;
		}
		
		return status;
	}

	
	/**
	 * Create a new job by:<p>
	 * 
	 * 1. Display all managed parks.<br>
	 * 2. Ask the user for the desired park number.<br>
	 * 3. Ask the user for all other details about the job.<br>
	 * 4. Pass the Job to Schedule.<br>
	 * 5. Tell the user if the new job was successfully added.
	 */
	public void createJob() {	
		System.out.println("\n------------------------------------------\nPlease select the number preceding the park where the job is located.");
		myUI.displayParks(myManagedParks); //Show the parks to the user, including their IDs
		
		int parkNum = myUI.selectParkNum();
		Park myPark = myManagedParks.get(parkNum);
		
		Job job = constructJob(myPark);
		
		boolean added = mySchedule.receiveJob(job);
		myUI.displayJobStatus(added);
	}
	
	/**
	 * Ask the Park Manager for the information needed to create a job, put it all together, and return the Job object.
	 * @param thePark The park where the job will occur.
	 * @return The constructed Job
	 */
	private Job constructJob(Park thePark) {	
		int myJobID = Job.getNextJobID();
		int myLight = myUI.getLightSlots();
		int myMedium = myUI.getMediumSlots();
		int myHeavy = myUI.getHeavySlots();	
		String myStartDate = myUI.getStartDate();
		String myEndDate = myUI.getEndDate();		
		List<String> myVolunteerList = new ArrayList<>();
		
		return new Job(myJobID, thePark, 0, myLight, 0, myMedium, 0, myHeavy, myStartDate, myEndDate, myEmail, myVolunteerList);
	}
	
	/**
	 * Print a list of all upcoming jobs for every Park that the ParkManager manages.
	 */
	public void viewUpcomingJobs() {
		ArrayList<Job> myJobList = (ArrayList<Job>) myPollster.getManagerJobs(this);
		myUI.displayJobs(myJobList);	
	}
	
	/**
	 * Print a list of every Volunteer for a selected Job.
	 */
	public void viewJobVolunteers() {
		int myJobID = myUI.getJobID();
		
		if(!checkPark(myJobID)) { //enter this if park ID is NOT valid.
			myUI.showJobIDError();
			return;
		}
		
		ArrayList<String> myVolunteerList = (ArrayList<String>) myPollster.getVolunteerList(myJobID);
		myUI.displayVolunteers(myVolunteerList, myPollster);		
	}
	
	public void setManagedParks(ArrayList<Park> theManagedParks) {
		this.myManagedParks = theManagedParks;
	}

	//TODO: What is the user story for this method?
	/**
	 * Check to make sure that the Job ID is valid.
	 * @return True if valid, false if not.
	 */
	private boolean checkPark(int theJobID) {
		//TODO
		return true; //Unsure of how to implement this at the moment. Will it be done through DataPollster?	
	}

	public List<Park> getManagedParks() {
		return Collections.unmodifiableList(myManagedParks);
	}	
	
	public String getEmail() {
		return myEmail;
	}

	public String getFirstName() {
		return this.myFirstName;
	}
	
	public String getLastName() {
		return this.myLastName;
	}
	
	@Override
	public boolean equals(Object theO)
	{
		if (!(theO instanceof ParkManager))
			return false;
	
		ParkManager theOther = (ParkManager) theO;
		
		return this.myEmail.equals(theOther.myEmail);
				
	}
}
