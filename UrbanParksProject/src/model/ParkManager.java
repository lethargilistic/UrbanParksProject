package model;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;


public class ParkManager {
	
	/**
	 * A list of all the parks managed by the park manager.
	 */
	private ArrayList<Park> myManagedParks;
	
	/**
	 * The first name of the park manager.
	 */
	private String myFirstName;
	/**
	 * The last name of the park manager.
	 */
	private String myLastName;
	
	private Schedule mySchedule = new Schedule();
	private DataPollster myPollster = new DataPollster();
	
	/**
	 * Give control of the program to the Park Manager user. Called upon login.
	 */
	public void startManager() {
		System.out.println("Hello " + myFirstName + " " + myLastName);
		pollManager();
	}
	
	/**
	 * Display possible commands to the user, and then handle any subsequent input. Acts as a control loop.
	 */
	private void pollManager() {
		System.out.println("\nnew job    view jobs    view job volunteers"); //Possible commands.
		Scanner in = new Scanner(System.in);
		
		String command = in.nextLine().toLowerCase();
		
		//Handle inputed command
		switch(command) { 
			case "new job":
			case "new":				
				createJob(); 
			break;
			
			case "view jobs":
			case "view job":
				viewUpcomingJobs();
			break;
			
			case "view job volunteers":
			case "view job volunteer":
			case "view volunteers":
				viewJobVolunteers();
			break;
			
			default: System.out.println("Sorry, but your command was not recognized.");
		}
		
		in.close();
		pollManager();		
	}
	
	
	
	
	/**
	 * Create a new job by sequentially asking the ParkManager for the details, and then submit it to
	 * Schedule for approval.
	 */
	public void createJob() {
		System.out.println("\nCreating Job...");

		Park myPark = selectPark();
		Job myJob = constructJob(myPark);
		
		if(mySchedule.receiveJob(myJob)){
			System.out.println("Job successfully added!");
		} else {
			System.out.println("Sorry, but the job could not be added.");
		}
		
	}
	
	/**
	 * Print a list of parks for the ParkManager, then prompt them to select one.
	 * @return The Park that the ParkManager has selected.
	 */
	private Park selectPark() {
		Scanner in = new Scanner(System.in);
		
		for(int i = 0; i < myManagedParks.size(); i++) {
			System.out.println(i + "    " + myManagedParks.get(i).getName());
		}
		
		System.out.println("\nPlease select the number preceding the park where the job is located.");
		int myParkNum = in.nextInt();
		in.close();
		
		return myManagedParks.get(myParkNum);		
	}
	
	/**
	 * Ask the Park Manager for the information needed to create a job, put it all together, and return the Job object.
	 * @param thePark The park where the job will occur.
	 * @return The constructed Job
	 */
	private Job constructJob(Park thePark) {
		Scanner in = new Scanner(System.in);
		
		System.out.println("\nHow many volunteers do you want for light grade work?");
		int myLight = in.nextInt();
		
		System.out.println("How many volunteers do you want for medium grade work?");
		int myMedium = in.nextInt();
		
		System.out.println("How many volunteers do you want for heavy grade work?");
		int myHeavy = in.nextInt();		
		
		
		System.out.println("Please enter the start date of the job in the following format: mmddyyyy");
		String myStringStartDate = in.nextLine();
		GregorianCalendar myStartDate = parseDate(myStringStartDate);
		
		System.out.println("Please enter the end date of the job in the following format: mmddyyyy");
		String myStringEndDate = in.nextLine();		
		GregorianCalendar myEndDate = parseDate(myStringEndDate);
		
		return new Job(thePark, myLight, myMedium, myHeavy, myStartDate, myEndDate);		
	}
	
	/**
	 * Convert a date string to a Gregorian Calendar object.
	 * @param stringDate A string representing a date, of format mmddyyyy
	 * @return A Gregorian Calendar object representing that date.
	 */
	private GregorianCalendar parseDate(String stringDate) {
		int myDay = Integer.parseInt(stringDate.substring(0, 2));
		int myMonth = Integer.parseInt(stringDate.substring(2, 4));
		int myYear = Integer.parseInt(stringDate.substring(4, 8));		
		
		return new GregorianCalendar(myYear, myMonth, myDay);
	}
	
	
	
	
	/**
	 * Print a list of all upcoming jobs for every Park that the ParkManager manages.
	 */
	public void viewUpcomingJobs() {
		System.out.println("\nViewing Jobs...");
		
		ArrayList<Job> myJobList = (ArrayList<Job>) myPollster.getManagerJobs(this);
		
		for(Job job : myJobList) {
			System.out.println("\n" + job.getJobID() + " " + job.getPark() + "\n    Begins:" + 
					job.getStartDate() + " , Ends:" + job.getEndDate() + "\n    Light Slots:" +
					job.getLightCurrent() + "/" + job.getLightMax() + "   Medium Slots:" +
					job.getMediumCurrent() + "/" + job.getMediumMax() +
					"   Heavy Slots:" + job.getHeavyCurrent() + "/" + job.getHeavyMax());
		}		
	}
	
	
	
	
	/**
	 * Print a list of every Volunteer for a selected Job.
	 */
	public void viewJobVolunteers() {
		System.out.println("\nViewing Volunteers For A Job...");
		Scanner in = new Scanner(System.in);
		
		System.out.println("Please input the ID of the job whose volunteers you would like to view.");
		int myJobID = in.nextInt();
		
		if(!checkPark(myJobID)) {
			System.out.println("Sorry, but the Job ID was not recognized.");
			return;
		}
		
		ArrayList<Volunteer> myVolunteerList = (ArrayList<Volunteer>) myPollster.getVolunteerList(myJobID);
		
		for(Volunteer volunteer : myVolunteerList) {
			System.out.println(volunteer.firstName + " " + volunteer.lastName);
		}
		
	}
	
	/**
	 * Check to make sure that the Job ID is valid.
	 * @return True if valid, false if not.
	 */
	private boolean checkPark(int theJobID) {
		return true; //Unsure of how to implement this at the moment. Will it be done through DataPollster?	
	}	
	
}
