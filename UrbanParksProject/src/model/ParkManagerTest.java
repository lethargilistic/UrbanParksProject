package model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ParkManagerTest {
	
	private ParkManager myManager;
	private Schedule mySchedule;
	private DataPollster myPollster;
	private JobList myList;

	@Before
	public void setUp() throws Exception {
		myList = new JobList();
		mySchedule = new Schedule(myList);
		myPollster = new DataPollster(myList);
		myManager = new ParkManager(mySchedule, myPollster);
	}

	@Test
	public void parseCommandTest() {
		assertFalse(myManager.parseCommand("quit"));
		assertFalse(myManager.parseCommand("Quit"));
		assertFalse(myManager.parseCommand("q  uit"));
		
		assertTrue(myManager.parseCommand("new job"));
		assertTrue(myManager.parseCommand("NEW job"));
		assertTrue(myManager.parseCommand("new"));
		assertFalse(myManager.parseCommand("new jobzo"));
	}

}
