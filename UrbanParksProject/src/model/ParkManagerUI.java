package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

/**
 * A console-based user interface for park managers to use.
 * @author Taylor Gorman
 * @author Reid Thompson
 * @version 6 May 2015
 */
public class ParkManagerUI {
	
	private Scanner myScanner;
	
	public ParkManagerUI() {
		myScanner = new Scanner(System.in);
	}
	
	/**
	 * Display the possible commands that the user could type. 
	 */
	public void listCommands() {
		System.out.println("\n------------------------------------------\nPark Manager Menu\n\nWhat would you like to do?");
		System.out.println("1) Create New Job");
		System.out.println("2) View My Jobs");
		System.out.println("3) View Volunteers for a Job");
		System.out.println("4) Logout");
	}
	
	/**
	 * Return an integer that the user has typed.
	 */
	public int getUserInt() {
		int userInput = 0;

		if(myScanner.hasNextInt()) {
			userInput = myScanner.nextInt();
		} else {
			myScanner.next();
		}

		return userInput;
	}
	
	private String getUserString() {		
		String userInput = myScanner.nextLine();
		
		if(userInput.equals("")) {
			userInput = myScanner.nextLine();
		}
		return userInput;
	}
	
	
	/**
	 * Prompt the user to enter the ID of a Job, and then return it.
	 */
	public int getJobID() {
		System.out.println("------------------------------------------\n\n"
				+ "Please input the ID of the job whose volunteers you would"
				+ " like to view.");
		int myJobID = getUserInt();
		return myJobID;
		
	}
	
	/**
	 * Prompt the user to enter how many volunteers they want for light grade work, then
	 * return it.
	 */
	public int getLightSlots() {		
		System.out.println("\nHow many volunteers do you want for light grade work?");
		int myLight = 0;
		myLight = getUserInt();
		return myLight;
	}

	/**
	 * Prompt the user to enter how many volunteers they want for medium grade work, then
	 * return it.
	 */
	public int getMediumSlots() {		
		System.out.println("\nHow many volunteers do you want for medium grade work?");
		int myMedium = getUserInt();
		return myMedium;
	}
	
	/**
	 * Prompt the user to enter how many volunteers they want for heavy grade work, then
	 * return it.
	 */
	public int getHeavySlots() {	
		System.out.println("\nHow many volunteers do you want for heavy grade work?");
		int myHeavy = getUserInt();
		return myHeavy;
	}
	
	/**
	 * Prompt the user to enter when they want to start the job, then return it.
	 * @return The start date in format mmddyyyy
	 */
	public String getStartDate() {
		System.out.println("Please enter the start date of the job in the following format:"
				+ " mmddyyyy");
		String myStartDate = getUserString();
		return myStartDate;
	}
	
	/**
	 * Prompt the user to enter when they want to end the job, then return it.
	 * @return The end date in format ddmmyyyy
	 */
	public String getEndDate() {
		System.out.println("Please enter the end date of the job in the following format:"
				+ " mmddyyyy");
		String myEndDate = getUserString();
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
	 * Request the user to select the appropriate park number.
	 */
	public void displayParkNumberRequest() {
		System.out.println("\n------------------------------------------\n"
				+ "Please select the number preceding the park where the job is located.");
	}
	
	/**
	 * Display all of the parks that the Park Manager manages in the console.
	 */
	public void displayParks(List<Park> myManagedParks) {
		int i;
		for(i = 0; i < myManagedParks.size(); i++) {
			System.out.println(i + ") " + myManagedParks.get(i).getName());
		}		
		System.out.println(i + ") Add New Park...");
	}
	
	public Park createNewPark() {
		System.out.println("\n------------------------------------------");
		
		System.out.println("What is the name of the park?");
		String parkName = getUserString();
		
		System.out.println("\nIn what city is the park located?");		
		String cityName = getUserString();
		
		System.out.println("\nWhat is the zipcode of the city?");
		int zipCode = getUserInt();
		
		return new Park(parkName, cityName, zipCode);
	}
	
	
	/**
	 * Take an ArrayList of Jobs, parse them, and then display their information in the console.
	 */
	public void displayJobs(List<Job> theJobList) {
		
		if(theJobList.size() == 0) {
			System.out.println("\nYou do not have any upcoming jobs to display.");
		}
		
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
	 * Take a List of Volunteers, and display their information to the console.
	 */
	public void displayVolunteers(List<Volunteer> theVolunteerList) {
		if (!theVolunteerList.isEmpty()) {
			for(Volunteer volunteer : theVolunteerList) {
				System.out.println("Name: " + volunteer.getFirstName() + " " + volunteer.getLastName());
				System.out.println("Email: " + volunteer.getEmail());
			}
		} else {
			System.out.println("There are no Volunteers associated with this Job.");
		}
	}

	
	public void displayInvalidChoiceError() {
		System.out.println("\nSorry, but your choice was invalid.");
	}
	
	/**
	 * Display an error in the console that the Job ID was not recognized.
	 */
	public void showJobIDError() {
		System.out.println("\nSorry, but this Job ID belongs to a park that you do not manage.");
	}
	
	public void displayTwoDayError() {
		System.out.println("\nSorry, but your job lasts more than two days, and could not be scheduled.");
	}
	
	
	/**
	 * Prompt the user for a park number, and return that value.
	 */
	public int selectParkNum() {
		
		int myParkNum = getUserInt();
		return myParkNum;
	}
}
