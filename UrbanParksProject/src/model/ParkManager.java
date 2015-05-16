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
	private List<String> myManagedParks;	
	
	private String myFirstName;
	private String myLastName;
	
	private String myEmail;

	//Constructor
	public ParkManager(String theEmail, String theFirstName, String theLastName, List<String> theParkList) {
		this.myEmail = theEmail;
		this.myFirstName = theFirstName;
		this.myLastName = theLastName;
		
		//theParkList is an Unmodifiable List, so we cannot cast it to ArrayList. So we copy it over instead.
		List<String> copiedParks = new ArrayList<String>();
		copiedParks.addAll(theParkList);
		this.myManagedParks = copiedParks;
	}
	

	
	
	/*============*
	 * UI Control *
	 *============*/
	
	/**
	 * Give control of Schedule and DataPollster to the ParkManager and initialize its UI.
	 */
	public void initialize(Schedule theSchedule, DataPollster thePollster) {
		mySchedule = theSchedule;
		myPollster = thePollster;
		myUI = new ParkManagerUI();
		commandLoop();
	}

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
				myUI.displayInvalidChoiceError();
				break;
		}
		
		return status;
	}

	
	
	
	/*==============*
	 * Job Creation *
	 *==============*/
	
	/**
	 * Create a new job by:<p>
	 * 
	 * 1. Ask the user for the desired Park.<br>
	 * 2. Ask the user for all other details about the Job.<br>
	 * 3. Pass the Job to Schedule.
	 */
	public void createJob() {	
		
		//Show Parks to ParkManager and ask for selection.
		myUI.displayParkNumberRequest();
		myUI.displayParks(myManagedParks);
		int parkNum = myUI.getUserInt();
		
		//ParkManager may request to first add a new park at this point.
		if(parkNum < myManagedParks.size()) {
			//ParkManager is assigning to an existing park.
			
			String myPark = myManagedParks.get(parkNum);			
			Job thisJob = constructJob(myPark);
			
			//Check if the job lasts longer than two days. Add if it does not.
			boolean lessThanTwoDays = getDays(thisJob.getStartDate(), thisJob.getEndDate()) < 2;		
			
			if(lessThanTwoDays) {
				boolean addedFlag = mySchedule.receiveJob(thisJob);
				myUI.displayJobStatus(addedFlag);
			} else {
				myUI.displayTwoDayError();
			}			
			
		} else {
			//ParkManager is adding a new park
			String newPark = myUI.createNewPark();
			myManagedParks.add(newPark);
			mySchedule.updateParkList(myEmail, myManagedParks);
			createJob();
		}
	}
	
	/**
	 * Returns the number of days between two GregorianCalendars.
	 */
	private int getDays(GregorianCalendar theStartDate, GregorianCalendar theEndDate) {
		long timeDifference = Math.abs(theEndDate.getTimeInMillis() - theStartDate.getTimeInMillis());
		return (int) (timeDifference / 86400000l);
	}
	
	/**
	 * Ask the Park Manager for the information needed to create a job, put it all together, and return the Job object.
	 * @param thePark The park where the job will occur.
	 * @return The constructed Job
	 */
	private Job constructJob(String thePark) {	
		int myJobID = myPollster.getNextJobID();
		int myLight = myUI.getLightSlots();
		int myMedium = myUI.getMediumSlots();
		int myHeavy = myUI.getHeavySlots();	
		String myStartDate = myUI.getStartDate();
		String myEndDate = myUI.getEndDate();		
		ArrayList<ArrayList<String>> myVolunteerList = new ArrayList<>();
		
		return new Job(myJobID, thePark, myLight, myMedium, myHeavy, myStartDate, myEndDate, myEmail, myVolunteerList);
	}
	
	
	
	/*==========================*
	 * View Jobs and Volunteers *
	 *==========================*/
	
	/**
	 * Print a list of all upcoming jobs for every Park that the ParkManager manages.
	 */
	public void viewUpcomingJobs() {
		List<Job> myJobList = myPollster.getManagerJobs(this);
		myUI.displayJobs(myJobList);
	}
	
	/**
	 * Print a list of every Volunteer for a selected Job.
	 */
	public void viewJobVolunteers() {
		int jobID = myUI.getJobID();
		
		if(jobInManagedParks(jobID)) {
			//Get Volunteer List and display it.
			List<Volunteer> volunteerList = myPollster.getJobVolunteerList(jobID);			
			myUI.displayVolunteers(volunteerList);	
			
		} else {
			myUI.showJobIDError();
		}
	}

	/**
	 * Check to make sure that the Job ID is valid.
	 * @return True if valid, false if not.
	 */
	private boolean jobInManagedParks(int theJobID) {		
		Job foundJob = myPollster.getJobCopy(theJobID);
		
		//If we found the job, then check to see if it is in one of our parks.
		if(foundJob != null) {
			for(String managedPark : myManagedParks) {
				if(managedPark.equals(foundJob.getPark())) return true;
			}
		}		
		
		return false;
	}
	
	
	
	/*=====================*
	 * Getters and Setters *
	 *=====================*/	
	
	public void setManagedParks(List<String> theManagedParks) {
		this.myManagedParks = theManagedParks;
	}
	
	public List<String> getManagedParks() {
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
	
	
	
	
	public Object[][] getJobArray() {
		JobList myJobList = new JobList();
		UserList myUserList = new UserList();
		myPollster = new DataPollster(myJobList, myUserList);
		myManagedParks = myPollster.getParkList("moverby@gmail.com");
		
		ArrayList<Job> jobs = (ArrayList<Job>) myPollster.getManagerJobs(this);
		
		Object[][] jobArray = new Object[jobs.size()][5];
		int jobNumber = 0;
		
		for(Job thisJob : jobs) {
			jobArray[jobNumber][0] = thisJob.getJobID();
			jobArray[jobNumber][1] = thisJob.getPark();
			jobArray[jobNumber][2] = thisJob.getLightCurrent() + "/" + thisJob.getLightMax();
			jobArray[jobNumber][3] = thisJob.getMediumCurrent() + "/" + thisJob.getMediumMax();
			jobArray[jobNumber][4] = thisJob.getHeavyCurrent() + "/" + thisJob.getHeavyMax();
		}
		
		return jobArray;
	}
	
}
