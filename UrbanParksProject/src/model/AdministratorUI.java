package model;

import java.util.List;
import java.util.Scanner;

/**
 * A class for the console user interface for Administrators.
 * 
 * @author Reid Thompson
 * @version 5.10.2015
 */
public class AdministratorUI {
	
	/**
	 * A Scanner object to read input for the Administrator's UI.
	 */
	private Scanner myIn;
	
// HOPEFULLY WE CAN CHANGE THE UI CLASSES LATER TO HAVE USER OBJECTS INSTEAD OF THE OPPOSITE WAY
//	/**
//	 * An Administrator object to be associated with this UI object.
//	 */
//	private Administrator myAdmin;
	
	/**
	 * Constructs an AdministratorUI object.
	 */
	public AdministratorUI() {
		myIn = new Scanner(System.in);
	}
	
//	/**
//	 * Controls the whole flow of interaction regarding this UI class and the Administrator class.
//	 */
//	public void initializeUI() {
//		listCommands();
//		String choiceStr = getInput();
//		int choice = getFirstIntChoice(choiceStr);
//		boolean stayLoggedIn = executeOptionChosen(choice);
//		if (stayLoggedIn) { // reprompt user to with list of commands - continuing their interaction with the UI
//			initializeUI();
//		} // the implied else would return control back to whatever method called initializeUI() - probably mainUI
//	}
	
	/**
	 * Prints the commands that the Administrator can select.
	 */
	public void listCommands() {
		System.out.println("\n------------------------------------------\n"
				+ "Administrator Menu\n"
				+ "\nWhat would you like to do?\n"
				+ "Please enter the option number, ie. 2 for Logout.");
		System.out.println("1) Search Volunteers by Last Name\n");
		System.out.println("2) Logout\n");
	}
	
	/**
	 * Returns the integer of the command chosen by the user. If there are more than one
	 * integer on a line, it takes the first one.
	 * 
	 * @param theChoice a string containing the user's response to the list of commands.
	 * @return the command chosen by the user.
	 */
	public int getFirstIntChoice(final String theChoice) {
		final Scanner scan = new Scanner(theChoice);
		int choice = -1;
		while (scan.hasNext()) {
			if (scan.hasNextInt()) {
				choice = scan.nextInt();
				break;
			}
		}
		scan.close();
		return choice;
	}
	
//	/**
//	 * Calls appropriate Administrator methods if valid input is given. Otherwise, it
//	 * reprompts the user with the original list of commands.
//	 * 
//	 * @param theChoice is the integer of the option to be executed by the user.
//	 * @return false if the user wishes to log out, and true otherwise.
//	 */
//	public boolean executeOptionChosen(final int theChoice) {
//		boolean stayLoggedIn = true;
//		if (theChoice <= 0 || theChoice > 2) {
//			System.out.println("Invalid command selected. Please try again.\n");
//			initializeUI(); // restart UI interaction
//		} else { // valid input was given
//			switch(theChoice) { // no default case needed because of original if test
//				case 1: // search volunteers by last name
//				    String lastName = promptForVolsLastName();
//				    boolean lastNameValid = !lastName.equals(""); // other conditions to add?
//					while (!lastNameValid) {
//						System.out.println("No last name was entered. Please try again.\n");
//						lastName = promptForVolsLastName();
//						lastNameValid = !lastName.equals("");
//					} // lastName is valid now
//					// get and output list of Volunteers with matching last names
//					// Step 1) List<Volunteer> matchingVols = getMatchingVolunteers(lastName)
//					// Step 2) displayMatchingVolunteers(matchingVols);
//				case 2: // logout
//					System.out.println("Option 2 selected.\n"
//							+ "Logging out now.\n");
//					stayLoggedIn = false;
//			}
//		}
//		return stayLoggedIn;
//	}
	
	/**
	 * Gets the input last entered by the Administrator indicating what they would like
	 * to do within the system.
	 * 
	 * @return a string indicating their command.
	 */
	public String getInput() {	
		return myIn.nextLine();
	}
	
	/**
	 * Returns a String of the last name of the Volunteer to search for.
	 * 
	 * @return a String of the last name of the Volunteer to search for.
	 */
	public String promptForVolsLastName() {
		System.out.println("Option 1 selected.\n"
				+ "Please enter the last name of the Volunteer to search for: \n");
		return getInput();
	}
	
	// access Vol list thru DataPollster thru UserList methods
	
//	/**
//	 * Returns a list of relevant Volunteers based on the last name entered.
//	 * 
//	 * @param theLastName is the last name of the Volunteer to search for.
//	 */
//	public List<Volunteer> getMatchingVolunteers(String theLastName) {
//		return null;
//	}
	
//	/**
//	 *  Would receive a list of relevant Volunteers.
//	 */
//	public void displayMatchingVolunteers(List<Volunteer> theMatchingVols) {
//		//TODO
//		//VolunteerList = Administrator.searchVolunteers()
//		//Print out list
//	}

// OLD METHOD - BROKE IT INTO SMALLER METHODS FOR CODE RE-USE
//	/**
//	 * Determine what command the user has typed, and call Administrator methods accordingly.
//	 * 
//	 * @param theCommand is the string entered by the Administrator indicating what they
//	 * would like to do within the system.
//	 * @return false if the user wishes to log out and true otherwise.
//	 */
//	private boolean parseCommand(String theCommand) {
//		int choice = -1;
//		final Scanner scan = new Scanner(theCommand);
//		boolean stayLoggedIn = true;
//		
//		// searches string until it encounters a number, which is interpreted
//		// as the desired command of the Administrator
//		while (!scan.hasNextInt()) {
//			scan.next();
//		}
//		choice = scan.nextInt();
//		scan.close();
//		
//		if (choice <= 0) {
//			 // should this call be here...? or would we call it from a different class?
//		} else if (choice == 1) {
//			displayVolunteers();
//		} else if (choice == 2) {
//			logout();
//			stayLoggedIn = false;
//		} else { //
//			System.out.println("Invalid command selected. Please try again.\n");
//			listCommands(); // should this call be here...? or would we call it from a different class?
//		}
//		
//		return stayLoggedIn;
//	}

//	(REID) I DON'T THINK THAT THIS METHOD IS NEEDED. A SIMPLE BOOLEAN CAN HANDLE "LOGGING OUT"
//  IT CAN DO THIS BY SIMPLY NOT REPROMPTING THE USER AND PRINTING THAT THEY ARE NOW BEING LOGGED OUT.
//	private void logout() {
//		//TODO
//		//Return control to MainUI
//	}
}
