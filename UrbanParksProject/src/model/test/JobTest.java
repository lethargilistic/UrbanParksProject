package model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import model.DataPollster;
import model.Job;
import model.JobList;
import model.Park;
import model.ParkManager;
import model.Schedule;
import model.UserList;

import org.junit.Before;
import org.junit.Test;

public class JobTest {
	
	private Job myJob;
	private Park myPark;
	private ParkManager myParkManager;
	
	@Before
	public void setUp() throws Exception {
		String startDate = "06111992";
		String endDate = "06111992";
		
		JobList jl = new JobList();
		UserList ul = new UserList();
		Schedule s = new Schedule(jl, ul);
		DataPollster dp = new DataPollster(jl, ul);
		
		Park p = new Park ("Boolean Park", "Tacoma", 19222);
		List<Park> pList = new ArrayList<>();
		pList.add(p);
		
		myParkManager = new ParkManager(s, dp, pList, "tjsg1992@gmail.com");
		
		
		myJob = new Job(myPark, 2, 3, 4, 
					    startDate, endDate,
					    myParkManager.getEmail(),
					    new ArrayList<ArrayList<String>>());
	}

	@Test
	public void constructorTest() {
		assertEquals(2, myJob.getLightMax());
		assertEquals(3, myJob.getMediumMax());
		assertEquals(4, myJob.getHeavyMax());
		assertEquals(0, myJob.getLightCurrent());
		assertEquals(0, myJob.getMediumCurrent());
		assertEquals(0, myJob.getHeavyCurrent());
		
		
		for (int i = 1; i <= 5; i++)
		{
			Job j = new Job(myPark, 2,3,4, 
							"06" + Integer.toString(11+i) + 1992, 
							"06" + Integer.toString(11+i) + 1992, 
							"moverby@gmail.com",
						    new ArrayList<ArrayList<String>>());
			assertEquals(i, j.getJobID());
		}
	}
	
	@Test
	public void incrementTest() {
		myJob.incrementLight();
		
		myJob.incrementMedium();
		myJob.incrementMedium();
		myJob.incrementMedium();
		myJob.incrementMedium();

		assertEquals(0, myJob.getHeavyCurrent());
		assertEquals(4, myJob.getHeavyMax());
		
		assertEquals(1, myJob.getLightCurrent());
		assertEquals(2, myJob.getLightMax());
		
		assertEquals(3, myJob.getMediumCurrent());
		assertEquals(3, myJob.getMediumMax());
	}
	
	@Test
	public void roomTest() {
		assertTrue(myJob.hasLightRoom());
		assertTrue(myJob.hasMediumRoom());
		assertTrue(myJob.hasHeavyRoom());
		
		myJob.incrementLight();
		myJob.incrementLight();
		
		myJob.incrementMedium();
		myJob.incrementMedium();
		myJob.incrementMedium();
		myJob.incrementMedium();
		
		myJob.incrementHeavy();
		myJob.incrementHeavy();
		
		assertFalse(myJob.hasLightRoom());
		assertFalse(myJob.hasMediumRoom());
		assertTrue(myJob.hasHeavyRoom());
		assertTrue(myJob.hasRoom());
		
		myJob.incrementHeavy();
		myJob.incrementHeavy();
		
		assertFalse(myJob.hasHeavyRoom());
		assertFalse(myJob.hasRoom());
	}

}
