package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Administrators can search all Volunteers alphabetically.
 * @author Reid Thompson
 * @author Taylor Gorman
 * @version 8 May 2015
 *
 */
public class Administrator {
	
	private static final int MAX_NUM_VOLS = 1000;
	
	//Class Variables
	private String myFirstName;
	private String myLastName;
	private String myEmail;
	private AdministratorUI myUI;
	private DataPollster myPollster;
	
	
	/**
	 * Constructs an Administrator object.
	 * 
	 * @param theFirstName The first name of the Administrator.
	 * @param theLastName The last name of the Administrator.
	 * @param theEmail The email of the Administrator.
	 */
	public Administrator(String theFirstName, String theLastName, String theEmail) {
		this.myFirstName = theFirstName;
		this.myLastName = theLastName;
		this.myEmail = theEmail;
	}
	
	/**
	 * Constructs an Administrator object, also initializing its DataPollster field.
	 */
	public Administrator(String theFirstName, String theLastName, String theEmail, DataPollster thePollster) {
		this(theFirstName, theLastName, theEmail);
		this.myPollster = thePollster;
	}
	
	/**
	 * Display a list of all volunteers, sorted in alphabetical order.
	 */
	public List<Volunteer> searchVolunteers() {
		List<Job> jobs = myPollster.getAllJobs();
		List<Volunteer> vols = new ArrayList<>(MAX_NUM_VOLS);
//		for (Job j : jobs) {
//			for (Volunteer v : j.myVolunteerList) {
//				
//			}
//		}
		
		// get list of vols
		// sort
		// output
		// return that sorted list
		
		return null;
	}

	public String getEmail() {
		return this.myEmail;
	}
	
	public String getFirstName() {
		return this.myFirstName;
	}
	
	public String getLastName() {
		return this.myLastName;
	}
}
