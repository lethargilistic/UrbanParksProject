package model;

import java.util.Scanner;

/**
 * A user interface for the login/register sequence of the program.
 * @author Taylor Gorman
 * @version 9 May 2015
 *
 */
public class MainUI {
	
	private Scanner myScanner = new Scanner(System.in);
	
	//Initialize UI.
	public void initialize() {
		greetUser();
	}
	
	public void greetUser() {
		System.out.println("Welcome to Urban Park's Volunteer Program!");
	}
	
	
	//Get user login choice.
	public int getLoginChoice() {
		displayLoginChoices();
		return getUserInt();
	}
			
	public void displayLoginChoices() {
		System.out.println("\n1) Existing User");
		System.out.println("2) New User");
		System.out.println("3) Exit\n");
	}
	
	
	
	public String getReturnEmail() {
		System.out.println("\nPlease enter your e-mail address to login:");
		return getUserString();
	}
	
	
	public String getNewEmail() {
		System.out.println("\nWhat is your e-mail address?");
		return getUserString();
	}
	
	public String getFirstName() {
		System.out.println("\nWhat is your first name?");
		return getUserString();
	}
	
	public String getLastName() {
		System.out.println("\nWhat is your last name?");
		return getUserString();
	}
	
	public String getUserType() {
		System.out.println("\nWhat type of user are you?");
		System.out.println("1) Volunteer");
		System.out.println("2) Park Manager");
		System.out.println("3) Administrator");
		
		int userType = getUserInt();
		
		switch (userType) {
			case 1: return "Volunteer";
			case 2: return "ParkManager";
			case 3: return "Administrator";
			default: displayInvalidChoice(); return null;
		}
	}
	
	
	
	
	
	public void displayExit() {
		System.out.println("\n--End Program--");
	}
	
	public void displayInvalidChoice() {
		System.out.println("\nSorry, but your choice was invalid.");
	}
	
	public void displayInvalidEmail() {
		System.out.println("\nSorry, but your e-mail address was not recognized.");
	}
	
	
	private int getUserInt() {
		int userInput = 0;
		
		if(myScanner.hasNextInt()) {
			userInput = myScanner.nextInt();
		}
		
		System.out.println("User Input: " + userInput);
		
		return userInput;
	}
	
	private String getUserString() {
		
		String userInput = myScanner.nextLine();
		
		if(userInput.equals("")) {
			userInput = myScanner.nextLine();
		} 
		
		return userInput;
	}
}
