package model;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		JobList myJobList = new JobList();
		Schedule mySchedule = new Schedule(myJobList);
		DataPollster myPollster = new DataPollster(myJobList);
		
		ArrayList<Park> myParkList = new ArrayList<Park>();
		Park myPark = new Park("Bobcat Park", "Hugo", 98335);
		myParkList.add(myPark);
		
		
		ParkManager myManager = new ParkManager(mySchedule, myPollster, myParkList);
		myManager.initialize();

	}

}
