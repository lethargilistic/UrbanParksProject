package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Administrators can search all Volunteers alphabetically.
 * @author Reid Thompson
 * @author Taylor Gorman
 * @version 8 May 2015
 */
public class Administrator {
	
	// need to add in methods for UI interaction...
	
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
	 * Display a list of all volunteers, sorted in alphabetical order by email address.
	 */
	public List<String> displayVolunteers() {
		List<Job> jobs = myPollster.getAllJobs();
		List<String> vols = new ArrayList<>(MAX_NUM_VOLS);
		
		// get list of volunteers' email addresses
		for (Job j : jobs) {
			for (String s : j.myVolunteerList) {
				if (!vols.contains(s)) {
					vols.add(s);
				}
			}
		}

		// sort
		Collections.sort(vols);
		
		// output
		for (int i = 0; i < vols.size(); i++) {
			System.out.println(vols.get(i));
		}
		
		return vols; // why do we need to return this data?
					 // also, I made the List be of Strings not of Volunteers...
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
