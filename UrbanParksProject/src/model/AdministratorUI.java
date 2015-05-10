package model;

import java.util.Scanner;

/**
 * 
 * @author Reid Thompson
 *
 */
public class AdministratorUI {

	// All parsing happens here.
	// All options are int choices.
	
	//Ready User Input
	private Scanner in = new Scanner(System.in);
	
	/**
	 * Return the commands that the Administrator can use.
	 */
	public void displayCommands() {
		//TODO
	}
	
	/**
	 * Determine what command the user has typed, and call Administrator methods accordingly.
	 */
	public void parseCommand(String command) {
		//TODO
		//switch command
		//if 1
		//diplayVolunteers()
		//if 2
		//logout
	}
	
	private String getCommand() {	
		return in.nextLine();
	}
	
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
