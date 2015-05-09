package model;

import java.util.ArrayList;
import java.util.List;

//TODO: we definitely need getters for the other classes, like Park and Job.
public class DataPollster {

	/**
	 * A reference to a list of jobs in memory.
	 */
	private JobList myJobList;
	
	private UserList myUserList;
	
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

		// Create a new list of Jobs with copies of the Jobs from Schedule’s master 
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

	public List<Job> getManagerJobs(ParkManager theManager){
		//USER STORY 5

		//Called by ParkManager.viewUpcomingJobs()

		//Calls JobList.getCopyList() to get a copy of myJobList
			//It already has a reference to the joblist
		
		//Create a new list of jobs with copies of the Jobs from Schedule’s 
		//myJobList

		List<Job> jobsManaging = new ArrayList<Job>();

		//Check through myJobList and select all Jobs in all park
		//managed by the PM.
		for (Job j : myJobList.getCopyList())
		{
			if (theManager.getManagedParks().contains(j.getPark()))
			{
				jobsManaging.add(j);
			}
		}
		
		//Return new list of copied Jobs
		return jobsManaging;
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
		// TODO Either "Volunteer", "ParkManager", or "Administrator"
		return "ParkManager";
	}

	/**
	 * Return the Park List associated with a ParkManager's e-mail.
	 */
	public List<Park> getParkList(String theEmail) {
		// TODO The following code is NOT an example of how this is to be implemented. It just forces a test case.
		
		
		//For testing purposes only:
		ArrayList<Park> myParkList = new ArrayList<Park>();
		Park myPark = new Park("Bobcat Park", "Hugo", 98335);
		Park myPark2 = new Park("Seahurt Park", "Burien", 98106);
		
		myParkList.add(myPark);
		myParkList.add(myPark2);
		return myParkList;
	}

	/**
	 * Given a volunteer's email, construct the Volunteer and return it.
	 * @param volunteerEmail
	 * @return
	 */
	public Volunteer getVolunteer(String volunteerEmail) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ParkManager getParkManager(String parkManagerEmail) {
		//TODO
		return null;
	}
}
