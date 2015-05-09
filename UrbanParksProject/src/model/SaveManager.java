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
		File jobFile = new File("jobList.txt");
		
		if(jobFile.exists() && !jobFile.isDirectory()) {
			try {
				loadJobFile(jobFile);
			} catch (FileNotFoundException e) {
				System.err.println("jobFile.txt was detected, but could not be loaded. Please delete jobFile.txt and restart the program.");
			}
		}
		
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
		
		
		return userFileList;
	}
	
	
	
	/*
	 * Recursively parse the array version of the Job or User file, and add
	 * the user or job to their lists.
	 */
	
	public List<Job> parseJobFile(ArrayList<String> theJobFileList, Schedule theSchedule) {
		ArrayList<Job> jobList = new ArrayList<Job>();
		
		
		
		return jobList;
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
