package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

/**
 * A console-based user interface for park managers to use.
 * @author Taylor Gorman
 * @version 6 May 2015
 */
public class ParkManagerUI {
	
	private Scanner in = new Scanner(System.in);
	
	/**
	 * Display the possible commands that the user could type. 
	 */
	public void listCommands() {
		System.out.println("\n1.new job     2.view jobs     3.view volunteers     4.quit\n");
	}
	
	/**
	 * Return the command that the user has typed.
	 */
	public String getCommand() {	
		return in.nextLine();
	}
	
	
	/**
	 * Prompt the user to enter the ID of a Job, and then return it.
	 */
	public int getJobID() {
		System.out.println("Please input the ID of the job whose volunteers you would"
				+ " like to view.");
		int myJobID = in.nextInt();
		return myJobID;
		
	}
	
	/**
	 * Prompt the user to enter how many volunteers they want for light grade work, then
	 * return it.
	 */
	public int getLightSlots() {		
		System.out.println("\nHow many volunteers do you want for light grade work?");
		int myLight = 0;
		
		if(in.hasNextInt()) { //The console UI has problems without this check.
			myLight = in.nextInt();
		}	
		
		return myLight;
	}

	/**
	 * Prompt the user to enter how many volunteers they want for medium grade work, then
	 * return it.
	 */
	public int getMediumSlots() {		
		System.out.println("\nHow many volunteers do you want for medium grade work?");
		int myMedium = in.nextInt();
		return myMedium;
	}
	
	/**
	 * Prompt the user to enter how many volunteers they want for heavy grade work, then
	 * return it.
	 */
	public int getHeavySlots() {	
		System.out.println("\nHow many volunteers do you want for heavy grade work?");
		int myHeavy = in.nextInt();
		return myHeavy;
	}
	
	/**
	 * Prompt the user to enter when they want to start the job, then return it.
	 * @return The start date in format ddmmyyyy
	 */
	public String getStartDate() {
		System.out.println("Please enter the start date of the job in the following format:"
				+ " mmddyyyy");
		in.nextLine(); // The console UI has problems without this line.
					   // Got rid of storing the output of this line as we never used the var.
		String myStartDate = in.nextLine();
		return myStartDate;
	}
	
	/**
	 * Prompt the user to enter when they want to end the job, then return it.
	 * @return The end date in format ddmmyyyy
	 */
	public String getEndDate() {
		System.out.println("Please enter the end date of the job in the following format:"
				+ " mmddyyyy");
		String myEndDate = in.nextLine();
		return myEndDate;
	}	
	
	/**
	 * Tell the user whether or not the job was successfully added.
	 */
	public void displayJobStatus(boolean addSuccess) {
		if(addSuccess){
			System.out.println("Job successfully added!");
		} else {
			System.out.println("Sorry, but the job could not be added.");
		}
	}
	
	
	/**
	 * Display all of the parks that the Park Manager manages in the console.
	 */
	public void displayParks(List<Park> myManagedParks) {
		System.out.println();
		for(int i = 0; i < myManagedParks.size(); i++) {
			System.out.println(i + "    " + myManagedParks.get(i).getName());
		}		
	}
	
	
	/**
	 * Take an ArrayList of Jobs, parse them, and then display their information in the console.
	 */
	public void displayJobs(ArrayList<Job> theJobList) {
		
		for(Job job : theJobList) {
			String startDate = calendarToString(job.getStartDate());
			String endDate = calendarToString(job.getEndDate());
			
			String jobString = "\n";
			jobString += "Job ID: " + job.getJobID();
			jobString += "\n    " + job.getPark();
			
			jobString += "\n    Begins: " + startDate;
			jobString += " , Ends: " + endDate;
			
			jobString += "\n    Light Slots: " + job.getLightCurrent() + "/" + job.getLightMax();
			jobString += "\n    Medium Slots: " + job.getMediumCurrent() + "/" + job.getMediumMax();
			jobString += "\n    Heavy Slots: " + job.getHeavyCurrent() + "/" + job.getHeavyMax() + "\n";
			
			System.out.println(jobString);
		}
	}
	
	/**
	 * Convert a GregorianCalendar object to string of format mmddyyyy
	 */
	private String calendarToString(GregorianCalendar theCalendar) {
		String returnString = theCalendar.get(Calendar.MONTH) + "/" +
				theCalendar.get(Calendar.DAY_OF_MONTH) + "/" +
				theCalendar.get(Calendar.YEAR);
		return returnString;
	}
	
	
	/**
	 * Take an ArrayList of Volunteers, and display their names to the console.
	 */
	public void displayVolunteers(ArrayList<Volunteer> theVolunteerList) {
		for(Volunteer volunteer : theVolunteerList) {
			System.out.println(volunteer.getFirstName() + " " + volunteer.getLastName());
		}
	}
	
	/**
	 * Display an error in the console that the Job ID was not recognized.
	 */
	public void showJobIDError() {
		System.out.println("Sorry, but the Job ID was not recognized.");
	}
	
	
	/**
	 * Prompt the user for a park number, and return that value.
	 */
	public int selectParkNum() {
		
		int myParkNum = in.nextInt();
		return myParkNum;
	}
}
