package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Main {

	public static MainUI myUI;
	

	public static void main(String[] args) {
		
		SaveManager mySaveManager = new SaveManager();
		String[] userInfo;
		
		
		myUI = new MainUI();
		
		myUI.initialize();
		
		//We return to the top of the loop whenever the user logs out, and prompt them to login again.
		//The loop continues until the user selects Exit.
		while(true) {
			JobList myJobList = mySaveManager.loadJobList();
			UserList myUserList = mySaveManager.loadUserList();
			Schedule mySchedule = new Schedule(myJobList);
			DataPollster myPollster = new DataPollster(myJobList, myUserList);
			
			userInfo = directLogin(mySchedule, myPollster);
			
			//If the command or information entered was invalid, we try again.
			if(userInfo == null || mySchedule == null || myPollster == null) {
				directLogin(mySchedule, myPollster);
			}
			
			if(userInfo[0].equals("login")) {
				giveControl(userInfo, mySchedule, myPollster);
			}
			
			if(userInfo[0].equals("register")) {
				mySchedule.addUser(userInfo[1], userInfo[2], userInfo[3], userInfo[4]);
				giveControl(userInfo, mySchedule, myPollster);
			}
			
			mySaveManager.saveJobList(myJobList);
			mySaveManager.saveUserList(myUserList);
			
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
	public static void giveControl(String[] theUserInfo, Schedule theSchedule, DataPollster thePollster) {
		String userType = thePollster.getUserType(theUserInfo[1]);
		
		if(userType.equals("ParkManager")) {
			List<Park> managedParks = thePollster.getParkList(theUserInfo[1]);
			String email = theUserInfo[1];
			ParkManager manager = new ParkManager(theSchedule, thePollster, managedParks, email);
			manager.initialize();
		}
		
		if(userType.equals("Volunteer")) {
			String email = theUserInfo[1];
			Volunteer volunteer = new Volunteer(theSchedule, thePollster, email);
			volunteer.initialize();
		}
		
		if(userType.equals("Administrator")) {
			String email = theUserInfo[1];
			Administrator administrator = new Administrator(thePollster, email);
			administrator.initialize();
		}
	}
	
	
	/**
	 * Breaks out of infinite loop in main method.
	 */
	public static void closeProgram() {
		System.exit(0);
	}

}
