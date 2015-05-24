package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * ParkManager can add new jobs to the schedule, view all jobs for the parks they manage,
 * and view all volunteers for a given job.
 * 
 * @author Taylor Gorman
 * @author Reid Thompson
 * @version 9 May 2015
 *
 */
public class ParkManager extends User {
	
	//Class Variables
	private Schedule mySchedule = Schedule.getInstance();
	private DataPollster myPollster = DataPollster.getInstance();
	private List<String> myManagedParks;	
	private String myEmail = super.getEmail();

	//Constructor
	public ParkManager(String theEmail, String theFirstName, String theLastName, List<String> theParkList) {
		super(theFirstName, theLastName, theEmail);
		super.setUserType("ParkManager");
		
		//theParkList is an Unmodifiable List, so we cannot cast it to ArrayList. So we copy it over instead.
		List<String> copiedParks = new ArrayList<String>();
		copiedParks.addAll(theParkList);
		this.myManagedParks = copiedParks;
	}
	
	
	
	
	/*======*
	 * Jobs *
	 *======*/
	
	
	public List<Job> getJobs() {
		return myPollster.getManagerJobs(myEmail);
	}
	
	public boolean addJob(Job theJob) {
		return mySchedule.receiveJob(theJob);
	}
	
	public int getNewJobID() {
		return myPollster.getNextJobID();
	}
	
	public boolean isManagerOfJob(int theJobID) {
		boolean containsJob = false;		
		for(Job job : getJobs()) {
			if(job.getJobID() == theJobID) containsJob = true;
		}
		
		return containsJob;
	}
	
	
	
	
	/*=======*
	 * Parks *
	 *======*/
	
	
	public List<String> getManagedParks() {
		return Collections.unmodifiableList(myManagedParks);
	}	
	
	public void setManagedParks(List<String> theManagedParks) {
		this.myManagedParks = theManagedParks;
	}
	
	
	
	
	/*============*
	 * Volunteers *
	 *============*/
	
	public List<Volunteer> getJobVolunteerList(int theJobID) {
		List<Volunteer> volunteerList = new ArrayList<Volunteer>();
		
		if(isManagerOfJob(theJobID)) {
			volunteerList.addAll(myPollster.getJobVolunteerList(theJobID));
		}		
		return volunteerList;
	}
	
}