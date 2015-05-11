package model.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.DataPollster;
import model.JobList;
import model.Park;
import model.ParkManager;
import model.Schedule;
import model.UserList;

import org.junit.Before;
import org.junit.Test;

public class ParkManagerTest {

	private ParkManager myManager;
	private Schedule mySchedule;
	private DataPollster myPollster;
	private JobList myList;
	private UserList userList;
	private List<Park> myParks;

	@Before
	public void setUp() throws Exception {
		myList = new JobList();
		mySchedule = new Schedule(myList, userList);
		myPollster = new DataPollster(myList, userList);
		myParks = new ArrayList<Park>();
		myParks.add(new Park("Bobcat Park", "Seattle", 98304));
		myManager = new ParkManager(mySchedule, myPollster, myParks, "arshdeep@gmail.com");
	}

//TODO: This is not testable automatically. It requires humaninput at several points.
	//Next time, we can't have the model containing the UI.
//	//TODO: Should be removed in favor of having the commands be processed in the UI.
//	@Test
//	public void parseCommandTest() {
//		assertFalse(myManager.parseCommand("quit"));
//		assertFalse(myManager.parseCommand("Quit"));
//		assertFalse(myManager.parseCommand("q  uit"));
//		
//		assertTrue(myManager.parseCommand("new job"));
//		assertTrue(myManager.parseCommand("NEW job"));
//		assertTrue(myManager.parseCommand("new"));
//		assertFalse(myManager.parseCommand("new jobzo"));
//	}
}
