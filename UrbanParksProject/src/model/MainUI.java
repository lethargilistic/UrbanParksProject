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
	
	
	
	
	public String getUserEmail() {
		System.out.println("\nPlease enter your e-mail address to login:");
		return myScanner.nextLine();
	}
	
	
	public int getUserRole() {
		return 0;
	}
	
	public String[] getUserContact() {
		String[] userInfo = new String[3];
		return userInfo;
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
		return myScanner.nextInt();
	}
	
	private String getUserString() {
		return myScanner.nextLine();
	}
}
