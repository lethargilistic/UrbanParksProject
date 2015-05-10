package model;

import java.util.ArrayList;
import java.util.List;

//TODO: we definitely need getters for the other classes, like Park and Job.
/**
 * A class to poll our data-containing classes on behalf of our users.
 * 
 * @author Mike Overby
 * @author Reid Thompson
 * @version 5.10.2015
 */
public class DataPollster {

	/**
	 * A reference to a list of jobs in memory.
	 */
	private JobList myJobList;
	
	private UserList myUserList;
	
	/**
	 * Return the next available Job ID to be used during the creation of a new job.
	 */
	public static int getNextJobID() {
		return Job.nextJobID++;
	}
	
	public DataPollster(JobList theJoblist, UserList theUserList)
	{
		myJobList = theJoblist;
		this.myUserList = theUserList;
	}
	
	//TODO: test all possible conflicts
		//Don't have a job they've already signed up for on that day
		//Job has no room.
		//TODO:The job's start date has not passed.
			//Not sure where we're storing the current date. Do we use the System time?
	public List<Job> getPendingJobs(Volunteer theVolunteer) {
		//USER STORY 2

		//Called by Volunteer.viewUpcomingJobs()

		//Calls JobList.getCopyList() to get a copy of myJobList
			//TODO: clear this up with the others.
			//It already has a reference to the joblist, though?
		
		//Check through myJobList and select all Jobs that the Volunteer can sign
		//up for

		List<Job> applicableJobs = new ArrayList<>();
		List<Job> volsJobs = getVolunteerJobs(theVolunteer);
		List<Job> jobs = myJobList.getCopyList();
		for (Job j : jobs)
		{
			//TODO, need to review the requirements for signing up for job
			int i = volsJobs.indexOf(j);
			if (i != -1) //if volunteer is not signed up for the job already
			{
				//Job has no room.
				if(!j.hasRoom())
				{
					continue;
				}
			}
			
			//Test for conflicts with jobs the volunteer has already signed up for.
			boolean dayConflict = false;
			for (Job anyVjob: volsJobs)
			{
				//Don't have a job they've already signed up for on that day
				if (anyVjob.getStartDate().equals(j.getStartDate()) 
						|| anyVjob.getStartDate().equals(j.getEndDate())
						|| anyVjob.getEndDate().equals(j.getStartDate())
						|| anyVjob.getEndDate().equals(j.getEndDate()))
				{
					dayConflict = true;
					break;
				}
			}
			if (dayConflict)
			{
				continue;
			}
			
			applicableJobs.add(j);
		}
		//Make a copy of each selected Job, and put it into a Job List.

		//May or may not hide Jobs sharing the date of a Job that the Volunteer has 
		//already signed up for

		//Return the list of copied Jobs to the Volunteer
		return applicableJobs;
	}

	public List<Job> getVolunteerJobs(Volunteer theVolunteer) {
		//USER STORY 4

		//Called by Volunteer.viewMyJobs()

		//Calls JobList.getCopyList() to get a copy of myJobList
			//It already has a reference to the joblist

		// Create a new list of Jobs with copies of the Jobs from Schedule�s master 
		// list
		List<Job> jobsSignedUpFor = new ArrayList<Job>();
		
		//Checks through myJobList and finds all jobs for which the volunteer has
		// signed up for
		for (Job j : myJobList.getCopyList())
		{
			//TODO: Should I get the job's list via a method?
			if (j.myVolunteerList.contains(theVolunteer))
			{
				jobsSignedUpFor.add(j);
			}
		}

		// Return new list of copied Jobs
		return jobsSignedUpFor;
	}

	/**
	 * Return a list of all jobs associated with a given ParkManager.
	 * @author Taylor Gorman
	 */
	public List<Job> getManagerJobs(ParkManager theManager){
		List<Job> jobReturnList = new ArrayList<Job>();
		List<Park> managedParks = theManager.getManagedParks();

		//Select all jobs in JobList with the same name as a Park that ParkManager manages.
		for (Job job : myJobList.getCopyList())	{
			String jobParkName = job.getPark().getName();
			
			for(Park park : managedParks) {						
				String managedParkName = park.getName();
				
				if(jobParkName.equals(managedParkName)) {
					jobReturnList.add(job);
				}
			}
		}

		return jobReturnList;
	}
	
	/**
	 * This method returns a list of all of the jobs.
	 * It is called when volunteer wants to see a list of jobs so that he can sign up.
	 */
	public List<Job> getAllJobs() {
		return new ArrayList<Job>(myJobList.getCopyList());
	}
	
	

	public List<String> getVolunteerList(int theJobID) {
		//USER STORY 6
		// Called by ParkManager.viewJobVolunteers()

		//Calls JobList.getCopyList() to get a copy of myJobList

		//Create an empty list for returning.
		List<String> retVols = new ArrayList<>();
		// Check through the myJobList and select the Job with that jobID
		for (Job j : myJobList.getCopyList())
		{
			if (j.getJobID() == theJobID)
			{
				// Use that Job object to get a copied list of associated Volunteers
				retVols.addAll(j.myVolunteerList);
			}
		}
		
		// Return that copied list of Volunteers
		return retVols;
	}
	
	/**
	 * Check the e-mail address of a user logging in to see if they exist in the system.
	 */
	public boolean checkEmail(String theEmail) {
		//TODO
		return true;
	}

	/**
	 * Return the user type associated with the e-mail as a String.
	 */
	public String getUserType(String theEmail) {
		//in this method, search each of the three lists in myUserList.
				//depending on which of the lists theEmail is found in, return the related string.
		
		String user = "";
		
		List<Volunteer> vList = myUserList.getVolunteerCopyList();
		for (Volunteer v : vList) {
			if (v.getEmail().equals(theEmail)) {
				user = "Volunteer";
			}
		}
		
		List<ParkManager> pList = myUserList.getParkManagerCopyList();
		for (ParkManager p: pList) {
			if (p.getEmail().equals(theEmail)) {
				user = "ParkManager";
			}
		}
		
		List<Administrator> aList = myUserList.getAdministratorCopyList();
		for (Administrator a : aList) {
			if (a.getEmail().equals(theEmail)) {
				user = "Administrator";
			}
		}

		return user;
		
		
	}

	/**
	 * Return the Park List associated with a ParkManager's e-mail.
	 * @author Taylor Gorman
	 */
	public List<Park> getParkList(String theEmail) {		
		List<ParkManager> managerList = myUserList.getParkManagerCopyList();
		
		for(ParkManager manager : managerList) {
			if(manager.getEmail().equals(theEmail)) {
				return manager.getManagedParks();
			}
		}
		return new ArrayList<Park>();
	}

	/**
	 * Given a volunteer's email, construct the Volunteer and return it.
	 * 
	 * @param theVolunteerEmail is the email address of the Volunteer.
	 * @return a new Volunteer object with the given email address.
	 * @author Taylor Gorman
	 */
	public Volunteer getVolunteer(String theVolunteerEmail) { // Reid: why do we need this method? where is it used?
		Volunteer defaultVolunteer = new Volunteer(theVolunteerEmail); //Default case if the Park Volunteer is not found.
		List<Volunteer> volunteerCopyList = myUserList.getVolunteerCopyList();
		
		for(Volunteer volunteer : volunteerCopyList) {
			if(volunteer.getEmail().equals(theVolunteerEmail)) {
				return volunteer;
			}
		}

		return defaultVolunteer;
	}
	
	/**
	 * Given a park manager's email, construct the Park Manager and return it.
	 * 
	 * @param theParkManagerEmail is the email address of the Park Manager.
	 * @return a new ParkManager object with the given email address.
	 * @author Taylor Gorman
	 */
	public ParkManager getParkManager(String theParkManagerEmail) {
		
		ParkManager defaultManager = new ParkManager(theParkManagerEmail); //Default case if the Park Manager is not found.
		List<ParkManager> managerCopyList = myUserList.getParkManagerCopyList();
		
		for(ParkManager manager : managerCopyList) {
			if(manager.getEmail().equals(theParkManagerEmail)) {
				return manager;
			}
		}

		return defaultManager;
	}
	
	/**
	 * Given a administrator's email, construct the Administrator and return it.
	 * 
	 * @param theAdministratorEmail is the email address of the Administrator.
	 * @return a new Administrator object with the given email address.
	 * @author Taylor Gorman
	 */
	public Administrator getAdministrator(String theAdministratorEmail) { // Reid: why do we need this method? where is it used?
		Administrator defaultAdministrator = new Administrator(theAdministratorEmail); //Default case if the Park Administrator is not found.
		List<Administrator> administratorCopyList = myUserList.getAdministratorCopyList();
		
		for(Administrator administrator : administratorCopyList) {
			if(administrator.getEmail().equals(theAdministratorEmail)) {
				return administrator;
			}
		}

		return defaultAdministrator;
	}
	
	/**
	 * Returns the UserList field.
	 * 
	 * @return the UserList field.
	 */
	public UserList getUserList() {
		return this.myUserList;
	}
}
