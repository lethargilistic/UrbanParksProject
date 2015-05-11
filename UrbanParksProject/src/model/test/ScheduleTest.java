package model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
import model.Volunteer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A test class for Schedule in the model package.
 * 
 * @author Reid Thompson
 * @date 5.3.2015
 */
public class ScheduleTest {

	/**
	 * Schedule object for testing.
	 */
	private Schedule mySchedule;
	
	/**
	 * Park object for testing.
	 */
	private Park myPark;
	
	/**
	 * Job object for testing.
	 */
	private Job myJob;
	
	private String myVolEmail;
	
	/**
	 * Volunteer object for testing.
	 */
	private Volunteer myVolunteer;
	
	private ParkManager myParkManager;
	
	/**
	 * Instantiates necessary data for testing methods.
	 */
	@Before
	public void setUp() {
		JobList myJoblist = new JobList();
		UserList myUserList = new UserList();
		List<Park> pList = new ArrayList<Park>();
		pList.add(myPark);
		mySchedule = new Schedule(myJoblist, myUserList);
		DataPollster dp = new DataPollster(myJoblist, myUserList);
	    myParkManager = new ParkManager(mySchedule, dp, pList, "tjsg1992@gmail.com");
		myPark = new Park("Wright Park", "Tacoma", 98403);
		myJob = new Job(myPark, 
						0, 10,
						0, 10,
						0, 10,
						"06172015",
						"06172015", 
						"tjsg1992@gmail.com",
						new ArrayList<String>());
		
		myVolunteer = new Volunteer("generic@gmail.com", "Bob", "Smith");
	}

	/**
	 * Testing if a valid job can be received.
	 */
	@Test
	public void test1ForReceiveJob() {
		mySchedule.receiveJob(myJob);
		//TODO: that duplication definitely needs to be removed from the API
		assertFalse("No job was added.", mySchedule.getJobList().getJobList().isEmpty());
		assertEquals("The incorrect job was added.", "[Wright Park]", mySchedule.getJobList().getJobList().toString()); 
	}
	
	/**
	 * Testing with a Job having invalid dates.
	 */
	@Test
	public void test2ForReceiveJob() {
		Job badDates = new Job(myPark, 
						0, 10,
						0, 10,
						0, 10,
						"06172015",
						"06152015", 
						"tjsg1992@gmail.com",
						new ArrayList<String>());
		boolean bool1 = mySchedule.receiveJob(badDates);
		assertFalse(bool1);
	}
	
	/**
	 * Testing with a Job that doesn't have an empty Volunteer list.
	 */
	@Test
	public void test4ForReceiveJob() {
		myJob.getVolunteerList().add(myVolEmail);
		boolean bool3 = mySchedule.receiveJob(myJob);
		assertFalse(bool3);
	}
	
	/**
	 * Testing with a Job with all zeroes for light, medium, and heavy
	 * work grade amounts.
	 */
	@Test
	public void test6ForReceiveJob() {
		Job noSpace  =  new Job(myPark, 
								0, 0,
								0, 0,
								0, 0,
								"06172015",
								"06172015", 
								"tjsg1992@gmail.com",
								new ArrayList<String>());
		boolean bool5 = mySchedule.receiveJob(noSpace);
		assertFalse(bool5);
	}

	//This is prevented by an exception in the incrementMethods. And a job shouldn't have Volunteers before it is received anyway. TODO: Discuss removal.
	/**
	 * Testing with a Job where the number of light volunteers exceeds its max.
	 */
	@Test
	public void test7ForReceiveJob() {
		int itrs = 5;
		Job overflowed = new Job(myPark, 
								 3, 2,
								 0, 2,
								 0, 2,
								 "06172015",
								 "06172015", 
								 "tjsg1992@gmail.com",
								 new ArrayList<String>());
		boolean bool6 = mySchedule.receiveJob(overflowed);
		assertFalse("Light overflow failed", bool6);
		
		overflowed = new Job(myPark, 
				0, 2,
				3, 2,
				0, 2,
				"06172015",
				"06172015", 
				"tjsg1992@gmail.com",
				new ArrayList<String>());
		bool6 = mySchedule.receiveJob(overflowed);
		assertFalse("Medium overflow failed", bool6);
		

		overflowed = new Job(myPark, 
				0, 2,
				0, 2,
				3, 2,
				"06172015",
				"06172015", 
				"tjsg1992@gmail.com",
				new ArrayList<String>());
		
		bool6 = mySchedule.receiveJob(overflowed);
		assertFalse("Heavy overflow failed", bool6);
	}
	
	/**
	 * Testing with a Job with a null Park.
	 */
	@Test
	public void test8ForReceiveJob() {
		Job nullPark =  new Job(myPark, 
								0, 2,
								0, 2,
								0, 2,
								"06172015",
								"06172015", 
								"tjsg1992@gmail.com",
								null);
		boolean bool7 = mySchedule.receiveJob(nullPark);
		assertFalse(bool7);
	}
	
	//Park Manager is retrieved from park, so this test is unnecessary. TODO: Discuss removal
	/**
	 * Testing with a Job with a null ParkManager.
	 */
	@Test
	public void test9ForReceiveJob() {
		Job nullMngr =  new Job(myPark, 
								0, 2,
								0, 2,
								0, 2,
								"06172015",
								"06172015", 
								null,
								new ArrayList<String>());
		boolean bool8 = mySchedule.receiveJob(nullMngr);
		assertFalse(bool8);
	}

	/**
	 * Testing with a negative job id number.
	 */
	@Test
	public void test1ForAddVolunteerToJob() {
		boolean bool1 = false; //an error was showing up so I had to initialize this to false.
		try { //I added exceptions to addVolunteer so I put this into a try catch block.
			bool1 = mySchedule.addVolunteerToJob(myVolEmail, -10, 2);
		} catch (Exception e) {
			
			//e.printStackTrace();
		}
		assertFalse(bool1);
	}
	
	/**
	 * Testing with an invalid work grade number.
	 */
	@Test
	public void test2ForAddVolunteerToJob() {
		boolean bool2 = false; //an error was showing up so I had to initialize this to false.
		try { //I added exceptions to addVolunteer so I put this into a try catch block.
			bool2 = mySchedule.addVolunteerToJob(myVolEmail, 10, 4);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		assertFalse(bool2);
	}
	
	@After
	public void tearDown()
	{
		Job.setNextJobID(0);
	}
}
