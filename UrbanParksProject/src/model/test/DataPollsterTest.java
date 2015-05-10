package model.test;

import static org.junit.Assert.assertEquals;

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
 * @author Mike
 *
 */
public class DataPollsterTest {

	private Park p;
	private JobList jl;
	private UserList ul;
	private DataPollster dp;
	private Schedule s;
	private List<String> vBank;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		p = new Park("Pioneer Park", "Tacoma", 98444);
		List<Park> pList = new ArrayList<>();
		pList.add(p);
		
		vBank = new ArrayList<>();
		vBank.add("moverby@gmail.com");
		vBank.add("senorreido@gmail.com");
		vBank.add("classmate1@gmail.com");
		vBank.add("refactoreverything@gmail.com");
		vBank.add("classmate2@gmail.com");
		vBank.add("internetfail@gmail.com");
		vBank.add("classmate3@gmail.com");
		
		jl = new JobList();
		List<Job> jBank = jl.getJobList();
		jBank.add(new Job(1000, p, 
						  0, 5,
						  0, 5,
						  0, 5,
						  "06152015", 
						  "06152015", 
						  "senorreido@gmail.com", new ArrayList<>()));

		jBank.add(new Job(1001, p, 
				  		  0, 5,
				  		  0, 5,
				  		  0, 5,
				  		  "06162015", 
				  		  "06162015", 
				  		  "senorreido@gmail.com", vBank));

		jBank.add(new Job(1002, p, 
				  		  0, 5,
				  		  0, 5,
				  		  0, 5,
				  		  "06172015", 
				  		  "06172015", 
				  		  "senorreido@gmail.com", vBank));
		jBank.add(new Job(1003, p, 
					 	  0, 5,
						  0, 5,
						  0, 5,
						  "06182015", 
						  "06182015", 
						  "senorreido@gmail.com", vBank));
		jBank.add(new Job(1004, p, 
				  		  0, 5,
				  		  0, 5,
				  		  0, 5,
				  		  "06192015", 
				  		  "06192015", 
				  		  "senorreido@gmail.com", vBank));
		jBank.add(new Job(1005, p, 
				  		  0, 5,
				  		  0, 5,
				  		  0, 5,
				  		  "06202015", 
				  		  "06202015", 
				  		  "senorreido@gmail.com", vBank));
		
		dp = new DataPollster(jl, ul);
		s = new Schedule(jl);
}

	/**
	 * Test method for {@link model.DataPollster#getPendingJobs(model.Volunteer)}.
	 * @throws Exception 
	 */
	@Test
	public void testGetPendingJobs() throws Exception {
		List<Job> jobs = jl.getJobList();
		assertEquals("Empty list problem.", jobs, dp.getPendingJobs(vBank.get(0)));
		
		
		//TODO: So why don't we just pass the Job in here instead of the ID?
		s.addVolunteerToJob(vBank.get(0), jobs.get(0).getJobID(), 1);//NOTE: I added @throws exception
		s.addVolunteerToJob(vBank.get(0), jobs.get(1).getJobID(), 1);//NOTE: I added @throws exception
		s.addVolunteerToJob(vBank.get(0), jobs.get(2).getJobID(), 1);//NOTE: I added @throws exception
		s.addVolunteerToJob(vBank.get(0), jobs.get(3).getJobID(), 1);//NOTE: I added @throws exception
		
		List<Job> pendJob = new ArrayList<Job>();
		pendJob.add(jobs.get(4));
		pendJob.add(jobs.get(5));
		
		assertEquals("The List filled incorrectly.", pendJob, dp.getPendingJobs(vBank.get(0)));
	}

	/**
	 * Test method for {@link model.DataPollster#getVolunteerJobs(model.Volunteer)}.
	 * @throws Exception 
	 */
	@Test
	public void testGetVolunteerJobs() throws Exception {
		List<Job> jobs = jl.getJobList();
		assertEquals("Empty list problem.", new ArrayList<Job>(), dp.getVolunteerJobs("nobody@yahoo.com"));
		
		
		//TODO: So why don't we just pass the Job in here instead of the ID?
		s.addVolunteerToJob(vBank.get(0), jobs.get(0).getJobID(), 1); //NOTE: I added @throws exception
		s.addVolunteerToJob(vBank.get(0), jobs.get(1).getJobID(), 1);//NOTE: I added @throws exception
		s.addVolunteerToJob(vBank.get(0), jobs.get(2).getJobID(), 1);//NOTE: I added @throws exception
		s.addVolunteerToJob(vBank.get(0), jobs.get(3).getJobID(), 1);//NOTE: I added @throws exception
		
		List<Job> vsJob = new ArrayList<Job>();
		vsJob.add(jobs.get(0));
		vsJob.add(jobs.get(1));
		vsJob.add(jobs.get(2));
		vsJob.add(jobs.get(3));
		
		assertEquals("The List filled incorrectly.", vsJob, dp.getVolunteerJobs(vBank.get(0)));
	}

	/**
	 * Test method for {@link model.DataPollster#getManagerJobs(model.ParkManager)}.
	 */
	@Test
	public void testGetManagerJobs() {
		List<Park> listOfP = new ArrayList<Park>();
		listOfP.add(p);
		ParkManager pm = new ParkManager(s, dp, listOfP, "randomemail@gmail.com");

		List<Job> pmJobs = jl.getCopyList();
		assertEquals("Not all jobs in pm's park were accounted for.", pmJobs, dp.getManagerJobs(pm));
		
		Park p2 = new Park("Foolly Park", "Portland", 98232);
		List<Job> jobs = jl.getJobList();
		jobs.add(new Job(1006, p2, 
				  		 0, 5,
				  		 0, 5,
				  		 0, 5,
				  		 "06192015", 
				  		 "06192015", 
				  		 "senorreido@gmail.com", vBank));
		
		
		assertEquals("A job at another park affected the return value.", pmJobs, dp.getManagerJobs(pm));
	}

	/**
	 * Test method for {@link model.DataPollster#getVolunteerList(int)}.
	 * @throws Exception 
	 */
	@Test
	public void testGetVolunteerList() throws Exception {
		List<Job> jobs = jl.getJobList();
		assertEquals("Empty list problem.", new ArrayList<Job>(), dp.getVolunteerList(jobs.get(0).getJobID()));
		
		List<String> vList = new ArrayList<>();
		
		for (int i = 0; i < 5; i++)
		{
			s.addVolunteerToJob(vBank.get(i), jobs.get(0).getJobID(), 1); //NOTE: I added @throws exception
			vList.add(vBank.get(i));
		}
		
		assertEquals("The List filled incorrectly.", vList, dp.getVolunteerList(jobs.get(0).getJobID()));
	}
	
	@After
	public void teardown()
	{
		Job.setNextJobID(0);
	}

}
