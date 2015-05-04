package model.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.GregorianCalendar;

import model.Job;
import model.JobList;
import model.Park;
import model.ParkManager;
import model.Schedule;
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
	
	/**
	 * Volunteer object for testing.
	 */
	private Volunteer myVolunteer;
	
	/**
	 * Instantiates necessary data for testing methods.
	 */
	@Before
	public void setUp() {
		JobList myJoblist = new JobList();
		mySchedule = new Schedule(myJoblist);
		myPark = new Park("Wright Park", "Tacoma", 98403);
		myJob = new Job(myPark, 10, 10, 10, new GregorianCalendar(2015, 6, 17), 
				new GregorianCalendar(2015, 6, 17));
		myVolunteer = new Volunteer("Bob", "Smith");
	}

	/**
	 * Testing if a valid job can be received.
	 */
	@Test
	public void test1ForReceiveJob() {
		mySchedule.receiveJob(myJob);
		//TODO: that duplication definitely needs to be removed from the API
		System.out.println(mySchedule.getJobList().getJobList()); 
		assertTrue(!mySchedule.getJobList().getJobList().isEmpty());
	}
	
	/**
	 * Testing with a Job having invalid dates.
	 */
	@Test
	public void test2ForReceiveJob() {
		Job badDates = new Job(myPark, 10, 10, 10, new GregorianCalendar(2015, 6, 17), 
					   new GregorianCalendar(2015, 6, 15));
		boolean bool1 = mySchedule.receiveJob(badDates);
		assertFalse(bool1);
	}
	
	//JobID is now initialized with the help of a static int, so this test is unnecessary. TODO: Discuss removal
//	/**
//	 * Testing with a Job with a bad id number.
//	 */
//	@Test
//	public void test3ForReceiveJob() {
//		Job badID = new Job(myPark, 10, 10, 10, new Date(System.currentTimeMillis()), 
//				new Date(System.currentTimeMillis() + 10000000), new ParkManager("Jane", "Doe"));
//		boolean bool2 = mySchedule.receiveJob(badID);
//		assertFalse(bool2);
//	}
	
	/**
	 * Testing with a Job that doesn't have an empty Volunteer list.
	 */
	@Test
	public void test4ForReceiveJob() {
		myJob.myVolunteerList.add(myVolunteer);
		boolean bool3 = mySchedule.receiveJob(myJob);
		assertFalse(bool3);
	}
	
	//We weren't using the request boolean. TODO: Discuss removal
//	/**
//	 * Testing with a Job that isn't a request.
//	 */
//	@Test
//	public void test5ForReceiveJob() {
//		myJob.myIsRequest = false;
//		boolean bool4 = mySchedule.receiveJob(myJob);
//		assertFalse(bool4);
//	}
	
	/**
	 * Testing with a Job with all zeroes for light, medium, and heavy
	 * work grade amounts.
	 */
	@Test
	public void test6ForReceiveJob() {
		Job noSpace = new Job(myPark, 0, 0, 0, new GregorianCalendar(2015, 6, 17), 
					  new GregorianCalendar(2015, 6, 17));
		boolean bool5 = mySchedule.receiveJob(noSpace);
		assertFalse(bool5);
	}

	//This is prevented by an exception in the incrementMethods. And a job shouldn't have Volunteers before it is received anyway. TODO: Discuss removal.
//	/**
//	 * Testing with a Job where the number of light volunteers exceeds its max.
//	 */
//	@Test
//	public void test7ForReceiveJob() {
//		int itrs = 5;
//		Job overflowed = new Job(myPark, 2, 10, 4, new GregorianCalendar(2015, 6, 17), 
//						 new GregorianCalendar(2015, 6, 17));
//		for (int i = 0; i < itrs; i++) {
//			overflowed.incrementLight();
//		}
//		boolean bool6 = mySchedule.receiveJob(overflowed);
//		assertFalse(bool6);
//	}
	
	/**
	 * Testing with a Job with a null Park.
	 */
	@Test
	public void test8ForReceiveJob() {
		Job nullPark = new Job(null, 5, 5, 5, new GregorianCalendar(2015, 6, 17),
				new GregorianCalendar(2015, 6, 17));
		boolean bool7 = mySchedule.receiveJob(nullPark);
		assertFalse(bool7);
	}
	
	//Park Manager is retrieved from park, so this test is unnecessary. TODO: Discuss removal
//	/**
//	 * Testing with a Job with a null ParkManager.
//	 */
//	@Test
//	public void test9ForReceiveJob() {
//		Job nullMngr = new Job(myPark, 5, 5, 5, new Date(System.currentTimeMillis()),
//				new Date(System.currentTimeMillis() + 1000000), null);
//		boolean bool8 = mySchedule.receiveJob(nullMngr);
//		assertFalse(bool8);
//	}

	/**
	 * Testing with a negative job id number.
	 */
	@Test
	public void test1ForAddVolunteerToJob() {
		boolean bool1 = mySchedule.addVolunteerToJob(myVolunteer, -10, 2);
		assertFalse(bool1);
	}
	
	/**
	 * Testing with an invalid work grade number.
	 */
	@Test
	public void test2ForAddVolunteerToJob() {
		boolean bool2 = mySchedule.addVolunteerToJob(myVolunteer, 10, 4);
		assertFalse(bool2);
	}
	
	@After
	public void teardown()
	{
		Job.nextJobID = 0;
	}
}
