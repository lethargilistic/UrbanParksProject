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
public class SaveLoad {
	
	
	/*============*
	 * Load Lists *
	 *============*/	
	
	/**
	 * Read jobList.txt, and generate from it a JobList containing all Jobs and their information.
	 */
	public JobList loadJobList() {
		File inFile = getListFile("jobList.txt");
		
		JobList myJobList = new JobList();
		ArrayList<String> jobFileList = new ArrayList<String>();
		ArrayList<Job> myParsedList = new ArrayList<Job>();
		
		if(inFile.exists() && !inFile.isDirectory()) {
			try {
				jobFileList = loadFile(inFile);
			} catch (FileNotFoundException e) {
				System.err.println("jobList.txt was detected, but could not be loaded. Please delete jobFile.txt and restart the program.");
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
		File inFile = getListFile("userList.txt");
		
		UserList myUserList = new UserList();
		ArrayList<String> userFileList = new ArrayList<String>();
		
		if(inFile.exists() && !inFile.isDirectory()) {
			try {
				userFileList = loadFile(inFile);
			} catch (FileNotFoundException e) {
				System.err.println("userList.txt was detected, but could not be loaded. Please delete userFile.txt and restart the program.");
			}
		}
			
		myUserList = parseUserFile(userFileList, myUserList);		
		return myUserList;
	}
	
	
	
	/*
	 * Return the File to load/save job or user data to.
	 * We make four different attempts, to account for iOS and Windows, on both Console and Jar.
	 * 
	 * Return null in the case that the file could not be found.
	 */
	private File getListFile(String fileName) {
		
		File dataFile;
		
		//Try iOS (Console)
		dataFile = new File("rsc/" + fileName);
		if(dataFile.exists()) return dataFile;
		
		//Try Windows (Console)
		dataFile = new File("rsc\\" + fileName);
		if(dataFile.exists()) return dataFile;
		
		File jarFile;
		
		//Generate path of the current file; used by the Jar file for detecting local text files.
		try {
			jarFile = new File(SaveLoad.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()); 
		} catch (URISyntaxException e1) {
			System.out.println("Sorry, but this program could not detect its location. Please close the program and try again.");
			return null;
		}		
		
		//Try iOS (Jar)
		jarFile = jarFile.getParentFile();		
		dataFile = new File(jarFile.toString() + "/" + fileName);		
		if(dataFile.exists()) return dataFile;
		
		//Try Windows (Jar)	
		dataFile = new File(jarFile.toString() + "\\" + fileName);
		
		return dataFile;
	}
	
	
	
	/*
	 * Read the contents of the Job or User file, line by line, into an array.
	 */
	private ArrayList<String> loadFile(File jobFile) throws FileNotFoundException {
		ArrayList<String> jobFileList = new ArrayList<String>();
		
		Scanner myScanner = new Scanner(jobFile);
		while(myScanner.hasNextLine()) {
			jobFileList.add(myScanner.nextLine());
		}

		myScanner.close();	
		return jobFileList;
	}	

	
	
	//Recursively parse the array version of the Job File, construct Jobs from the information, and pass back a list of those jobs.
	private ArrayList<Job> parseJobFile(ArrayList<String> jobFileList, ArrayList<Job> theParsedList) {		
		
		//jobList.txt was not found, which is normal if the program has not been executed before. So we just return an empty list.
		if(jobFileList.size() < 1) {
			return theParsedList;
		}

		//If jobFileList has ended, then return our parsed list.
		if(jobFileList.get(0).equals("End Job List")) {
			return theParsedList;
		}
		
		/*
		 * Each Job in jobList.txt has 8 lines containing Job data, followed by a list of Volunteers
		 * for the job and their grade of work.
		 */
		
		//Construct Job from the 8 lines containing Job data.
		int myJobID = Integer.parseInt(jobFileList.get(0));
		
		int myLightMax = Integer.parseInt(jobFileList.get(1));
		int myMediumMax = Integer.parseInt(jobFileList.get(2));
		int myHeavyMax = Integer.parseInt(jobFileList.get(3));
		
		String myStartDate = jobFileList.get(4);
		String myEndDate = jobFileList.get(5);
		String myPark = jobFileList.get(6);
		String myManager = jobFileList.get(7);
		
		//Remove the 8 lines containing Job data from the File List.
		for(int i = 0; i < 8; i++) {
			jobFileList.remove(0); 
		}
		
		//With the Job detailed, we begin adding Volunteers.
		ArrayList<ArrayList<String>> myVolunteerList = new ArrayList<ArrayList<String>>();
		
		//Add a volunteer to the Volunteer list for the Job, then remove it from the File List.
		while(!jobFileList.get(0).equals("End Volunteer List")) {
			ArrayList<String> volunteer = new ArrayList<String>();
			
			volunteer.add(jobFileList.get(0)); //Volunteer Name
			volunteer.add(jobFileList.get(1)); //Volunteer Work Grade
			myVolunteerList.add(volunteer); 
			
			jobFileList.remove(0);
			jobFileList.remove(0);
		}
		
		//Remove "End Volunteer List"
		jobFileList.remove(0);
		
		String newPark = myPark;
		
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
		
		//userList.txt was not found, which is normal if the program has not been executed before. So we return an empty list.
				if(theUserFileList.size() < 1) {
					return theUserList;
				}
		
		//If userFileList has ended, then return our parsed list.
		if(theUserFileList.get(0).equals("End User List")) {
			return theUserList;
		}
		
		//Each User in userList.txt has 4 lines with details on the User.
		
		
		//Construct User with the 4 lines detailing the user.
		String myEmail = theUserFileList.get(0);
		String myRole = theUserFileList.get(1);
		String myFirstName = theUserFileList.get(2);
		String myLastName = theUserFileList.get(3);

		//Remove the 4 lines with User information from the File List.
		for(int i = 0; i < 4; i++) {
			theUserFileList.remove(0);
		}
		
		//Used only by ParkManager to store the list of Parks they are associated with.
		List<String> myParkList = new ArrayList<String>();
		
		//If we are working with a ParkManager, we now load its Parks into myParkList.
		if(myRole.equals("ParkManager")) {
			
			//Add the Parks associated with the ParkManager, and remove them from the File List as we go.
			while(!theUserFileList.get(0).equals("End Park List")) {
				String myPark = theUserFileList.get(0);
				myParkList.add(myPark);
				theUserFileList.remove(0);
			}			
			
			//Remove "End Park List"
			theUserFileList.remove(0);
		}
		
		
		//For whichever role the user is, construct their corresponding Object and add it to the User List.
		
		if(myRole.equals("Volunteer")) {
			Volunteer myVolunteer = new Volunteer(myEmail, myFirstName, myLastName);			
			
			//Get the Volunteer List, add the Volunteer to it, and then send it back to UserList.
//			List<Volunteer> myVolunteerList = theUserList.getVolunteerCopyList();
//			myVolunteerList.add(myVolunteer);
//			theUserList.setVolunteerList(myVolunteerList);
			
			theUserList.addNewVolunteer(myVolunteer);
		}
		
		if(myRole.equals("Administrator")) {
			Administrator myAdministrator = new Administrator(myFirstName, myLastName, myEmail);
			
			//Get the Administrator List, add the Administrator to it, and then send it back to UserList.
//			List<Administrator> myAdministratorList = theUserList.getAdministratorCopyList();
//			myAdministratorList.add(myAdministrator);
//			theUserList.setAdministratorList(myAdministratorList);
			
			theUserList.addNewAdministrator(myAdministrator);
		}
		
		if(myRole.equals("ParkManager")) {

			ParkManager myManager = new ParkManager(myEmail, myFirstName, myLastName, myParkList);
			
			//Get the ParkManager List, add the ParkManager to it, and then send it back to UserList.
//			List<ParkManager> myManagerList = theUserList.getParkManagerCopyList();
//			myManagerList.add(myManager);
//			theUserList.setParkManagerList(myManagerList);	
			
			theUserList.addNewParkManager(myManager);
		}
		
		//Recursively make calls until userFileList is empty.
		return parseUserFile(theUserFileList, theUserList);
	}
	
	
	
	
	
	/*============*
	 * Save Lists *
	 *============*/

	/**
	 * Parse a JobList and write its contents to a text file.
	 */
	public boolean saveJobList(JobList theJobList) {
		
		//jobInfo contains a String Array of Jobs from JobList to be written to the text file.
		List<String> jobInfo = extractJobInfo(theJobList);
		
		File outFile = getListFile("jobList.txt");
		
		try {
			FileWriter fw = new FileWriter(outFile);
			BufferedWriter bw = new BufferedWriter(fw);
			
			//Write jobInfo to outFile.
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
	
	/**
	 * Parse a UserList and write its contents to a text file.
	 */
	public boolean saveUserList(UserList theUserList) {
		
		//userInfo contains a String Array of Users from UserList to be written to the text file.
		List<String> userInfo = extractUserInfo(theUserList);
		
		File outFile = getListFile("userList.txt");
		
		try {
			FileWriter fw = new FileWriter(outFile);
			BufferedWriter bw = new BufferedWriter(fw);
			
			//Write userInfo to outFile.
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
	 * Copy JobList into an Array, line-by-line, that is ready to be printed to a text file.
	 */
	private List<String> extractJobInfo(JobList theJobList) {
		List<String> jobInfo = new ArrayList<String>();
		
		for(Job job : theJobList.getCopyList()) {
			//Extract Job details.
			jobInfo.add(String.valueOf(job.getJobID()));
			jobInfo.add(String.valueOf(job.getLightMax()));
			jobInfo.add(String.valueOf(job.getMediumMax()));
			jobInfo.add(String.valueOf(job.getHeavyMax()));
			jobInfo.add(calendarToString(job.getStartDate()));
			jobInfo.add(calendarToString(job.getEndDate()));
			jobInfo.add(job.getPark());
			jobInfo.add(job.getManager());
			
			//Extract Volunteer List.
			for(ArrayList<String> volunteer : job.getVolunteerList()) {
				jobInfo.add(volunteer.get(0)); //Volunteer Email
				jobInfo.add(volunteer.get(1)); //Work Grade
			}
			
			jobInfo.add("End Volunteer List");
		}
		
		jobInfo.add("End Job List");	
		return jobInfo;
	}
	
	/*
	 * Copy UserList into an Array, line-by-line, that is ready to be printed to a text file.
	 */
	private List<String> extractUserInfo(UserList theUserList) {
		List<String> userInfo = new ArrayList<String>();
		
		/*
		 * We separately iterate through each of the three sub-lists of UserList, and write their contents
		 * to userInfo.
		 */
		
		for (User user : theUserList.getUserListCopy()) {
			userInfo.add(user.getEmail());
			userInfo.add(user.getUserType());
			userInfo.add(user.getFirstName());
			userInfo.add(user.getLastName());
			if (user.getUserType().equals("ParkManager")) {
				//Load Parks.
				for(String park : ((ParkManager) user).getManagedParks()) {
					userInfo.add(park);
				}
				userInfo.add("End Park List");
			}
		}
		
//		for(Volunteer volunteer : theUserList.getVolunteerCopyList()) {
//			userInfo.add(volunteer.getEmail());
//			userInfo.add("Volunteer");
//			userInfo.add(volunteer.getFirstName());
//			userInfo.add(volunteer.getLastName());
//		}
//		
//		for(Administrator admin : theUserList.getAdministratorCopyList()) {
//			userInfo.add(admin.getEmail());
//			userInfo.add("Administrator");
//			userInfo.add(admin.getFirstName());
//			userInfo.add(admin.getLastName());
//		}
//		
//		for(ParkManager manager : theUserList.getParkManagerCopyList()) {
//			userInfo.add(manager.getEmail());
//			userInfo.add("ParkManager");
//			userInfo.add(manager.getFirstName());
//			userInfo.add(manager.getLastName());
			
		userInfo.add("End User List");	
		
		return userInfo;
	}
	
	
	
	/*================*
	 * Helper Classes *
	 *================*/
	
	/*
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
