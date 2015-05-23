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

	//Constructor
	public ParkManager(String theEmail, String theFirstName, String theLastName, List<String> theParkList) {
		super(theFirstName, theLastName, theEmail);
		super.setUserType("ParkManager");
		
		//theParkList is an Unmodifiable List, so we cannot cast it to ArrayList. So we copy it over instead.
		List<String> copiedParks = new ArrayList<String>();
		copiedParks.addAll(theParkList);
		this.myManagedParks = copiedParks;
	}
	
	
	
	/*==============*
	 * Job Creation *
	 *==============*/
	
	public boolean createJob(String thePark, int theLightSlots, int theMediumSlots, int theHeavySlots,
			GregorianCalendar theStartDate, GregorianCalendar theEndDate) {
		
		ArrayList<ArrayList<String>> volunteerList = new ArrayList<>();
		int jobID = myPollster.getNextJobID();
		String startDateString = calendarToArgument(theStartDate);
		String endDateString = calendarToArgument(theEndDate);
		
		Job addJob = new Job(jobID, thePark, theLightSlots, theMediumSlots, theHeavySlots,
				startDateString, endDateString, super.getEmail(), volunteerList);
		
		boolean lessThanTwoDays = getDays(theStartDate, theEndDate) < 2;		
		boolean addedFlag = false;
		
		if(lessThanTwoDays) {
			addedFlag = mySchedule.receiveJob(addJob);
		}
		
		return addedFlag;
		
		
	}
	
	/**
	 * Returns the number of days between two GregorianCalendars.
	 */
	private int getDays(GregorianCalendar theStartDate, GregorianCalendar theEndDate) {
		long timeDifference = Math.abs(theEndDate.getTimeInMillis() - theStartDate.getTimeInMillis());
		return (int) (timeDifference / 86400000l);
	}
	
	
	
	/*=====================*
	 * Getters and Setters *
	 *=====================*/	
	
	public void setManagedParks(List<String> theManagedParks) {
		this.myManagedParks = theManagedParks;
	}
	
	public List<String> getManagedParks() {
		return Collections.unmodifiableList(myManagedParks);
	}	
	
	
	public Object[][] getJobArray() {
		
		ArrayList<Job> jobs = (ArrayList<Job>) myPollster.getManagerJobs(this);
		
		Object[][] jobArray = new Object[jobs.size()][7];
		int jobNumber = 0;
		
		for(Job thisJob : jobs) {
			jobArray[jobNumber][0] = thisJob.getJobID();
			jobArray[jobNumber][1] = thisJob.getPark();
			jobArray[jobNumber][2] = thisJob.getLightCurrent() + "/" + thisJob.getLightMax();
			jobArray[jobNumber][3] = thisJob.getMediumCurrent() + "/" + thisJob.getMediumMax();
			jobArray[jobNumber][4] = thisJob.getHeavyCurrent() + "/" + thisJob.getHeavyMax();
			jobArray[jobNumber][5] = calendarToString(thisJob.getStartDate());
			jobArray[jobNumber][6] = calendarToString(thisJob.getEndDate());
			
			jobNumber++;
		}
		
		return jobArray;
	}
	
	public String getVolunteerString(int theJobID) {
		String volunteerString = "";

		List<Volunteer> volunteerList = myPollster.getJobVolunteerList(theJobID);
		
		for(Volunteer volunteer : volunteerList) {
			volunteerString += volunteer.getFirstName() + " " + volunteer.getLastName() + "\n";
			volunteerString += volunteer.getEmail() + "\n";
			volunteerString += myPollster.getVolunteerGrade(theJobID, volunteer.getEmail()) + "\n\n";
		}
		
		return volunteerString;
	}
	
	
	private String calendarToString(GregorianCalendar theCalendar) {
		String returnString = "";
		returnString += theCalendar.get(Calendar.MONTH) + "/";
		returnString += theCalendar.get(Calendar.DAY_OF_MONTH) + "/";
		returnString += theCalendar.get(Calendar.YEAR);

		return returnString;
	}
	
	/*
	 * A slight variation on calendarToString. I am so sorry for doing something this stupid but it works.
	 */
	private String calendarToArgument(GregorianCalendar theCalendar) {
		//TODO see if there's some more elegant way to do this with calendarToString or something.
		String returnString = "";
		
		if(theCalendar.get(Calendar.MONTH) < 10) {
			returnString += "0";
		}		
		returnString += theCalendar.get(Calendar.MONTH);
		
		if(theCalendar.get(Calendar.DAY_OF_MONTH) < 10) {
			returnString += "0";
		}
		returnString += theCalendar.get(Calendar.DAY_OF_MONTH);
		
		returnString += theCalendar.get(Calendar.YEAR);
		
		return returnString;
	}
	
}