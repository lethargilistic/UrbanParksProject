package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import model.DataPollster;
import model.Job;
import model.JobList;
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
		mySchedule = Schedule.getInstance();
		
		JobList myJoblist = new JobList();
		UserList myUserList = new UserList();
		List<String> pList = new ArrayList<>();
		pList.add("Foo Park");
	    myParkManager = new ParkManager("tjsg1992@gmail.com", "Taylor", "Gorman", pList);
		myJob = new Job(55, "Foo Park", 10, 10,10,
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
		Job badDates = new Job(0, "Foo Park", 10, 10, 10,
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
		Job noSpace  =  new Job(55, "Foo Park", 0, 0, 0,
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
		Job nullPark =  new Job(55, "Foo Park", 2, 2, 2,
								"06172015",
								"06172015", 
								"tjsg1992@gmail.com",
								null);
		boolean bool6 = mySchedule.receiveJob(nullPark);
		assertFalse(bool6);
	}
	
	/**
	 * Testing with a Job with a null ParkManager.
	 */
	@Test
	public void testForNullParkManagerReceiveJob() {
		Job nullMngr =  new Job(55, "Foo Park", 2, 2, 2,
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
		boolean bool1 = true;
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
	
	// Reid: removed this method call b/c I didn't know if it was necessary.
	@After
	public void tearDown()
	{
//		Job.setNextJobID(0);
	}
}
