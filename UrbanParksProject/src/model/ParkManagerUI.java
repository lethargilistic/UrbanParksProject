package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

public class ParkManagerUI {
	
	private Scanner in = new Scanner(System.in);
	
	public void listCommands() {
		System.out.println("\nnew job    view jobs    view job volunteers    quit");
	}
	
	public String getCommand() {	
		return in.nextLine();
	}
	
	
	public int getJobID() {
		System.out.println("Please input the ID of the job whose volunteers you would like to view.");
		int myJobID = in.nextInt();
		return myJobID;
	}
	
	
	
	
	public int getLightSlots() {		
		System.out.println("\nHow many volunteers do you want for light grade work?");
		int myLight = 0;
		
		if(in.hasNextInt()) {
			myLight = in.nextInt();
		}	
		
		return myLight;
	}
	
	public int getMediumSlots() {		
		System.out.println("\nHow many volunteers do you want for medium grade work?");
		int myMedium = in.nextInt();
		return myMedium;
	}
	
	public int getHeavySlots() {	
		System.out.println("\nHow many volunteers do you want for heavy grade work?");
		int myHeavy = in.nextInt();
		return myHeavy;
	}
	
	public String getStartDate() {
		System.out.println("Please enter the start date of the job in the following format: mmddyyyy");
		String inputBuffer = in.nextLine();
		String myStartDate = in.nextLine();
		return myStartDate;
	}
	
	public String getEndDate() {
		System.out.println("Please enter the end date of the job in the following format: mmddyyyy");
		String myEndDate = in.nextLine();
		return myEndDate;
	}	
	
	
	
	
	/**
	 * Tells the user whether or not the job was successfully added.
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
			
			System.out.println("\n" + job.getJobID() + " " + job.getPark() + "\n    Begins: " + 
					startDate + " , Ends: " + endDate + "\n    Light Slots: " +
					job.getLightCurrent() + "/" + job.getLightMax() + "   Medium Slots: " +
					job.getMediumCurrent() + "/" + job.getMediumMax() +
					"   Heavy Slots: " + job.getHeavyCurrent() + "/" + job.getHeavyMax());
		}
	}
	
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
		System.out.println("\nPlease select the number preceding the park where the job is located.");
		int myParkNum = in.nextInt();
		return myParkNum;
	}
}
