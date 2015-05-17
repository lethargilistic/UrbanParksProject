package tests;

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
		ArrayList<String> vol;
		
		//Light
		assertEquals(0, myJob.getLightCurrent());
		vol = new ArrayList<>();
		vol.add("moverby@gmail.com");
		vol.add("Light");
		myJob.getVolunteerList().add(vol);
		assertEquals(1, myJob.getLightCurrent());
		vol = new ArrayList<>();
		vol.add("farkle@gmail.com");
		vol.add("Light");
		myJob.getVolunteerList().add(vol);
		assertEquals(2, myJob.getLightCurrent());
		
		//Medium
		assertEquals(0, myJob.getMediumCurrent());
		vol = new ArrayList<>();
		vol.add("jbehnen@gmail.com");
		vol.add("Medium");
		myJob.getVolunteerList().add(vol);
		assertEquals(1, myJob.getMediumCurrent());
		
		//Heavy
		assertEquals(0, myJob.getHeavyCurrent());
		vol = new ArrayList<>();
		vol.add("jazzmezz@gmail.com");
		vol.add("Heavy");
		myJob.getVolunteerList().add(vol);
		assertEquals(1, myJob.getHeavyCurrent());
	}
	
	@Test
	public void roomTest() {
		assertTrue(myJob.hasLightRoom());
		assertTrue(myJob.hasMediumRoom());
		assertTrue(myJob.hasHeavyRoom());
		
		ArrayList<String> vol0 = new ArrayList<>();
		ArrayList<String> vol1 = new ArrayList<>();
		ArrayList<String> vol2 = new ArrayList<>();
		ArrayList<String> vol3= new ArrayList<>();
		ArrayList<String> vol4 = new ArrayList<>();
		ArrayList<String> vol5 = new ArrayList<>();
		ArrayList<String> vol6 = new ArrayList<>();
		ArrayList<String> vol7 = new ArrayList<>();
		ArrayList<String> vol8 = new ArrayList<>();
		ArrayList<String> vol9 = new ArrayList<>();
		ArrayList<String> vol10 = new ArrayList<>();
		ArrayList<String> vol11 = new ArrayList<>();

		vol0.add("moverby@gmail.com");
		vol0.add("Light");
		myJob.getVolunteerList().add(vol0);
		assertTrue(myJob.hasLightRoom());
		vol1.add("moverby@gmail.com");
		vol1.add("Light");
		myJob.getVolunteerList().add(vol1);
		vol2.add("gotosleep@gmail.com");
		vol2.add("Light");
		myJob.getVolunteerList().add(vol2);
		
		vol3.add("yaaaaa@gmail.com");
		vol3.add("Medium");
		myJob.getVolunteerList().add(vol3);
		assertTrue(myJob.hasMediumRoom());
		vol4.add("foop@gmail.com");
		vol4.add("Medium");
		myJob.getVolunteerList().add(vol4);
		vol5.add("witchtable@gmail.com");
		vol5.add("Medium");
		myJob.getVolunteerList().add(vol5);
		
		vol6.add("whodunnit@gmail.com");
		vol6.add("Heavy");
		myJob.getVolunteerList().add(vol6);
		assertTrue(myJob.hasHeavyRoom());
		vol7.add("gitblame@gmail.com");
		vol7.add("Heavy");
		myJob.getVolunteerList().add(vol7);
		vol8.add("manager@gmail.com");
		vol8.add("Heavy");
		myJob.getVolunteerList().add(vol8);
		vol9.add("whomsoever@gmail.com");
		vol9.add("Heavy");
		myJob.getVolunteerList().add(vol9);
		
		assertFalse(myJob.hasLightRoom());
		assertFalse(myJob.hasMediumRoom());
		assertFalse(myJob.hasHeavyRoom());
		assertFalse(myJob.hasRoom());
		
		vol10.add("notmyemail@gmail.com");
		vol10.add("Heavy");
		myJob.getVolunteerList().add(vol10);
		vol11.add("taylorsall@hotmail.com");
		vol11.add("Heavy");
		myJob.getVolunteerList().add(vol11);
		
		assertFalse(myJob.hasHeavyRoom());
		assertFalse(myJob.hasRoom());
	}

}
