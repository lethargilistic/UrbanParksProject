package model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

public class VolunteerUI {

	private Scanner myScanner;
	
	public VolunteerUI() {
		myScanner = new Scanner(System.in);
	}
	
	/**
	 * Display the possible commands that the user could type. 
	 */
	public void listCommands() {
		System.out.println("\n------------------------------------------");
		System.out.println("Volunteer Menu");
		System.out.println("\nWhat would you like to do?");
		System.out.println("1) View All Jobs");
		System.out.println("2) Sign Up for a Job");
		System.out.println("3) View My Jobs");
		System.out.println("4) Logout");
		}
	
	/**
	 * Return the command that the user has typed.
	 */
	public String getCommand() {	
		return getUserString();
	}

	public void displayJobs(List<Job> theJobList) {
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
	 * Prompts the user to enter a number which represents the job's ID.
	 * @return ID number
	 */
	public int getJobID() {
		System.out.println("Please enter the Job ID of the job you would like to sign up for:");
		int ID = 0;
		
		ID = getUserInt();
		
		return ID;
	}
	
	/**
	 * Prompts the user to enter in a number depending on the difficulty of the work
	 * he wants to do.
	 * @return difficulty level.
	 */
	public String getDifficultyLevel() {
		System.out.println("\nPlease enter the work intensity you would like to sign up for:");
		int level = 0;
		
		System.out.println("1) Light");
		System.out.println("2) Medium");
		System.out.println("3) Heavy");
		
		level = getUserInt();
		String levelString = null;
		
		switch(level) {
		case 1: levelString = "Light"; break;
		case 2: levelString = "Medium"; break;
		case 3: levelString = "Heavy"; break;
		}
		
		return levelString;
	}

	
	
	
	/**
	 * Display an error in the console that the Job ID was not recognized.
	 */
	public void showJobIDError() {
		System.out.println("Sorry, but the Job ID was not recognized.");
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
		
		if(userInput.equals("")) { //TODO, maybe make this a while so that it will continuously 
									//prompt the user, instead of just once? - Reid agrees.
			userInput = myScanner.nextLine();
		}
		return userInput;
	}
	
}

