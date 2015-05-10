package model;

import java.util.Scanner;

/**
 * A class for the console user interface for Administrators.
 * 
 * @author Reid Thompson
 * @version 5.10.2015
 */
public class AdministratorUI {
	
	//Ready User Input
	private Scanner myIn;
	
	public AdministratorUI() {
		myIn = new Scanner(System.in);
	}
	
	/**
	 * Controls the whole flow of interaction regarding this UI class and the Administrator class.
	 */
	public void initializeUI() {
		listCommands();
		String choice = myIn.nextLine();
		boolean contSession = parseCommand(choice);
		
	}
	
	/**
	 * Prints the commands that the Administrator can select.
	 */
	private void listCommands() {
		System.out.println("\n------------------------------------------\n"
				+ "Administrator Menu\n"
				+ "\nWhat would you like to do?\n"
				+ "Please enter the option number, ie. 2 for Logout.");
		System.out.println("1) Search Volunteers by Last Name\n");
		System.out.println("2) Logout\n");
	}
	
	/**
	 * 
	 */
	// getInt here
	
	/**
	 * Determine what command the user has typed, and call Administrator methods accordingly.
	 * 
	 * @param theCommand is the string entered by the Administrator indicating what they
	 * would like to do within the system.
	 * @return false if the user wishes to log out and true otherwise.
	 */
	public boolean parseCommand(String theCommand) {
		int choice = -1;
		final Scanner scan = new Scanner(theCommand);
		boolean stayLoggedIn = true;
		
		// searches string until it encounters a number, which is interpreted
		// as the desired command of the Administrator
		while (!scan.hasNextInt()) {
			scan.next();
		}
		choice = scan.nextInt();
		scan.close();
		
		if (choice <= 0) {
			System.out.println("Invalid command selected. Please try again.\n");
			listCommands(); // should this call be here...? or would we call it from a different class?
		} else if (choice == 1) {
			displayVolunteers();
		} else if (choice == 2) {
			logout();
			stayLoggedIn = false;
		} else { //
			System.out.println("Invalid command selected. Please try again.\n");
			listCommands(); // should this call be here...? or would we call it from a different class?
		}
		
		return stayLoggedIn;
	}
	
	/**
	 * Gets the command last entered by the Administrator indicating what they would like
	 * to do within the system.
	 * 
	 * @return a string indicating their command.
	 */
	private String getCommand() {	
		return myIn.nextLine();
	}
	
	/**
	 * 
	 */
	public void displayVolunteers() {
		//TODO
		//VolunteerList = Administrator.searchVolunteers()
		//Print out list
	}
	
	public void logout() {
		//TODO
		//Return control to MainUI
	}
}
