import java.util.ArrayList;
import java.util.List;

//TODO: we definitely need getters for the other classes, like Park and Job.
public class DataPollster {

	/**
	 * A reference to a list of jobs in memory.
	 */
	List<Job> myJobList;
	
	DataPollster(List<Job> theJoblist)
	{
		myJobList = theJoblist;
	}
	
	//TODO: test all possible conflicts
		//Don't have a job they've already signed up for on that day
		//Job has no room.
		//TODO:The job's start date has not passed.
			//Not sure where we're storing the current date. Do we use the System time?
	List<Job> getPendingJobs(Volunteer theVolunteer) {
		//USER STORY 2

		//Called by Volunteer.viewUpcomingJobs()

		//Calls JobList.getCopyList() to get a copy of myJobList
			//TODO: clear this up with the others.
			//It already has a reference to the joblist, though?
		
		//Check through myJobList and select all Jobs that the Volunteer can sign
		//up for

		List<Job> applicableJobs = new ArrayList<>();
		List<Job> volsJobs = getVolunteerJobs(theVolunteer);
		for (Job j : myJobList)
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
				//TODO:The job's start date has not passed.
				
				//Test for conflicts with jobs the volunteer has already signed up for.
				for (Job anyVjob: volsJobs)
				{
					//Don't have a job they've already signed up for on that day
					//TODO: boolean testDate() method in Job?
					if (anyVjob.myStartDate.equals(j.myStartDate) 
							|| anyVjob.myStartDate.equals(j.myEndDate)
							|| anyVjob.myEndDate.equals(j.myStartDate)
							|| anyVjob.myEndDate.equals(j.myEndDate))
					{
						break;
					}
				}
			}
		}
		//Make a copy of each selected Job, and put it into a Job List.

		//May or may not hide Jobs sharing the date of a Job that the Volunteer has 
		//already signed up for

		//Return the list of copied Jobs to the Volunteer
		return applicableJobs;
	}

	List<Job> getVolunteerJobs(Volunteer theVolunteer) {
		//USER STORY 4

		//Called by Volunteer.viewMyJobs()

		//Calls JobList.getCopyList() to get a copy of myJobList
			//It already has a reference to the joblist

		// Create a new list of Jobs with copies of the Jobs from Schedule’s master 
		// list
		List<Job> jobsSignedUpFor = new ArrayList<Job>();
		
		//Checks through myJobList and finds all jobs for which the volunteer has
		// signed up for
		for (Job j : myJobList)
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

	List<Job> getManagerJobs(ParkManager theManager){
		//USER STORY 5

		//Called by ParkManager.viewUpcomingJobs()

		//Calls JobList.getCopyList() to get a copy of myJobList
			//It already has a refrence to the joblist
		
		//Create a new list of jobs with copies of the Jobs from Schedule’s 
		//myJobList

		List<Job> jobsManaging = new ArrayList<Job>();

		//Check through myJobList and select all Jobs in all park
		//managed by the PM.
		for (Job j : myJobList)
		{
			if (theManager.myManagedParks.contains(j.myPark))
			{
				jobsManaging.add(j);
			}
		}
		
		//Return new list of copied Jobs
		return jobsManaging;
	}

	List<Volunteer> getVolunteerList(int theJobID) {
		//USER STORY 6
		// Called by ParkManager.viewJobVolunteers()

		//Calls JobList.getCopyList() to get a copy of myJobList

		//Create an empty list for returning.
		List<Volunteer> retVols = new ArrayList<>();
		// Check through the myJobList and select the Job with that jobID
		for (Job j : myJobList)
		{
			if (j.myJobID == theJobID)
			{
				// Use that Job object to get a copied list of associated Volunteers
				retVols.addAll(j.myVolunteerList);
			}
		}
		
		// Return that copied list of Volunteers
		return retVols;
	}
}
