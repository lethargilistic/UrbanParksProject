package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.DataPollster;
import model.Job;
import model.JobList;
import model.ParkManager;
import model.Schedule;
import model.UserList;
import model.Volunteer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParkManagerTest {
	
	static DataPollster myPollster = DataPollster.getInstance();
	static Schedule mySchedule = Schedule.getInstance();
	static JobList myJobList;
	static UserList myUserList;
	
	static ParkManager testManager;
	
	static Volunteer volunteer1;
	static Volunteer volunteer2;
	static Volunteer volunteer3;
	static Volunteer volunteer4;
	static Volunteer volunteer5;
	
	static ArrayList<ArrayList<String>> volunteerList;
	
	static Job job1;
	static Job job2;
	static Job job3;
	
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		myJobList = new JobList();
		myUserList = new UserList();
		
		mySchedule.setJobList(myJobList);
		mySchedule.setUserList(myUserList);
		myPollster.setJobList(myJobList);
		myPollster.setUserList(myUserList);
		
		List<String> parkList = new ArrayList<String>();
		parkList.add("Test Park 1");
		parkList.add("Test Park 2");
		parkList.add("Test Park 3");
		mySchedule.addUser("testmanager@gmail.com", "Test", "Manager", "ParkManager");
		mySchedule.updateParkList("testmanager@gmail.com", parkList);
		
		mySchedule.addUser("testvolunteer1@gmail.com", "Test1", "Volunteer1", "Volunteer");
		mySchedule.addUser("testvolunteer2@gmail.com", "Test2", "Volunteer2", "Volunteer");
		mySchedule.addUser("testvolunteer3@gmail.com", "Test3", "Volunteer3", "Volunteer");
		mySchedule.addUser("testvolunteer4@gmail.com", "Test4", "Volunteer4", "Volunteer");
		mySchedule.addUser("testvolunteer5@gmail.com", "Test5", "Volunteer5", "Volunteer");		
		volunteerList = new ArrayList<ArrayList<String>>();
		
		ArrayList<String> volunteer1Array = new ArrayList<String>();
		volunteer1Array.add("testVolunteer1@gmail.com");
		volunteer1Array.add("Light");
		
		ArrayList<String> volunteer2Array = new ArrayList<String>();
		volunteer2Array.add("testVolunteer2@gmail.com");
		volunteer2Array.add("Medium");
		
		ArrayList<String> volunteer3Array = new ArrayList<String>();
		volunteer3Array.add("testVolunteer3@gmail.com");
		volunteer3Array.add("Heavy");
		
		ArrayList<String> volunteer4Array = new ArrayList<String>();
		volunteer4Array.add("testVolunteer4@gmail.com");
		volunteer4Array.add("Heavy");
		
		job1 = new Job(0, "Test Park 1", 5, 5, 5, "06012015", "06012015", "testmanager@gmail.com", volunteerList);
		job2 = new Job(1, "Test Park 2", 5, 5, 5, "06032015", "06032015", "testmanager@gmail.com", volunteerList);
		job3 = new Job(2, "Test Park 3", 5, 5, 5, "06052015", "06052015", "testmanager@gmail.com", volunteerList);
		
		mySchedule.receiveJob(job1);
		mySchedule.receiveJob(job2);
		mySchedule.receiveJob(job3);
		
		mySchedule.addVolunteerToJob(volunteer4Array, 0);
		mySchedule.addVolunteerToJob(volunteer2Array, 0);
		mySchedule.addVolunteerToJob(volunteer1Array, 1);
		mySchedule.addVolunteerToJob(volunteer2Array, 1);
		mySchedule.addVolunteerToJob(volunteer3Array, 2);		
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
		List<String> parkList = new ArrayList<String>();
		parkList.add("Constructor Test 1");
		parkList.add("Constructor Test 2");		
		ParkManager constructorManager = new ParkManager("construct@gmail.com", "Construct", "Manager", parkList);
		
		assertEquals(constructorManager.getEmail(), "construct@gmail.com");
		assertEquals(constructorManager.getFirstName(), "Construct");
		assertEquals(constructorManager.getLastName(), "Manager");
		assertEquals(constructorManager.getManagedParks().get(0), "Constructor Test 1");
		assertEquals(constructorManager.getManagedParks().get(1), "Constructor Test 2");
	}

	@Test
	public void testGetJobs() {
		List<Job> managerJobList = testManager.getJobs();
		
		assertEquals(managerJobList.size(), 3);
		assertEquals(managerJobList.get(0).getJobID(), 0);
		assertEquals(managerJobList.get(0).getVolunteerList().get(0).get(0), "Test1");
	}

	@Test
	public void testAddJob() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNewJobID() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsManagerOfJob() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetManagedParks() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetManagedParks() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetJobVolunteerList() {
		fail("Not yet implemented");
	}

}
