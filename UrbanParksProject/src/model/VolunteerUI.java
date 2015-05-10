package model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

public class VolunteerUI {

	private Scanner in;
	
	/**
	 * 
	 */
	public VolunteerUI() {
		in= new Scanner(System.in);
	}
	
	
	/**
	 * Display the possible commands that the user could type. 
	 */
	public void listCommands() {
		System.out.println("\n1.view jobs     2.sign up     3.view my jobs     4.quit\n");
	}
	
	/**
	 * Return the command that the user has typed.
	 */
	public String getCommand() {	
		return in.nextLine();
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
		System.out.println("To select a job to sign up for, please type its ID #.");
		int ID = 0;
		
		if(in.hasNextInt()) {
			ID = in.nextInt();
		}	
		
		return ID;
	}
	
	/**
	 * Prompts the user to enter in a number depending on the difficulty of the work
	 * he wants to do.
	 * @return difficulty level.
	 */
	public int getDifficultyLevel() {
		System.out.println("\nWhat level of work do you want to sign up for?");
		int level = 0;
		
		System.out.println("Type 1 for light work, 2 for medium work, or 3 for heavy work.");
		
		if(in.hasNextInt()) {
			level = in.nextInt();
		} 
		
		return level;
	}

	
	
	
	/**
	 * Display an error in the console that the Job ID was not recognized.
	 */
	public void showJobIDError() {
		System.out.println("Sorry, but the Job ID was not recognized.");
	}
	
	
}

