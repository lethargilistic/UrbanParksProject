package model;

import java.util.ArrayList;
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
	 * This is a constructor.
	 */
	public JobList() {
		myJobList = new ArrayList<Job>();
		
		
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
	

}
