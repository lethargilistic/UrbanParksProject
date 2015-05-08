package model;

import java.util.List;

/**
 * Administrators can search all Volunteers alphabetically.
 * @author Taylor Gorman
 * @version 8 May 2015
 *
 */
public class Administrator {
	
	//Class Variables
	private String myFirstName;
	private String myLastName;
	private String myEmail;
	private AdministratorUI myUI;
	
	
	/**
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
	 * Display a list of all volunteers, sorted in alphabetical order.
	 */
	public List<Volunteer> searchVolunteers() {
		//TODO
		return null;
	}
}
