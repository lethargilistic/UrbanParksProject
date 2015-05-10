package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles both saving and loading from the jobList.txt and userList.txt files.
 * @author Taylor Gorman
 * @version 9 May 2015
 *
 */
public class SaveManager {
	
	/**
	 * Read jobList.txt, and generate from it a JobList containing all Jobs and their information.
	 */
	public JobList loadJobList() {
		JobList myJobList = new JobList();
		ArrayList<String> jobFileList = new ArrayList<String>();
		ArrayList<Job> myParsedList = new ArrayList<Job>();
		
		File jobFile = new File("rsc\\jobList.txt");
		if(jobFile.exists() && !jobFile.isDirectory()) {
			try {
				jobFileList = loadJobFile(jobFile);
			} catch (FileNotFoundException e) {
				System.err.println("jobFile.txt was detected, but could not be loaded. Please delete jobFile.txt and restart the program.");
			}
		}
		
		myParsedList = parseJobFile(jobFileList, myParsedList);
		myJobList.setJobList(myParsedList);
		
		return myJobList;
		
	}
	
	/**
	 * Read userList.txt, and generate from it a UserList containing all Users and their information.
	 */
	public UserList loadUserList() {
		UserList myUserList = new UserList();
		ArrayList<String> userFileList = new ArrayList<String>();
		ArrayList myParsedList = new ArrayList();
		
		File userFile = new File("rsc\\userList.txt");
		
		
		if(userFile.exists() && !userFile.isDirectory()) {
			try {
				userFileList = loadUserFile(userFile);
			} catch (FileNotFoundException e) {
				System.err.println("userFile.txt was detected, but could not be loaded. Please delete userFile.txt and restart the program.");
			}
		}
		
		myUserList = parseUserFile(userFileList, myUserList);		
		return myUserList;
	}
	
	
	
	/*
	 * Read the contents of the Job or User file, line by line, into an array.
	 */
	private ArrayList<String> loadJobFile(File jobFile) throws FileNotFoundException {
		ArrayList<String> jobFileList = new ArrayList<String>();
		
		Scanner myScanner = new Scanner(new File("rsc\\jobList.txt"));
		while(myScanner.hasNextLine()) {
			jobFileList.add(myScanner.nextLine());
		}
		
		myScanner.close();	
		return jobFileList;
	}
	
	private ArrayList loadUserFile(File userFile) throws FileNotFoundException {
		ArrayList userFileList = new ArrayList();
		
		Scanner myScanner = new Scanner(new File("rsc\\userList.txt"));
		while(myScanner.hasNextLine()) {
			userFileList.add(myScanner.nextLine());
		}
		
		myScanner.close();			
		return userFileList;
	}
	
	
	
	/*
	 * Recursively parse the array version of the Job or User file, and add
	 * the user or job to their lists.
	 */
	
	public ArrayList<Job> parseJobFile(ArrayList<String> jobFileList, ArrayList<Job> theParsedList) {
		theParsedList = new ArrayList<Job>();
		
		//jobList.txt was not found, which is normal if the program has not been executed before.
		if(jobFileList.size() < 1) {
			return theParsedList;
		}

		//If jobFileList has ended, then return our parsed list.		 
		if(jobFileList.get(0).equals("End Job List")) {
			return theParsedList;
		}
		
		//Construct Job
		int myJobID = Integer.parseInt(jobFileList.get(0));
		
		int myLightCurrent = Integer.parseInt(jobFileList.get(1));
		int myLightMax = Integer.parseInt(jobFileList.get(2));
		int myMediumCurrent = Integer.parseInt(jobFileList.get(3));
		int myMediumMax = Integer.parseInt(jobFileList.get(4));
		int myHeavyCurrent = Integer.parseInt(jobFileList.get(5));
		int myHeavyMax = Integer.parseInt(jobFileList.get(6));
		
		String myStartDate = jobFileList.get(7);
		String myEndDate = jobFileList.get(8);
		String myPark = jobFileList.get(9);
		String myManager = jobFileList.get(10);
		
		//Remove all added elements from the file list.
		for(int i = 0; i < 11; i++) {
			jobFileList.remove(0); 
		}
		
		List<String> myVolunteerList = new ArrayList<String>();
		
		//Add the volunteer to the volunteer list, then remove it from the file list.
		while(!jobFileList.get(0).equals("End Volunteer List")) {
			myVolunteerList.add(jobFileList.get(0)); 
			jobFileList.remove(0);
		}
		
		//Remove "End Volunteer List"
		jobFileList.remove(0);
		Park newPark = new Park("Happy Park", "Tacoma", 98335);
		
		//Construct the new job, and then add it to the list.
		Job myJob = new Job(myJobID, newPark, myLightCurrent, myLightMax, myMediumCurrent, myMediumMax, myHeavyCurrent, myHeavyMax, 
				myStartDate, myEndDate, myManager, myVolunteerList);
		theParsedList.add(myJob);

		//Recursively make calls until jobFileList is empty.
		return parseJobFile(jobFileList, theParsedList);
	}
	
	
	
	
	
	public UserList parseUserFile(ArrayList<String> theUserFileList, UserList theUserList) {
		
		//userList.txt was not found, which is normal if the program has not been executed before.
				if(theUserFileList.size() < 1) {
					return theUserList;
				}
		
		//If userFileList has ended, then return our parsed list.		 
		if(theUserFileList.get(0).equals("End User List")) {
			return theUserList;
		}

		//Get basic user information.
		String myEmail = theUserFileList.get(0);
		String myRole = theUserFileList.get(1);
		String myFirstName = theUserFileList.get(2);
		String myLastName = theUserFileList.get(3);

		//Remove gathered information from file list.
		for(int i = 0; i < 4; i++) {
			theUserFileList.remove(0);
		}
		
		
		
		//For whichever role the user is, construct their corresponding Object and add it to the User List.
		if(myRole.equals("Volunteer")) {
			Volunteer myVolunteer = new Volunteer(myEmail, myFirstName, myLastName);
			List<Volunteer> myVolunteerList = theUserList.getVolunteerCopyList();
			myVolunteerList.add(myVolunteer);
			theUserList.setVolunteerList(myVolunteerList);
		}
		
		if(myRole.equals("Administrator")) {
			Administrator myAdministrator = new Administrator(myEmail, myFirstName, myLastName);
			List<Administrator> myAdministratorList = theUserList.getAdministratorCopyList();
			myAdministratorList.add(myAdministrator);
			theUserList.setAdministratorList(myAdministratorList);
		}
		
		if(myRole.equals("ParkManager")) {
			List<Park> myParkList = new ArrayList<Park>();
			
			//Add the parks associated with the ParkManager, and remove them from the file list as we go.
			while(!theUserFileList.get(0).equals("End Park List")) {
				Park myPark = new Park(theUserFileList.get(0));
				myParkList.add(myPark);
				theUserFileList.remove(0);
			}			
			
			theUserFileList.remove(0);
			
			ParkManager myManager = new ParkManager(myEmail, myFirstName, myLastName, myParkList);
			
			List<ParkManager> myManagerList = theUserList.getParkManagerCopyList();
			myManagerList.add(myManager);
			theUserList.setParkManagerList(myManagerList);		
		}
		
		//Recursively make calls until userFileList is empty.
		return parseUserFile(theUserFileList, theUserList);
	}
	
	
	
	
	
	
	
	
	
	public boolean saveJobList(JobList theJobList) {
		
		return true;
	}
	
	public boolean saveUserList(UserList theUserList) {
		
		return true;
	}
}
