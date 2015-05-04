package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

public class ParkManager {
	
	/**
	 * A list of all the parks managed by the park manager.
	 */
	private ArrayList<Park> myManagedParks;
	
	private String myFirstName;
	private String myLastName;
	
	private ParkManagerUI myUI;	
	private Schedule mySchedule;
	private DataPollster myPollster;
	
	
	/**
	 * Constructor for ParkManager, which requires a Schedule and DataPollster to be passed to it.
	 */
	public ParkManager(Schedule theSchedule, DataPollster thePollster) {
		this.mySchedule = theSchedule;
		this.myPollster = thePollster;
	}

	
	/**
	 * The main loop for Park Manager.<br>
	 * Lists possible commands, prompts the user for one, and then acts on it.<br>
	 * If the user chooses to quit, or inputs an invalid command, then the loop terminates.
	 */
	public void commandLoop() {
		myUI.listCommands();
		String command = myUI.getCommand();
		if(parseCommand(command)) {
			commandLoop();
		}
	}
	
	/**
	 * Parse a command, and call other methods to execute the command.
	 */
	private boolean parseCommand(String command) {
		switch(command) { 
			case "new job":
			case "new":				
				createJob(); 
				return true;
			
			case "view jobs":
			case "view job":
				viewUpcomingJobs();
				return true;
			
			case "view job volunteers":
			case "view job volunteer":
			case "view volunteers":
				viewJobVolunteers();
				return true;
			
			case "exit":
			case "close":
			case "quit":
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
		myUI.displayParks(myManagedParks);
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
		
		GregorianCalendar myStartDate = parseDate(myStartString);
		GregorianCalendar myEndDate = parseDate(myEndString);
		
		return new Job(thePark, myLight, myMedium, myHeavy, myStartDate, myEndDate);		
	}
	
	
	/**
	 * Convert a date string to a Gregorian Calendar object.
	 * @param stringDate A string representing a date, of format mmddyyyy
	 * @return A Gregorian Calendar object representing that date.
	 */
	private GregorianCalendar parseDate(String stringDate) {
		int myDay = Integer.parseInt(stringDate.substring(0, 2));
		int myMonth = Integer.parseInt(stringDate.substring(2, 4));
		int myYear = Integer.parseInt(stringDate.substring(4, 8));		
		
		return new GregorianCalendar(myYear, myMonth, myDay);
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
		
		if(!checkPark(myJobID)) {
			myUI.showJobIDError();
			return;
		}
		
		ArrayList<Volunteer> myVolunteerList = (ArrayList<Volunteer>) myPollster.getVolunteerList(myJobID);
		myUI.displayVolunteers(myVolunteerList);		
	}

	
	/**
	 * Check to make sure that the Job ID is valid.
	 * @return True if valid, false if not.
	 */
	private boolean checkPark(int theJobID) {
		return true; //Unsure of how to implement this at the moment. Will it be done through DataPollster?	
	}

	List<Park> getManagedParks() {
		List<Park> retList = new ArrayList<>();
		Collections.copy(retList, myManagedParks);
		return retList;
	}	
	
}
