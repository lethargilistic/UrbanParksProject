package tests;

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
		myJob = new Job(myPark, 10, 10,10,
						"06172015",
						"06172015", 
						"tjsg1992@gmail.com",
						new ArrayList<ArrayList<String>>());
		
		myVolunteer = new Volunteer("generic@gmail.com", "Bob", "Smith");
	}

	/**
	 * Testing if a valid job can be received.
	 */
	@Test
	public void testForValidReceiveJob() {
		mySchedule.receiveJob(myJob);
		//TODO: that duplication definitely needs to be removed from the API
		assertFalse("No job was added.", mySchedule.getJobList().getJobList().isEmpty());
		assertEquals("The incorrect job was added.", "[Wright Park]", mySchedule.getJobList().getJobList().toString()); 
	}
	
	/**
	 * Testing with a Job having invalid dates.
	 */
	@Test
	public void testForInvalidDatesReceiveJob() {
		Job badDates = new Job(myPark, 10, 10, 10,
						"06172015",
						"06152015", 
						"tjsg1992@gmail.com",
						new ArrayList<ArrayList<String>>());
		boolean bool1 = mySchedule.receiveJob(badDates);
		assertFalse(bool1);
	}
	
	/**
	 * Testing with a Job that doesn't have an empty Volunteer list.
	 */
	@Test
	public void testForEmptyVolListReceiveJob() {
		ArrayList<String> temp = new ArrayList<>();
		temp.add(myVolEmail);
		temp.add("Light");
		ArrayList<ArrayList<String>> temp2 = new ArrayList<>();
		temp2.add(temp);
		
		myJob.getVolunteerList().add(temp);
		boolean bool3 = mySchedule.receiveJob(myJob);
		assertFalse(bool3);
	}
	
	/**
	 * Testing with a Job with all zeroes for light, medium, and heavy
	 * work grade amounts.
	 */
	@Test
	public void testForNoWorkersReceiveJob() {
		Job noSpace  =  new Job(myPark, 0, 0, 0,
								"06172015",
								"06172015", 
								"tjsg1992@gmail.com",
								new ArrayList<ArrayList<String>>());
		boolean bool5 = mySchedule.receiveJob(noSpace);
		assertFalse(bool5);
	}
	
	/**
	 * Testing with a Job with a null Park.
	 */
	@Test
	public void testForNullParkReceiveJob() {
		Job nullPark =  new Job(myPark, 2, 2, 2,
								"06172015",
								"06172015", 
								"tjsg1992@gmail.com",
								null);
		boolean bool6 = mySchedule.receiveJob(nullPark);
		assertFalse(bool6);
	}
	
	//Park Manager is retrieved from park, so this test is unnecessary. TODO: Discuss removal
	/**
	 * Testing with a Job with a null ParkManager.
	 */
	@Test
	public void testForNullParkManagerReceiveJob() {
		Job nullMngr =  new Job(myPark, 2, 2, 2,
								"06172015",
								"06172015", 
								null,
								new ArrayList<ArrayList<String>>());
		boolean bool7 = mySchedule.receiveJob(nullMngr);
		assertFalse(bool7);
	}

	/**
	 * Testing with a negative job id number.
	 */
	@Test
	public void testForAddNegJobIDVolunteerToJob() {
		boolean bool9 = false; //an error was showing up so I had to initialize this to false.
		ArrayList<String> temp = new ArrayList<>();
		temp.add("moverby@gmail.com");
		temp.add("Light");
		try { //I added exceptions to addVolunteer so I put this into a try catch block.
			bool9 = mySchedule.addVolunteerToJob(temp, -10);
		} catch (Exception e) {
			
			//e.printStackTrace();
		}
		assertFalse(bool9);
	}
	
	/**
	 * Testing with an invalid work grade number.
	 */
	@Test
	public void testForInvalidWorkGradeAddVolunteerToJob() {
		boolean bool1 = false; //an error was showing up so I had to initialize this to false.
		ArrayList<String> temp = new ArrayList<>();
		temp.add("moverby@gmail.com");
		temp.add("Light");
		try { //I added exceptions to addVolunteer so I put this into a try catch block.
			bool1 = mySchedule.addVolunteerToJob(temp, 10);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		assertFalse(bool1);
	}
	
	@After
	public void tearDown()
	{
		Job.setNextJobID(0);
	}
}
