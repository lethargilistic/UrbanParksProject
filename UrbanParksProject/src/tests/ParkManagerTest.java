package tests;

import static org.junit.Assert.fail;
import model.Job;
import model.JobList;
import model.Volunteer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParkManagerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		JobList jobList = new JobList();
		
		Volunteer volunteer1 = new Volunteer("testvolunteer1@gmail.com", "Test1", "Volunteer1");
		Volunteer volunteer2 = new Volunteer("testvolunteer2@gmail.com", "Test2", "Volunteer2");
		Volunteer volunteer3 = new Volunteer("testvolunteer3@gmail.com", "Test3", "Volunteer3");
		Volunteer volunteer4 = new Volunteer("testvolunteer4@gmail.com", "Test4", "Volunteer4");
		Volunteer volunteer5 = new Volunteer("testvolunteer5@gmail.com", "Test5", "Volunteer5");
		Volunteer volunteer6 = new Volunteer("testvolunteer6@gmail.com", "Test6", "Volunteer6");
		
		Job job1 = new Job(0, "Test Park 1", 5, 5, 5, "06012015", "06012015", "testmanager@gmail.com", null);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testParkManager() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateJob() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetManagedParks() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetManagedParks() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetJobArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetVolunteerString() {
		fail("Not yet implemented");
	}

}
