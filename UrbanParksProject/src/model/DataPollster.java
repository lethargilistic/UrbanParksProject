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
	public int getNextJobID() {
		return myJobList.getNumberJobs();
	}
	
	public DataPollster(JobList theJoblist, UserList theUserList)
	{
		myJobList = theJoblist;
		this.myUserList = theUserList;
	}
	
	//TODO: test all possible conflicts
		//Don't have a job they've already signed up for on that day
		//Job has no room.
			//Not sure where we're storing the current date. Do we use the System time?
	public List<Job> getPendingJobs(Volunteer theVolunteer) {
		//USER STORY 2

		//Called by Volunteer.viewUpcomingJobs()
		
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
			if (j.getVolunteerList().contains(theVolunteer))
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
		List<String> managedParks = theManager.getManagedParks();

		//Select all jobs in JobList with the same name as a Park that ParkManager manages.
		for (Job job : myJobList.getCopyList())	{
			String jobParkName = job.getPark();
			
			for(String managedPark : managedParks) {						
				
				if(jobParkName.equals(managedPark)) {
					jobReturnList.add(job);
				}
			}
		}

		return jobReturnList;
	}
	
	public Job getJobCopy(int theJobID) {
		for(Job job : getJobListCopy()) {
			if(job.getJobID() == theJobID) return job;
		}
		
		return null;
	}
	
	
	/**
	 * This method returns a list of all of the jobs.
	 * It is called when volunteer wants to see a list of jobs so that he can sign up.
	 */
	public List<Job> getJobListCopy() {
		return new ArrayList<Job>(myJobList.getCopyList());
	}
	
	
	/**
	 * Return a list of all Volunteers for a given job.
	 */
	public List<Volunteer> getJobVolunteerList(int theJobID) {
		
		List<Volunteer> returnList = new ArrayList<Volunteer>();
		Job job = myJobList.getJobCopy(theJobID);
		
		if(job != null) {
			for(ArrayList<String> volunteer : job.getVolunteerList()) {
				String volunteerEmail = volunteer.get(0);
				returnList.add(getVolunteer(volunteerEmail));
			}
		}
		
		return returnList;
	}
	
	/**
	 * Check the e-mail address of a user logging in to see if they exist in the system.
	 * @author Reid Thompson
	 */
	public boolean checkEmail(String theEmail) {
		boolean result = false;
		List<Volunteer> vols = myUserList.getVolunteerCopyList();

		for (int i = 0; i < vols.size(); i++) {
			final Volunteer v = vols.get(i);
			if (v.getEmail().equals(theEmail)) {
				result = true;
				break;
			}
		}

		if (!result) {
			List<ParkManager> mngrs = myUserList.getParkManagerCopyList();
			for (int i = 0; i < mngrs.size(); i++) {
				final ParkManager pm = mngrs.get(i);
				if (pm.getEmail().equals(theEmail)) {
					result = true;
					break;
				}
			}
		}

		if (!result) {
			List<Administrator> admins = myUserList.getAdministratorCopyList();
			for (int i = 0; i < admins.size(); i++) {
				final Administrator a = admins.get(i);
				if (a.getEmail().equals(theEmail)) {
					result = true;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * Return the user type associated with the e-mail as a String.
	 * @author Taylor Gorman
	 */
	public String getUserType(String theEmail) {
		String userType = "";
		
		for(Volunteer volunteer : myUserList.getVolunteerCopyList()) {
			if(volunteer.getEmail().equals(theEmail)) userType = "Volunteer";
		}
		
		for(ParkManager manager : myUserList.getParkManagerCopyList()) {
			if(manager.getEmail().equals(theEmail)) userType = "ParkManager";
		}
		

		for(Administrator administrator : myUserList.getAdministratorCopyList()) {
			if(administrator.getEmail().equals(theEmail)) userType = "Administrator";

		}


		return userType;

	}

	/**
	 * Return the Park List associated with a ParkManager's e-mail.
	 * @author Taylor Gorman
	 */
	public List<String> getParkList(String theEmail) {		
		List<ParkManager> managerList = myUserList.getParkManagerCopyList();
		
		for(ParkManager manager : managerList) {
			if(manager.getEmail().equals(theEmail)) {
				return manager.getManagedParks();
			}
		}
		return new ArrayList<String>();
	}

	// Reid: we should be returning null and handling it wherever this method is called.
	// Reid: we shouldn't be constructing a "default" volunteer, in my opinion.
	
	/**
	 * Given a volunteer's email, construct the Volunteer and return it.
	 * 
	 * @param theVolunteerEmail is the email address of the Volunteer.
	 * @return a new Volunteer object with the given email address.
	 * @author Taylor Gorman
	 */
	public Volunteer getVolunteer(String theVolunteerEmail) { 		   // Reid: why do we need this method? where is it used?
//		Volunteer defaultVolunteer = new Volunteer(theVolunteerEmail); // Default case if the Park Volunteer is not found.
		Volunteer volToReturn = null;
		List<Volunteer> volunteerCopyList = myUserList.getVolunteerCopyList();
		
		for(Volunteer volunteer : volunteerCopyList) {
			if(volunteer.getEmail().equals(theVolunteerEmail)) {
				volToReturn = volunteer;
			}
		}

		return volToReturn;
	}
	
	/**
	 * Given a park manager's email, construct the Park Manager and return it.
	 * 
	 * @param theParkManagerEmail is the email address of the Park Manager.
	 * @return a new ParkManager object with the given email address.
	 * @author Taylor Gorman
	 */
	public ParkManager getParkManager(String theParkManagerEmail) {
		
		List<ParkManager> managerCopyList = myUserList.getParkManagerCopyList();
		
		for(ParkManager manager : managerCopyList) {
			if(manager.getEmail().equals(theParkManagerEmail)) {
				//Manager found, so we copy the data over and return it.
				String firstName = manager.getFirstName();
				String lastName = manager.getLastName();
				List<String> parkList = manager.getManagedParks();
				return new ParkManager(theParkManagerEmail, firstName, lastName, parkList);
			}
		}

		return null;
	}
	
	// Reid: again, same thing - I see no reason to have "default" users.
	// Reid: let's just return null and have the caller handle that case...
	
	/**
	 * Given a administrator's email, construct the Administrator and return it.
	 * 
	 * @param theAdministratorEmail is the email address of the Administrator.
	 * @return a new Administrator object with the given email address.
	 * @author Taylor Gorman
	 */
	public Administrator getAdministrator(String theAdministratorEmail) { 			   // Reid: why do we need this method? where is it used?
//		Administrator defaultAdministrator = new Administrator(theAdministratorEmail); // Default case if the Park Administrator is not found.
		Administrator adminToReturn = null;
		List<Administrator> administratorCopyList = myUserList.getAdministratorCopyList();
		
		for(Administrator administrator : administratorCopyList) {
			if(administrator.getEmail().equals(theAdministratorEmail)) {
				adminToReturn = administrator;
			}
		}

		return adminToReturn;
	}
	
	/**
	 * Returns the UserList field.
	 * 
	 * @return the UserList field.
	 */
	public UserList getUserList() {
		return this.myUserList;
	}
	
	/**
	 * Return the work grade of a Volunteer for a given job.
	 * @author Taylor Gorman
	 */
	public String getVolunteerGrade(int theJobID, String theVolunteerEmail) {
		for(ArrayList<String> volunteer : myJobList.getJobCopy(theJobID).getVolunteerList()) {
			if(volunteer.get(0).equals(theVolunteerEmail)) return volunteer.get(1);
		}
		
		return null;
	}
}
