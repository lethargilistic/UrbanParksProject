package model;

import java.util.Scanner;

/**
 * A class for the console user interface for Administrators.
 * 
 * @author Reid Thompson
 * @version 5.10.2015
 */
public class AdministratorUI {

	// TODO:
	// All parsing happens here.
	// Going to have to called by mainUI.
	// Need to review Taylor's design changes.
	
	//Ready User Input
	private Scanner myIn = new Scanner(System.in);
	
	/**
	 * Prints the commands that the Administrator can select.
	 */
	public void displayCommands() {
		System.out.println("\n------------------------------------------\nAdministrator Menu\n\nWhat would you like to do?");
		System.out.println("1) Search Volunteers by Last Name\n");
		System.out.println("2) Logout\n");
	}
	
	/**
	 * Determine what command the user has typed, and call Administrator methods accordingly.
	 * 
	 * @param theCommand is the string entered by the Administrator indicating what they
	 * would like to do within the system.
	 */
	public void parseCommand(String theCommand) {
		int choice = -1;
		final Scanner scan = new Scanner(theCommand);
		
		// searches string until it encounters a number, which is interpreted
		// as the desired command of the Administrator
		while (!scan.hasNextInt()) {
			scan.next();
		}
		choice = scan.nextInt();
		scan.close();
		
		if (choice <= 0) {
			System.out.println("Invalid command selected. Please try again.\n");
			displayCommands();
		} else if (choice == 1) {
			displayVolunteers();
		} else if (choice == 2) {
			logout();
		} else { //
			System.out.println("Invalid command selected. Please try again.\n");
			displayCommands();
		}
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
