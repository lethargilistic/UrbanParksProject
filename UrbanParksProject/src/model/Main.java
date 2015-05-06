package model;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		JobList myJobList = new JobList();
		Schedule mySchedule = new Schedule(myJobList);
		DataPollster myPollster = new DataPollster(myJobList);
		
		ArrayList<Park> myParkList = new ArrayList<Park>();
		Park myPark = new Park("Bobcat Park", "Hugo", 98335);
		Park myPark2 = new Park("Seahurt Park", "Burien", 98106);
		
		myParkList.add(myPark);
		myParkList.add(myPark2);
		
		
		ParkManager myManager = new ParkManager(mySchedule, myPollster, myParkList);
		myManager.initialize();

	}
	
	
	
	
	//NOTE: when creating an account for volunteer, don't forget to add him into the myVolunteerList in JobList class.

}
