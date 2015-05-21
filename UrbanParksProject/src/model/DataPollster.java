package model;

import java.util.ArrayList;
import java.util.List;

//TODO: we definitely need getters for the other classes, like Park and Job.
/**
 * A class to poll our data-containing classes on behalf of our users.
 * 
 * @author Mike Overby - initial implementation
 * @author Reid Thompson - added User functionality.
 * @version 5.10.2015
 */
public class DataPollster {

	//Class Variables
	private static DataPollster dataPollster = new DataPollster();
	private JobList myJobList;
	private UserList myUserList;
	
	//Constructor
	private DataPollster() {
		//Not to be instantiated; Singleton
	}
	
	//Instance
	public static DataPollster getInstance() {
		return dataPollster;
	}

	
	/*=================*
	 * JobList Subsets *
	 *=================*/
	
	/**
	 * Return a list of all Jobs that are visible to the Volunteer. This excludes:<br>
	 * 1. Jobs that have no room.<br>
	 * 2. Jobs that occur on the same date as a job the Volunteer is already signed up for.
	 * 
	 * @author Mike Overby - initial implementation.
	 * @author Taylor Gorman - refactor
	 */
	public List<Job> getPendingJobs(Volunteer theVolunteer) {
		List<Job> visibleJobs = new ArrayList<>();
		List<Job> allJobs = myJobList.getCopyList();
		
		//Check through myJobList and select all Jobs that the Volunteer can sign up ("visible").
		for (Job j : allJobs) {
			if(visibleToVolunteer(theVolunteer, j)) {
				visibleJobs.add(j);
			}
		}
		
		return visibleJobs; //Return the list of visible Jobs.
	}
	

	/**
	 * Return a list of all jobs that the Volunteer is signed up for.
	 */
	public List<Job> getVolunteerJobs(Volunteer theVolunteer) {
		List<Job> volunteerJobs = new ArrayList<Job>();
		
		//Select all Jobs in JobList that the Volunteer is signed up for.
		for (Job job : myJobList.getCopyList()) {
			if (job.getVolunteerList().contains(theVolunteer)) {
				volunteerJobs.add(job);
			}
		}

		return volunteerJobs;
	}

	/**
	 * Return a list of all jobs associated with the ParkManager.
	 * @author Taylor Gorman
	 */
	public List<Job> getManagerJobs(ParkManager theManager){		
		List<Job> managerJobs = new ArrayList<Job>();
		List<String> managedParks = theManager.getManagedParks();

		//Select all Jobs in JobList with the same name as a Park that ParkManager manages.
		for (Job job : myJobList.getCopyList())	{
			String jobParkName = job.getPark();
			
			for(String park : managedParks) {			
				if(jobParkName.equals(park)) {
					managerJobs.add(job);
				}
			}
		}

		return managerJobs;
	}
	


	/*==============*
	 * Job Handling *
	 *==============*/
	
	/**
	 * Return a copy of the Job matching theJobID 
	 * @author Reid Thompson
	 */
	public Job getJobCopy(int theJobID) {
		Job jobToReturn = null;
		
		for(Job job : getJobListCopy()) {
			if(job.getJobID() == theJobID) {
				jobToReturn = job;
			}
		}		
		return jobToReturn;
	}
	
	/**
	 * Return the next available Job ID to be used during the creation of a new job.
	 * @author Taylor Gorman
	 */
	public int getNextJobID() {
		int nextID = 0;
		
		for(Job job : getJobListCopy()) {
			if(job.getJobID() >= nextID) {
				nextID = job.getJobID() + 1;
			}
		}		
		return nextID;
	}
	
	/**
	 * Return a list of all Volunteers for a given job.
	 */
	public List<Volunteer> getJobVolunteerList(int theJobID) {
		
		List<Volunteer> volunteerList = new ArrayList<Volunteer>();
		Job job = myJobList.getJobCopy(theJobID);
		
		if(job != null) {
			for(ArrayList<String> volunteer : job.getVolunteerList()) {
				String volunteerEmail = volunteer.get(0);
				volunteerList.add(getVolunteer(volunteerEmail));
			}
		}
		
		return volunteerList;
	}
	
	
	
	
	/*===============*
	 * User Handling *
	 *===============*/
	
	/**
	 * Check the e-mail address of a user logging in to see if they exist in the system.
	 * @author Reid Thompson - initial implementation and changed for User functionality.
	 */
	public boolean checkEmail(String theEmail) {
		boolean result = false;
		List<User> allUsers = myUserList.getUserListCopy();

		for (int i = 0; i < allUsers.size(); i++) {
			final User u = allUsers.get(i);
			if (u.getEmail().equals(theEmail)) {
				result = true;
				break;
			}
		}

		return result;
	}

	/**
	 * Return the user type associated with the e-mail as a String.
	 * @author Taylor Gorman
	 * @author Reid Thompson - added User functionality
	 * @return Null if there is no user associated with this email in the system; "ParkManager", "Volunteer", or "Administrator" otherwise.
	 */
	public String getUserType(String theEmail) {
		String userType = null;
		
		for (User user : myUserList.getUserListCopy()) {
			if (user.getEmail().equals(theEmail)) {
				userType = user.getUserType();
			}
		}
		
		return userType;
	}
	
	/**
	 * Return the work grade of a Volunteer for a given job.
	 * @author Taylor Gorman - initial implementation
	 * @author Reid Thompson - single exit point of method.
	 */
	public String getVolunteerGrade(int theJobID, String theVolunteerEmail) {
		String gradeToReturn = null;
		
		for(ArrayList<String> volunteer : myJobList.getJobCopy(theJobID).getVolunteerList()) {
			if(volunteer.get(0).equals(theVolunteerEmail)) {
				gradeToReturn = volunteer.get(1);
			}
		}
		
		return gradeToReturn;
	}

	/**
	 * Return the Park List associated with a ParkManager's e-mail. 
	 * @author Taylor Gorman - initial implementation
	 * @author Reid Thompson - added User functionality.
	 */
	public List<String> getParkList(String theEmail) {		
		List<User> managerList = myUserList.getParkManagerListCopy();
		
		for(User manager : managerList) {
			if(manager.getEmail().equals(theEmail)) {
				return ((ParkManager) manager).getManagedParks();
			}
		}
		return new ArrayList<String>();
	}
	
	
	
	/*==============*
	 * User Getters *
	 *==============*/
	
	/**
	 * Given a volunteer's email, construct the Volunteer and return it.
	 * @author Taylor Gorman
	 */
	public Volunteer getVolunteer(String theVolunteerEmail) { // Reid: Removed "default" volunteer from being returned.
		Volunteer returnVolunteer = null;
		List<User> volunteerCopyList = myUserList.getVolunteerListCopy();
		
		for(User volunteer : volunteerCopyList) {
			if(volunteer.getEmail().equals(theVolunteerEmail)) {
				final String firstName = volunteer.getFirstName();
				final String lastName = volunteer.getLastName();
				final String email = volunteer.getEmail();
				
				returnVolunteer = new Volunteer(email, firstName, lastName);
			}
		}

		return returnVolunteer;
	}
	
	/**
	 * Given a park manager's email, construct the Park Manager and return it. 
	 * @author Taylor Gorman
	 */
	public ParkManager getParkManager(String theParkManagerEmail) {
		ParkManager returnManager = null;
		
		List<User> managerCopyList = myUserList.getParkManagerListCopy();
		
		for(User manager : managerCopyList) {
			if(manager.getEmail().equals(theParkManagerEmail)) {
				//Manager found, so we copy the data over and return it.
				String firstName = manager.getFirstName();
				String lastName = manager.getLastName();
				List<String> parkList = ((ParkManager) manager).getManagedParks();
				returnManager = new ParkManager(theParkManagerEmail, firstName, lastName, parkList);
			}
		}

		return returnManager;
	}
	
	/**
	 * Given an Administrator's email, construct the Administrator and return it.
	 * @author Taylor Gorman
	 */
	public Administrator getAdministrator(String theAdministratorEmail) {
		Administrator returnAdministrator = null;
		List<User> administratorCopyList = myUserList.getAdministratorListCopy();
		
		for(User administrator : administratorCopyList) {
			if(administrator.getEmail().equals(theAdministratorEmail)) {
				returnAdministrator = (Administrator) administrator;
			}
		}

		return returnAdministrator;
	}
	
	
	
	
	
	/*==============*
	 * List Getters *
	 *==============*/
	
	public List<Job> getJobListCopy() {
		return myJobList.getCopyList();
	}

	public List<User> getAllUserList() {
		return myUserList.getUserListCopy();
	}
	
	public List<User> getVolunteerListCopy() {
		return myUserList.getVolunteerListCopy();
	}
	
	public List<User> getManagerListCopy() {
		return myUserList.getParkManagerListCopy();
	}
	
	public List<User> getAdministratorListCopy() {
		return myUserList.getAdministratorListCopy();
	}
	
	public void setJobList(JobList theJobList) {
		this.myJobList = theJobList;
	}
	
	public void setUserList(UserList theUserList) {
		this.myUserList = theUserList;
	}
	
	
	
	/*================*
	 * Helper Classes *
	 *================*/
	
	/*
	 * Return true if the job is visible to the volunteer; false otherwise.
	 */
	private boolean visibleToVolunteer(Volunteer theVolunteer, Job theJob) {
		List<Job> volunteerJobs = getVolunteerJobs(theVolunteer);
		boolean isVisible = true;
		
		//Volunteer is already signed up for the job.
		if(volunteerJobs.indexOf(theJob) != -1) isVisible = false;
		
		//The job has no room.
		if(!theJob.hasRoom()) isVisible = false;
		
		//The Volunteer has already signed up for a job on the same date.
		for(Job volunteerJob : volunteerJobs) {
			if(shareSameDate(volunteerJob, theJob)) isVisible = false;
		}
		
		return isVisible;
	}
	
	/*
	 * Return true if the two jobs share a day; false otherwise.
	 */
	private boolean shareSameDate(Job job1, Job job2) {		
		boolean hasSameDay = false;
		
		if(job1.getStartDate().equals(job2.getStartDate())) hasSameDay = true;
		if(job1.getStartDate().equals(job2.getEndDate())) hasSameDay = true;
		if(job1.getEndDate().equals(job2.getStartDate())) hasSameDay = true;
		if(job1.getEndDate().equals(job2.getEndDate())) hasSameDay = true;
		
		return hasSameDay;		
	}	
}
