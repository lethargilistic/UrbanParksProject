package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

/**
 * Handles both saving and loading from the jobList.txt and userList.txt files.
 * @author Taylor Gorman
 * @version 13 May 2015
 *
 */
public class SaveManager {
	
	/**
	 * Read jobList.txt, and generate from it a JobList containing all Jobs and their information.
	 */
	public JobList loadJobList() {
		File inFile = getJobFile();
		
		JobList myJobList = new JobList();
		ArrayList<String> jobFileList = new ArrayList<String>();
		ArrayList<Job> myParsedList = new ArrayList<Job>();
		
		if(inFile.exists() && !inFile.isDirectory()) {
			try {
				jobFileList = loadJobFile(inFile);
			} catch (FileNotFoundException e) {
				System.err.println("jobList.txt was detected, but could not be loaded. Please delete jobFile.txt and restart the program.");
			}
		} 
		
		myParsedList = parseJobFile(jobFileList, myParsedList);
		myJobList.setJobList(myParsedList);
		
		return myJobList;		
	}
	
	/*
	 * Return the File to load/save job data to.
	 */
	private File getJobFile() {
		File jobFile;
		
		jobFile = new File("rsc/jobList.txt"); //Try iOS
		if(jobFile.exists()) return jobFile;
		
		jobFile = new File("rsc\\jobList.txt"); //Try Windows
		if(jobFile.exists()) return jobFile;
		
		try {
			jobFile = new File(SaveManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()); 
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		File copyFile = jobFile;	//Backup the File path before we start appending to it.	
		jobFile = jobFile.getParentFile();		
		jobFile = new File(jobFile.toString() + "/jobList.txt");		
		
		if(jobFile.exists()) return jobFile; //Try jar on iOS
		
		jobFile = copyFile;		
		jobFile = jobFile.getParentFile();		
		jobFile = new File(jobFile.toString() + "\\jobList.txt");	//Try jar on Windows
		return jobFile;
	}
	
	/**
	 * Read userList.txt, and generate from it a UserList containing all Users and their information.
	 */
	public UserList loadUserList() {
		File inFile = getUserFile();
		
		UserList myUserList = new UserList();
		ArrayList<String> userFileList = new ArrayList<String>();
		ArrayList myParsedList = new ArrayList();
		
		if(inFile.exists() && !inFile.isDirectory()) {
			try {
				userFileList = loadUserFile(inFile);
			} catch (FileNotFoundException e) {
				System.err.println("userList.txt was detected, but could not be loaded. Please delete userFile.txt and restart the program.");
			}
		}
				
		myUserList = parseUserFile(userFileList, myUserList);		
		return myUserList;
	}
	
	/*
	 * Return the File to load/save user data to.
	 */
	private File getUserFile() {
		File userFile = new File("rsc/userList.txt");
		
		userFile = new File("rsc/userList.txt"); //Try iOS
		if(userFile.exists()) return userFile;
		
		userFile = new File("rsc\\jobList.txt"); //Try Windows
		if(userFile.exists()) return userFile;
		
		try {
			userFile = new File(SaveManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()); //Try jar
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		File copyFile = userFile;
		
		userFile = userFile.getParentFile();		
		userFile = new File(userFile.toString() + "/userList.txt");		
		if(userFile.exists()) return userFile; //Try iOS Jar

		userFile = copyFile;
		userFile = userFile.getParentFile();		
		userFile = new File(userFile.toString() + "\\userList.txt");		
		return userFile; //Try Windows Jar
	}
	
	
	
	/*
	 * Read the contents of the Job or User file, line by line, into an array.
	 */
	private ArrayList<String> loadJobFile(File jobFile) throws FileNotFoundException {
		ArrayList<String> jobFileList = new ArrayList<String>();
		
		Scanner myScanner = new Scanner(jobFile);
		while(myScanner.hasNextLine()) {
			jobFileList.add(myScanner.nextLine());
		}

		myScanner.close();	
		return jobFileList;
	}
	
	private ArrayList loadUserFile(File userFile) throws FileNotFoundException {
		ArrayList userFileList = new ArrayList();
		
		Scanner myScanner = new Scanner(userFile);
		while(myScanner.hasNextLine()) {
			userFileList.add(myScanner.nextLine());
		}
		
		myScanner.close();			
		return userFileList;
	}
	
	
	


	//Recursively parse the array version of the Job File, construct Jobs from the information, and pass back a list of those jobs.
	
	private ArrayList<Job> parseJobFile(ArrayList<String> jobFileList, ArrayList<Job> theParsedList) {		
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
		
		int myLightMax = Integer.parseInt(jobFileList.get(1));
		int myMediumMax = Integer.parseInt(jobFileList.get(2));
		int myHeavyMax = Integer.parseInt(jobFileList.get(3));
		
		String myStartDate = jobFileList.get(4);
		String myEndDate = jobFileList.get(5);
		String myPark = jobFileList.get(6);
		String myManager = jobFileList.get(7);
		
		//Remove all added elements from the file list.
		for(int i = 0; i < 8; i++) {
			jobFileList.remove(0); 
		}
		
		ArrayList<ArrayList<String>> myVolunteerList = new ArrayList<ArrayList<String>>();
		
		//Add the volunteer to the volunteer list, then remove it from the file list.
		while(!jobFileList.get(0).equals("End Volunteer List")) {
			ArrayList<String> volunteer = new ArrayList<String>();
			volunteer.add(jobFileList.get(0));
			volunteer.add(jobFileList.get(1));
			myVolunteerList.add(volunteer); 
			
			jobFileList.remove(0);
			jobFileList.remove(0);
		}
		
		//Remove "End Volunteer List"
		jobFileList.remove(0);
		Park newPark = new Park(myPark, "Tacoma", 98335);
		
		//Construct the new job, and then add it to the list.
		Job myJob = new Job(myJobID, newPark, myLightMax, myMediumMax, myHeavyMax, myStartDate, myEndDate, myManager, myVolunteerList);
		theParsedList.add(myJob);

		//Recursively make calls until jobFileList is empty.
		return parseJobFile(jobFileList, theParsedList);
	}
	
	
	
	
	/*
	 * Recursively parse the array version of the User File, construct Users from the information, and pass back a list of those users.
	 */
	private UserList parseUserFile(ArrayList<String> theUserFileList, UserList theUserList) {
		
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
			Administrator myAdministrator = new Administrator(myFirstName, myLastName, myEmail);
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
	
	
	

	/**
	 * Parse a JobList and write its contents to a text file.
	 */
	public boolean saveJobList(JobList theJobList) {
		List<String> jobInfo = extractJobInfo(theJobList);
		
		File outFile = getJobFile();
		
		try {
			FileWriter fw = new FileWriter(outFile);
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(int i = 0; i < jobInfo.size(); i++) {
				bw.write(jobInfo.get(i));
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			System.out.println("Job List could not be saved.");
			return false;
		}		
		
		return true;
	}
	
	/*
	 * Copy JobList into an Array, line-by-line.
	 */
	private List<String> extractJobInfo(JobList theJobList) {
		List<String> jobInfo = new ArrayList<String>();
		
		for(Job job : theJobList.getCopyList()) {
			jobInfo.add(String.valueOf(job.getJobID()));
			jobInfo.add(String.valueOf(job.getLightMax()));
			jobInfo.add(String.valueOf(job.getMediumMax()));
			jobInfo.add(String.valueOf(job.getHeavyMax()));
			jobInfo.add(calendarToString(job.getStartDate()));
			jobInfo.add(calendarToString(job.getEndDate()));
			jobInfo.add(job.getPark().getName());
			jobInfo.add(job.getManager());
			
			for(ArrayList<String> volunteer : job.getVolunteerList()) {
				jobInfo.add(volunteer.get(0)); //Volunteer Email
				jobInfo.add(volunteer.get(1)); //Work Grade
			}
			
			jobInfo.add("End Volunteer List");
		}
		
		jobInfo.add("End Job List");	
		return jobInfo;
	}
	
	
	
	
	/**
	 * Parse a UserList and write its contents to a text file.
	 */
	public boolean saveUserList(UserList theUserList) {
		
		File outFile = getUserFile();
		
		List<String> userInfo = extractUserInfo(theUserList);
		
		try {
			FileWriter fw = new FileWriter(outFile);
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(int i = 0; i < userInfo.size(); i++) {
				bw.write(userInfo.get(i));
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			System.out.println("User List could not be saved.");
			return false;
		}		
		
		return true;
	}
	
	/*
	 * Copy UserList into an Array, line-by-line.
	 */
	private List<String> extractUserInfo(UserList theUserList) {
		List<String> userInfo = new ArrayList<String>();
		
		for(Volunteer volunteer : theUserList.getVolunteerCopyList()) {
			userInfo.add(volunteer.getEmail());
			userInfo.add("Volunteer");
			userInfo.add(volunteer.getFirstName());
			userInfo.add(volunteer.getLastName());
		}
		
		for(ParkManager manager : theUserList.getParkManagerCopyList()) {
			userInfo.add(manager.getEmail());
			userInfo.add("ParkManager");
			userInfo.add(manager.getFirstName());
			userInfo.add(manager.getLastName());
			
			for(Park park : manager.getManagedParks()) {
				userInfo.add(park.getName());
			}
			userInfo.add("End Park List");
		}
		
		for(Administrator admin : theUserList.getAdministratorCopyList()) {
			userInfo.add(admin.getEmail());
			userInfo.add("Administrator");
			userInfo.add(admin.getFirstName());
			userInfo.add(admin.getLastName());
		}
		
		userInfo.add("End User List");	
		return userInfo;
	}
	
	
	
	
	
	/**
	 * Convert a GregorianCalendar object to string of format mmddyyyy
	 */
	private String calendarToString(GregorianCalendar theCalendar) {
		
		String returnString = "";
		
		if(theCalendar.get(Calendar.MONTH) < 10) {
			returnString += "0";
		}
		returnString += theCalendar.get(Calendar.MONTH);
		
		if(theCalendar.get(Calendar.DAY_OF_MONTH) < 10) {
			returnString += "0";
		}
		returnString += theCalendar.get(Calendar.DAY_OF_MONTH);
		
		returnString += theCalendar.get(Calendar.YEAR);

		return returnString;
	}
}
