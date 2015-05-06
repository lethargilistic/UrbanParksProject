package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * ParkManager can add new jobs to the schedule, view all jobs for the parks they manage,
 * and view all volunteers for a given job.
 * @author Taylor Gorman
 * @version 1.01
 *
 */
public class ParkManager {
	
	//Class Variables
	private ParkManagerUI myUI;	
	private Schedule mySchedule;
	private DataPollster myPollster;
	private List<Park> myManagedParks;	
	
	
	/**
	 * Constructor for ParkManager, which requires a Schedule and DataPollster to be passed to it.
	 */
	public ParkManager(Schedule theSchedule, DataPollster thePollster,
			List<Park> theManagedParks) {
		this.mySchedule = theSchedule;
		this.myPollster = thePollster;
		this.myManagedParks = new ArrayList<>(theManagedParks);
		this.myUI = new ParkManagerUI();
	}

	//TODO: Should be removed in favor of having the commands be processed in the UI.
	public void initialize() {
		commandLoop();
	}

	//TODO: Should be removed in favor of having the commands be processed in the UI.
	/**
	 * The main loop for Park Manager.<br>
	 * Lists possible commands, prompts the user for one, and then acts on it.<br>
	 * If the user chooses to quit, or inputs an invalid command, then the loop terminates.
	 */
	public void commandLoop() {
		System.out.println("Choose an option");
		myUI.listCommands();
		String command = myUI.getCommand();
		
		if(parseCommand(command)) {
			commandLoop();
		}
	}
	
	//TODO: Should be removed in favor of having the commands be processed in the UI.
	/**
	 * Parse a command, and call other methods to execute the command.
	 */
	public boolean parseCommand(String command) {
		command = command.toLowerCase(); //lower case to avoid ambiguity
		
		switch(command) { 
			case "new job":
			case "new":	
			case "n":
			case "1":
				createJob(); 
				return true;
			
			case "view jobs":
			case "view job":
			case "j":
			case "2":
				viewUpcomingJobs();
				return true;
			
			case "view volunteers":
			case "view volunteer":
			case "v":
			case "3":
				viewJobVolunteers();
				return true;
			
			case "exit":
			case "close":
			case "quit":
			case "4":
			default: 
				return false;
		}
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
		System.out.println("\nPlease select the number preceding the park where the job is located.");
		myUI.displayParks(myManagedParks); //Show the parks to the user, including their IDs
		
		int parkNum = myUI.selectParkNum();
		Park myPark = myManagedParks.get(parkNum);
		
		Job myJob = constructJob(myPark);
		
		boolean added = mySchedule.receiveJob(myJob);
		myUI.displayJobStatus(added);
	}
	
	/**
	 * Ask the Park Manager for the information needed to create a job, put it all together, and return the Job object.
	 * @param thePark The park where the job will occur.
	 * @return The constructed Job
	 */
	private Job constructJob(Park thePark) {		
		int myLight = myUI.getLightSlots();
		int myMedium = myUI.getMediumSlots();
		int myHeavy = myUI.getHeavySlots();	
		String myStartString = myUI.getStartDate();
		String myEndString = myUI.getEndDate();		
		
		GregorianCalendar myStartDate = stringToCalendar(myStartString);
		GregorianCalendar myEndDate = stringToCalendar(myEndString);
		
		return new Job(thePark, myLight, myMedium, myHeavy, myStartDate, myEndDate, this);		
	}
	
	
	/**
	 * Convert a date string to a Gregorian Calendar object.
	 * @param stringDate A string representing a date, of format mmddyyyy
	 * @return A Gregorian Calendar object representing that date.
	 */
	private GregorianCalendar stringToCalendar(String stringDate) {
		int myDay = Integer.parseInt(stringDate.substring(0, 2));
		int myMonth = Integer.parseInt(stringDate.substring(2, 4));
		int myYear = Integer.parseInt(stringDate.substring(4, 8));		
		
		return new GregorianCalendar(myYear, myDay, myMonth);
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
		
		ArrayList<Volunteer> myVolunteerList = (ArrayList<Volunteer>) myPollster.getVolunteerList(myJobID);
		myUI.displayVolunteers(myVolunteerList);		
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

	List<Park> getManagedParks() {
		return Collections.unmodifiableList(myManagedParks);
	}	
	
}
