package model;

import java.util.ArrayList;

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
		
		
		return myJobList;
		
	}
	
	/**
	 * Read userList.txt, and generate from it a UserList containing all Users and their information.
	 */
	public UserList loadUserList() {
		UserList myUserList = new UserList();
		
		return myUserList;
	}
	
	
	
	/*
	 * Read the contents of the Job or User file, line by line, into an array.
	 */
	private ArrayList<Job> loadJobFile() {
		ArrayList<Job> jobFileList = new ArrayList<Job>();
		
		
		
		return jobFileList;
	}
	
	private ArrayList loadUserFile() {
		ArrayList userFileList = new ArrayList();
		
		
		return userFileList;
	}
	
	
	
	/*
	 * Recursively parse the array version of the Job or User file, and add
	 * the user or job to their lists.
	 */
	
	
	public boolean saveJobList(JobList theJobList) {
		
		return true;
	}
	
	public boolean saveUserList(UserList theUserList) {
		
		return true;
	}
}
