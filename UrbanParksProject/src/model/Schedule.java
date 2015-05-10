package model;

import java.util.List;

/**
 * Defines the Schedule object for an application.
 * This entity contains the master job list and enforces
 * business rules associated with its role in the program.
 * 
 * @author Reid Thompson
 * @version 5.3.2015
 */
public class Schedule {
	
	/**
	 * An integer representing the total number of jobs 
	 */
	private static final int MAX_TOTAL_NUM_JOBS = 1000; // before it was Integer.MAX_VALUE
	
	/**
	 * This is the Schedule's master job list.
	 */
	private JobList myJobList;
	
	private UserList myUserList;
	
	/**
	 * Constructs a Schedule object.
	 */
	public Schedule(JobList theJobList, UserList theUserList) {
		this.myJobList = theJobList;
		this.myUserList = theUserList;
	}

	/**
	 * Receives a job and adds it to the master job list if its data is valid.
	 * 
	 * For user story #1. Called by createJob() method of ParkManager class.
	 * 
	 * @param theJob is the Job to potentially add to Schedule's list of Jobs.
	 * @return true if theJob was added, and false otherwise.
	 */
	public boolean receiveJob(Job theJob) {		
		// Checks the fields of the object to make sure they're valid.
		boolean okToAdd = true;
		
		// checks if date range is valid
		if (theJob.getStartDate().after(theJob.getEndDate())) {
			okToAdd = false;
		}
		
		// checks that the job id is valid
		if (theJob.getJobID() < Integer.MIN_VALUE || theJob.getJobID() > Integer.MAX_VALUE) {
			okToAdd = false;
		}
		
		// checks if volunteer list is empty
		if (!theJob.getVolunteerList().isEmpty()) {
			okToAdd = false;
		}
		
		//removed myIsRequest if-statement from here.
		
		
		//NOTE: The problem with this if-statement is that it will be entered if any
		//of Light, Medium, or Heavy has 0 spaces.
		//Some jobs will have zero space for one or two of these levels which means when
		//that job is passed in, it will always enter this if-statement and return false....not good.
		
		//I made changed here! I changed all the or (||) to and (&&). Now the if-statement wont be entered
		//unless there is zero room in each job level. Arsh.
		if (!theJob.hasLightRoom() && !theJob.hasMediumRoom() && !theJob.hasHeavyRoom()) {
			okToAdd = false;
		}
		
		if (theJob.getLightCurrent() > theJob.getLightMax() || theJob.getMediumCurrent() > theJob.getMediumMax()
				|| theJob.getHeavyCurrent() > theJob.getHeavyMax()) {
			okToAdd = false;
		}
		
		if (theJob.getPark() == null) {
			okToAdd = false;
		}
		
		if (theJob.getManager() == null){
			okToAdd = false;
		}
		
		if (okToAdd) {
			// To get the master job list which is editable
			List<Job> editableJobList = myJobList.getJobList();
			editableJobList.add(theJob); // add valid job to list
		} else {
			System.out.println("Error: job data is invalid and therefore was not added.");
		}

		return okToAdd;
	}

	/**
	 * Adds a Volunteer to an existing Job if the given data is valid.
	 * 
	 * For User Story #3. Called by Volunteer.signup()
	 * 
	 * @param theVolunteer the Volunteer to add to a particular job.
	 * @param theJobID the ID number for the Job to which the Volunteer will be added.
	 * @param theWorkGrade the work grade selected by the Volunteer for a particular Job.
	 * I'm assuming this value would be 1 = light, 2 = medium, or 3 = heavy.
	 * @return true if the Volunteer was added to the Job and false otherwise.
	 * @throws Exception 
	 */
	public boolean addVolunteerToJob(String theVolunteer, int theJobID, int theWorkGrade) throws Exception {
		boolean okToAdd = true;
		
		//Schedule will check to make sure the Job ID is valid
		if (theJobID < 0 || theJobID > MAX_TOTAL_NUM_JOBS) {
			okToAdd = false;
			
			throw new IllegalArgumentException("This job ID does not exist");
																	//NOTE: this exception is thrown if the user enters
																	//an invalid ID. It will be caught in a try-catch block.
		}
		
		if (theWorkGrade < 1 || theWorkGrade > 3) {
			okToAdd = false;
		}
		
		//Calls JobList.getJobList() to get the master job list which is editable
		List<Job> editableJobList = myJobList.getJobList();
		
		boolean jobExists = false;
		Job j = null;
		for (int i = 0; i < editableJobList.size(); i++) {
			// If invalid, return else get Job object with that Job ID
			j = editableJobList.get(i);
			
			if (j.getJobID() == theJobID) {
				jobExists = true;
				break;
			}
		}
		
		// If the job doesn't exist, return false.
		if (j == null)
			return false;
		
		// Schedule will check to make sure there is a slot open in the grade using 
		// the Job object from before
		boolean openGrade = false;
		switch (theWorkGrade) {
			case 1:
				if (j.hasLightRoom()) {
					openGrade = true;
				}
				break;
			case 2:
				if (j.hasMediumRoom()) {
					openGrade = true;
				}
				break;
			case 3:
				if (j.hasHeavyRoom()) {
					openGrade = true;
				}
				break;
			default:
					throw new IllegalStateException(theWorkGrade + " for job " + theJobID + " is full");	
												//This will be caught in a try-catch block when the volunteer attempts
												//to join a job grade that is not available.
		}
		
		if (okToAdd && jobExists && openGrade) {
			// If everything is okay, we add the Volunteer to the Job’s Volunteer List,
			// increment the grade slot, and return.
			j.getVolunteerList().add(theVolunteer);
			
			switch (theWorkGrade) {
				case 1:
					j.incrementLight();
					break;
				case 2:
					j.incrementMedium();
					break;
				case 3:
					j.incrementHeavy();
					break;
			}
		} else {
			// If either of these are false, we print to the console and return
			System.out.println("Error with given data. Volunteer was not added to Job.");
		}
		
		return okToAdd && jobExists && openGrade;
	}
	
	public JobList getJobList() {
		return myJobList;
	}

	public void addUser(String theEmail, String theFirstName, String theLastName,
			String theUserType) {

		switch(theUserType) {
			case "Administrator":
				Administrator a = new Administrator(theFirstName, theLastName, theEmail);
				List<Administrator> listAdmin = myUserList.getAdministratorCopyList();
				listAdmin.add(a);
				myUserList.setAdministratorList(listAdmin);
				break;
				
			case "ParkManager":

				ParkManager newManager = new ParkManager(theEmail, theFirstName, theLastName);
				List<ParkManager> managerList = myUserList.getParkManagerCopyList();
				managerList.add(newManager);
				myUserList.setParkManagerList(managerList);
				break;
				
			case "Volunteer":
				Volunteer v = new Volunteer(theEmail, theFirstName, theLastName);
				List<Volunteer> listVols = myUserList.getVolunteerCopyList();
				listVols.add(v);
				myUserList.setVolunteerList(listVols);
				break;
		}
		
	}

}
