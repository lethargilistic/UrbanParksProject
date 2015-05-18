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
	
	public DataPollster(JobList theJoblist, UserList theUserList) {
		myJobList = theJoblist;
		this.myUserList = theUserList;
	}
	
	//TODO: test all possible conflicts
	//TODO: Not sure where we're storing the current date. Do we use the System time? - Gregorian Calendar, I think...
	
	/**
	 * Checks if job has no room or if they don't have a job that they've already signed up for on that day.
	 * 
	 * Returns a List of Jobs that are pending for theVolunteer.
	 * 
	 * @author Mike Overby - initial implementation.
	 * @param theVolunteer
	 * @return
	 */
	public List<Job> getPendingJobs(Volunteer theVolunteer) {
		//Check through myJobList and select all Jobs that the Volunteer can sign up for
		List<Job> applicableJobs = new ArrayList<>();
		List<Job> volsJobs = getVolunteerJobs(theVolunteer);
		List<Job> jobs = myJobList.getCopyList();
		for (Job j : jobs) {
			//TODO, need to review the requirements for signing up for job
			int i = volsJobs.indexOf(j);
			if (i != -1) { // if volunteer is not signed up for the job already
				// Job has no room.
				if(!j.hasRoom()) {
					continue;
				}
			}
			
			// Test for conflicts with jobs the volunteer has already signed up for.
			boolean dayConflict = false;
			for (Job anyVjob: volsJobs) {
				// Don't have a job they've already signed up for on that day
				if (anyVjob.getStartDate().equals(j.getStartDate()) 
						|| anyVjob.getStartDate().equals(j.getEndDate())
						|| anyVjob.getEndDate().equals(j.getStartDate())
						|| anyVjob.getEndDate().equals(j.getEndDate())) {
					dayConflict = true;
					break;
				}
			}
			if (dayConflict) {
				continue;
			}
			// Make a copy of each selected Job, and put it into a Job List.
			applicableJobs.add(j);
		}
		
		return applicableJobs; //Return the list of copied Jobs
	}

	/**
	 * For user story 4. 
	 * 
	 * @param theVolunteer
	 * @return
	 */
	public List<Job> getVolunteerJobs(Volunteer theVolunteer) {
		// Create a new list of Jobs with copies of the Jobs from Schedule's master list
		List<Job> jobsSignedUpFor = new ArrayList<Job>();
		
		//Checks through myJobList and finds all jobs for which the volunteer has signed up for
		for (Job j : myJobList.getCopyList()) {
			if (j.getVolunteerList().contains(theVolunteer)) {
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

	/**
	 * Returns a copy of the Job matching theJobID
	 * 
	 * @author Reid Thompson - changed to have only one exit point.
	 * @param theJobID
	 * @return a copy of the Job matching theJobID or null otherwise.
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
	 * This method returns a list of all of the jobs.
	 * It is called when volunteer wants to see a list of jobs so that he/she can sign up.
	 */
	public List<Job> getJobListCopy() {
		return myJobList.getCopyList();
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
	 * @return null if there is no user associated with this email in the system,
	 * and "ParkManager", "Volunteer", or "Administrator" otherwise.
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
	 * Return the Park List associated with a ParkManager's e-mail.
	 * 
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
	
	/**
	 * Given a volunteer's email, construct the Volunteer and return it.
	 * 
	 * @param theVolunteerEmail is the email address of the Volunteer.
	 * @return a new Volunteer object with the given email address.
	 * @author Taylor Gorman
	 */
	public Volunteer getVolunteer(String theVolunteerEmail) { // Reid: Removed "default" volunteer from being returned.
		Volunteer volToReturn = null;
		List<User> volunteerCopyList = myUserList.getVolunteerListCopy();
		
		for(User volunteer : volunteerCopyList) {
			if(volunteer.getEmail().equals(theVolunteerEmail)) {
				final String firstName = volunteer.getFirstName();
				final String lastName = volunteer.getLastName();
				final String email = volunteer.getEmail();
				
				volToReturn = new Volunteer(email, firstName, lastName);
			}
		}

		return volToReturn;
	}
	
	/**
	 * Given a park manager's email, construct the Park Manager and return it.
	 * 
	 * @param theParkManagerEmail is the email address of the Park Manager.
	 * @return a new ParkManager object with the given email address.
	 * 
	 * @author Taylor Gorman
	 */
	public ParkManager getParkManager(String theParkManagerEmail) {
		ParkManager prkMngrToReturn = null;
		
		List<User> managerCopyList = myUserList.getParkManagerListCopy();
		
		for(User manager : managerCopyList) {
			if(manager.getEmail().equals(theParkManagerEmail)) {
				//Manager found, so we copy the data over and return it.
				String firstName = manager.getFirstName();
				String lastName = manager.getLastName();
				List<String> parkList = ((ParkManager) manager).getManagedParks();
				prkMngrToReturn = new ParkManager(theParkManagerEmail, firstName, lastName, parkList);
			}
		}

		return prkMngrToReturn;
	}
	
	// TODO: Reid stopped working here!!!
	
	/**
	 * Given a administrator's email, construct the Administrator and return it.
	 * 
	 * @param theAdministratorEmail is the email address of the Administrator.
	 * @return a new Administrator object with the given email address.
	 * @author Taylor Gorman
	 */
	public Administrator getAdministrator(String theAdministratorEmail) { // Reid: Removed "default" admin from being returned.
		Administrator adminToReturn = null;
		List<User> administratorCopyList = myUserList.getAdministratorListCopy();
		
		for(User administrator : administratorCopyList) {
			if(administrator.getEmail().equals(theAdministratorEmail)) {
				adminToReturn = (Administrator) administrator;
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
