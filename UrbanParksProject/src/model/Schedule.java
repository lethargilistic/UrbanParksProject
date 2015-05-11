package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
		
		
		//BIZ rule 1. A job may not be added if the total number of pending jobs is currently 30. 
		if (myJobList.getCopyList().size() >= 30) {
			okToAdd = false;
		}
		//BIZ rule 2. A job may not be added if the total number of pending jobs during that week 
			//(3 days on either side of the job days) is currently 5.
		if (!checkThisWeek(theJob)) {
			okToAdd = false; //this is entered if checkThisWeek() returns false;
		} 
		
		
		//BIZ rule 5. A job may not be added that is in the past or more than three months in the future. 
				//I am going to say that the manager can only create a job on a date after today.
		Calendar now = new GregorianCalendar();
		if (!theJob.getStartDate().after(now)) {
			okToAdd = false;
		}
		now.add(Calendar.DAY_OF_MONTH, 90);
		if (theJob.getStartDate().after(now)) {
			okToAdd = false;
		}
		
		

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
	 * This method is made for business rule 2.
	 * It checks either side of the jobs date and returns false if there are already
	 * 5 jobs set up for that 7 day period.
	 * @return true if this job is in the clear (there are less than 5 jobs in a 7 day period), false otherwise
	 */
	private boolean checkThisWeek(Job theJob) {
		//count increases for every other job that is within 3 days before start and 3 days after end.
		int count = 0;
		
		//3 days before start and 3 days after end
		Calendar aStart = (Calendar) theJob.getStartDate().clone();
		
		aStart.add(Calendar.DAY_OF_MONTH, -3);

		Calendar aEnd = (Calendar) theJob.getEndDate().clone();
		aEnd.add(Calendar.DAY_OF_MONTH, 3);
		
		List<Job> aList = myJobList.getCopyList();
		for (Job j: aList) {
			
			long difference =  j.getEndDate().getTimeInMillis() - aStart.getTimeInMillis();
			difference /= (3600*24*1000); //change value to days instead of milliseconds
			long difference2 =  aEnd.getTimeInMillis() - j.getStartDate().getTimeInMillis();
			difference2 /= (3600*24*1000); //change value to days instead of milliseconds
			
			//System.out.println(j.getPark() + " " + j.getStartDate() + " " + j.getEndDate());
			//System.out.println(difference);
			//System.out.println(difference2);
			
			//if ending date of j is within 3 days before theJob, increment count
			//if starting date of j is within 3 days after theJob, increment count
			if ((difference <= 3 && difference >= 0)|| (difference2 <= 3 && difference2 >= 0)) {
				count++;
			}
			//System.out.println("Count is " + count);
			if (count >= 5) {
				return false;
			}
		}
		return true;
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
	public boolean addVolunteerToJob(ArrayList<String> theVolunteer, int theJobID) throws Exception {
		boolean okToAdd = true;
		
		//Schedule will check to make sure the Job ID is valid
		if (theJobID < 0 || theJobID > MAX_TOTAL_NUM_JOBS) {
			okToAdd = false;
			
			throw new IllegalArgumentException("This job ID does not exist");
																	//NOTE: this exception is thrown if the user enters
																	//an invalid ID. It will be caught in a try-catch block.
		}
		
		//Calls JobList.getJobList() to get the master job list which is editable
		List<Job> editableJobList = myJobList.getJobList();
		
		final GregorianCalendar now = new GregorianCalendar();
		final List<Job> validJobList = new ArrayList<>(myJobList.getJobList().size());
		for (int i = 0; i < editableJobList.size(); i++) {
			final Job j = editableJobList.get(i);
			if (j.getStartDate().after(now)) { // second condition takes care of BIZ RULE #6
				validJobList.add(j);
			}
		}
		
		boolean jobExists = false;
		Job j = null;
		for (int i = 0; i < validJobList.size(); i++) {
			// If invalid, return else get Job object with that Job ID
			j = validJobList.get(i);
			
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
		switch (theVolunteer.get(1)) {
			case "Light":
				if (j.hasLightRoom()) {
					openGrade = true;
				}
				break;
			case "Medium":
				if (j.hasMediumRoom()) {
					openGrade = true;
				}
				break;
			case "Heavy":
				if (j.hasHeavyRoom()) {
					openGrade = true;
				}
				break;
			default:
					throw new IllegalStateException(theVolunteer.get(1) + " for job " + theJobID + " is full");	
												//This will be caught in a try-catch block when the volunteer attempts
												//to join a job grade that is not available.
		}
		
		if (okToAdd && jobExists && openGrade) {
			// If everything is okay, we add the Volunteer to the Job’s Volunteer List,
			// increment the grade slot, and return.

			j.getVolunteerList().add(theVolunteer);
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
	
	/**
	 * Update the list of managed parks of a Park Manager.
	 * @author Taylor Gorman
	 */
	public void updateParkList(String theEmail, List<Park> theManagedParks) {
		List<ParkManager> myManagerList = myUserList.getParkManagerCopyList();
		
		for(ParkManager manager : myManagerList) {
			if(manager.getEmail().equals(theEmail)) {
				manager.setManagedParks(theManagedParks);
			}
		}
		
	}

}
