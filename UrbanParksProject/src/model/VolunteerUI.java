package model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

public class VolunteerUI {

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
	
	
	
}

