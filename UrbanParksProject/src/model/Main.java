package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Main {
	
	static MainUI myUI;

	public static void main(String[] args) {
		JobList myJobList = new JobList();
		Schedule mySchedule = new Schedule(myJobList);
		DataPollster myPollster = new DataPollster(myJobList);
		myUI = new MainUI();
		
		ArrayList<Park> myParkList = new ArrayList<Park>();
		Park myPark = new Park("Bobcat Park", "Hugo", 98335);
		Park myPark2 = new Park("Seahurt Park", "Burien", 98106);
		
		myParkList.add(myPark);
		myParkList.add(myPark2);
		
		
		ParkManager myManager = new ParkManager(mySchedule, myPollster, myParkList);
		//myManager.initialize();

		GregorianCalendar myStartDate = stringToCalendar("06052015");
		GregorianCalendar myEndDate = stringToCalendar("06062015");
		Job aJob = new Job(myPark, 1, 2, 3, myStartDate, myEndDate, myManager); //create a new job to test volunteer
		mySchedule.receiveJob(aJob);
		
		Volunteer theVol = new Volunteer("Arsh", "Singh", myPollster, mySchedule);
		theVol.initialize();
	}
	
	public void parseCommand(int theCommand) {
		switch(theCommand) {
		case 1: myUI.requestLogin(); break;
		case 2: myUI.requestRegister(); break;
		case 3: myUI.exitDisplay(); closeProgram(); break;
		default: myUI.invalidChoice(); break; //Loop back if invalid
	}
	}
	
	/**
	 * Convert a date string to a Gregorian Calendar object.
	 * @param stringDate A string representing a date, of format mmddyyyy
	 * @return A Gregorian Calendar object representing that date.
	 */
	private static GregorianCalendar stringToCalendar(String stringDate) {
		int myDay = Integer.parseInt(stringDate.substring(0, 2));
		int myMonth = Integer.parseInt(stringDate.substring(2, 4));
		int myYear = Integer.parseInt(stringDate.substring(4, 8));		
		
		return new GregorianCalendar(myYear, myDay, myMonth);
	}
	
	public static void closeProgram() {
		System.exit(0);
	}
	
	
	//NOTE: when creating an account for volunteer, don't forget to add him into the myVolunteerList in JobList class 

}
