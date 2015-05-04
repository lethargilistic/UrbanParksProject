package model;

import java.util.ArrayList;
import java.util.Scanner;

public class ParkManagerUI {
	
	public void listCommands() {
		System.out.println("\nnew job    view jobs    view job volunteers    quit");
	}
	
	public String getCommand() {
		Scanner in = new Scanner(System.in);		
		return in.nextLine().toLowerCase(); //lower case to avoid ambiguity
	}
	
	
	public int getJobID() {
		Scanner in = new Scanner(System.in);
		System.out.println("Please input the ID of the job whose volunteers you would like to view.");
		int myJobID = in.nextInt();
		
		in.close();
		return myJobID;
	}
	
	
	
	
	public int getLightSlots() {
		Scanner in = new Scanner(System.in);		
		System.out.println("\nHow many volunteers do you want for light grade work?");
		int myLight = in.nextInt();
		
		in.close();
		return myLight;
	}
	
	public int getMediumSlots() {
		Scanner in = new Scanner(System.in);		
		System.out.println("\nHow many volunteers do you want for medium grade work?");
		int myMedium = in.nextInt();
		
		in.close();
		return myMedium;
	}
	
	public int getHeavySlots() {
		Scanner in = new Scanner(System.in);		
		System.out.println("\nHow many volunteers do you want for heavy grade work?");
		int myHeavy = in.nextInt();
		
		in.close();
		return myHeavy;
	}
	
	public String getStartDate() {
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the start date of the job in the following format: mmddyyyy");
		String myStartDate = in.nextLine();
		
		in.close();
		return myStartDate;
	}
	
	public String getEndDate() {
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the end date of the job in the following format: mmddyyyy");
		String myEndDate = in.nextLine();
		
		in.close();
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
	public void displayParks(ArrayList<Park> theParkList) {
		for(int i = 0; i < theParkList.size(); i++) {
			System.out.println(i + "    " + theParkList.get(i).getName());
		}		
	}
	
	
	/**
	 * Take an ArrayList of Jobs, parse them, and then display their information in the console.
	 */
	public void displayJobs(ArrayList<Job> theJobList) {
		
		for(Job job : theJobList) {
			System.out.println("\n" + job.getJobID() + " " + job.getPark() + "\n    Begins:" + 
					job.getStartDate() + " , Ends:" + job.getEndDate() + "\n    Light Slots:" +
					job.getLightCurrent() + "/" + job.getLightMax() + "   Medium Slots:" +
					job.getMediumCurrent() + "/" + job.getMediumMax() +
					"   Heavy Slots:" + job.getHeavyCurrent() + "/" + job.getHeavyMax());
		}
	}
	
	
	/**
	 * Take an ArrayList of Volunteers, and display their names to the console.
	 */
	public void displayVolunteers(ArrayList<Volunteer> theVolunteerList) {
		for(Volunteer volunteer : theVolunteerList) {
			System.out.println(volunteer.myFirstName + " " + volunteer.myLastName);
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
		Scanner in = new Scanner(System.in);
		System.out.println("\nPlease select the number preceding the park where the job is located.");
		int myParkNum = in.nextInt();
		in.close();
		
		return myParkNum;
	}
}
