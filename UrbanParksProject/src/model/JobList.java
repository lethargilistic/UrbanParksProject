package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to create lists for all of the pending jobs.
 * @author Arshdeep Singh
 * @version 1 May 2015
 *
 */
public class JobList {
	
	private List<Job> myJobList;
	
	public JobList() {
		myJobList = new ArrayList<Job>();		
	}
	
	/**
	 * Return a copy of the list of jobs.
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
	
	public void setJobList(ArrayList<Job> theJobList) {
		this.myJobList = theJobList;
	}
	
	/**
	 * Return the number of jobs contained within JobList.
	 */
	public int getNumberJobs() {
		return myJobList.size();
	}
}
