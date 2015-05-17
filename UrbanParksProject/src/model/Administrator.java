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
public class Administrator extends User {
	
	// these 2 fields MUST GO!
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
		super(theFirstName, theLastName, theEmail);
		super.setUserType("Administrator");
		this.myUI = new AdministratorUI();
	}
	
	// this work will be done by GUI
	
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
	
	// this work will be done by GUI
	
	/**
	 * Calls appropriate methods if valid input is given. Otherwise, it
	 * reprompts the user with the original list of commands.
	 * 
	 * @param theChoice is the integer of the option to be executed by the user.
	 * @return false if the user wishes to log out, and true otherwise.
	 */
	private boolean executeOptionChosen(final int theChoice) {
		boolean stayLoggedIn = true;
		if (theChoice <= 0 || theChoice > 3) {
			System.out.println("Invalid command selected. Please try again.\n");
			initialize(); // restart UI interaction
		} else { // valid input was given
			switch(theChoice) { // no default case needed because of original if test
				case 1: // list all volunteers by last name, first name (sorted ascending)
					List<User> allVols = myPollster.getUserList().getVolunteerListCopy();
					Collections.sort(allVols, new Comparator<User>() {
						
						// to sort Volunteers by last name ascending and then by first name ascending
						@Override
						public int compare(User theVol1, User theVol2) {
							int result = theVol1.getLastName().compareTo(theVol2.getLastName());
							if (result == 0) {
								result = theVol1.getFirstName().compareTo(theVol2.getFirstName());
							}
							return result;
						}
						
					});
					displayAllVolunteersLNFN(allVols);
					break;
				case 2: // search volunteers by last name
					System.out.println("Option 2 selected.\n");
				    String lastName = myUI.promptForVolsLastName();
				    boolean lastNameValid = !lastName.equals(""); // other conditions to add?
					while (!lastNameValid) {
						System.out.println("No last name was entered. Please try again.\n");
						lastName = myUI.promptForVolsLastName();
						lastNameValid = !lastName.equals("");
					} // lastName is valid now
					// get and output list of Volunteers with matching last names
					List<User> matchingVols = getMatchingVolunteers(lastName);
					if (!matchingVols.isEmpty()) {
						Collections.sort(matchingVols, new Comparator<User>() {
	
							// to sort Volunteers in ascending order based on first name
							@Override
							public int compare(final User theVol1, final User theVol2) {
								return theVol1.getFirstName().compareTo(theVol2.getFirstName());
							}
							
						});
						displayMatchingVolunteers(lastName, matchingVols);
					} else {
						System.out.println("There were no Volunteers matching the last name " + lastName);
					}
					break;
				case 3: // logout
					System.out.println("Option 3 selected.\n"
							+ "Logging out now.\n");
					stayLoggedIn = false;
					break;
			}
		}
		return stayLoggedIn;
	}
	
	// this work will be done by GUI
	
	/**
	 * Returns a List of all Volunteers matching the given last name.
	 * 
	 * @param theLastName is the last name by which to search for Volunteer matches.
	 * @return a List of Volunteers with the same last name as theLastName.
	 */
	private List<User> getMatchingVolunteers(String theLastName) {
		List<User> matchingVols = new ArrayList<>();
		List<User> allVols = myPollster.getUserList().getVolunteerListCopy();
		
		for (int i = 0; i < allVols.size(); i++) {
			final User currVol = allVols.get(i);
			if (currVol.getLastName().equals(theLastName)) {
				matchingVols.add(currVol);
			}
		}
		
		return matchingVols;
	}
	
	// this work will be done by GUI
	
	/**
	 * Outputs the list of Volunteers with matching last names sorted by first name in ascending order.
	 * @param theLastName is the last name used for matching.
	 * @param theMatchingVols is the list of Volunteers with matching last names.
	 */
	private void displayMatchingVolunteers(final String theLastName, final List<User> theMatchingVols) {
		System.out.println("Here are all of the Volunteers whose last name matches " + theLastName);
		
		for (int i = 0; i < theMatchingVols.size(); i++) {
			final User v = theMatchingVols.get(i);
			System.out.println(v.getFirstName() + " " + v.getLastName());
			System.out.println("Email: " + v.getEmail());
			List<Job> jobs = myPollster.getVolunteerJobs((Volunteer) v);
			if (!jobs.isEmpty()) {
				System.out.println("Jobs signed up for: ");
				for (int j = 0; j < jobs.size(); j++) {
					final Job job = jobs.get(j);
					System.out.println("\tJob ID #" + job.getJobID());
				}
			} else {
				System.out.println("This volunteer has not signed up for any jobs.\n");
			}
		}
	}
	
	// this work will be done by GUI
	
	/**
	 * 
	 */
	private void displayAllVolunteersLNFN(final List<User> theVols) {
		System.out.println("Here is the list of all Volunteers: Last name, First name\n");
		
		for (int i = 0; i < theVols.size(); i++) {
			final User v = theVols.get(i);
			System.out.println(v.getLastName() + ", " + v.getFirstName());
		}
		
	}
}
