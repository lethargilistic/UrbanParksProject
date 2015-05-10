package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The class for the Administrator user for the system.
 * 
 * Administrators can search for Volunteers by last name.
 * 
 * @author Reid Thompson
 * @author Taylor Gorman
 * @version 8 May 2015
 */
public class Administrator {
	
	// This is a test!
	
	private static final int MAX_NUM_VOLS = 1000;
	
	//Class Variables
	private String myFirstName;
	private String myLastName;
	private String myEmail;
	private AdministratorUI myUI;
	private DataPollster myPollster;
	
	public Administrator(String theEmail) {
		this.myEmail = theEmail;
	}
	
	
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
		this.myUI = new AdministratorUI();
	}
	
	/**
	 * Constructs an Administrator object, also initializing its DataPollster field.
	 */
	public Administrator(String theFirstName, String theLastName, String theEmail, DataPollster thePollster) {
		//TODO remove this constructor, implement getters and setters for First and Last name instead.
		this(theFirstName, theLastName, theEmail);
		this.myPollster = thePollster;
		this.myUI = new AdministratorUI();
	}
	
	public Administrator(DataPollster thePollster, String theEmail) {
		this.myPollster = thePollster;
		this.myEmail = theEmail;
		this.myUI = new AdministratorUI();
	}
	
	/**
	 * Returns the email address of this Administrator.
	 * @return the email address of this Administrator.
	 */
	public String getEmail() {
		return this.myEmail;
	}
	
	/**
	 * Returns the first name of this Administrator.
	 * @return the first name of this Administrator.
	 */
	public String getFirstName() {
		return this.myFirstName;
	}
	
	/**
	 * Returns the last name of this Administrator.
	 * @return the last name of this Administrator.
	 */
	public String getLastName() {
		return this.myLastName;
	}
	
	/**
	 * Initializes the UI for the Administrator user.
	 */
	public void initialize() {
		myUI.listCommands();
		int choice = myUI.getUserInt();
		boolean stayLoggedIn = executeOptionChosen(choice);
		if (stayLoggedIn) { // reprompt user to with list of commands - continuing their interaction with the UI
			initialize();
		} // the implied else would return control back to whatever method called initializeUI() - probably mainUI
	}
	
	/**
	 * Calls appropriate methods if valid input is given. Otherwise, it
	 * reprompts the user with the original list of commands.
	 * 
	 * @param theChoice is the integer of the option to be executed by the user.
	 * @return false if the user wishes to log out, and true otherwise.
	 */
	private boolean executeOptionChosen(final int theChoice) {
		boolean stayLoggedIn = true;
		if (theChoice <= 0 || theChoice > 2) {
			System.out.println("Invalid command selected. Please try again.\n");
			initialize(); // restart UI interaction
		} else { // valid input was given
			switch(theChoice) { // no default case needed because of original if test
				case 1: // search volunteers by last name
				    String lastName = myUI.promptForVolsLastName();
				    boolean lastNameValid = !lastName.equals(""); // other conditions to add?
					while (!lastNameValid) {
						System.out.println("No last name was entered. Please try again.\n");
						lastName = myUI.promptForVolsLastName();
						lastNameValid = !lastName.equals("");
					} // lastName is valid now
					// get and output list of Volunteers with matching last names
					List<Volunteer> matchingVols = getMatchingVolunteers(lastName);
					Collections.sort(matchingVols, new Comparator<Volunteer>() {

						// to sort Volunteers in ascending order based on first name
						@Override
						public int compare(final Volunteer theVol1, final Volunteer theVol2) {
							return theVol1.getFirstName().compareTo(theVol2.getFirstName());
						}
						
					});
					displayMatchingVolunteers(lastName, matchingVols);
					break;
				case 2: // logout
					System.out.println("Option 2 selected.\n"
							+ "Logging out now.\n");
					stayLoggedIn = false;
					break;
			}
		}
		return stayLoggedIn;
	}
	
	/**
	 * Returns a List of all Volunteers matching the given last name.
	 * 
	 * @param theLastName is the last name by which to search for Volunteer matches.
	 * @return a List of Volunteers with the same last name as theLastName.
	 */
	private List<Volunteer> getMatchingVolunteers(String theLastName) {
		List<Volunteer> matchingVols = new ArrayList<>(MAX_NUM_VOLS);
		List<Volunteer> allVols = myPollster.getUserList().getVolunteerCopyList();
		
		for (int i = 0; i < allVols.size(); i++) {
			final Volunteer currVol = allVols.get(i);
			if (currVol.getLastName().equals(theLastName)) {
				matchingVols.add(currVol);
			}
		}
		
		return matchingVols;
	}
	
	/**
	 * Outputs the list of Volunteers with matching last names sorted by first name in ascending order.
	 * @param theLastName is the last name used for matching.
	 * @param theMatchingVols is the list of Volunteers with matching last names.
	 */
	private void displayMatchingVolunteers(final String theLastName, final List<Volunteer> theMatchingVols) {
		System.out.println("Here are all of the Volunteers whose last name matches " + theLastName);
		
		for (int i = 0; i < theMatchingVols.size(); i++) {
			final Volunteer currVol = theMatchingVols.get(i);
			System.out.println(currVol.getFirstName() + " " + currVol.getLastName());
		}
	}
	
	
//	/**
//	 * Display a list of all volunteers, sorted in alphabetical order by email address.
//	 */
//	public List<String> displayVolunteers() {
//		List<Job> jobs = myPollster.getAllJobs();
//		List<String> vols = new ArrayList<>(MAX_NUM_VOLS);
//		
//		// get list of volunteers' email addresses
//		for (Job j : jobs) {
//			for (String s : j.myVolunteerList) {
//				if (!vols.contains(s)) {
//					vols.add(s);
//				}
//			}
//		}
//
//		// sort
//		Collections.sort(vols);
//		
//		// output
//		for (int i = 0; i < vols.size(); i++) {
//			System.out.println(vols.get(i));
//		}
//		
//		return vols; // why do we need to return this data?
//					 // also, I made the List be of Strings not of Volunteers...
//	}
}
