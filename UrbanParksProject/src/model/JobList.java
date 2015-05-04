package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is used to create lists for all the pending jobs and all the
 * volunteers who have created accounts.
 * @author Arsh
 * @version May 1
 *
 */
public class JobList {
	
	/**
	 * This is a list of all the pending jobs.
	 */
	private List<Job> myJobList;
	
	/**
	 * This is a list of volunteers who have created accounts with the program.
	 */
	private List<Volunteer> myVolunteerList;

	
	/**
	 * This is a constructor.
	 */
	public JobList() {
		myJobList = new ArrayList<Job>();
		myVolunteerList = new ArrayList<Volunteer>();
		
	}
	
	
	/**
	 * This method should be called by Sorter class.
	 * 
	 * It returns a copy of myJobList.
	 * @return a copy of the jobs list.
	 */
	public List<Job> getCopyList() {
		return new ArrayList<Job>(myJobList);
	}    
	
	/**
	 * This method should be called by Schedule class.
	 * 
	 * It returns an actual reference to the jobs in myJobList.
	 * @return the actual list of jobs.
	 */
	public List<Job> getJobList() { 
		return myJobList;
    }
	
	/**
	 * NOTE: we didnt have this on our pseudocode but I thought we should add it so that
	 * we can keep the volunteer list private.
	 * 
	 * This method returns an actual reference to myVolunteerList.
	 * @return a list.
	 */
	public List<Volunteer> getVolunteerList() {
		return myVolunteerList;
	}
	
	/**
	 * NOTE: we didnt have this on our pseudocode but I thought we should add it so that
	 * we can keep the volunteer list private.
	 * 
	 * This method returns a copy of myVolunteerList.
	 * 
	 * @return a list.
	 */
	public List<Volunteer> getCopyVList() {
		return new ArrayList<Volunteer>(myVolunteerList);
	}
}
