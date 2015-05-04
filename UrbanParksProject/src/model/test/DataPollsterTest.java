/**
 * 
 */
package model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import model.DataPollster;
import model.Job;
import model.JobList;
import model.Park;
import model.Schedule;
import model.Volunteer;

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
		
		jl = new JobList();
		List<Job> jobs = jl.getJobList();
		jobs.add(new Job(p, 5, 5, 5, 
				new GregorianCalendar(2015, 6, 15), 
				new GregorianCalendar(2015, 6, 15)));
		jobs.add(new Job(p, 5, 5, 5, 
				new GregorianCalendar(2015, 6, 16), 
				new GregorianCalendar(2015, 6, 16)));
		jobs.add(new Job(p, 5, 5, 5, 
				new GregorianCalendar(2015, 6, 17), 
				new GregorianCalendar(2015, 6, 17)));
		jobs.add(new Job(p, 5, 5, 5, 
				new GregorianCalendar(2015, 6, 18), 
				new GregorianCalendar(2015, 6, 18)));
		jobs.add(new Job(p, 5, 5, 5, 
				new GregorianCalendar(2015, 6, 19), 
				new GregorianCalendar(2015, 6, 19)));
		jobs.add(new Job(p, 5, 5, 5, 
				new GregorianCalendar(2015, 6, 20), 
				new GregorianCalendar(2015, 6, 20)));
		
		System.out.println((jl.getJobList()));
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
		assertEquals("foo", new ArrayList<Job>(), dp.getPendingJobs(vBank.get(0)));
		
		
		//TODO: So why don't we just pass the Job in here instead of the ID?
		s.addVolunteerToJob(vBank.get(0), jobs.get(0).myJobID, 1);
		s.addVolunteerToJob(vBank.get(1), jobs.get(0).myJobID, 1);
		s.addVolunteerToJob(vBank.get(2), jobs.get(0).myJobID, 1);
		s.addVolunteerToJob(vBank.get(3), jobs.get(0).myJobID, 1);
	}

	/**
	 * Test method for {@link model.DataPollster#getVolunteerJobs(model.Volunteer)}.
	 */
	@Test
	public void testGetVolunteerJobs() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.DataPollster#getManagerJobs(model.ParkManager)}.
	 */
	@Test
	public void testGetManagerJobs() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link model.DataPollster#getVolunteerList(int)}.
	 */
	@Test
	public void testGetVolunteerList() {
		fail("Not yet implemented");
	}

}
