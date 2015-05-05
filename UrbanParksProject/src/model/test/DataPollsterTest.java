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
	private DataPollster dp;
	private Schedule s;
	private List<Volunteer> vBank;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		p = new Park("Pioneer Park", "Tacoma", 98444);
		List<Park> pList = new ArrayList<>();
		pList.add(p);
		
		jl = new JobList();
		List<Job> jBank = jl.getJobList();
		jBank.add(new Job(p, 5, 5, 5, 
				new GregorianCalendar(2015, 6, 15), 
				new GregorianCalendar(2015, 6, 15), 
				new ParkManager(s, dp, pList)));
		jBank.add(new Job(p, 5, 5, 5, 
				new GregorianCalendar(2015, 6, 16), 
				new GregorianCalendar(2015, 6, 16), 
				new ParkManager(s, dp, pList)));
		jBank.add(new Job(p, 5, 5, 5, 
				new GregorianCalendar(2015, 6, 17), 
				new GregorianCalendar(2015, 6, 17),
				new ParkManager(s, dp, pList)));
		jBank.add(new Job(p, 5, 5, 5, 
				new GregorianCalendar(2015, 6, 18), 
				new GregorianCalendar(2015, 6, 18), 
				new ParkManager(s, dp, pList)));
		jBank.add(new Job(p, 5, 5, 5, 
				new GregorianCalendar(2015, 6, 19), 
				new GregorianCalendar(2015, 6, 19),
				new ParkManager(s, dp, pList)));
		jBank.add(new Job(p, 5, 5, 5, 
				new GregorianCalendar(2015, 6, 20), 
				new GregorianCalendar(2015, 6, 20), 
				new ParkManager(s, dp, pList)));
		
		dp = new DataPollster(jl);
		s = new Schedule(jl);
		vBank = new ArrayList<>();
		vBank.add(new Volunteer("Mike", "Overby"));
		vBank.add(new Volunteer("Reid", "Thompson"));
		vBank.add(new Volunteer("Ariel", "McNamera"));
		vBank.add(new Volunteer("Taylor", "Gorman"));
		vBank.add(new Volunteer("Jasmine", "Pederson"));
		vBank.add(new Volunteer("Arshdeep", "Singh"));
		vBank.add(new Volunteer("Samantha", "Kuk"));
}

	/**
	 * Test method for {@link model.DataPollster#getPendingJobs(model.Volunteer)}.
	 */
	@Test
	public void testGetPendingJobs() {
		List<Job> jobs = jl.getJobList();
		assertEquals("Empty list problem.", jobs, dp.getPendingJobs(vBank.get(0)));
		
		
		//TODO: So why don't we just pass the Job in here instead of the ID?
		s.addVolunteerToJob(vBank.get(0), jobs.get(0).myJobID, 1);
		s.addVolunteerToJob(vBank.get(0), jobs.get(1).myJobID, 1);
		s.addVolunteerToJob(vBank.get(0), jobs.get(2).myJobID, 1);
		s.addVolunteerToJob(vBank.get(0), jobs.get(3).myJobID, 1);
		
		List<Job> pendJob = new ArrayList<Job>();
		pendJob.add(jobs.get(4));
		pendJob.add(jobs.get(5));
		
		assertEquals("The List filled incorrectly.", pendJob, dp.getPendingJobs(vBank.get(0)));
	}

	/**
	 * Test method for {@link model.DataPollster#getVolunteerJobs(model.Volunteer)}.
	 */
	@Test
	public void testGetVolunteerJobs() {
		List<Job> jobs = jl.getJobList();
		assertEquals("Empty list problem.", new ArrayList<Job>(), dp.getVolunteerJobs(vBank.get(0)));
		
		
		//TODO: So why don't we just pass the Job in here instead of the ID?
		s.addVolunteerToJob(vBank.get(0), jobs.get(0).myJobID, 1);
		s.addVolunteerToJob(vBank.get(0), jobs.get(1).myJobID, 1);
		s.addVolunteerToJob(vBank.get(0), jobs.get(2).myJobID, 1);
		s.addVolunteerToJob(vBank.get(0), jobs.get(3).myJobID, 1);
		
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
		ParkManager pm = new ParkManager(s, dp, listOfP);

		List<Job> pmJobs = jl.getCopyList();
		assertEquals("Not all jobs in pm's park were accounted for.", pmJobs, dp.getManagerJobs(pm));
		
		Park p2 = new Park("Foolly Park", "Portland", 98232);
		List<Job> jobs = jl.getJobList();
		jobs.add(new Job(p2, 5, 5, 5, 
				new GregorianCalendar(2015, 6, 15), 
				new GregorianCalendar(2015, 6, 15),
				pm));
		
		
		assertEquals("A job at another park affected the return value.", pmJobs, dp.getManagerJobs(pm));
	}

	/**
	 * Test method for {@link model.DataPollster#getVolunteerList(int)}.
	 */
	@Test
	public void testGetVolunteerList() {
		List<Job> jobs = jl.getJobList();
		assertEquals("Empty list problem.", new ArrayList<Job>(), dp.getVolunteerList(jobs.get(0).myJobID));
		
		List<Volunteer> vList = new ArrayList<>();
		
		for (int i = 0; i <5; i++)
		{
			s.addVolunteerToJob(vBank.get(i), jobs.get(0).myJobID, 1);
			vList.add(vBank.get(i));
		}
		
		assertEquals("The List filled incorrectly.", vList, dp.getVolunteerList(jobs.get(0).myJobID));
	}
	
	@After
	public void teardown()
	{
		Job.nextJobID = 0;
	}

}
