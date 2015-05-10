package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Main {

	public static MainUI myUI;
	

	public static void main(String[] args) {
		
		SaveManager mySaveManager = new SaveManager();
		JobList myJobList = mySaveManager.loadJobList();
		UserList myUserList = mySaveManager.loadUserList();
		String[] userInfo;
		
		Schedule mySchedule = new Schedule(myJobList);
		DataPollster myPollster = new DataPollster(myJobList, myUserList);
		myUI = new MainUI();
		
		myUI.initialize();
		
		//We return to the top of the loop whenever the user logs out, and prompt them to login again.
		//The loop continues until the user selects Exit.
		while(true) {
			userInfo = directLogin(mySchedule, myPollster);
			
			if(userInfo == null) { //If the command or information entered was invalid, we try again.
				directLogin(mySchedule, myPollster);
			}
			
			if(userInfo[0].equals("login")) {
				giveControl(userInfo[1], mySchedule, myPollster);
			}
			
			if(userInfo[0].equals("register")) {
				mySchedule.addUser(userInfo[1], userInfo[2], userInfo[3], userInfo[4]);
				giveControl(userInfo[1], mySchedule, myPollster);
			}
			
			myUI.greetUser();
		}
		
	}
	
	
	
	/**
	 * Prompt the user to either login, register, or exit.<br>
	 * Then, ask the user for login or register details.
	 */
	public static String[] directLogin(Schedule theSchedule, DataPollster thePollster) {
		int loginCommand = myUI.getLoginChoice();
		String[] userInfo = null;
		
		switch (loginCommand) {
			case 1: userInfo = loginUser(theSchedule, thePollster); break;
			case 2: userInfo = registerUser(theSchedule, thePollster); break;
			case 3: myUI.displayExit(); closeProgram(); break; //Ends program.
			default: myUI.displayInvalidChoice(); break;
		}
		return userInfo;
	}
	
	/*
	 * Return a String array that specifies the user as logging in, and the e-mail of the user.
	 */
	private static String[] loginUser(Schedule mySchedule, DataPollster myPollster) {
		
		String userEmail = myUI.getReturnEmail();
		String[] userInfo = null;
		
		if(myPollster.checkEmail(userEmail)) {
			userInfo = new String[2];
			userInfo[0] = "login";
			userInfo[1] = userEmail;
			
		} else {
			myUI.displayInvalidEmail();
		}
		return userInfo;
	}
	
	/*
	 * Return a String array that specifies the user as registering, along with info on that user.
	 */
	private static String[] registerUser(Schedule mySchedule, DataPollster myPollster) {
		String[] userInfo = new String[5];
		
		userInfo[0] = "register";
		userInfo[1] = myUI.getNewEmail();
		userInfo[2] = myUI.getFirstName();
		userInfo[3] = myUI.getLastName();
		userInfo[4] = myUI.getUserType();
		
		if(userInfo[4] == null) userInfo = null;
		
		return userInfo;
	}
	
	
	/*
	 * Transfer control to the user, specified by their e-mail address.
	 */
	public static void giveControl(String theEmail, Schedule mySchedule, DataPollster myPollster) {
		String userType = myPollster.getUserType(theEmail);
		
		if(userType.equals("ParkManager")) {
			List<Park> myManagedParks = myPollster.getParkList(theEmail);
			String myEmail = "tjsg1992@gmail.com";
			ParkManager myManager = new ParkManager(mySchedule, myPollster, myManagedParks, myEmail);
			myManager.initialize();
		}
		
		//TODO for Volunteer and Administrator
	}
	
	
	
	
	
	public static void closeProgram() {
		System.exit(0);
	}

}
