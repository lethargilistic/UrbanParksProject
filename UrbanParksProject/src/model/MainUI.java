package model;

import java.util.Scanner;

/**
 * A user interface for the login/register sequence of the program.
 * @author Taylor Gorman
 * @version 8 May 2015
 *
 */
public class MainUI {
	
	private Scanner myScanner = new Scanner(System.in);
	
	public void initialize() {
		greetUser();
		userLogin();
	}
	
	public void userLogin() {
		displayLoginChoices();
		int choice = getUserChoice();
		
		switch(choice) {
			case 1: requestLogin(); break;
			case 2: requestRegister(); break;
			case 3: System.out.println("\n--End Program--"); Main.closeProgram(); break;
			default: System.out.println("Invalid choice."); userLogin(); break; //Loop back if invalid
		}
	}
	
	public void greetUser() {
		System.out.println("Welcome to Urban Park's Volunteer Program!");
	}
	
	public void displayLoginChoices() {
		System.out.println("\n1) Existing User");
		System.out.println("2) New User");
		System.out.println("3) Exit\n");
	}
	
	private int getUserChoice() {
		return myScanner.nextInt();
	}
	
	private String getUserString() {
		return myScanner.nextLine();
	}
	
	private void parseUserCommand(String theCommand) {

		
	}
	
	public String requestLogin() {
		System.out.println("Please enter your e-mail address to login:");
		return myScanner.nextLine();
	}
	
	public void requestRegister() {
		
	}
	
	public void exitDisplay() {
		System.out.println("\n--End Program--");
	}
	
	public void invalidChoice() {
		System.out.println("\nSorry, but your choice was invalid.");
	}
}
