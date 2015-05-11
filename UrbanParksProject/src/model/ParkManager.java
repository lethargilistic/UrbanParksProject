package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * ParkManager can add new jobs to the schedule, view all jobs for the parks they manage,
 * and view all volunteers for a given job.
 * 
 * @author Taylor Gorman
 * @author Reid Thompson
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
		myManagedParks = new ArrayList<Park>();
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
				break;
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
		System.out.println("\n------------------------------------------\n"
				+ "Please select the number preceding the park where the job is located.");
		myUI.displayParks(myManagedParks); //Show the parks to the user, including their IDs
		
		int parkNum = myUI.getUserInt();
		
		if(parkNum < myManagedParks.size()) {
			//ParkManager is assigning to an existing park.
			Park myPark = myManagedParks.get(parkNum);
			
			Job job = constructJob(myPark);
			
			// ENFORCING BUSINESS RULE #4 - A job may not be scheduled if it lasts more than 2 days.
			boolean okToAdd = getDays(job.getStartDate(), job.getEndDate()) <= 2;
			
			if (okToAdd) {
				boolean added = mySchedule.receiveJob(job);
				myUI.displayJobStatus(added);
			} else {
				System.out.println("A job may not be scheduled if it lasts more than 2 days");
			}
		} else {
			//ParkManager is adding a new park
			Park newPark = myUI.createNewPark();
			myManagedParks.add(newPark);
			mySchedule.updateParkList(myEmail, myManagedParks);
			createJob();
		}
	}
	
	/**
	 * Returns the number of days between 2 GregorianCalendar objects.
	 * 
	 * @author http://stackoverflow.com/questions/10624229/compare-dates-using-calendar-class-using-java
	 * 
	 * @param theG1 the first GregorianCalendar object.
	 * @param theG2 the second GregorianCalendar object.
	 * @return the number of days between 2 GregorianCalendar objects.
	 */
	public int getDays(GregorianCalendar theG1, GregorianCalendar theG2) {
		int elapsed = 0;
		GregorianCalendar gc1, gc2;
		if (theG2.after(theG1)) {
			gc2 = (GregorianCalendar) theG2.clone();
			gc1 = (GregorianCalendar) theG1.clone();
		}
		else   {
			gc2 = (GregorianCalendar) theG1.clone();
			gc1 = (GregorianCalendar) theG2.clone();
		}
		gc1.clear(Calendar.MILLISECOND);
		gc1.clear(Calendar.SECOND);
		gc1.clear(Calendar.MINUTE);
		gc1.clear(Calendar.HOUR_OF_DAY);
		gc2.clear(Calendar.MILLISECOND);
		gc2.clear(Calendar.SECOND);
		gc2.clear(Calendar.MINUTE);
		gc2.clear(Calendar.HOUR_OF_DAY);
		while ( gc1.before(gc2) ) {
			gc1.add(Calendar.DATE, 1);
			elapsed++;
		}
		return elapsed;
	}
	
	/**
	 * Ask the Park Manager for the information needed to create a job, put it all together, and return the Job object.
	 * @param thePark The park where the job will occur.
	 * @return The constructed Job
	 */
	private Job constructJob(Park thePark) {	
		int myJobID = myPollster.getNextJobID();
		int myLight = myUI.getLightSlots();
		int myMedium = myUI.getMediumSlots();
		int myHeavy = myUI.getHeavySlots();	
		String myStartDate = myUI.getStartDate();
		String myEndDate = myUI.getEndDate();		
		ArrayList<ArrayList<String>> myVolunteerList = new ArrayList<>();
		
		return new Job(myJobID, thePark, myLight, myMedium, myHeavy, myStartDate, myEndDate, myEmail, myVolunteerList);
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
		int jobID = myUI.getJobID();
		
		if(!checkPark(jobID)) { //enter this if park ID is NOT valid.
			myUI.showJobIDError();
		} else {
			ArrayList<ArrayList<String>> myVolunteerList = myPollster.getVolunteerList(jobID);
			myUI.displayVolunteers(myVolunteerList, myPollster);	
		}
	}
	
	public void setManagedParks(List<Park> theManagedParks) {
		this.myManagedParks = theManagedParks;
	}

	//TODO: What is the user story for this method?
	// user story #6
	/**
	 * Check to make sure that the Job ID is valid.
	 * @return True if valid, false if not.
	 */
	private boolean checkPark(int theJobID) {
		boolean result = false;

		for (Job j : myPollster.getAllJobs()) {
			if(j.getJobID() == theJobID) {
				for(Park park : myManagedParks) {
					if(park.getName().equals(j.getPark().getName())) return true;
				}
			}

		}
		return result;
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
	
}
