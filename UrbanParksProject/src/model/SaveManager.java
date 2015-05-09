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
		
		File jobFile = new File("jobList.txt");
		
		if(jobFile.exists() && !jobFile.isDirectory()) {
			try {
				jobFileList = loadJobFile(jobFile);
			} catch (FileNotFoundException e) {
				System.err.println("jobFile.txt was detected, but could not be loaded. Please delete jobFile.txt and restart the program.");
			}
		}
		
		myParsedList = parseJobFile(jobFileList, myParsedList);
		
		return myJobList;
		
	}
	
	/**
	 * Read userList.txt, and generate from it a UserList containing all Users and their information.
	 */
	public UserList loadUserList() {
		UserList myUserList = new UserList();
		File userFile = new File("userList.txt");
		
		if(userFile.exists() && !userFile.isDirectory()) {
			try {
				loadUserFile(userFile);
			} catch (FileNotFoundException e) {
				System.err.println("userFile.txt was detected, but could not be loaded. Please delete userFile.txt and restart the program.");
			}
		}
		
		return myUserList;
	}
	
	
	
	/*
	 * Read the contents of the Job or User file, line by line, into an array.
	 */
	private ArrayList<String> loadJobFile(File jobFile) throws FileNotFoundException {
		ArrayList<String> jobFileList = new ArrayList<String>();
		
		Scanner myScanner = new Scanner(new File("jobList.txt"));
		while(myScanner.hasNextLine()) {
			jobFileList.add(myScanner.nextLine());
		}
		
		myScanner.close();		
		return jobFileList;
	}
	
	private ArrayList loadUserFile(File userFile) throws FileNotFoundException {
		ArrayList userFileList = new ArrayList();
		
		Scanner myScanner = new Scanner(new File("userFile.txt"));
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
		for(int i = 0; i < 12; i++) {
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
	
	public List parseUserFile(ArrayList<String> theUserFileList) {
		ArrayList userList = new ArrayList();
		
		return userList;
	}
	
	
	
	
	
	
	
	
	
	public boolean saveJobList(JobList theJobList) {
		
		return true;
	}
	
	public boolean saveUserList(UserList theUserList) {
		
		return true;
	}
}
