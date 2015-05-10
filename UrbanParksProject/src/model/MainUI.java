package model;

import java.util.InputMismatchException;
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
		System.out.println("\n------------------------------------------\nWelcome to Urban Park's Volunteer Program!");
	}
	
	
	//Get user login choice.
	public int getLoginChoice() {
		while(true) {
			displayLoginChoices();
			
			int userChoice = getUserInt();
			
			if(userChoice > 0 && userChoice < 4) return userChoice;
			else displayInvalidChoice();
		}
	}
			
	public void displayLoginChoices() {
		System.out.println("\n1) Existing User");
		System.out.println("2) New User");
		System.out.println("3) Exit\n");
	}
	
	
	
	public String getReturnEmail() {
		System.out.println("------------------------------------------\n\nPlease enter your e-mail address to login:");
		return getUserString();
	}
	
	
	public String getNewEmail() {
		System.out.println("------------------------------------------\n\nWhat is your e-mail address?");
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
			default: displayInvalidChoice(); return getUserType();
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
				} else{
					myScanner.next();
				}

			return userInput;
		}

	
	private String getUserString() {
		
		String userInput = myScanner.nextLine();
		
		if(userInput.equals("")) { //TODO, maybe make this a while so that it will continuously 
									//prompt the user, instead of just once?
			userInput = myScanner.nextLine();
		}
		
		return userInput;
	}
}
